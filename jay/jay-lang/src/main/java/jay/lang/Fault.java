package jay.lang;

import java.io.Serializable;
import java.util.Locale;

import jay.lang.annotation.CategoryId;
import jay.lang.annotation.FaultCodeDef;

/**
 * This class represents the details associated to any {@link IFaultCode fault code}.
 * 
 * A typical usage of this API is as shown below
 * <pre>
 *    Fault fault = Fault.valueOf(MyFaultCode.SOME_FAILURE,"arg1", "arg2");
 *    
 *    IFaultMessage faultMessage = fault.getFaultMessage();
 *    System.err.printf("Error: %s\n", faultMessage.getSummary());
 *    
 *    // release the fault instance to help garbage collector
 *    fault.purge();
 * </pre>
 * 
 * This class is mostly used by {@link CheckedException} and {@link UncheckedException}. For most practical
 * use cases, a developer do not need this API directly. However, in scenarios where there is a requirement
 * to fetch the fault message in a different language than the one been fetched, then this API comes quite 
 * handy. The following example shows how to achieve the same.
 * 
 * <pre>
 * File srcFile = new File("/home/corvid/");
 * File destFile = new File("/home/corvid/hello.txt");
 * 
 * try {
 *     FileCopyAPI.copy(srcFile, destFile, true);
 * } catch(FileCopyAPIException e) {
 *     // Let us assume the original fault message was in English.
 *     // Now get the equivalent message in German.
 *     Fault fault = e.getFault();
 *     IFaultMessage message = fault.getFaultMessage(Locale.GERMAN);
 *     // display the message in German.
 * }
 * </pre>
 * 
 * An instance of this class is serializable. It is possible that serialization fail, if the message arguments
 * maintained by this instance are not serializable.
 * 
 * This class implements {@link IPurgable}, denoting that once the calling routine is done with 
 * using the fault, it is recommended to invoke {@link #purge()} method. This helps garbage collection.
 * 
 * @author sangeeth
 * @version 1.0.0
 */
public class Fault implements Serializable, IPurgable {
    /**
     * To get the fault instance for a given faultCode.
     * 
     * If the faultCode is null then the value returned will be null as well. Otherwise, it
     * guaranteed to return a non-null value.
     * 
     * The default locale (with respect to the Java Virutal Machine) will be used for fetching the 
     * message details.
     * 
     * @param faultCode The fault code for which the details are required.
     * @param messageArgs The arguments to be used while formatting the fault message.
     * 
     * @return The fault instance for a given faultCode.
     */
    public static Fault valueOf(IFaultCode faultCode, Object... messageArgs) {
        return valueOf(faultCode, null, messageArgs);
    }

    /**
     * To get the fault instance for a given faultCode and locale.
     * 
     * If the faultCode is null then the value returned will be null as well. Otherwise, it
     * guaranteed to return a non-null value.
     * 
     * If the locale is null, then the default locale (with respect to the Java Virutal Machine) will be used
     * for fetching the message details.
     * 
     * @param faultCode The fault code for which the details are required.
     * @param locale The locale in which the message details are to be fetched.
     * @param messageArgs The arguments to be used while formatting the fault message.
     * 
     * @return The fault instance for a given faultCode.
     */
    public static Fault valueOf(IFaultCode faultCode, Locale locale, Object... messageArgs) {
        Fault fault = null;

        if (faultCode != null) {
            FaultDefinition faultDefinition = FaultDefinition.valueOf(faultCode);
            fault = faultDefinition.newInstance(locale, messageArgs);
        }

        return fault;
    }

    private static final long serialVersionUID = 1L;

