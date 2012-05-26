package jay.lang;
/**
 * This fault code enumerates the default set of faults any application might need.
 * 
 * @author sangeeth
 * @version 1.0.0
 * 
 * @see FaultDefinition
 * @see Severity#UNKNOWN
 * @see Fault
 * @see IFaultCode
 */
public enum FaultCode implements IFaultCode {
	UNKNOWN;
	
	/**
	 * To get the fully-qualified name for the given fault code.
	 * 
	 * For example, the FQN of {@link #UNKNOWN FaultCode.UNKNOWN} 
	 * is <code>jay.lang.FaultCode.UNKNOWN</code>.
	 * 
	 * @return the fully-qualified name for the given fault code.
	 */
	public static String FQN(IFaultCode faultCode) {
	    String fqn = null;
	    if (faultCode!=null) {
	        String typeName = faultCode.getClass().getName();
	        fqn = String.format("%s.%s", typeName, faultCode.name());
	    }
	    return fqn;
	}
}
