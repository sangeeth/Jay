package jay.lang;

import java.util.Locale;

public interface IException {
    public Throwable getCause();
    
    public IFaultCode getFaultCode();

    public ISeverity getSeverity();

    public Fault getFault();

    public String getMessage();

    public Locale getLocale();
}
