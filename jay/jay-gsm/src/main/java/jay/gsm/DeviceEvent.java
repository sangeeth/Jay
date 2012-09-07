package jay.gsm;

public class DeviceEvent {
    public static enum Type {
        MEMORY_SIZE,
        SIGNAL_STRENGTH
    }

    private Type type;
    private Object value;

    public DeviceEvent(Type type, Object value) {
        super();
        this.type = type;
        this.value = value;
    }
    public Type getType() {
        return type;
    }
    public Object getValue() {
        return value;
    }
}
