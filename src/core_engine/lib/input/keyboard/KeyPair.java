package lib.input.keyboard;

import java.awt.event.KeyEvent;

import lib.util.Stringifier;
import lombok.Getter;

@Getter
public class KeyPair {

	char key;
	int keyCode;

	public KeyPair(char key, int keyCode) {
		this.key = key;
		this.keyCode = keyCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof KeyPair))
			return false;

		KeyPair other = (KeyPair) obj;

		return equals(other.key, other.keyCode);
	}

	public boolean equals(char key, int keyCode) {
		return Character.toUpperCase(key) == Character.toUpperCase(this.key)
				&& keyCode == this.keyCode;
	}

	public boolean isPrintable() {
		if (key == KeyEvent.CHAR_UNDEFINED)
			return false;

		if (Character.isISOControl(key))
			return false;

		Character.UnicodeBlock block = Character.UnicodeBlock.of(key);
		return block != null && block != Character.UnicodeBlock.SPECIALS;
	}

	@Override
	public String toString() {
		return String.format("Key %c(\\u%s) | %d(0x%s)",
				key, Stringifier.asHex(key, 4),
				keyCode, Stringifier.asHex(keyCode, 8));
	}

}
