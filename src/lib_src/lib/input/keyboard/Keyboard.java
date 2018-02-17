package lib.input.keyboard;

import java.util.ArrayList;

import lib.util.Stringifier;

public class Keyboard {
	private static ArrayList<KeyPair> activeKeys = new ArrayList<>();
	
	public static void keyActivated(char key, int keyCode) {
		KeyPair pair = new KeyPair(Character.toUpperCase(key), keyCode);
		if(activeKeys.contains(pair)) return;
		activeKeys.add(pair);
	}
	
	public static void keyDeactivated(char key, int keyCode) {
		activeKeys.remove(new KeyPair(Character.toUpperCase(key), keyCode));
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
	
	/* To Handle Once */
	public static boolean isKeyActiveOnce(char key, int keyCode) {
		return isKeyActiveOnce(new KeyPair(key, keyCode));
	}

	public static boolean isKeyActiveOnce(char key) {
		if(key == '\uFFFF') throw new IllegalArgumentException("Coded key flag can't be passed as an argument.");
		
		key = Character.toUpperCase(key);
		
		for(KeyPair pair : activeKeys) {
			if(pair.key!='\uFFFF' && pair.key==key) {
				keyDeactivated(pair.key, pair.keyCode);
				return true; 
			}
		}
		
		return false;
	}
	
	public static boolean isKeyActiveOnce(KeyPair pair) {
		if(activeKeys.contains(pair)) {
			keyDeactivated(pair.key, pair.keyCode);
			return true;
		}
		return false;
	}

	/* Getters */
	public static ArrayList<KeyPair> getActiveKeys() {
		return activeKeys;
	}
	
	public static String getKeyString(char key, int keyCode) {
		return String.format("%c(\\u%s) | %d(0x%s)", 
				key, Stringifier.asHex(key, 4), 
				keyCode, Stringifier.asHex(keyCode, 8));
	}

	public static String[] getKeyList() {
		String[] list = new String[activeKeys.size()];
		
		for(int i=0; i<list.length; i++) {
			KeyPair pair = activeKeys.get(i);
			list[i] = getKeyString(pair.key, pair.keyCode);
		}
		
		return list;
	}
}
