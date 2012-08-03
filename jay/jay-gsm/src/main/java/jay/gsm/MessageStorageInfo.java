package jay.gsm;

import java.io.Serializable;

public class MessageStorageInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name; // TODO change it to an enum
	
	private int capacity;
	
	private int size;

	public MessageStorageInfo() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
