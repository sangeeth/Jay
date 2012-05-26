package corvid.lang.resource;

import java.util.ListResourceBundle;

public class StringResourceBundle_en extends ListResourceBundle {
    private static final Object [][] contents = {
        {"key1", "string value"  },
        {"key2", "10" },
        {"key3", "The arguments passed are {0} and {1}"}
    };
    
    public Object [][] getContents() {
        return contents;
    }
}
