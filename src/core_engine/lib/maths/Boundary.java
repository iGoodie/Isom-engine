package lib.maths;

import lombok.Getter;
import processing.core.PVector;

@Getter
public class Boundary {

	private PVector position;
	private float width, height;

	public Boundary(float x, float y, float width, float height) {
		this.position = new PVector(x, y);
		this.width = width;
		this.height = height;
	}
	
	public boolean inRange(float x, float y) {
		return position.x <= x && x <= position.x + width
				&& position.y <= y && y <= position.y + height;
	}
	
	public boolean inRange(IsoVector v) {
		return inRange(v.x, v.y);
	}
	
}
