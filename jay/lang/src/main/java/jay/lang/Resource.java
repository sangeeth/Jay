package jay.lang;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Resource {
    private static final Logger logger = Logger.getLogger(Resource.class.getName());
    
    public static Resource valueOf(String resourceBundleSource) {
        return ResourceManager.getInstance().getResource(resourceBundleSource, null, null);
    }

    public static Resource valueOf(String resourceBundleSource, Locale locale) {
        return ResourceManager.getInstance().getResource(resourceBundleSource, locale, null);
    }
    
    private ClassLoader bundleLoader;
    private Locale locale;
    private String resourceBundleSource;
    private ResourceBundle resourceBundle;

    protected Resource() {
        this(null, null, null, null);
    }
    protected Resource(String resourceBundleSource, ClassLoader bundleLoader, Locale locale, ResourceBundle resourceBundle) {
        super();
        this.resourceBundleSource = resourceBundleSource;
        this.bundleLoader = (bundleLoader==null) 
                            ? (resourceBundle == null 
                                ? null 
                                :resourceBundle.getClass().getClassLoader())
                            : bundleLoader;
        this.locale = locale==null?Locale.getDefault():locale;
        this.resourceBundle = resourceBundle;
    }
    
    public ClassLoader getBundleLoader() {
        return bundleLoader;
    }

    public Locale getLocale() {
        return locale;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
    
    public String getString(Locale locale, String key, String defaultValue, Object... args) {
        String value = null;
        
        if (this.resourceBundleSource!=null) {
            try {
                ResourceBundle resourceBundle = this.resourceBundle;
                if (resourceBundle == null 
                    || locale==null 
                    || !locale.equals(this.resourceBundle.getLocale())) {
                    // if locale is null or the locale is not the same as the primary resource bundle.
                    ResourceManager resourceManager = ResourceManager.getInstance();
                    resourceBundle = resourceManager.getResourceBundle(this.resourceBundleSource, locale, this.bundleLoader);
                }
                
                value = resourceBundle.getString(key);
            } catch(MissingResourceException e) {
                logger.log(Level.WARNING, String.format("The given resource bundle %s is not found. [Locale: %s, ClassLoader: %s]", resourceBundleSource, locale, bundleLoader), e);
                
                value = defaultValue;
            }
        } else {
            value = defaultValue;
        }
        
        if (value!=null && args!=null && args.length > 0) {
            // TODO Need to make the formatting logic extensible/customizable to leverage latest technologies.
            value = MessageFormat.format(value, args);
        }
        
        return value;
    }    
    
    public String getString(String key, String defaultValue, Object... args) {
        return getString(this.locale, key, defaultValue, args);
    }
    
    public <A extends Number> A getNumber(Class<A> expectedType, String key, A defaultValue) {
        A number = null;
        try {
            String value = getString(key, defaultValue.toString());
            Method valueOfMethod = expectedType.getMethod("valueOf", String.class);
            number = (A)valueOfMethod.invoke(null, value);
        } catch(Exception e) {
            // TODO Need to log this exception
            number = defaultValue;
        }
        return number;
    }
}
