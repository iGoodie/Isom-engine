package lib.input.keyboard;

import java.util.ArrayList;

public class Keyboard {

	private static ArrayList<KeyboardListener> registeredListeners = new ArrayList<>();

	private static ArrayList<KeyPair> activeKeys = new ArrayList<>();

	public static void keyActivated(char key, int keyCode) {
		KeyPair pair = new KeyPair(key, keyCode);

		if(activeKeys.contains(pair)) return;

		activeKeys.add(pair);

		// Notify listeners about that activation
		for(int i=registeredListeners.size()-1; i>=0; i--) {
			KeyboardListener listener = registeredListeners.get(i);
			
			listener.keyPressed(pair);

			if(pair.isPrintable())
				listener.keyTyped(pair);
		}
	}

	public static void keyDeactivated(char key, int keyCode) {
		KeyPair pair = new KeyPair(key, keyCode);

		activeKeys.remove(pair);
		
		// Notify listeners about that deactivation
		for(int i=registeredListeners.size()-1; i>=0; i--) {
			KeyboardListener listener = registeredListeners.get(i);
			
			listener.keyReleased(pair);
		}
	}

	public static void subscribe(KeyboardListener listener) {
		registeredListeners.add(listener);
	}

	public static void unsubscribe(KeyboardListener listener) {
		registeredListeners.remove(listener);
	}

	public static void reset() {
		activeKeys.clear();
	}

	/* To Check Anywhere */
	public static boolean isKeyActive(char key, int keyCode) {
		return isKeyActive(new KeyPair(key, keyCode));
	}

	public static boolean isKeyActive(char key) {
		if(key == '\uFFFF') throw new IllegalArgumentException("Coded key flag can't be passed as an argument.");

		key = Character.toUpperCase(key);

		for(KeyPair pair : activeKeys) {
			if(pair.key == key) return true;
		}

		return false;
	}

	public static boolean isKeyActive(KeyPair pair) {
		return activeKeys.contains(pair);
	}

	public static boolean isKeyActive(KeyPair...pairs) {
		for(KeyPair pair : pairs) {
			if(!activeKeys.contains(pair)) {
				return false;
			}
		}
		return true;
	}
	
	/* Getters */
	public static ArrayList<KeyPair> getActiveKeys() {
		return activeKeys;
	}

	public static String[] getKeyList() {
		String[] list = new String[activeKeys.size()];

		for(int i=0; i<list.length; i++) {
			KeyPair pair = activeKeys.get(i);
			list[i] = pair.toString();
		}

		return list;
	}

	/* Keys */
	public static final KeyPair KEY_ESC = new KeyPair('\u001B', 0x0000001B);

	public static final KeyPair KEY_UP = new KeyPair('\uFFFF', 0x00000026);
	public static final KeyPair KEY_DOWN = new KeyPair('\uFFFF', 0x00000028);
	public static final KeyPair KEY_LEFT = new KeyPair('\uFFFF', 0x00000025);
	public static final KeyPair KEY_RIGHT = new KeyPair('\uFFFF', 0x00000027);

	public static final KeyPair KEY_F1 = new KeyPair('\u0000', 0x00000061);
	public static final KeyPair KEY_F2 = new KeyPair('\u0000', 0x00000062);
	public static final KeyPair KEY_F3 = new KeyPair('\u0000', 0x00000063);
	public static final KeyPair KEY_F4 = new KeyPair('\u0000', 0x00000064);
	public static final KeyPair KEY_F5 = new KeyPair('\u0000', 0x00000065);
	public static final KeyPair KEY_F6 = new KeyPair('\u0000', 0x00000066);
	public static final KeyPair KEY_F7 = new KeyPair('\u0000', 0x00000067);
	public static final KeyPair KEY_F8 = new KeyPair('\u0000', 0x00000068);
	public static final KeyPair KEY_F9 = new KeyPair('\u0000', 0x00000069);
	public static final KeyPair KEY_F10 = new KeyPair('\u0000', 0x0000006A);
	public static final KeyPair KEY_F11 = new KeyPair('\u0000', 0x0000006B);
	public static final KeyPair KEY_F12 = new KeyPair('\u0000', 0x0000006C);

	public static final KeyPair KEY_W = new KeyPair('W', 0x00000057);
	public static final KeyPair KEY_A = new KeyPair('A', 0x00000041);
	public static final KeyPair KEY_S = new KeyPair('S', 0x00000053);
	public static final KeyPair KEY_D = new KeyPair('D', 0x00000044);
	public static final KeyPair KEY_F = new KeyPair('F', 0x00000046);
	public static final KeyPair KEY_E = new KeyPair('E', 0x00000045);

	public static final KeyPair KEY_SPACE = new KeyPair(' ', 0x00000020);

	public static final KeyPair KEY_SHIFT = new KeyPair('\uFFFF', 0x00000010);
	public static final KeyPair KEY_CTRL = new KeyPair('\uFFFF', 0x00000011);
	public static final KeyPair KEY_ALT = new KeyPair('\uFFFF', 0x00000012);
	
	public static final KeyPair KEY_WIN_ENTER = new KeyPair('\n', 0x0000000A);
	public static final KeyPair KEY_WIN_BACKSPACE = new KeyPair('\u0008', 0x00000008);
}
