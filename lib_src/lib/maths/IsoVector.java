package lib.maths;

import processing.core.PVector;

public class IsoVector extends PVector {
	private static final long serialVersionUID = 001_000L;

	public static IsoVector add(IsoVector v1, IsoVector v2) {
		return new IsoVector(v1.x+v2.x, v1.y+v2.y);
	}

	public static IsoVector sub(IsoVector v1, IsoVector v2) {
		return new IsoVector(v1.x-v2.x, v1.y-v2.y);
	}
	
	public static IsoVector mult(IsoVector v1, float n) {
		return new IsoVector(v1.x*n, v1.y*n);
	}
	
	public static IsoVector cross(IsoVector v1, IsoVector v2) {
		return new IsoVector(v1.x*v2.x, v1.y*v2.y);
	}
	
	public static IsoVector div(IsoVector v1, float n) {
		return new IsoVector(v1.x/n, v1.y/n);
	}
	
	public IsoVector() {
		super();
	}
	
	public IsoVector(float x, float y) {
		super(x, y);
	}
	
	public IsoVector(float x, float y, float z) {
		super(x, y, z);
	}

	public void len(float len) {
		normalize();
		mult(len);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append((int)x + ",");
		sb.append((int)y + ",");
		sb.append((int)z + "}");
		return sb.toString();
	}
}
