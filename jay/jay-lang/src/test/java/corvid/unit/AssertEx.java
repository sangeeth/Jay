package corvid.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.*;

import java.util.Locale;

import jay.lang.Fault;
import jay.lang.FaultDefinition;
import jay.lang.IException;
import jay.lang.IFaultCode;
import jay.lang.IFaultMessage;
import jay.lang.IFaultMessageProvider;
import jay.lang.ISeverity;

public class AssertEx {
    public static void assertFaultDefinition(
            IFaultCode expectedFaultCode,
            Class<? extends IFaultMessageProvider> expectedMessageProviderType,
            ISeverity expectedSeverity, 
            String expectedFQN,
            String expectedCategoryId, 
            String expectedId,
            String expectedResourceBundleSource,
            FaultDefinition actualFaultDefinition) {
        
        assertNotNull(actualFaultDefinition);
        
        IFaultCode actualFaultCode = actualFaultDefinition.getFaultCode();
        IFaultMessageProvider actualFaultMessageProvider = actualFaultDefinition.getFaultMessageProvider();
        String actualFQN = actualFaultDefinition.getFQN();
        String actualId = actualFaultDefinition.getId();
        String actualCategoryId = actualFaultDefinition.getCategoryId();
        String actualResourceBundleSource = actualFaultDefinition.getResourceBundleSource();
        ISeverity actualSeverity = actualFaultDefinition.getSeverity();

        assertEquals(expectedFaultCode, actualFaultCode);
        assertNotNull(actualFaultMessageProvider);
        assertEquals(expectedMessageProviderType, actualFaultMessageProvider.getClass());
        assertEquals(expectedFQN, actualFQN);
        assertEquals(expectedId, actualId);
        assertEquals(expectedResourceBundleSource, actualResourceBundleSource);
        assertEquals(expectedCategoryId, actualCategoryId);
        assertEquals(expectedSeverity, actualSeverity);
    }
    
    public static void assertFault(IFaultCode expectedFaultCode,
                                    FaultDefinition expectedFaultDefinition,
                                    String expectedCategoryId,
                                    String expectedId,
                                    String expectedFQN,
                                    IFaultMessage expectedMessage,
                                    Locale expectedLocale,
                                    Object [] expectedMessageArgs,
                                    ISeverity expectedSeverity,
                                    String expectedSimpleName,
                                    String expectedSummary,
                                    Fault actualFault) {
        
        String actualCategoryId = actualFault.getCategoryId();
        String actualFQN = actualFault.getFQN();
        IFaultCode actualFaultCode = actualFault.getFaultCode();
        FaultDefinition actualFaultDefinition = actualFault.getFaultDefinition();
        IFaultMessage actualMessage = actualFault.getFaultMessage();
        Locale actualLocale = actualFault.getLocale();
        Object [] actualMessageArgs = actualFault.getMessageArgs();
        ISeverity actualSeverity = actualFault.getSeverity();
        String actualSimpleName = actualFault.getSimpleName();
        String actualSummary = actualFault.getSummary();
        
        assertEquals(expectedCategoryId, actualCategoryId);
        assertEquals(expectedFQN, actualFQN);
        assertEquals(expectedFaultCode, actualFaultCode);
        assertEquals(expectedFaultDefinition, actualFaultDefinition);
        if (expectedMessage==null) {
            assertNull(actualMessage);
        } else {
            assertEquals(expectedMessage.toString(), actualMessage.toString());
        }
        assertEquals(expectedLocale, actualLocale);
        
        if (expectedMessageArgs==null) {
            assertNull(actualMessageArgs);
        } else {
            assertArrayEquals(expectedMessageArgs, actualMessageArgs);
        }
        assertEquals(expectedSeverity, actualSeverity);
        assertEquals(expectedSimpleName, actualSimpleName);
        assertEquals(expectedSummary, actualSummary);
    }
       
    public static void assertException(Throwable expectedCause,
                                        Fault expectedFault,
                                        IFaultCode expectedFaultCode,
                                        Locale expectedLocale,
                                        String expectedMessage,
                                        ISeverity expectedSeverity,
                                        IException actualException) {
        Throwable actualCause = actualException.getCause();
        Fault actualFault = actualException.getFault();
        IFaultCode actualFaultCode = actualException.getFaultCode();
        Locale actualLocale =actualException.getLocale();
        String actualMessage = actualException.getMessage();
        ISeverity actualSeverity = actualException.getSeverity();
        
        assertEquals(expectedCause, actualCause);
        if (expectedFault==null) {
            assertNull(actualFault);
        } else {
            assertEquals(expectedFault.getFaultMessage().toString(), actualFault.getFaultMessage().toString());
        }
        assertEquals(expectedFaultCode, actualFaultCode);
        assertEquals(expectedLocale, actualLocale);
        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSeverity, actualSeverity);
    }
}
