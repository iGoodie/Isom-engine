package lib.input.mouse;

import lib.maths.IsoVector;
import lib.util.Stringifier;

public class MousePress {
	
	int x, y;
	int count;
	int button;
	
	public MousePress(int x, int y, int count, int button) {
		this.x = x;
		this.y = y;
		this.count = count;
		this.button = button;
	}
	
	public IsoVector getPos() {
		return new IsoVector(x, y);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getCount() {
		return count;
	}
	
	public int getButton() {
		return button;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof MousePress)) return false;
		
		MousePress m2 = (MousePress) obj;
		return m2.button == this.button;
	}
	
	public boolean equals(int button) {
		return button == this.button;
	}

	@Override
	public String toString() {
		return String.format("Mouse %d(0x%s) | count:%d",
				button, Stringifier.asHex(button, 4),
				count);
	}
}
