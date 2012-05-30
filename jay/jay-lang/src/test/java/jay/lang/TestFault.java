package jay.lang;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import corvid.lang.CustomFaultMessage;
import corvid.lang.ExampleFaultCode;
import corvid.lang.NonEnumFaultCode;
import corvid.lang.SomeFaultCode;
import corvid.lang.UndefinedFaultCode;
import corvid.unit.AssertEx;

public class TestFault {
    @Test
    public void test_valueOf_faultCode_messageArgs() {
        IFaultCode expectedFaultCode =  ExampleFaultCode.ACCESS_DENIED;
        Object [] expectedMessageArgs = { "sangeeth" };
        Fault actualFault = Fault.valueOf(expectedFaultCode, expectedMessageArgs);
        
        FaultDefinition expectedFaultDefinition = FaultDefinition.valueOf(ExampleFaultCode.ACCESS_DENIED);
        
        FaultMessage expectedMessage = new FaultMessage();
        expectedMessage.setSummary("Access denied");
        expectedMessage.setDetails("The user sangeeth is either not registered or doesn't have sufficient privileges to access the system.");
        expectedMessage.setFormattedText("EG-1001: Access denied\nThe user sangeeth is either not registered or does not have sufficient privileges to access the system.");
        
        AssertEx.assertFault(expectedFaultCode, 
                            expectedFaultDefinition, 
                            "EG", // expectedCategoryId, 
                            "1001", //expectedId, 
                            String.format("%s.ACCESS_DENIED",ExampleFaultCode.class.getName()), // expectedFQN
                            expectedMessage, 
                            Locale.getDefault(), // expectedLocale
                            expectedMessageArgs,
                            Severity.SEVERE, // expectedSeverity
                            "EG-1001", // expectedSimpleName
                            "Access denied", // expectedSummary
                            actualFault); // actualFault        
    }
    
    @Test
    public void test_getFaultMessage_locale_messageArgs() {
        IFaultCode expectedFaultCode =  ExampleFaultCode.ACCESS_DENIED;
        Object [] messageArgs = { "sangeeth" };
        Fault fault = Fault.valueOf(expectedFaultCode, messageArgs);
        
        FaultMessage expectedMessage = new FaultMessage();
        expectedMessage.setSummary("FRENCH: Access denied");
        expectedMessage.setDetails("FRENCH: The user sangeeth is either not registered or doesn't have sufficient privileges to access the system.");
        expectedMessage.setFormattedText("EG-1001: FRENCH: Access denied\nFRENCH: The user sangeeth is either not registered or does not have sufficient privileges to access the system.");

        IFaultMessage actualMessage = fault.getFaultMessage(Locale.FRENCH, messageArgs);
        
        Assert.assertNotNull(actualMessage);
        Assert.assertEquals(expectedMessage.toString(), actualMessage.toString());
    }
    
    @Test
    public void test_getFaultMessage_withNullLocale_messageArgs() {
        IFaultCode expectedFaultCode =  ExampleFaultCode.ACCESS_DENIED;
        Object [] messageArgs = { "sangeeth" };
        Fault fault = Fault.valueOf(expectedFaultCode, messageArgs);
        
        IFaultMessage expectedFaultMessage = fault.getFaultMessage();
        

        Locale locale = null;
        IFaultMessage actualMessage = fault.getFaultMessage(locale, messageArgs);
        
        Assert.assertTrue(expectedFaultMessage == actualMessage);
    }    
    
    @Test
    public void test_getFaultMessage_locale_withoutMessageArgs() {
        IFaultCode expectedFaultCode =  ExampleFaultCode.ACCESS_DENIED;
        Object [] messageArgs = { "sangeeth" };
        Fault fault = Fault.valueOf(expectedFaultCode, messageArgs);
        
        FaultMessage expectedMessage = new FaultMessage();
        expectedMessage.setSummary("FRENCH: Access denied");
        expectedMessage.setDetails("FRENCH: The user sangeeth is either not registered or doesn't have sufficient privileges to access the system.");
        expectedMessage.setFormattedText("EG-1001: FRENCH: Access denied\nFRENCH: The user sangeeth is either not registered or does not have sufficient privileges to access the system.");

        IFaultMessage actualMessage = fault.getFaultMessage(Locale.FRENCH);
        
        Assert.assertNotNull(actualMessage);
        Assert.assertEquals(expectedMessage.toString(), actualMessage.toString());
    }
    
