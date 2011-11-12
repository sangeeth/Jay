package jay.lang;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResourceManager {
    private static final Logger logger = Logger.getLogger(ResourceManager.class.getName());
    
    private static ResourceManager instance;
    public static ResourceManager getInstance() {
        // TODO Use proxy factory or Spring 
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }
    
    private WeakHashMap<String, Resource> resourceCache;
    protected ResourceManager() {
        this.resourceCache = new WeakHashMap<String, Resource>();
    }
    
    protected ResourceBundle getResourceBundle(String resourceBundleSource, 
                Locale locale, 
                ClassLoader classLoader) throws MissingResourceException{
        ResourceBundle resourceBundle = null;
        
        if (classLoader!=null && locale!=null) {
            resourceBundle = ResourceBundle.getBundle(resourceBundleSource, locale, classLoader);
        } else if (classLoader!=null) {
            resourceBundle = ResourceBundle.getBundle(resourceBundleSource, Locale.getDefault(), classLoader);   
        } else if (locale!=null){
            resourceBundle = ResourceBundle.getBundle(resourceBundleSource, locale);
        } else {
            resourceBundle = ResourceBundle.getBundle(resourceBundleSource);
        }
        
        return resourceBundle;
    }
    
    public Resource getResource(String resourceBundleSource, Locale locale, ClassLoader classLoader) {
        Resource resource = null;
        if (resourceBundleSource!=null) {
            StringBuffer keyBuffer = new StringBuffer();
            keyBuffer.append(resourceBundleSource);
            if (locale!=null) {
                keyBuffer.append('.').append(locale.toString());
            } 
            
            if (classLoader!=null) {
                keyBuffer.append('.').append(classLoader.getClass().getName());
            }
            
            String key = keyBuffer.toString();
            
            resource = this.resourceCache.get(key);
            
            if (resource==null) {
                ResourceBundle resourceBundle = null;
                
                try {
                    resourceBundle = getResourceBundle(resourceBundleSource, locale, classLoader);
                } catch (MissingResourceException e) {
                    logger.log(Level.WARNING, String.format("The given resource bundle %s is not found. [Locale: %s, ClassLoader: %s]", resourceBundleSource, locale, classLoader), e);
                }
                
                resource = new Resource(resourceBundleSource, classLoader, locale, resourceBundle);
                resourceCache.put(key, resource);
            }
        } else {
            resource = new Resource();
        }
        
        return resource;
    }
    
    public void clearCache() {
        this.resourceCache.clear();
    }
}
