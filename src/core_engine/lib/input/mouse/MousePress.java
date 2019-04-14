package lib.input.mouse;

import lib.maths.IsoVector;
import lib.util.Stringifier;
import lombok.Getter;

@Getter
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

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof MousePress)) return false;
		
		MousePress other = (MousePress) obj;
		
		return equals(other.button);
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
