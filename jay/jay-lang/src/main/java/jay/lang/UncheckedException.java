package jay.lang;

import java.util.Locale;

import jay.lang.annotation.FaultCodeDef;
/**
 * This class represents an unchecked exception. The difference from standard <code>java.lang.RuntimeException</code> is
 * that it includes the following additional attributes
 * 
 * <ul>
 *    <li>{@link IFaultCode faultCode} - the fault code</li>
 *    <li>{@link ISeverity severity} - the severity of the fault</li>
 *    <li>{@link Fault fault} - the complete details about the given faultCode</li>
 *    <li>locale - the locale in which the fault details are created initially</li> 
 * </ul>
 * 
 * @author sangeeth
 * @version 1.0.0
 * 
 * @see IFaultCode
 * @see ISeverity
 * @see UncheckedException
 * @see Fault
 * @see FaultDefinition
 */
public class UncheckedException extends RuntimeException implements IException {
    private static final long serialVersionUID = 1L;

    private IFaultCode faultCode;
    private ISeverity severity;
    private Fault fault;
    private String message;
    private Locale locale;

    /**
     * Constructs a new unchecked exception with {@link FaultCode#UNKNOWN} and {@link Severity#UNKNOWN}.
     * 
     * In this case, the value returned by {@link #getFault()} will be null.
     * 
     */
    public UncheckedException() {
        this.init((IFaultCode) null, (ISeverity) null, (Locale) null);
    }

    /**
     * Constructs a new unchecked exception with a given message, {@link FaultCode#UNKNOWN} and {@link Severity#UNKNOWN}.
     * 
     * In this case, the value returned by {@link #getFault()} will be null.
     * 
     * @param message The message to be associated to the exception instance.
     */
    public UncheckedException(String message) {
        this.init((IFaultCode) null, (ISeverity) null, (Locale) null);
        
        // Overwrite the message value
        this.message = message;
    }

    /**
     * Constructs a new unchecked exception with a given cause, {@link FaultCode#UNKNOWN} and {@link Severity#UNKNOWN}.
     * 
     * If the cause is null, then it will assumed that no cause exist.
     * 
     * In this case, the value returned by {@link #getFault()} will be null.
     * 
     * @param cause The cause to be associated to the exception instance.
     */
    public UncheckedException(Throwable cause) {
        this(cause, (IFaultCode) null, (ISeverity) null, (Locale) null);
    }

    /**
     * Constructs a new unchecked exception with a given message, cause, {@link FaultCode#UNKNOWN} and {@link Severity#UNKNOWN}.
     * 
     * @param message The message to be associated to the exception instance.
     * @param cause The cause to be associated to the exception instance.
     */
    public UncheckedException(String message, Throwable cause) {
        this(cause, (IFaultCode) null, (ISeverity) null, (Locale) null);

        // Overwrite the message value
        this.message = message;
    }
    
    /**
     * Constructs a new unchecked exception with a given faultCode. 
     *
     * If the faultCode is null, then {@link FaultCode#UNKNOWN} will be used and the value returned by 
     * {@link #getFault()} will be null.
     * 
     * The severity will be the one annotated using {@link FaultCodeDef}. 
     * If severity is not mapped then, {@link Severity#UNKNOWN} will be used.
     *  
     * @param faultCode The fault code to be associated to this exception instance.
     * @param messageArgs The arguments to be used while formatting the fault message.
     */
    public UncheckedException(IFaultCode faultCode, Object... messageArgs) {
        this(faultCode, null, null, messageArgs);
    }

    /**
     * Constructs a new unchecked exception with a given faultCode and locale. 
     * 
     * If the faultCode is null, then {@link FaultCode#UNKNOWN} will be used and the value returned by 
     * {@link #getFault()} will be null.
     * 
     * The severity will be the one annotated using {@link FaultCodeDef}. 
     * If the severity is not mapped then, {@link Severity#UNKNOWN} will be used.
     * 
     * If the locale is null, then the default locale (with respect to the Java Virutal Machine) will be used.
     * 
     * @param faultCode The fault code to be associated to this exception instance.
     * @param locale The locale in which the message need to be fetched and formatted.
     * @param messageArgs The arguments to be used while formatting the fault message.
     */
    public UncheckedException(IFaultCode faultCode, Locale locale, Object... messageArgs) {
        this(faultCode, null, locale, messageArgs);
    }

