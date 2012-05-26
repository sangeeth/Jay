package corvid.lang;

import jay.lang.IFaultCode;
import jay.lang.annotation.FaultMessageProviderDef;
import jay.lang.annotation.ResourceBundleSource;

@FaultMessageProviderDef(CustomFaultMessageProvider.class)
@ResourceBundleSource("corvid.lang.resource.NonEnumFaultResourceBundle")
public class NonEnumFaultCode implements IFaultCode {
	private static final long serialVersionUID = 1L;
	
	public static final NonEnumFaultCode ERROR_1 = new NonEnumFaultCode("err1");
	public static final NonEnumFaultCode ERROR_2 = new NonEnumFaultCode("err2");
	
	private String name;
	private int ordinal;
	
	private static int count = 0;
	
	protected NonEnumFaultCode(String name) {
		this.name = name;
		this.ordinal = count++;
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
