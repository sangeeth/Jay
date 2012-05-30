package jay.util;

import java.util.ArrayList;
import java.util.List;

import jay.lang.IPurgable;

public class EventSupport<T> implements IPurgable {
	private List<IEventListener<T>> listeners;
	
	/**
	 * Construct an instance of EventSupport class.
	 */
	public EventSupport() {
		this.listeners = new ArrayList<IEventListener<T>>();
	}
	/**
	 * To add an event listener.
	 * @param listener the event listener.  
	 */
	public void addEventListener(IEventListener<T> listener) {
		if (listener!=null) {
			this.listeners.add(listener);
		}
	}
	/**
	 * To remove an event listener.
	 * @param listener the event listener.
	 */
	public void removeEventListener(IEventListener<T> listener) {
		if (listener!=null) {
			this.listeners.remove(listener);
		}
	}
	/**
	 * To fire an event and update all the event listeners.
	 * @param e The event to be published to all event listeners.
	 */
	public boolean fireEvent(T e) {
	    boolean cancelled = false;
		if (e!=null) {
			for(IEventListener<T> l:this.listeners) {
				l.update(e);
				if (e instanceof ICancellable
				     &&((ICancellable)e).isCancelled()) {
				    cancelled = true;
				    break;
				}
			}
			if (e instanceof IPurgable) {
				((IPurgable)e).purge();
			}
		}
		return cancelled;
	}
	/**
	 * To clear all listeners and be ready for garbage collection.
	 */
	public void purge() {
		this.listeners.clear();
		this.listeners = null;
	}
}
