package jay.lang;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import jay.lang.annotation.CategoryId;
import jay.lang.annotation.FaultCodeDef;
import jay.lang.annotation.FaultMessageProviderDef;
import jay.lang.annotation.ResourceBundleSource;

/**
 * This class represents the complete definition about a fault code.
 * 
 * A typical usage of this API is as shown below
 * <pre>
 * FaultDefinition faultDefinition = FaultDefinition.valueOf(MyAppFaultCode.SOME_FAILURE);
 * // Use the fault definition. 
 * </pre>
 *
 * This class is primarily used by {@link Fault} API, which in turn used by {@link CheckedException} and
 * {@link UncheckedException}.
 * 
 * The fault definitions are cached locally using java.util.WeakHashMap. 
 * 
 * @author sangkuma
 * @version 1.0.0
 * 
 * @see IFaultCode
 * @see IFaultMessageProvider
 * @see Fault
 */
public class FaultDefinition {
    private static Map<IFaultCode, FaultDefinition> cache = new WeakHashMap<IFaultCode, FaultDefinition>();
    private static Lock lock = new ReentrantLock();
    
    /**
     * To get the fault definition instance of the given faultCode. 
     * 
     * @param faultCode The fault definition instance of the given faultCode.
     * @return The fault definition instance of the given faultCode.
     */
    public static FaultDefinition valueOf(IFaultCode faultCode) {
        FaultDefinition value = null;

        if (faultCode != null) {
            // Using Reentrant lock to avoid deadlock.
            // Entering into critical section after 1 minute of wait.
            try {
                lock.tryLock(1, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                // TODO Log this event.
            }
            
            value = cache.get(faultCode);
            if (value == null) {
                // TODO Improve this code.
                value = new FaultDefinition(faultCode);
                
                cache.put(faultCode, value);
            }
            
            lock.unlock();
        }

        return value;
    }

    /**
     * The fault code for which this instance contain the definition details.
     */
    private IFaultCode faultCode;
    /**
     * The severity of the fault code
     */
    private ISeverity severity;
    /**
     * The category identifier of the referring fault code.
     */
    private String categoryId;
    /**
     * The fault identifier.
     */
    private String id;
    /**
     * The name of the resource bundle.
     */
    private String resourceBundleSource;
    /**
     * The fully-qualified name of the fault code.
     * @see FaultCode#FQN(IFaultCode)
     */
    private String FQN;
    /**
     * The fault message provider associated to the fault code
     */
    private IFaultMessageProvider faultMessageProvider;

    /**
     * Constructs a new fault definition instance for the given faultCode.
     * 
     * @param faultCode The fault code for which the fault definition need to be created.
     */
    protected FaultDefinition(IFaultCode faultCode) {
        this.faultCode = faultCode;

        this.init();
    }

    /**
     * To extract details about the given fault code and initialize
     * this instance.
     */
    protected void init() {
        Class<? extends IFaultCode> type = faultCode.getClass();

        this.FQN = FaultCode.FQN(faultCode);

        CategoryId categoryId = type.getAnnotation(CategoryId.class);

        if (categoryId != null) {
            this.categoryId = categoryId.value();
        }

        Field faultCodeField = null;
        try {
            // Assume the fault name and the field name are same.
            // as this is the recommended practice.
            faultCodeField = type.getField(faultCode.name());
        } catch (NoSuchFieldException e) {
            // Probably the fault code name and the field name doesn't match
            // hence iterate through all the static fields to identify field
            
            Field [] fields = type.getFields();
            for(Field f:fields) {
                int modifiers = f.getModifiers();
                if (Modifier.isStatic(modifiers) 
                    && Modifier.isPublic(modifiers)) {
                    try {
                        Object value = f.get(null);
                        if (value == faultCode) {
                            faultCodeField = f;
                            break;
                        }
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
        
        FaultCodeDef faultCodeDef = faultCodeField.getAnnotation(FaultCodeDef.class);
        if (faultCodeDef != null) {
            this.id = faultCodeDef.id();
            this.severity = faultCodeDef.severity();
        } else {
            this.id = null;
            this.severity = Severity.UNKNOWN;
        }        

        ResourceBundleSource rb = type.getAnnotation(ResourceBundleSource.class);
        if (rb != null) {
            this.resourceBundleSource = rb.value();
        }

        FaultMessageProviderDef providerDef = type.getAnnotation(FaultMessageProviderDef.class);
        if (providerDef != null) {
            try {
                Class<? extends IFaultMessageProvider> providerClass = providerDef.value();
                this.faultMessageProvider = providerClass.newInstance();
            } catch (Exception e) {
                // TODO Handle this exception appropriately
                throw new RuntimeException(e);
            }
        } else {
            this.faultMessageProvider = FaultManager.getInstance().getFaultMessageProvider(type);
        }

    }

    /**
     * To get a new instance of {@link Fault} from this fault definition.
     * 
     * It is guaranteed that this method will return a non-null value.
     * 
     * @param messageArgs The arguments required for formatting the message fetched from the resource bundle
     *                    associated to the fault code whose definition is maintained by this instance.
     * @return A new instance of {@link Fault} from this fault definition.
     */
    public Fault newInstance(Object... messageArgs) {
        return newInstance(null, messageArgs);
    }
    
    /**
     * To get a new instance of {@link Fault} from this fault definition, with the messages
     * fetched for a specific locale.
     * 
     * It is guaranteed that this method will return a non-null value.
     *  
     * @param locale The locale in which the fault message need to be fetched and formatted.
     * @param messageArgs The arguments required for formatting the message fetched from the resource bundle
     *                    associated to the fault code whoe definition is maintained by this instance.
     * @return A new instance of {@link Fault} from this fault definition.
     */
    public Fault newInstance(Locale locale, Object... messageArgs) {
        Fault fault = new Fault();

        fault.setFaultCode(this.getFaultCode());
        fault.setFaultDefinition(this);
        fault.setId(this.getId());
        fault.setCategoryId(this.getCategoryId());
        fault.setSeverity(this.getSeverity());
        fault.setMessageArgs((messageArgs.length==0)?null:messageArgs);

        fault.setLocale(locale == null ? Locale.getDefault() : locale);

        IFaultMessageProvider provider = this.getFaultMessageProvider();
        if (provider!=null) {
            IFaultMessage faultMessage = provider.getMessage(fault, locale, messageArgs);
            fault.setFaultMessage(faultMessage);
            fault.setSummary(faultMessage.getSummary());
        }

        return fault;
    }

    /**
     * To get the fault code for which this object contains the definition.
     * 
     * @return The fault code.
     */
    public IFaultCode getFaultCode() {
        return faultCode;
    }

    /**
     * To get the severity of the fault.
     * 
     * The value returned by this method will be null, if the severity 
     * is not associated to the fault code using {@link FaultCodeDef}.
     * 
     * @return The severity of the fault.
     */
    public ISeverity getSeverity() {
        return severity;
    }

    /**
     * To get the category identifier associated to the fault code.
     * 
     * The value returned by this method will be null, if the category identifier
     * is not associated to the fault code using {@link CategoryId}.
     * 
     * @return The category identifier associated to the fault code.
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * To get the fault identifier.
     * 
     * The value returned by this method will be null, if the identifier 
     * is not associated to the fault code using {@link FaultCodeDef}.
     * 
     * @return The fault identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * To get the associated resource bundle name.
     * 
     * The value returned by this method will be null, if the resource bundle name 
     * is not associated to the fault code using {@link ResourceBundleSource}.
     * 
     * @return The associated resource bundle name.
     */
    public String getResourceBundleSource() {
        return resourceBundleSource;
    }

    /**
     * To get the fully-qualified name.
     * 
     * The value returned by this method is guaranteed to be non-null.
     * 
     * @return The fully-qualified name.
     */
    public String getFQN() {
        return FQN;
    }

    /**
     * To get the fault message provider.
     * 
     * The value returned by this method is guaranteed to be non-null.
     * 
     * @return The fault message provider.
     */
    public IFaultMessageProvider getFaultMessageProvider() {
        return faultMessageProvider;
    }
}
