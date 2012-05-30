package jay.util;

public interface IEventListener<T> extends java.util.EventListener {
	public void update(T e);
}
