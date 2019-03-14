package lib.input.keyboard;


import java.awt.event.KeyEvent;

import lib.util.Stringifier;

public class KeyPair {
	
	char key;
	int keyCode;

	public KeyPair(char key, int keyCode) {
		this.key = key;
		this.keyCode = keyCode;
	}

	public char getKey() {
		return key;
	}
	
	public int getKeyCode() {
		return keyCode;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof KeyPair)) return false;
		
		KeyPair k2 = (KeyPair) obj;

		return Character.toUpperCase(k2.key) == Character.toUpperCase(this.key)
				&& k2.keyCode == this.keyCode;
	}

	public boolean equals(char key, int keyCode) {
		return Character.toUpperCase(key) == Character.toUpperCase(this.key)
				&& keyCode == this.keyCode;
	}
	
	public boolean isPrintable() {
		Character.UnicodeBlock block = Character.UnicodeBlock.of(key);
	    return (!Character.isISOControl(key)) &&
	            key != KeyEvent.CHAR_UNDEFINED &&
	            block != null &&
	            block != Character.UnicodeBlock.SPECIALS;
	}
	
	@Override
	public String toString() {
		return String.format("Key %c(\\u%s) | %d(0x%s)", 
				key, Stringifier.asHex(key, 4), 
				keyCode, Stringifier.asHex(keyCode, 8));
	}
}
