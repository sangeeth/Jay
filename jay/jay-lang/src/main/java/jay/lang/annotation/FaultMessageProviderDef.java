package jay.lang.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jay.lang.IFaultMessageProvider;
/**
 * This annotation helps to associate a {@link IFaultMessageProvider} to a fault code.
 * 
 * A typical usage of this interface is as shown below
 * <pre>
 * &#64;CategoryId("CSO")
 * &#64;ResourceBundleSource("FaultCodeResourceBundle")
 * &#64;FaultMessageProviderDef(MyCustomFaultMessageProfiler.class)
 * public enum FileCopyAPIFaultCode implements IFaultCode {
 *    &#64;FaultCodeDef(id="1000", severity=Severity.SEVERE)
 *    SOURCE_FILE_NOT_FOUND,
 *    
 *    &#64;FaultCodeDef(id="1001", severity=Severity.SEVERE)
 *    NOT_A_FILE,
 *    
 *    &#64;FaultCodeDef(id="1005", severity=Severity.SEVERE)
 *    FILE_COPY_FAILED
 * }
 * </pre>
 * 
 * @author sangeeth
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FaultMessageProviderDef {
    /**
     * To get the type of {@link IFaultMessageProvider} implementation to be used for fetching the message
     * for the associated fault code type.
     * 
     * @return The type of {@link IFaultMessageProvider} implementation.
     */
    public Class<? extends IFaultMessageProvider> value();
}
