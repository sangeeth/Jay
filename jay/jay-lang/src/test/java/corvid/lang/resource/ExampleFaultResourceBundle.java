package corvid.lang.resource;

import java.util.ListResourceBundle;

public class ExampleFaultResourceBundle extends ListResourceBundle {
	public static final Object [][] contents = {
		{"corvid.lang.ExampleFaultCode.ACCESS_DENIED.summary","Access denied."},
		{"corvid.lang.ExampleFaultCode.ACCESS_DENIED.details","The given user is either not registered or doesn't have sufficient privileges to access the system."},
	};
	
	public Object [][] getContents() {
		return contents;
	}
}
