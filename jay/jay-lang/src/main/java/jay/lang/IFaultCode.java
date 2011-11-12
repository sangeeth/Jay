package jay.lang;

import jay.lang.annotation.CategoryId;
import jay.lang.annotation.FaultCodeDef;
import jay.lang.annotation.FaultMessageProviderDef;
import jay.lang.annotation.ResourceBundleSource;

/**
 * This interface is a marker interface for a fault code.
 * 
 * A typical usage of this interface is as shown below
 * <pre>
 * &#64;CategoryId("CSO")
 * &#64;ResourceBundleSource("FaultCodeResourceBundle")
 * public enum FileCopyAPIFaultCode implements IFaultCode {
 *    &#64;FaultCodeDef(id="1000", severity=Severity.SEVERE)
 *    SOURCE_FILE_NOT_FOUND,
 *    
 *    &#64;FaultCodeDef(id="1001", severity=Severity.SEVERE)
 *    NOT_A_FILE,
 *    
 *    &#64;FaultCodeDef(id="1002", severity=Severity.SEVERE)
 *    CANNOT_COPY_FILE_ONTO_ITSELF,
 *    
 *    &#64;FaultCodeDef(id="1003", severity=Severity.SEVERE)
 *    TARGET_FILE_ALREADY_EXISTS,
 *    
 *    &#64;FaultCodeDef(id="1004", severity=Severity.SEVERE)
 *    PARENT_DIRECTORY_CREATION_FAILED,
 *    
 *    &#64;FaultCodeDef(id="1005", severity=Severity.SEVERE)
 *    FILE_COPY_FAILED
 * }
 * </pre>
 * 
 * @author sangkuma
 * @version 1.0.0
 * 
 * @see CategoryId
 * @see FaultCodeDef
 * @see FaultMessageProviderDef
 * @see ResourceBundleSource
 * @see ISeverity
 * @see Fault
 * @see IFaultMessage
 * @see FaultDefinition
 */
public interface IFaultCode extends IIncidentCode {
}
