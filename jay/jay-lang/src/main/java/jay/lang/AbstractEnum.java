package jay.lang;

/**
 * This abstract class represents a basic implementation of {@link IEnumable} interface.
 * 
 * A typical usage of this class is shown below
 * 
 * <code>
 * public class CustomSeverity extends AbstractEnum implements ISeverity {
 *    private static final long serialVersionUID = 1L;
 *
 *    public static final CustomSeverity NOTHING = new CustomSeverity("nothing");
 *    public static final CustomSeverity DISASTER = new CustomSeverity("disaster");
 *
 *    private static int count = 0;
 *    protected CustomSeverity(String name) {
 *        super(name,count++);
 *   }
 * }
 * </code>
 *  
 * @author sangeeth
 * @version 1.0.0
 */
abstract public class AbstractEnum implements IEnumable {
    private String name;
    private int ordinal;

    protected AbstractEnum(String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public int ordinal() {
        return this.ordinal;
    }
}