    /**
     * The fault code for which this instance contains the details.
     */
    private IFaultCode faultCode;
    /**
     * The arguments required for formatting the fault message fetched from the associated resource bundle.
     */
    private Object[] messageArgs;
    /**
     * The severity associated to the faultCode using {@link FaultCodeDef} annotation.
     * But it can be modified at runtime.
     */
    private ISeverity severity;
    /**
     * The category identifier for the referring faultCode. 
     * 
     * @see CategoryId
     */
    private String categoryId;
    /**
     * A short description about the faultCode.
     * 
     * This by default is same as value returned by {@link IFaultMessage#getSummary()}.
     */
    private String summary;
    /**
     * The locale in which the message as been fetched.
     */
    private Locale locale;
    /**
     * The fault identifier. This could be supplemented by {@link #categoryId}.
     */
    private String id;
    /**
     * The message associated to the given {@link #faultCode}.
     */
    private IFaultMessage faultMessage;
    /**
     * The definition of the given {@link #faultCode}.
     */
    private FaultDefinition faultDefinition;
    /**
     * A simple name of the given {@link #faultCode}.
     * For example: CIC-10000, ORA-12514
     */
    private String simpleName;

    /**
     * Constructs an instance of fault.
     */
    protected Fault() {
    }
    /**
     * To get the message based on a given locale.
     * 
     * If the locale is null, then this method returns the value returned by {@link #getFaultMessage()} method.
     * If messageArgs is null or empty, the original messageArgs which was provided to this instance on creation
     * will be used.
     * 
     * @param locale The locale in which the faultMessage need to be
     * @param messageArgs The arguments required for formatting the message fetched from the associated resource bundle.
     * @return The message  based on the given locale.
     */
    public IFaultMessage getFaultMessage(Locale locale, Object...messageArgs) {
        IFaultMessage faultMessage = null;
        
        if (this.faultDefinition!=null && locale!=null) {
            if (messageArgs==null || messageArgs.length == 0) {
                // If the user doesn't provide the message args
                // use the original ones, if exists.
                messageArgs = this.messageArgs;
            }
            
            IFaultMessageProvider provider = this.faultDefinition.getFaultMessageProvider();
            faultMessage = provider.getMessage(this, locale, messageArgs);
        } else {
            faultMessage = this.faultMessage;
        }
        
        return faultMessage;
    }    

    /**
     * To get the original fault message.
     * 
     * @return The original fault message.
     */
    public IFaultMessage getFaultMessage() {
        return faultMessage;
    }

    /**
     * To set a new fault message. 
     * 
     * Though it is not recommended to modify the original fault definition, 
     * this flexibility might be useful under certain circumstances specific to 
     * the host application or software. It is recommended to take enough care
     * while using this API.
     * 
     * @param faultMessage The new fault message to be used as replacement for the original fault message. 
     */
    public void setFaultMessage(IFaultMessage faultMessage) {
        this.faultMessage = faultMessage;
    }

    /**
     * To get the actual definition of the fault code.
     * 
     * @return The actual definition of the fault code.
     */
    public FaultDefinition getFaultDefinition() {
        return faultDefinition;
    }

    /**
     * To set the fault definition. 
     * 
     * Though it is not recommended to modify the original fault definition, 
     * this flexibility might be useful under certain circumstances specific to 
     * the host application or software. It is recommended to take enough care
     * while using this API.
     * 
     * @param faultDefinition The fault definition to used be used as replacement for the original definition.
     */
    public void setFaultDefinition(FaultDefinition faultDefinition) {
        this.faultDefinition = faultDefinition;
    }

    /**
     * To get the fault identifier.
     * 
     * @return The fault identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * To set the fault identifier.
     * 
     * Though it is not recommended to modify the original fault definition, 
     * this flexibility might be useful under certain circumstances specific to 
     * the host application or software. It is recommended to take enough care
     * while using this API.
     * 
     * @param id The fault identifier.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * To get the full-qualified name of the fault code.
     * 
     * If the fault definition is null, thie method will return null.
     * 
     * @return The fully-qualified name of the fault code.
     * @see FaultCode#FQN(IFaultCode)
     */
    public String getFQN() {
        return this.faultDefinition!=null?this.faultDefinition.getFQN():null;
    }

