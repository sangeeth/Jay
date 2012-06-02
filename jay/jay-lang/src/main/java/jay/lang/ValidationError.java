package jay.lang;

import java.io.Serializable;
import java.util.Locale;

public class ValidationError implements Serializable {
    private static final long serialVersionUID = 1L;

    private IFaultCode faultCode;
    private ISeverity severity;
    private Fault fault;
    private String message;
    private Locale locale;

    public ValidationError() {
        super();
    }

    public ValidationError(IFaultCode faultCode, Object... messageArgs) {
        this.init(faultCode, null, null, messageArgs);
    }

    public ValidationError(IFaultCode faultCode, Locale locale,
            Object... messageArgs) {
        this.init(faultCode, null, locale, messageArgs);
    }

    public ValidationError(IFaultCode faultCode, ISeverity severity,
            Object... messageArgs) {
        this.init(faultCode, severity, null, messageArgs);
    }

    protected void init(IFaultCode faultCode, ISeverity severity,
            Locale locale, Object... messageArgs) {
        this.faultCode = faultCode == null ? FaultCode.UNKNOWN : faultCode;
        this.locale = locale == null ? Locale.getDefault() : locale;

        if (faultCode != null
                && (this.fault = Fault.valueOf(faultCode, locale, messageArgs)) != null) {
            this.message = fault.getSummary();

            // if the given severity is not null, then use it, otherwise pick
            // the default value from the fault itself.
            this.severity = severity != null ? severity : fault.getSeverity();
        } else {
            // if the given severity is not null, then use it, otherwise
            // leave it as UNKNOWN
            this.severity = severity != null ? severity : Severity.UNKNOWN;
        }
    }

    public IFaultCode getFaultCode() {
        return faultCode;
    }

    public ISeverity getSeverity() {
        return severity;
    }

    public Fault getFault() {
        return fault;
    }

    public String getMessage() {
        return message;
    }

    public Locale getLocale() {
        return locale;
    }
}
