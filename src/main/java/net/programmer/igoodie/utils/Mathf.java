package net.programmer.igoodie.utils;

public final class Mathf {

	private Mathf() {}

	public static final float PI = (float) Math.PI;
	public static final float TWO_PI = 2f * PI;
	public static final float HALF_PI = PI / 2f;
	public static final float QUARTER_PI = PI / 4f;

	public static float clamp(float val, float min, float max) {
		if (val < min)
			return min;

		if (val > max)
			return max;

		return val;
	}
	
	public static float sqrt(float number) {
		return (float) Math.sqrt(number);
	}

}
