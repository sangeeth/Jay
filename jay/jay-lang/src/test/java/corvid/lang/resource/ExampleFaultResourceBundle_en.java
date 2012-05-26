package corvid.lang.resource;

import java.util.ListResourceBundle;

public class ExampleFaultResourceBundle_en extends ListResourceBundle {
	public static final Object [][] contents = {
          {"corvid.lang.ExampleFaultCode.ACCESS_DENIED.summary","Access denied"},
          {"corvid.lang.ExampleFaultCode.ACCESS_DENIED.details","The user {0} is either not registered or does not have sufficient privileges to access the system."},
     };
	
	public Object [][] getContents() {
     	return contents;
     }
}
