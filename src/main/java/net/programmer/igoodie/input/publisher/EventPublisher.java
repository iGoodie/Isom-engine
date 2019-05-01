package net.programmer.igoodie.input.publisher;

import java.util.LinkedList;
import java.util.List;

import net.programmer.igoodie.input.subscriber.EventSubscriber;
import net.programmer.igoodie.window.Window;

public abstract class EventPublisher<S extends EventSubscriber> {

	protected List<S> subscribers;
	
	public EventPublisher() {
		this.subscribers = new LinkedList<>();
	}
	
	public abstract void setCallbackToWindow(Window window);
	
	public void subscribe(S sub) {
		subscribers.add(sub);
	}
	
	public boolean unsubscribe(S sub) {
		return subscribers.remove(sub);
	}
	
	public void unsubscribeAll() {
		subscribers.clear();
	}
	
}
