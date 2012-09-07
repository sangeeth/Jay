package jay.gsm;

public class Memory {
    private int size;
    
    private int capacity;

    public Memory() {
        super();
    }

    public Memory(int size, int capacity) {
        super();
        this.size = size;
        this.capacity = capacity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public boolean isFull() {
        return this.capacity==this.size;
    }
}
