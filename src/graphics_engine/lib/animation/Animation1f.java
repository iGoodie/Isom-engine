package lib.animation;

public class Animation1f {
	
	public Easing1f easing = Easing1f.LINEAR;
	
	private float current;
	public float from, to;
	public float duration;
	public float tolerance;

	float time = 0;
	
	public Animation1f() {}
	
	public Animation1f(float from, float to, float duration) {
		this(from, to, duration, 0);
	}
	
	public Animation1f(float from, float to, float duration, Easing1f easing) {
		this(from, to, duration);
		this.easing = easing;
	}
	
	public Animation1f(float from, float to, float duration, float tolerance) {
		this.from = from;
		this.to = to;
		this.duration = duration;
		this.tolerance = tolerance;
	}
	
	public void reset() {
		time = 0;
	}
	
	public void finish() {
		time = duration;
	}
	
	public float proceed(float dt) {
		time += dt;
		if(isFinished()) return to;
		
		float distance = to-from;
		if(distance < tolerance) 
			finish();
		
		current = easing.interpolate(from, distance, time, duration);
		return current;
	}
	
	public float getValue() {
		if(isFinished()) return to;
		return current;
	}
	
	public boolean isFinished() {
		return time >= duration;
	}
	
}
