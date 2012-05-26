package jay.lang;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This abstract class represents a basic implementation of {@link IFaultMessageProvider} interface.
 * 
 * @author sangeeth
 * @version 1.0.0
 */
abstract public class AbstractFaultMessageProvider implements IFaultMessageProvider {
    private static final Logger logger = Logger.getLogger(AbstractFaultMessageProvider.class.getName());
    
    /**
     * To load the resource bundle associated to the fault code.  
     * 
     * @param fault The fault representing the fault code.
     * @param locale The locale in which the fault resource bundle need to be loaded.
     * @return the resource bundle associated to the fault code.
     */
    protected Resource getResource(Fault fault, Locale locale) {
        FaultDefinition faultDefinition = fault.getFaultDefinition();
        String resourceBundleSource = faultDefinition.getResourceBundleSource();
        
        logger.log(Level.FINE, "Loading resource for bundle {0}", resourceBundleSource);
        
        Resource resource =  Resource.valueOf(resourceBundleSource, locale);
        return resource;
    }
}
