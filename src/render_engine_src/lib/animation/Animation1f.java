package lib.animation;

import processing.core.PApplet;

public class Animation1f {
	public static enum Easing1f {
		LINEAR {
			@Override
			public float interpolate(float from, float distance, float time, float duration) {
				return distance * (time / duration) + from;
			}
		},
		SINE_OUT {
			@Override
			public float interpolate(float from, float distance, float time, float duration) {
				return distance * PApplet.sin(PApplet.HALF_PI * time/duration) + from;
			}
		},
		SINE_IN {
			@Override
			public float interpolate(float from, float distance, float time, float duration) {
				return distance * (1 - PApplet.cos(PApplet.HALF_PI * time/duration)) + from;
			}
		},
		SINE_IN_OUT {
			@Override
			public float interpolate(float from, float distance, float time, float duration) {
				return -distance/2 * (PApplet.cos(PApplet.PI * time/duration) - 1) + from;
			}
		};
		
		public float interpolate(float from, float to, float time, float duration) { return 0; }
	}
	
	public Easing1f easing = Easing1f.LINEAR;
	
	public float from, to;
	public float duration;
	public float tolerance;

	float time = 0;
	
	public Animation1f() {}
	
	public Animation1f(float from, float to, float duration) {
		this(from, to, duration, 0);
	}
	
	public Animation1f(float from, float to, float duration, float tolerance) {
		this.from = from;
		this.to = to;
		this.duration = duration;
		this.tolerance = tolerance;
	}
	
	public boolean isFinished() {
		return time >= duration;
	}
	
	public void finish() {
		time = duration;
	}
	
	public float proceed(float dt) {
		time += dt;
		if(isFinished()) return to;
		
		float distance = to-from;
		if(distance < tolerance) finish();
		
		return easing.interpolate(from, distance, time, duration);
	}
}
