package corvid.lang.resource;

import java.util.ListResourceBundle;

public class ExampleFaultResourceBundle_fr extends ListResourceBundle {
	public static final Object [][] contents = {
	    {"corvid.lang.ExampleFaultCode.ACCESS_DENIED.summary","FRENCH: Access denied"},
        {"corvid.lang.ExampleFaultCode.ACCESS_DENIED.details","FRENCH: The user {0} is either not registered or does not have sufficient privileges to access the system."},
	};
	
	public Object [][] getContents() {
     	return contents;
	}
}
