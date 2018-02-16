package lib.input.keyboard;

public class KeyPair {
	char key;
	int keyCode;

	public KeyPair(char key, int keyCode) {
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
