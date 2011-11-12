package jay.lang.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jay.lang.IFaultCode;
import jay.lang.Severity;
/**
 * This annotation helps to associate the fault identifier and the severity to a fault code.
 * 
 * Refer API documentation of {@link IFaultCode} for usage example.
 * 
 * @author sangkuma
 * @version 1.0.0
 * 
 * @see {@link IFaultCode}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
public @interface FaultCodeDef {
    /**
     * To get the identifier of the fault code.
     * 
     * It is recommended to keep the identifier have 3 to 8 characters/digits.
     *  
     * @return The identifier of the fault code.
     */
    public String id();
    /**
     * To get the severity of the fault code.
     * 
     * @return The severity of the fault code.
     */
    public Severity severity() default Severity.WARNING;
}
