package net.programmer.igoodie.input.state;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MouseStroke implements Comparable<MouseStroke> {
	
	private int button;
	
	@Override
	public int compareTo(MouseStroke other) {
		return Integer.compare(this.button, other.button);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof MouseStroke))
			return false;

		MouseStroke other = (MouseStroke) obj;
		
		return this.button == other.button;
	}
	
	@Override
	public String toString() {
		return String.format("[Button: %d]", button);
	}
	
}
