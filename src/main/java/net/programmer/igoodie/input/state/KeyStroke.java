package net.programmer.igoodie.input.state;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KeyStroke implements Comparable<KeyStroke> {

	private int key;
	private int keyCode;

	@Override
	public int compareTo(KeyStroke other) {
		return Integer.compare(this.key, other.key);
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof KeyStroke))
			return false;
		
		KeyStroke other = (KeyStroke) obj;
		
		return this.key == other.key;
	}
	
	@Override
	public String toString() {
		return String.format("[Key: %d, Code: %d]", key, keyCode);
	}

}