    @Test
    public void test_getSimpleName_faultCode_with_no_id_but_categoryId() {
        Fault fault = Fault.valueOf(SomeFaultCode.SOME_FAULT_WITH_NO_DEFINITION);
        
        Assert.assertEquals(fault.getCategoryId(), fault.getSimpleName());
    }
    
    @Test
    public void test_valueOf_undefinedFaultCode() {
        IFaultCode expectedFaultCode = UndefinedFaultCode.SOME_FAULT_WITH_NO_DEFINITION;
        Fault actualFault = Fault.valueOf(expectedFaultCode);
        
        FaultDefinition expectedFaultDefinition = FaultDefinition.valueOf(expectedFaultCode);

        String expectedFQN = String.format("%s.SOME_FAULT_WITH_NO_DEFINITION",UndefinedFaultCode.class.getName());
        
        FaultMessage expectedMessage = new FaultMessage();
        expectedMessage.setSummary(expectedFQN);
        expectedMessage.setDetails(null);
        expectedMessage.setFormattedText(expectedFQN);
        
        AssertEx.assertFault(expectedFaultCode, 
                expectedFaultDefinition, 
                null, // expectedCategoryId, 
                null, //expectedId, 
                expectedFQN,
                expectedMessage, 
                Locale.getDefault(), // expectedLocale
                null, // expectedMessageArgs
                Severity.UNKNOWN, // expectedSeverity
                null, // expectedSimpleName
                expectedFQN, // expectedSummary
                actualFault); // actualFault  
    }
    
    @Test
    public void test_purge() {
        IFaultCode expectedFaultCode =  ExampleFaultCode.ACCESS_DENIED;
        Object [] expectedMessageArgs = { "sangeeth" };
        Fault actualFault = Fault.valueOf(expectedFaultCode, expectedMessageArgs);
        
        actualFault.purge();
        
        Assert.assertNull(actualFault.getCategoryId());
        Assert.assertNull(actualFault.getFQN());
        Assert.assertNull(actualFault.getId());
        Assert.assertNull(actualFault.getSimpleName());
        Assert.assertNull(actualFault.getSummary());
        Assert.assertNull(actualFault.getFaultCode());
        Assert.assertNull(actualFault.getFaultDefinition());
        Assert.assertNull(actualFault.getFaultMessage());
        Assert.assertNull(actualFault.getMessageArgs());
        Assert.assertNull(actualFault.getSeverity());
    }
    
    @Test
    public void test_simpleName() {
        IFaultCode expectedFaultCode =  ExampleFaultCode.ACCESS_DENIED;
        Object [] expectedMessageArgs = { "sangeeth" };
        Fault actualFault = Fault.valueOf(expectedFaultCode, expectedMessageArgs);
        
        String expectedSimpleName = "MODIFIED-ONE";
        actualFault.setSimpleName(expectedSimpleName);
        
        Assert.assertEquals(expectedSimpleName, actualFault.getSimpleName());
    }
    
    @Ignore("Need to investigate why this is failing")
    @Test
    public void test_non_enum_based_faultcode() {
        IFaultCode expectedFaultCode = NonEnumFaultCode.ERROR_2;
        
        Fault actualFault = Fault.valueOf(expectedFaultCode);
        
        FaultDefinition expectedFaultDefinition = FaultDefinition.valueOf(expectedFaultCode);
            
        CustomFaultMessage expectedMessage = new CustomFaultMessage();
        expectedMessage.setDescription("This is error 2");
        expectedMessage.setCause("This is the cause for error 2");
        expectedMessage.setAction("This is the recommended action to avoid error 2");
        expectedMessage.setFormattedString("ERR-1002: This is error 2\nCause - This is the cause for error 2\nAction - This is the recommended action to avoid error 2");            
        
        AssertEx.assertFault(expectedFaultCode, 
                expectedFaultDefinition, 
                null, // expectedCategoryId, 
                "ERR-1002", //expectedId, 
                FaultCode.FQN(expectedFaultCode), // expectedFQN
                expectedMessage, 
                Locale.getDefault(), // expectedLocale
                null, // expectedMessageArgs
                Severity.SEVERE, // expectedSeverity
                "ERR-1002", // expectedSimpleName
                "This is error 2", // expectedSummary
                actualFault); // actualFault  
    }    
}
