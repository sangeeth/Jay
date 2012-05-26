package corvid.lang;

import jay.lang.IFaultCode;
import jay.lang.Severity;
import jay.lang.annotation.CategoryId;
import jay.lang.annotation.FaultCodeDef;
import jay.lang.annotation.FaultMessageProviderDef;
import jay.lang.annotation.ResourceBundleSource;

@CategoryId("SOME") // Recommended but Optional
@ResourceBundleSource("corvid.lang.resource.SomeFaultResourceBundle") // Recommended but Optional
@FaultMessageProviderDef(CustomFaultMessageProvider.class) // Optional
public enum SomeFaultCode implements IFaultCode {
	@FaultCodeDef(id="2000",severity=Severity.SEVERE) // Recommended but Optional
	SOME_SEVERE_FAILURE,
	@FaultCodeDef(id="2001",severity=Severity.WARNING) // Recommended but Optional
	SOME_WARNING,
	SOME_FAULT_WITH_NO_DEFINITION,
	@FaultCodeDef(id="2003")
	SOME_FAULT_WITH_NO_SEVERITY_MENTIONED
}