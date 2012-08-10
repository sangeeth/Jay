package jay.util;

import java.util.Map;
import java.util.TreeMap;

public class Json {
	private Map<String, Object> store;
	public Json() {
		this.store = new TreeMap<String, Object>();
	}
	public Json set(String name, Number value) {
		this.store.put(name, value);
		return this;
	}
	public Json set(String name, String value) {
		this.store.put(name, value);
		return this;
	}
	public Json set(String name, Boolean value) {
		this.store.put(name, value);
		return this;
	}
	public Json set(String name, Json value) {
		this.store.put(name, value);
		return this;
	}
	public String toString() {
		return toString(this);
	}
	
	private static String toString(Json json) {
		if (json==null) {
			return null;
		}
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("{");
		
		int count = json.store.size();
		for(Map.Entry<String, Object> e:json.store.entrySet()) {
			--count;
			buffer.append("\"").append(e.getKey()).append("\"")
			      .append(":");
			
			Object value = e.getValue();
			if (value instanceof String) {
				buffer.append("\"").append(value).append("\"");
			} else if (value instanceof Json) {
				buffer.append(toString((Json)value));
			} else {
				buffer.append(value);
			}
			if (count!=0) {
				buffer.append(',');
			}
		}
		
		buffer.append("}");
		
		return buffer.toString();
 	}
}