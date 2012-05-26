package jay.lang;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestFaultMessage {
    private FaultMessage faultMessage;
    @Before
    public void setUp() {
        faultMessage = new FaultMessage();
    }
    
    @After
    public void tearDown() {
        faultMessage = null;
    }
    
    @Test
    public void test_set_get_summary() {
       String expectedSummary = "This is summary";
       
       faultMessage.setSummary(expectedSummary);
       
       Assert.assertEquals(expectedSummary, faultMessage.getSummary());
    }
    
    @Test
    public void test_set_get_details() {
       String expectedDetails = "This is details";
       
       faultMessage.setDetails(expectedDetails);
       
       Assert.assertEquals(expectedDetails, faultMessage.getDetails());
    }
    
    @Test
    public void test_set_get_formattedText() {
       String expectedFormattedText = "This is formatted text";
       
       faultMessage.setFormattedText(expectedFormattedText);
       
       Assert.assertEquals(expectedFormattedText, faultMessage.getFormattedText());
    }    
    
    @Test
    public void test_toString() {
        String expectedSummary = "This is summary";
        
        faultMessage.setSummary(expectedSummary);
        
        Assert.assertEquals(expectedSummary, faultMessage.getSummary());
    }
    
    @Test
    public void test_summaryKey() {
        IFaultCode faultCode = FaultCode.UNKNOWN;
        String actualKey = FaultMessage.summaryKey(faultCode);
        Assert.assertEquals(String.format("%s.summary",FaultCode.FQN(faultCode)), actualKey);
    }
    
    @Test
    public void test_detailsKey() {
        IFaultCode faultCode = FaultCode.UNKNOWN;
        String actualKey = FaultMessage.detailsKey(faultCode);
        Assert.assertEquals(String.format("%s.details",FaultCode.FQN(faultCode)), actualKey);
    }    
}