    /**
     * To get the fault code.
     * 
     * @return The fault code.
     */
    public IFaultCode getFaultCode() {
        return faultCode;
    }

    /**
     * To set the fault code. 
     * 
     * Though it is not recommended to modify the original fault definition, 
     * this flexibility might be useful under certain circumstances specific to 
     * the host application or software. It is recommended to take enough care
     * while using this API.
     * 
     * @param faultCode
     */
    protected void setFaultCode(IFaultCode faultCode) {
        this.faultCode = faultCode;
    }

    /**
     * To get the arguments used/to be used for formatting the fault message 
     * fetched from the associated resource bundle.
     * 
     * @return The message arguments.
     */
    public Object[] getMessageArgs() {
        return messageArgs;
    }

    /**
     * To set the arguments to be used for forammting the fault message
     * fetched from the associated resource bundle.
     * 
     * @param messageArgs The message arguments.
     */
    protected void setMessageArgs(Object[] messageArgs) {
        this.messageArgs = messageArgs;
    }

    /**
     * The severity of the fault.
     * 
     * @return The severity of the fault.
     */
    public ISeverity getSeverity() {
        return severity;
    }

    /**
     * To set the severity of the fault. 
     * 
     * It is safe to change this value, under any circumstance. It is 
     * recommended not to set null as the severity. Instead, use {@link Severity#UNKNOWN}.
     * 
     * @param severity The severity of the fault.
     */
    public void setSeverity(ISeverity severity) {
        this.severity = severity;
    }

    /**
     * To get the category identifier. 
     * 
     * @return The category identifier. 
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * To set the category identifier. 
     * 
     * It is recommended that the category is simple and easy to understand.
     * A typical category id should be 3 to 5 characters. 
     * For example: 
     * <ul>
     *   <li>CSO - Referring Cisco</li>
     *   <li>CRE - Referring Connected Real Estate</li>
     *   <li>CSDP - Referring Cisco Service Delivery Platform</li>
     * </ul>
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * To get the fault message summary.
     * 
     * @return The fault message summary.
     */
    public String getSummary() {
        return summary;
    }

    /**
     * To set the fault message summary.
     * 
     * It is recommended not to specify null as the summary. The
     * value returned by this method is used by {@link CheckedException}
     * and {@link UncheckedException}.
     * 
     * @param summary The fault message summary.
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * To get the locale in which the current fault message is based on.
     * 
     * @return The locale in which the current fault message is based on.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * To set the locale in which the current fault message is based on.
     * 
     * Though it is not recommended to modify this property, enough care should
     * be taken to modify this value. For example, the calling routine should
     * ensure that the locale in which the message has been fetched and formatted
     * is same the one being set through this method.
     * 
     * @param locale The new locale to be used as replacement.
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * To get the simple name.
     * 
     * if (categoryId and Id are not null) {
     *   simple name is {categoryId}-{id}
     * } else if (categoryId is null and id is not not null) {
     *   simple name is {id}
     * } else if (categoryId is not null and id is null) {
     *   simple name is {categoryId}
     * }
     *  
     * @return The simple name.
     */
    public String getSimpleName() {
        if (this.simpleName==null) {
            if (categoryId!=null && id!=null) {
                this.simpleName = String.format("%s-%s", categoryId, id);    
            } else if (categoryId!=null) { // && id == null
                this.simpleName = categoryId;
            } else { // id != null && categoryId == null
                this.simpleName = id;
            }
        }
        return simpleName;
    }

    /**
     * To set the simple name.
     * 
     * @param simpleName The simple name to be used as replacement for the existing one.
     */
    protected void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    // Implement IPurgable interface
    /**
     * To reset all the attributes of this instance to null, to assist
     * garbage collector.
     */    
    @Override
    public void purge() {
        this.categoryId = null;
        this.id = null;
        this.summary = null;
        this.simpleName = null;
        this.messageArgs = null;
        this.faultCode = null;
        this.faultMessage = null;
        this.faultDefinition = null;
        this.severity = null;
        this.locale = null;
    }
}
