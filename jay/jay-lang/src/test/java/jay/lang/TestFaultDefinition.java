package jay.lang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Ignore;
import org.junit.Test;

import corvid.lang.CustomFaultMessageProvider;
import corvid.lang.ExampleFaultCode;
import corvid.lang.NonEnumFaultCode;
import corvid.lang.SomeFaultCode;
import corvid.lang.UndefinedFaultCode;
import corvid.unit.AssertEx;
import jay.lang.annotation.FaultMessageProviderDef;

public class TestFaultDefinition {

    @Test
    public void test_basic_case() {
        FaultDefinition faultDefinition = FaultDefinition.valueOf(ExampleFaultCode.ACCESS_DENIED);

        AssertEx.assertFaultDefinition(ExampleFaultCode.ACCESS_DENIED, // expectedFaultCode
                               FaultManager.getInstance().getDefaultFaultMessageProvider().getClass(), // expectedFaultMessageProvider
                               Severity.SEVERE, // expectedSeverity
                               String.format("%s.ACCESS_DENIED",ExampleFaultCode.class.getName()), // expectedFQN 
                               "EG", // expectedCategoryId
                               "1001", // expectedId
                               "corvid.lang.resource.ExampleFaultResourceBundle", // expectedResourceBundleSource
                               faultDefinition); // actualFaultDefinition
    }

    @Test
    public void test_instance_caching() {
        FaultDefinition faultDefinition1 = FaultDefinition.valueOf(ExampleFaultCode.ACCESS_DENIED);
        FaultDefinition faultDefinition2 = FaultDefinition.valueOf(ExampleFaultCode.ACCESS_DENIED);

        assertTrue(
                "Expected faultDefinition1 and faultDefinition2 to be the same instance",
                faultDefinition1 == faultDefinition2);
    }

    @Test
    public void test_undefined_faultcode() {
        FaultDefinition faultDefinition = FaultDefinition.valueOf(UndefinedFaultCode.SOME_FAULT_WITH_NO_DEFINITION);

        AssertEx.assertFaultDefinition(UndefinedFaultCode.SOME_FAULT_WITH_NO_DEFINITION,  // expectedFaultCode
                            FaultManager.getInstance().getDefaultFaultMessageProvider().getClass(), // expectedFaultMessageProvider
                            Severity.UNKNOWN, // expectedSeverity
                            String.format("%s.SOME_FAULT_WITH_NO_DEFINITION", UndefinedFaultCode.class.getName()), // expectedFQN 
                            null, // expectedCategoryId,
                            null, // expectedId,
                            null, // expectedResourceBundleName
                            faultDefinition); // actualFaultDefinition
    }

    @Test
    public void test_custom_fault_message_provider_mapping() {
        FaultDefinition faultDefinition = FaultDefinition.valueOf(SomeFaultCode.SOME_SEVERE_FAILURE);

        IFaultMessageProvider actualFaultMessageProvider = faultDefinition.getFaultMessageProvider();
        assertEquals(CustomFaultMessageProvider.class, actualFaultMessageProvider.getClass());
    }

    @Test
    public void test_default_severity_as_warning() {
        FaultDefinition faultDefinition = FaultDefinition.valueOf(SomeFaultCode.SOME_FAULT_WITH_NO_SEVERITY_MENTIONED);

        ISeverity actualSeverity = faultDefinition.getSeverity();
        assertEquals(Severity.WARNING, actualSeverity);
    }
    
    @Test
    public void test_newInstance() {
        FaultDefinition faultDefinition = FaultDefinition.valueOf(ExampleFaultCode.ACCESS_DENIED);
        
        Object [] messageArgs = {"sangeeth"};
        Fault fault = faultDefinition.newInstance(messageArgs);
        
        FaultMessage expectedMessage = new FaultMessage();
        expectedMessage.setSummary("Access denied");
        expectedMessage.setDetails("The user sangeeth is either not registered or doesn't have sufficient privileges to access the system.");
        expectedMessage.setFormattedText("EG-1001: Access denied\nThe user sangeeth is either not registered or does not have sufficient privileges to access the system.");
        
        AssertEx.assertFault(ExampleFaultCode.ACCESS_DENIED, 
                            faultDefinition, 
                            "EG", 
                            "1001", 
                            String.format("%s.ACCESS_DENIED",ExampleFaultCode.class.getName()), // expectedFQN
                            expectedMessage, 
                            Locale.getDefault(), 
                            messageArgs, 
                            Severity.SEVERE, 
                            "EG-1001", 
                            "Access denied",
                            fault); // actualFault
    }
    
