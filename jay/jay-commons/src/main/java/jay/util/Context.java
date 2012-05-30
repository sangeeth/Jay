package jay.util;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Context implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Map<String, Object> attributes;
    
    public Context() {
        super();
        this.attributes = new TreeMap<String, Object>();
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
    
    public void setAttribute(String name, Object value) {
        this.attributes.put(name, value);
    }
    
    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }
}
