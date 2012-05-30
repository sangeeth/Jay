package jay.lang.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * This annotation helps to define the category identifier of type, method or field.
 * 
 * It is recommended that the category identifier is simple and easy to understand.
 * A typical category identifier should be of 3 to 5 characters. 
 * For example: 
 * <ul>
 *   <li>COR - Referring Corvid Foundation</li>
 *   <li>ORA - Referring Oracle RDBMS</li>
 * </ul>
 * 
 * @author sangeeth
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
public @interface CategoryId {
    /**
     * To get the category identifier value.
     * 
     * @return The category identifier value.
     */
    public String value();
}
