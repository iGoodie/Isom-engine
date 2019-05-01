package net.programmer.igoodie.input;

import java.util.EventListener;

import net.programmer.igoodie.input.publisher.KeyboardHandler;
import net.programmer.igoodie.input.publisher.MouseHandler;
import net.programmer.igoodie.input.publisher.WindowHandler;
import net.programmer.igoodie.input.subscriber.EventSubscriber;
import net.programmer.igoodie.input.subscriber.KeyboardListener;
import net.programmer.igoodie.input.subscriber.MouseListener;
import net.programmer.igoodie.input.subscriber.WindowListener;
import net.programmer.igoodie.window.Window;

public class EventManager {

	private Window parentWindow;
	
	private MouseHandler handlerMouse;
	private WindowHandler handlerWindow;
	private KeyboardHandler handlerKeyboard;

	public EventManager(Window parentWindow) {
		this.parentWindow = parentWindow;
	}

	public void createHandlers() {
		(handlerMouse = new MouseHandler()).setCallbackToWindow(parentWindow);
		(handlerWindow = new WindowHandler()).setCallbackToWindow(parentWindow);
		(handlerKeyboard = new KeyboardHandler()).setCallbackToWindow(parentWindow);
	}
	
	public void subscribe(EventSubscriber listener) {
		if(listener instanceof MouseListener)
			handlerMouse.subscribe((MouseListener) listener);
		if(listener instanceof WindowListener)
			handlerWindow.subscribe((WindowListener) listener);
		if(listener instanceof KeyboardListener)
			handlerKeyboard.subscribe((KeyboardListener) listener);
	}
	
	public void unsubscribe(EventListener listener) {
		if(listener instanceof MouseListener)
			handlerMouse.unsubscribe((MouseListener) listener);
		if(listener instanceof WindowListener)
			handlerWindow.unsubscribe((WindowListener) listener);
		if(listener instanceof KeyboardListener)
			handlerKeyboard.unsubscribe((KeyboardListener) listener);
	}

}
