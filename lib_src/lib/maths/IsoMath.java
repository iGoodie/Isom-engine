package lib.maths;

import java.util.Random;

public class IsoMath {
	private static final Random random = new Random();
	
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
	
	public static int randomInt(int min, int max) {
		return random.nextInt(max - min + 1) + min;
	}
	
	public static float randomFloat(float min, float max) {
		return random.nextFloat() * (max-min) + min;
	}
}