    /**
     * Constructs a new unchecked exception with a given faultCode and severity. 
     * 
     * If the faultCode is null, then {@link FaultCode#UNKNOWN} will be used and the value returned by 
     * {@link #getFault()} will be null.
     * 
     * If the severity is null, then the one annotated using {@link FaultCodeDef} will be used. 
     * If the severity is not mapped then, {@link Severity#UNKNOWN} will be used.
     * 
     * @param faultCode The fault code to be associated to this exception instance.
     * @param severity The severity of the fault.
     * @param messageArgs The arguments to be used while formatting the fault message.
     */
    public UncheckedException(IFaultCode faultCode, ISeverity severity,
            Object... messageArgs) {
        this(faultCode, severity, null, messageArgs);
    }

    /**
     * Constructs a new unchecked exception with a given faultCode, severity and locale.
     * 
     * If the faultCode is null, then {@link FaultCode#UNKNOWN} will be used and the value returned by 
     * {@link #getFault()} will be null.
     * 
     * If the severity is null, then the one annotated using {@link FaultCodeDef} will be used. If the
     * severity is not mapped then, {@link Severity#UNKNOWN} will be used.
     * 
     * If the locale is null, then the default locale (with respect to the Java Virutal Machine) will be used.
     * 
     * @param faultCode The fault code to be associated to this exception instance.
     * @param severity The severity of the fault.
     * @param locale The locale in which the message need to be fetched and formatted.
     * @param messageArgs The arguments to be used while formatting the fault message.
     */
    public UncheckedException(IFaultCode faultCode, ISeverity severity,
            Locale locale, Object... messageArgs) {
        this.init(faultCode, severity, locale, messageArgs);
    }

    /**
     * Constructs a new unchecked exception with a given cause and faultCode.
     * 
     * If the cause is null, it will be considered that there is no cause and the value returned by
     * {@link #getCause()} will be null as well.
     * 
     * If the faultCode is null, then {@link FaultCode#UNKNOWN} will be used and the value returned by 
     * {@link #getFault()} will be null.
     * 
     * The severity will be the one annotated using {@link FaultCodeDef}. 
     * If the severity is not mapped then, {@link Severity#UNKNOWN} will be used.
     * 
     * @param cause The cause is original exception which caused this exception.  
     * @param faultCode The fault code to be associated to this exception instance.
     * @param messageArgs The arguments to be used while formatting the fault message.
     */
    public UncheckedException(Throwable cause, IFaultCode faultCode, Object... messageArgs) {
        this(cause, faultCode, null, null, messageArgs);
    }
    
    /**
     * Constructs a new unchecked exception with a given cause, faultCode and locale.
     *
     * If the cause is null, it will be considered that there is no cause and the value returned by
     * {@link #getCause()} will be null as well.
     * 
     * If the faultCode is null, then {@link FaultCode#UNKNOWN} will be used and the value returned by 
     * {@link #getFault()} will be null.  
     * 
     * The severity will be the one annotated using {@link FaultCodeDef}. 
     * If the severity is not mapped then, {@link Severity#UNKNOWN} will be used.
     * 
     * If the locale is null, then the default locale (with respect to the Java Virutal Machine) will be used.
     * 
     * @param cause The cause is original exception which caused this exception.
     * @param faultCode The fault code to be associated to this exception instance.
     * @param locale The locale in which the message need to be fetched and formatted.
     * @param messageArgs The arguments to be used while formatting the fault message.
     */
    public UncheckedException(Throwable cause, IFaultCode faultCode, Locale locale, Object... messageArgs) {
        this(cause, faultCode, null, locale, messageArgs);
    }    
    
