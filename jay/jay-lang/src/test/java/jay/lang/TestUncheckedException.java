package jay.lang;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import corvid.lang.CustomSeverity;
import corvid.lang.ExampleFaultCode;
import corvid.lang.UndefinedFaultCode;
import corvid.unit.AssertEx;

public class TestUncheckedException {

    @Test
    public void test_type() {
        UncheckedException e = new UncheckedException();
        Assert.assertTrue(e.getClass().getSuperclass().equals(RuntimeException.class));
    }
    
    @Test
    public void test_default_constructor() {
        UncheckedException e = new UncheckedException();
        
        AssertEx.assertException(null, // expectedCause
                null, // expectedFault 
                FaultCode.UNKNOWN, // expectedFaultCode 
                Locale.getDefault(), // expectedLocale
                null, //expectedMessage 
                Severity.UNKNOWN, //expectedSeverity 
                e ); //actualException  
    }
    
    @Test
    public void test_message_constructor() {
        String expectedMessage = "It works";
        UncheckedException e = new UncheckedException(expectedMessage);
        
        AssertEx.assertException(null, // expectedCause
                null, // expectedFault 
                FaultCode.UNKNOWN, // expectedFaultCode 
                Locale.getDefault(), // expectedLocale
                expectedMessage, //expectedMessage 
                Severity.UNKNOWN, //expectedSeverity 
                e ); //actualException          
    }
    
    @Test
    public void test_cause_with_null_message_constructor() {
        NullPointerException expectedCause = new NullPointerException();
        UncheckedException e = new UncheckedException(expectedCause);
        
        AssertEx.assertException(expectedCause, // expectedCause
                null, // expectedFault 
                FaultCode.UNKNOWN, // expectedFaultCode 
                Locale.getDefault(), // expectedLocale
                NullPointerException.class.getName(), //expectedMessage 
                Severity.UNKNOWN, //expectedSeverity 
                e ); //actualException          
    }
    
    @Test
    public void test_cause_with_predefined_message_constructor() {
        String expectedMessage = "Value out of range";
        IllegalArgumentException expectedCause = new IllegalArgumentException(expectedMessage);
        UncheckedException e = new UncheckedException(expectedCause);
        
        AssertEx.assertException(expectedCause, // expectedCause
                null, // expectedFault 
                FaultCode.UNKNOWN, // expectedFaultCode 
                Locale.getDefault(), // expectedLocale
                expectedMessage, //expectedMessage 
                Severity.UNKNOWN, //expectedSeverity 
                e ); //actualException          
    }    
    
    @Test
    public void test_message_cause_constructor() {
        String expectedMessage = "It works";
        IllegalArgumentException expectedCause = new IllegalArgumentException();
        UncheckedException e = new UncheckedException(expectedMessage, expectedCause);
        
        AssertEx.assertException(expectedCause, // expectedCause
                null, // expectedFault 
                FaultCode.UNKNOWN, // expectedFaultCode 
                Locale.getDefault(), // expectedLocale
                expectedMessage, //expectedMessage 
                Severity.UNKNOWN, //expectedSeverity 
                e ); //actualException      
    }
    
    @Test
    public void test_faultcode_messageargs_constructor() {
        IFaultCode expectedFaultCode = ExampleFaultCode.ACCESS_DENIED;
        
        UncheckedException e = new UncheckedException(expectedFaultCode, "sangeeth");
        
        Fault expectedFault = Fault.valueOf(expectedFaultCode, "sangeeth");
        
        AssertEx.assertException(null, 
                                 expectedFault, 
                                 expectedFaultCode, 
                                 Locale.getDefault(), //expectedLocale 
                                 expectedFault.getSummary(), //expectedMessage 
                                 Severity.SEVERE, //expectedSeverity
                                 e);  //actualException
    }
    
