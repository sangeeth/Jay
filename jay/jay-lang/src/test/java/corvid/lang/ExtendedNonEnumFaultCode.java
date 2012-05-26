package corvid.lang;

public class ExtendedNonEnumFaultCode extends NonEnumFaultCode {
	private static final long serialVersionUID = 1L;
	
	public static final ExtendedNonEnumFaultCode EXTENDED_ERROR_1 = new ExtendedNonEnumFaultCode("extended.err1");
	public static final ExtendedNonEnumFaultCode EXTENDED_ERROR_2 = new ExtendedNonEnumFaultCode("extended.err2");

	protected ExtendedNonEnumFaultCode(String name) {
		super(name);
	}
}
