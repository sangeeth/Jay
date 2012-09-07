package jay.gsm;

public enum ConnectionState {
    ACTIVE,
    /**
     * Modem switched-off
     */
    SUSPENDED,
    /**
     * Modem switched-on
     */
    RESET,
    /**
     * Modem unplugged
     */
    DISCONNECTED
}
