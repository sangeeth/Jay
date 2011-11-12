package jay.lang;

import java.util.Locale;

public interface IFaultMessageProvider {
    public IFaultMessage getMessage(Fault fault, Locale locale, Object... messageArgs);
}
