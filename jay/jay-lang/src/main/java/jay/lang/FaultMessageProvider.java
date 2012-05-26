package jay.lang;

import java.util.Locale;

/**
 * This class represents a default implementation of {@link IFaultMessageProvider}. 
 * 
 * This implementation creates fault message as {@link FaultMessage}. Hence expects
 * the resource bundle to have two entries for each fault code; one for summary and one for details.
 * 
 * A typical example is as shown below
 * <pre>
 * package com.company.fileapi.resource;
 * 
 * import com.company.fileapi.FileCopyAPIFaultCode;
 * 
 * import static jay.lang.FaultMessage.summaryKey;
 * import static jay.lang.FaultMessage.detailsKey;
 * import java.util.ListResourceBundle;
 * 
 * public class FileCopyAPIFaultCodeResourceBundle extends ListResourceBundle{
 *   private static final Object [][] contents = {
 *      // FileCopyAPIFaultCode.PARENT_DIRECTORY_CREATION_FAILED
 *      {summaryKey(FileCopyAPIFaultCode.PARENT_DIRECTORY_CREATION_FAILED),"Failed to create parent directories while copying {0} to {1}"},
 *      {details(FileCopyAPIFaultCode.PARENT_DIRECTORY_CREATION_FAILED), "Unexpected error occurred while creating the nonexistent parent directories of the destination file {1}. Manually verify whether the destination directory is possible to be created."},
 * 
 *      // FileCopyAPIFaultCode.SOURCE_FILE_NOT_FOUND
 *      {summaryKey(FileCopyAPIFaultCode.SOURCE_FILE_NOT_FOUND),"Copy failed as the source file {0} do not exist"},
 *      {detailsKey(FileCopyAPIFaultCode.SOURCE_FILE_NOT_FOUND),"Ensure that the source file exist."},
 *      
 *      ...
 *      ...
 *      ...
 *   };
 *   &#64;Override
 *   protected Object[][] getContents() {
 *     return contents;
 *   }
 * }
 * </pre>
 * 
 * If messages are stored in properties resource bundle, then it may appear as shown below
 * <pre>
 * # FileCopyAPIFaultCode.PARENT_DIRECTORY_CREATION_FAILED
 * com.company.fileapi.FileCopyAPIFaultCode.PARENT_DIRECTORY_CREATION_FAILED.summary=Failed to create parent directories while copying {0} to {1}
 * com.company.fileapi.FileCopyAPIFaultCode.PARENT_DIRECTORY_CREATION_FAILED.details=Unexpected error occurred while creating the nonexistent parent directories of the destination file {1}. Manually verify whether the destination directory is possible to be created.
 * 
 * # FileCopyAPIFaultCode.SOURCE_FILE_NOT_FOUND
 * com.company.fileapi.FileCopyAPIFaultCode.SOURCE_FILE_NOT_FOUND.summary=Copy failed as the source file {0} do not exist
 * com.company.fileapi.FileCopyAPIFaultCode.SOURCE_FILE_NOT_FOUND.details=Ensure that the source file exist.
 * </pre>
 * 
 * @author sangeeth
 * @version 1.0.0
 * 
 * @see FaultMessage
 */
public class FaultMessageProvider extends AbstractFaultMessageProvider {
    /**
     * To get the message in a given locale for the given fault.
     * 
     * It is guaranteed that this method will return a non-null value.
     */
    @Override
    public IFaultMessage getMessage(Fault fault, Locale locale, Object... messageArgs) {
        FaultMessage faultMessage = new FaultMessage();

        Resource resource = getResource(fault, locale);

        IFaultCode faultCode = fault.getFaultCode();
        String fqn = fault.getFQN();

        String summary = resource.getString(locale, FaultMessage.summaryKey(faultCode), fqn, messageArgs);
        faultMessage.setSummary(summary);

        String details = resource.getString(locale, FaultMessage.detailsKey(faultCode), null, messageArgs);
        faultMessage.setDetails(details);

        String simpleName = fault.getSimpleName();
        
        StringBuffer buffer = new StringBuffer();
        buffer.append((simpleName!=null) ? String.format("%s: ",simpleName) : "")
              .append(summary) // guaranteed to be non-null
              .append((details!=null) ? String.format("\n%s", details) : "");
        
        faultMessage.setFormattedText(buffer.toString());

        return faultMessage;
    }
}