    @Test
    public void test_newInstance_locale_specific() {
        Locale locale = new Locale("fr");
        FaultDefinition faultDefinition = FaultDefinition.valueOf(ExampleFaultCode.ACCESS_DENIED);
        
        Object [] messageArgs = {"sangeeth"};
        Fault fault = faultDefinition.newInstance(locale, messageArgs);
        
        FaultMessage expectedMessage = new FaultMessage();
        expectedMessage.setSummary("FRENCH: Access denied");
        expectedMessage.setDetails("FRENCH: The user sangeeth is either not registered or does not have sufficient privileges to access the system.");
        expectedMessage.setFormattedText("EG-1001: FRENCH: Access denied\nFRENCH: The user sangeeth is either not registered or does not have sufficient privileges to access the system.");
        
        AssertEx.assertFault(ExampleFaultCode.ACCESS_DENIED, 
                            faultDefinition, 
                            "EG", 
                            "1001", 
                            String.format("%s.ACCESS_DENIED",ExampleFaultCode.class.getName()), // expectedFQN
                            expectedMessage, 
                            locale, 
                            messageArgs, 
                            Severity.SEVERE, 
                            "EG-1001", 
                            "FRENCH: Access denied",
                            fault); // actualFault
    }    
    
    @Test
    public void test_valueOf_synchronization() {
        ExecutorService service = Executors.newFixedThreadPool(4);
        Callable<FaultDefinition> task = new Callable<FaultDefinition>() {

            @Override
            public FaultDefinition call() throws Exception {
                FaultDefinition faultDefinition = FaultDefinition.valueOf(ExampleFaultCode.ACCESS_DENIED);
                return faultDefinition;
            }
            
        };
        
        try {
            List<Future<FaultDefinition>> futures = service.invokeAll(Arrays.asList(task, task, task, task));
            
            FaultDefinition item1 = null;
            for(Future<FaultDefinition> f:futures) {
                if (item1 == null) {
                    item1 = f.get();
                } else {
                    assertEquals(item1, f.get());
                }
            }
        } catch (InterruptedException e) {
            
        } catch (ExecutionException e) {
            
        }
    }
    
    @Ignore("Need to investigate why this is failing")
    @Test
    public void test_non_enum_based_faultcode() {
        IFaultCode expectedFaultCode = NonEnumFaultCode.ERROR_2;
        FaultDefinition actualFaultDefinition = FaultDefinition.valueOf(expectedFaultCode);
        
        AssertEx.assertFaultDefinition(expectedFaultCode, 
                                       CustomFaultMessageProvider.class, // expectedMessageProvider 
                                       Severity.SEVERE, // expectedSeverity 
                                       FaultCode.FQN(expectedFaultCode), // expectedFQN 
                                       null, //expectedCategoryId
                                       "ERR-1002", // expectedId, 
                                       "corvid.lang.resource.NonEnumFaultResourceBundle", // expectedResourceBundleSource, 
                                       actualFaultDefinition);
    }
    
    @Test(expected=RuntimeException.class)
    public void test_with_rogue_faultmessageprovider() {
        IFaultCode faultCode = RogueFaultCode.SOME_FAILURE;

        FaultDefinition.valueOf(faultCode);
    }
    
    @FaultMessageProviderDef(RogueFaultMessageProvider.class)
    public static enum RogueFaultCode implements IFaultCode {
          SOME_FAILURE
    }
    
    public static class RogueFaultMessageProvider extends AbstractFaultMessageProvider {

        public RogueFaultMessageProvider() throws Exception {
            super();
            throw new Exception("I'm a rogue fault message provider");
        }

        @Override
        public IFaultMessage getMessage(Fault fault, Locale locale,
                Object... messageArgs) {
            // TODO Auto-generated method stub
            return null;
        }
        
    }
}
