package jay.util;

import jay.lang.IPurgable;

public class Event<T,E extends IEventType> 
                       implements IPurgable,
                                  ICancellable{
	private T source;
	private E type;
	private boolean cancelled;
	/**
	 * Construct an instance of EventObject with the event source.
	 * @param source The event source. 
	 */
	public Event(T source) {
		this(source,null);
	}
	/**
	 * Construct an instance of EventObject with the event source and type.
	 * 
	 * @param source The event source.
	 * @param type The event type.
	 */
	public Event(T source, E type) {
		this.source = source;
		this.type = type;
	}
	/**
	 * To get the source of the event.
	 * @return the source of the event.
	 */
	public T getSource() {
		return source;
	}
	/**
	 * To get the type of event. 
	 * 
	 * @return the type of event.
	 */
	public E getType() {
		return type;
	}
	// Implements ICancellable methods
	public boolean isCancelled() {
        return cancelled;
    }
    
    public void cancel() {
	    this.cancelled = true;
	}
	
    // Implement IPurgable methods
	public void purge() {
		this.type = null;
		this.source = null;
	}
}