    @Test
    public void test_faultcode_locale_messageargs_constructor() {
        IFaultCode expectedFaultCode = ExampleFaultCode.ACCESS_DENIED;
        
        UncheckedException e = new UncheckedException(expectedFaultCode, Locale.FRENCH, "sangeeth");
        
        Fault expectedFault = Fault.valueOf(expectedFaultCode, Locale.FRENCH, "sangeeth");
        
        AssertEx.assertException(null, 
                                 expectedFault, 
                                 expectedFaultCode, 
                                 Locale.FRENCH, //expectedLocale 
                                 expectedFault.getSummary(), //expectedMessage 
                                 Severity.SEVERE, //expectedSeverity
                                 e);  //actualException
    }

    
    @Test
    public void test_faultcode_severity_messageargs_constructor() {
        IFaultCode expectedFaultCode = ExampleFaultCode.ACCESS_DENIED;
        
        ISeverity expectedSeverity = CustomSeverity.DISASTER;
        UncheckedException e = new UncheckedException(expectedFaultCode, expectedSeverity, "sangeeth");
        
        Fault expectedFault = Fault.valueOf(expectedFaultCode, "sangeeth");
        
        AssertEx.assertException(null, 
                                 expectedFault, 
                                 expectedFaultCode, 
                                 Locale.getDefault(), //expectedLocale 
                                 expectedFault.getSummary(), //expectedMessage 
                                 expectedSeverity, //expectedSeverity
                                 e);  //actualException
    }
    
    @Test
    public void test_faultcode_severity_locale_messageargs_constructor() {
        IFaultCode expectedFaultCode = ExampleFaultCode.ACCESS_DENIED;
        
        ISeverity expectedSeverity = CustomSeverity.DISASTER;
        UncheckedException e = new UncheckedException(expectedFaultCode, expectedSeverity, Locale.FRENCH, "sangeeth");
        
        Fault expectedFault = Fault.valueOf(expectedFaultCode, Locale.FRENCH, "sangeeth");
        
        AssertEx.assertException(null, 
                                 expectedFault, 
                                 expectedFaultCode, 
                                 Locale.FRENCH, //expectedLocale 
                                 expectedFault.getSummary(), //expectedMessage 
                                 expectedSeverity, //expectedSeverity
                                 e);  //actualException
    }
    
    @Test
    public void test_cause_faultcode_messageargs_constructor() {
        IFaultCode expectedFaultCode = ExampleFaultCode.ACCESS_DENIED;
        IllegalArgumentException expectedCause = new IllegalArgumentException();
        
        UncheckedException e = new UncheckedException(expectedCause, expectedFaultCode, "sangeeth");
        
        Fault expectedFault = Fault.valueOf(expectedFaultCode, "sangeeth");
        ISeverity expectedSeverity = expectedFault.getSeverity();
        
        AssertEx.assertException(expectedCause, 
                                 expectedFault, 
                                 expectedFaultCode, 
                                 Locale.getDefault(), //expectedLocale 
                                 expectedFault.getSummary(), //expectedMessage 
                                 expectedSeverity, //expectedSeverity
                                 e);  //actualException
    }
    
    @Test
    public void test_cause_faultcode_severity_messageargs_constructor() {
        IFaultCode expectedFaultCode = ExampleFaultCode.ACCESS_DENIED;
        IllegalArgumentException expectedCause = new IllegalArgumentException();
        ISeverity expectedSeverity = CustomSeverity.DISASTER;
        UncheckedException e = new UncheckedException(expectedCause, expectedFaultCode, expectedSeverity, "sangeeth");
        
        Fault expectedFault = Fault.valueOf(expectedFaultCode, "sangeeth");
        
        AssertEx.assertException(expectedCause, 
                                 expectedFault, 
                                 expectedFaultCode, 
                                 Locale.getDefault(), //expectedLocale 
                                 expectedFault.getSummary(), //expectedMessage 
                                 expectedSeverity, //expectedSeverity
                                 e);  //actualException
    }
    
