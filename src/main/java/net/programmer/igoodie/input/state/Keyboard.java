package net.programmer.igoodie.input.state;

import java.util.Set;
import java.util.TreeSet;

import net.programmer.igoodie.input.subscriber.KeyboardListener;
import net.programmer.igoodie.input.subscriber.WindowListener;
import net.programmer.igoodie.window.Window;

public class Keyboard implements KeyboardListener, WindowListener {

	private Window parentWindow;
	
	private Set<KeyStroke> downKeys;

	public Keyboard(Window parentWindow) {
		this.parentWindow = parentWindow;
		this.downKeys = new TreeSet<>();
		
		// Subscribe self to the event bus
		this.parentWindow.getEventManager().subscribe(this);
	}

	public int downKeyCount() {
		return downKeys.size();
	}
	
	public boolean isDown() {
		return downKeys.size() != 0;
	}
	
	public boolean isKeyDown(int key) {
		return isKeyDown(key, 0x00000000);
	}

	public boolean isKeyDown(int key, int keyCode) {
		return isKeyDown(new KeyStroke(key, keyCode));
	}

	public boolean isKeyDown(KeyStroke pair) {
		return downKeys.contains(pair);
	}

	/* -------------------- */
	
	@Override
	public void keyPressed(int key, int keyCode, int mods) {
		KeyStroke pair = new KeyStroke(key, keyCode);
		downKeys.add(pair);
	}

	@Override
	public void keyReleased(int key, int keyCode, int mods) {
		KeyStroke pair = new KeyStroke(key, keyCode);
		downKeys.remove(pair);
	}

	@Override
	public void focusLost() {
		downKeys.clear();	
	}

}
