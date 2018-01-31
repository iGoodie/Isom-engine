package lib.input;

import java.util.ArrayList;

import lib.util.Stringifier;

public class Keyboard {
	public static class KeyPair {
		char key;
		int keyCode;
		
		private KeyPair(char key, int keyCode) {
			this.key = key;
			this.keyCode = keyCode;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof KeyPair)) return false;
			KeyPair k2 = (KeyPair) obj;
			return (k2.key==this.key) && (k2.keyCode==this.keyCode);
		}
	
		public boolean equals(char key, int keyCode) {
			return this.key==key && this.keyCode==keyCode;
		}
	}
	
	public static final KeyPair KEY_ESC = new KeyPair('\u001B', 0x0000001B);
	
	public static final KeyPair KEY_UP = new KeyPair('\uFFFF', 0x00000026);
	public static final KeyPair KEY_DOWN = new KeyPair('\uFFFF', 0x00000028);
	public static final KeyPair KEY_LEFT = new KeyPair('\uFFFF', 0x00000025);
	public static final KeyPair KEY_RIGHT = new KeyPair('\uFFFF', 0x00000027);
	
	public static final KeyPair KEY_F11 = new KeyPair('\u0000', 0x0000006B);
	
	public static final KeyPair KEY_W = new KeyPair('W', 0x00000057);
	public static final KeyPair KEY_A = new KeyPair('A', 0x00000041);
	public static final KeyPair KEY_S = new KeyPair('S', 0x00000053);
	public static final KeyPair KEY_D = new KeyPair('D', 0x00000044);
	public static final KeyPair KEY_F = new KeyPair('F', 0x00000046);
	public static final KeyPair KEY_E = new KeyPair('E', 0x00000045);
	
	public static final KeyPair KEY_SHIFT = new KeyPair('\uFFFF', 0x00000010);
	public static final KeyPair KEY_CTRL = new KeyPair('\uFFFF', 0x00000011);
	public static final KeyPair KEY_ALT = new KeyPair('\uFFFF', 0x00000012);
	
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
		
		for(int i=0; i<activeKeys.size(); i++) {
			KeyPair pair = activeKeys.get(i);
			if(pair.key!='\uFFFF' && pair.key==key) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean isKeyActive(KeyPair pair) {
		return activeKeys.contains(pair);
	}

	/* To Handle Once */
	public static boolean isKeyActiveOnce(char key, int keyCode) {
		return isKeyActiveOnce(new KeyPair(key, keyCode));
	}

	public static boolean isKeyActiveOnce(char key) {
		if(key == '\uFFFF') throw new IllegalArgumentException("Coded key flag can't be passed as an argument.");
		
		key = Character.toUpperCase(key);
		
		for(int i=0; i<activeKeys.size(); i++) {
			KeyPair pair = activeKeys.get(i);
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
