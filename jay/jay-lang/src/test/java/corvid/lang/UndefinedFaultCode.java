package corvid.lang;

import jay.lang.IFaultCode;

public enum UndefinedFaultCode implements IFaultCode {
	SOME_SEVERE_FAILURE,
	SOME_WARNING,
	SOME_FAULT_WITH_NO_DEFINITION,
	SOME_FAULT_WITH_NO_SEVERITY_MENTIONED
}