package corvid.lang.resource;

import java.util.ListResourceBundle;

public class StringResourceBundle_fr extends ListResourceBundle {
    private static final Object [][] contents = {
        {"key1", "FRENCH: string value"  },
        {"key2", "10" },
        {"key3", "FRENCH: The arguments passed are {0} and {1}"}
    };
    
    public Object [][] getContents() {
        return contents;
    }
}
