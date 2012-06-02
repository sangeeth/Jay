package corvid.lang;

import jay.lang.IFaultCode;
import jay.lang.Severity;
import jay.lang.annotation.CategoryId;
import jay.lang.annotation.FaultCodeDef;
import jay.lang.annotation.ResourceBundleSource;

@CategoryId("EG") // Recommended but Optional
@ResourceBundleSource("corvid.lang.resource.ExampleFaultResourceBundle") // Recommended but Optional
public enum ExampleFaultCode implements IFaultCode {
    @FaultCodeDef(id="1000",severity=Severity.SEVERE) // Recommended but Optional
    INVALID_USER,
    @FaultCodeDef(id="1001",severity=Severity.SEVERE) // Recommended but Optional
    ACCESS_DENIED
}

