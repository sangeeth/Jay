package jay.lang;

/**
 * This class represents the default implementation of {@link IFaultMessage}.
 * 
 * This fault message includes two parts
 * <ul>
 *    <li>summary - the summary of the fault message</li>
 *    <li>details - the details including what is the cause and what could be the remedy (or recommended user action)</li>
 * </ul>
 * 
 * @author sangeeth
 * @version 1.0.0
 */
public class FaultMessage implements IFaultMessage {
    /**
     * To get the resource bundle key for summary part of the fault message.
     * 
     * The key will be of the form <code>{FQN of the faultCode}.summary</code>.
     * 
     * For example, the summaryKey of {@link FaultCode#UNKNOWN} will be 
     * <code>jay.lang.FaultCode.UNKNOWN.summary</code>. 
     * 
     * @param faultCode The fault code for which the summary key is required.
     * @return The resource bundle key for summary part of the fault message.
     */
    public static String summaryKey(IFaultCode faultCode) {
        return String.format("%s.summary", FaultCode.FQN(faultCode));
    }
    
    /**
     * To get the resource bundle key for summary part of the fault message.
     *
     * The key will be of the form <code>{FQN of the faultCode}.summary</code>.
     * For example, the summaryKey of {@link FaultCode#UNKNOWN} will be 
     * <code>jay.lang.FaultCode.UNKNOWN.summary</code>. 
     * 
     * @param faultCode The fault code for which the details key is required.
     * @return The resource bundle key for details part of the fault message.
     */
    public static String detailsKey(IFaultCode faultCode) {
        return String.format("%s.details", FaultCode.FQN(faultCode));
    } 
    
    /**
     * The message summary
     */
    private String summary;
    /**
     * The message details.
     */
    private String details;
    /**
     * The formatted text which will include both summary and details and 
     * probably with simple name of the fault code
     */
    private String formattedText;

    /**
     * Constructs a new instance of fault message.
     */
    public FaultMessage() {
        super();
    }

    /**
     * To get the summary of the fault.
     */
    public String getSummary() {
        return summary;
    }

    /**
     * To set the summary of the fault.
     * 
     * @param summary The summary of the fault.
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * To get the details about the fault.
     * 
     * @return The details about the fault.
     */
    public String getDetails() {
        return details;
    }

    /**
     * To set the details about the fault.
     * 
     * @param details The details about the fault.
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * To get the formatted text.
     * 
     * @return The formatted text.
     */
    public String getFormattedText() {
        return formattedText;
    }

    /**
     * To set the the formatted text.
     * 
     * @param formattedText The formatted text.
     */
    public void setFormattedText(String formattedText) {
        this.formattedText = formattedText;
    }

    /**
     * To get the textual representation of this message. 
     * 
     * The value returned is same as the one returned by {@link #getFormattedText()}.
     */
    @Override
    public String toString() {
        return this.formattedText;
    }
}