    @Test
    public void test_cause_faultcode_locale_messageargs_constructor() {
        IFaultCode expectedFaultCode = ExampleFaultCode.ACCESS_DENIED;
        IllegalArgumentException expectedCause = new IllegalArgumentException();
        
        UncheckedException e = new UncheckedException(expectedCause, expectedFaultCode, Locale.FRENCH, "sangeeth");
        
        Fault expectedFault = Fault.valueOf(expectedFaultCode, Locale.FRENCH, "sangeeth");
        ISeverity expectedSeverity = expectedFault.getSeverity();
        
        AssertEx.assertException(expectedCause, 
                                 expectedFault, 
                                 expectedFaultCode, 
                                 Locale.FRENCH, //expectedLocale 
                                 expectedFault.getSummary(), //expectedMessage 
                                 expectedSeverity, //expectedSeverity
                                 e);  //actualException
    }    
    
    @Test
    public void test_cause_faultcode_severity_locale_messageargs_constructor() {
        IFaultCode expectedFaultCode = ExampleFaultCode.ACCESS_DENIED;
        IllegalArgumentException expectedCause = new IllegalArgumentException();
        ISeverity expectedSeverity = CustomSeverity.DISASTER;
        UncheckedException e = new UncheckedException(expectedCause, expectedFaultCode, expectedSeverity, Locale.FRENCH, "sangeeth");
        
        Fault expectedFault = Fault.valueOf(expectedFaultCode, Locale.FRENCH, "sangeeth");
        
        AssertEx.assertException(expectedCause, 
                                 expectedFault, 
                                 expectedFaultCode, 
                                 Locale.FRENCH, //expectedLocale 
                                 expectedFault.getSummary(), //expectedMessage 
                                 expectedSeverity, //expectedSeverity
                                 e);  //actualException
    }
    
    @Test
    public void test_cause_faultcodeAsNULL_severity_locale_messageargs_constructor() {
        IFaultCode expectedFaultCode = FaultCode.UNKNOWN;
        IllegalArgumentException expectedCause = new IllegalArgumentException();
        ISeverity expectedSeverity = CustomSeverity.DISASTER;
        UncheckedException e = new UncheckedException(expectedCause, null, expectedSeverity, Locale.FRENCH, "sangeeth");
        
        AssertEx.assertException(expectedCause, 
                                 null, 
                                 expectedFaultCode, 
                                 Locale.FRENCH, //expectedLocale 
                                 expectedCause.getClass().getName(), //expectedMessage 
                                 expectedSeverity, //expectedSeverity
                                 e);  //actualException
    }
    
    @Test
    public void test_with_cause_undefined_faultCode() {
        IFaultCode expectedFaultCode = UndefinedFaultCode.SOME_FAULT_WITH_NO_DEFINITION;
        IllegalArgumentException expectedCause = new IllegalArgumentException();
        ISeverity expectedSeverity = Severity.UNKNOWN;
        UncheckedException e = new UncheckedException(expectedCause, expectedFaultCode);
        
        Fault expectedFault = Fault.valueOf(expectedFaultCode);
        
        AssertEx.assertException(expectedCause, 
                                 expectedFault, 
                                 expectedFaultCode, 
                                 Locale.getDefault(), //expectedLocale 
                                 FaultCode.FQN(expectedFaultCode), //expectedMessage 
                                 expectedSeverity, //expectedSeverity
                                 e);  //actualException
    }
    
    @Test
    public void test_with_undefined_faultCode() {
        IFaultCode expectedFaultCode = UndefinedFaultCode.SOME_FAULT_WITH_NO_DEFINITION;
        ISeverity expectedSeverity = Severity.UNKNOWN;
        UncheckedException e = new UncheckedException(expectedFaultCode);
        
        Fault expectedFault = Fault.valueOf(expectedFaultCode);
        
        AssertEx.assertException(null, 
                                 expectedFault, 
                                 expectedFaultCode, 
                                 Locale.getDefault(), //expectedLocale 
                                 FaultCode.FQN(expectedFaultCode), //expectedMessage 
                                 expectedSeverity, //expectedSeverity
                                 e);  //actualException
    }    
}