    /**
     * Constructs a new unchecked exception with a given cause, faultCode and severity.
     * 
     * If the cause is null, it will be considered that there is no cause and the value returned by
     * {@link #getCause()} will be null as well.
     * 
     * If the faultCode is null, then {@link FaultCode#UNKNOWN} will be used and the value returned by 
     * {@link #getFault()} will be null. 
     * 
     * If the severity is null, then the one annotated using {@link FaultCodeDef} will be used. If the
     * severity is not mapped then, {@link Severity#UNKNOWN} will be used.
     * 
     * @param cause The cause is original exception which caused this exception.
     * @param faultCode The fault code to be associated to this exception instance.
     * @param severity The severity of the fault.
     * @param messageArgs The arguments to be used while formatting the fault message.
     */
    public UncheckedException(Throwable cause, IFaultCode faultCode,
            ISeverity severity, Object... messageArgs) {
        this(cause, faultCode, severity, null, messageArgs);
    }
    
    /**
     * Constructs a new unchecked exception with a given cause, faultCode, severity and locale.
     * 
     * If the cause is null, it will be considered that there is no cause and the value returned by
     * {@link #getCause()} will be null as well.
     * 
     * If the faultCode is null, then {@link FaultCode#UNKNOWN} will be used and the value returned by 
     * {@link #getFault()} will be null. 
     *
     * If the severity is null, then the one annotated using {@link FaultCodeDef} will be used. If the
     * severity is not mapped then, {@link Severity#UNKNOWN} will be used.
     * 
     * If the locale is null, then the default locale (with respect to the Java Virutal Machine) will be used.
     * 
     * @param cause The cause is original exception which caused this exception.
     * @param faultCode The fault code to be associated to this exception instance.
     * @param severity The severity of the fault.
     * @param locale The locale in which the message need to be fetched and formatted. 
     * @param messageArgs The arguments to be used while formatting the fault message.
     */
    public UncheckedException(Throwable cause, IFaultCode faultCode,
            ISeverity severity, Locale locale, Object... messageArgs) {
        super(cause);
        this.init(faultCode, severity, locale, messageArgs);
    }

    protected void init(IFaultCode faultCode, ISeverity severity, Locale locale, Object... messageArgs) {
        this.faultCode = faultCode == null ? FaultCode.UNKNOWN : faultCode;
        this.locale = locale == null ? Locale.getDefault() : locale;
        
        if (faultCode != null &&
            (this.fault = Fault.valueOf(faultCode, locale, messageArgs)) != null) {
            this.message = fault.getSummary();
            
            // if the given severity is not null, then use it, otherwise pick  
            // the default value from the fault itself.
            this.severity = severity != null ? severity : fault.getSeverity();
        } else {
            // if the given severity is not null, then use it, otherwise 
            // leave it as UNKNOWN
            this.severity = severity != null ? severity : Severity.UNKNOWN;
            
            Throwable cause = this.getCause();
            if (cause!=null) {
                String localizedMessage = cause.getLocalizedMessage();
                this.message = (localizedMessage != null) ? localizedMessage :  cause.getClass().getName();
            }
        }
    }

    /**
     * To get the associated faultCode.
     * 
     * It is guaranteed that this value will be always non-null. 
     */
    @Override
    public IFaultCode getFaultCode() {
        return this.faultCode;
    }

    /**
     * To get the associated fault.
     * 
     * This method may return null.
     */
    @Override
    public Fault getFault() {
        return this.fault;
    }

    /**
     * To get the severity of the exception.
     * 
     * It is guaranteed that this value will be always non-null.
     */
    @Override
    public ISeverity getSeverity() {
        return this.severity;
    }

    /**
     * To get the message or summary of the fault message.
     * 
     * @see IFaultMessage#getSummary()
     */
    @Override
    public String getMessage() {
        return this.message;
    }

    /**
     * To get the locale in which message was fetched (or intended to be fetched)
     * 
     * It is guaranteed that this value will be always non-null. 
     */
    @Override
    public Locale getLocale() {
        return this.locale;
    }
}