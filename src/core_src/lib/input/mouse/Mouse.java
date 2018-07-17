package lib.input.mouse;

import java.util.ArrayList;

public class Mouse {

	private static ArrayList<MouseListener> registeredListeners = new ArrayList<>();

	private static ArrayList<MousePress> activePresses = new ArrayList<>();

	public static void buttonActivated(int x, int y, int count, int button) {
		MousePress press = new MousePress(x, y, count, button);

		int index = activePresses.indexOf(press);

		// If press is already in, increase count
		if(index >= 0) {
			int deltaCount = press.count;
			press = activePresses.get(index);
			press.count += deltaCount;
		}
		else { // Else add it in
			activePresses.add(press);			
		}

		// Notify every listener about this activation
		for(int i=registeredListeners.size()-1; i>=0; i--) {
			MouseListener listener = registeredListeners.get(i);

			listener.mousePressed(press);
		}
	}

	public static void buttonDeactivated(int x, int y, int count, int button) {
		MousePress press = new MousePress(x, y, count, button);

		activePresses.remove(press);

		// Notify every listener about this activation
		for(int i=registeredListeners.size()-1; i>=0; i--) {
			MouseListener listener = registeredListeners.get(i);

			listener.mouseReleased(press);
		}
	}

	public static void wheelMoved(float downCount) {
		for(int i=registeredListeners.size()-1; i>=0; i--) {
			MouseListener listener = registeredListeners.get(i);

			listener.wheelMoved(downCount);
		}
	}

	public static void subscribe(MouseListener listener) {
		registeredListeners.add(listener);
	}

	public static void unsubscribe(MouseListener listener) {
		registeredListeners.remove(listener);
	}

	public static void reset() {
		activePresses.clear();
	}

	/* Checkers */
	public static boolean isButtonActive(int button) {
		for(int i=0; i<activePresses.size(); i++) {
			if(activePresses.get(i).button == button) {
				return true;
			}
		}
		return false;
	}

	public static boolean isButtonActive(MousePress press) {
		return activePresses.contains(press);
	}

	/* Getters */
	public static ArrayList<MousePress> getActivePresses() {
		return activePresses;
	}

	public static String[] getPressList() {
		String[] list = new String[activePresses.size()];

		for(int i=0; i<list.length; i++) {
			MousePress pair = activePresses.get(i);
			list[i] = pair.toString();
		}

		return list;
	}

	/* Buttons */
	public static final int BTN_RIGHT = 39;
	public static final int BTN_LEFT = 37;
	public static final int BTN_MID = 3;
}
