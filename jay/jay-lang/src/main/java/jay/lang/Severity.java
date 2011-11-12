package jay.lang;
/**
 * This enum represents the commonly used severity levels.
 * 
 * @author sangkuma
 * @version 1.0.0
 */
public enum Severity implements ISeverity {
    /**
     * Irrecoverable system fault
     */
    FATAL,
    /**
     * Severe fault, but the system can manage to recover and work with limited
     * features.
     */
    SEVERE,
    /**
     * Warning level fault, but the system may ignore it and continue working.
     */
    WARNING,
    /**
     * A trivial issue which can be completely ignored.
     */
    TRIVIAL,
    /**
     * Unknown severity level
     */
    UNKNOWN
}
