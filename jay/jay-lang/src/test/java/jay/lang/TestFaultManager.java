package jay.lang;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

public class TestFaultManager {
    @Test
    public void test_getDefaultFaultMessageProvider() {
        FaultManager faultManager = FaultManager.getInstance();
        IFaultMessageProvider actualDefaultFaultMessageProvider = faultManager.getDefaultFaultMessageProvider();
        
        Assert.assertNotNull(actualDefaultFaultMessageProvider);
        Assert.assertTrue(actualDefaultFaultMessageProvider instanceof FaultMessageProvider);
    }
    
    @Test
    public void test_set_get_defaultFaultMessageProvider() {
        FaultManager faultManager = FaultManager.getInstance();
        
        IFaultMessageProvider oldValue = faultManager.getDefaultFaultMessageProvider();
        
        IFaultMessageProvider newValue = new IFaultMessageProvider() {
            @Override
            public IFaultMessage getMessage(Fault fault, Locale locale,
                    Object... messageArgs) {
                // TODO Auto-generated method stub
                return null;
            }
        };
        
        faultManager.setDefaultFaultMessageProvider(newValue);
        IFaultMessageProvider actualDefaultFaultMessageProvider = faultManager.getDefaultFaultMessageProvider();
        
        Assert.assertNotNull(actualDefaultFaultMessageProvider);
        Assert.assertEquals(newValue, actualDefaultFaultMessageProvider);
        
        // reset the old value
        faultManager.setDefaultFaultMessageProvider(oldValue);
    }
}
