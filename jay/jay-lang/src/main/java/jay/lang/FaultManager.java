package jay.lang;

/**
 * This class helps to manage the fault handling system.  
 * 
 * <b>WARNING:</b> Presently, this class is in its most primitive stage, hence not recommended for use.
 *  
 * @author sangeeth
 * @version 1.0.0
 */
public class FaultManager {
    private static FaultManager instance = null;

    // TODO Use ProxyFactory to create instance
    public static FaultManager getInstance() {
        if (instance == null) {
            instance = new FaultManager();
        }
        return instance;
    }

    private IFaultMessageProvider defaultFaultMessageProvider;

    protected FaultManager() {
        super();
        this.defaultFaultMessageProvider = new FaultMessageProvider();
    }

    public IFaultMessageProvider getDefaultFaultMessageProvider() {
        return defaultFaultMessageProvider;
    }

    public void setDefaultFaultMessageProvider(IFaultMessageProvider defaultFaultMessageProvider) {
        this.defaultFaultMessageProvider = defaultFaultMessageProvider;
    }

    public IFaultMessageProvider getFaultMessageProvider(Class<? extends IFaultCode> faultCodeType) {
        // TODO Use configuration or external mapping to find the appropriate FaultMessageProvider type.
        return defaultFaultMessageProvider;
    }
}
