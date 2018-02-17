package lib.maths;

import java.util.Random;

public class IsoMath {
	private static final Random random = new Random();
	
	public static int randomInt() {
		return random.nextInt();
	}
	
	public static int randomInt(int min, int max) {
		return random.nextInt(max - min + 1) + min;
	}

	public static float randomFloat() {
		return random.nextFloat();
	}
	
	public static float randomFloat(float min, float max) {
		return random.nextFloat() * (max-min) + min;
	}
	
	public static int clamp(int value, int min, int max) {
		if(value < min) return min;
		if(value > max) return max;
		return value;
	}
	
	public static float clamp(float value, float min, float max) {
		if(value < min) return min;
		if(value > max) return max;
		return value;
	}
	
	public static float resolveError(float num, float resolution) {
		float f10 = (float) Math.pow(10, resolution);
		int i10 = (int) f10;
		return (int)(num*i10)/f10;
	}
}
