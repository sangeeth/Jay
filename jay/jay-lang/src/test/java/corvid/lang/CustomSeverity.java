package corvid.lang;

import jay.lang.AbstractEnum;
import jay.lang.ISeverity;

public class CustomSeverity extends AbstractEnum implements ISeverity {
    private static final long serialVersionUID = 1L;
    
    public static final CustomSeverity NOTHING = new CustomSeverity("nothing", 0);
    public static final CustomSeverity DISASTER = new CustomSeverity("disaster", 1);
    
    protected CustomSeverity(String name, int ordinal) {
        super(name, ordinal);
    }
}
