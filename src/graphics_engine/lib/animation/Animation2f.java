package lib.animation;

import lib.maths.IsoVector;

public class Animation2f {
	
	public Easing2f easing = Easing2f.LINEAR;
	
	private IsoVector current;
	public IsoVector from, to;
	public float duration;
	float toleranceSq;

	float time = 0;
	
	public Animation2f(IsoVector from, IsoVector to) {
		this(from, to, 0, 0);
	}
	
	public Animation2f(IsoVector from, IsoVector to, float duration) {
		this(from, to, duration, 0);
	}
	
	public Animation2f(IsoVector from, IsoVector to, float duration, Easing2f easing) {
		this(from, to, duration);
		this.easing = easing;
	}
	
	public Animation2f(IsoVector from, IsoVector to, float duration, float tolerance) {
		this.from = from;
		this.current = new IsoVector(from);
		this.to = to;
		this.duration = duration;
		this.toleranceSq = tolerance * tolerance;
	}

	public void setTolerance(float toleranceSeconds) {
		this.toleranceSq = toleranceSeconds * toleranceSeconds;
	}

	public void finish() {
		time = duration;
	}
	
	public IsoVector proceed(float dt) {
		time += dt;
		if(isFinished()) return to;
		
		IsoVector distance = to.copy().sub(from);
		if(distance.magSq() < toleranceSq)
			finish();
		
		current.set(easing.interpolate(from, distance, time, duration));
		return current;
	}

	public IsoVector getValue() {
		if(isFinished()) return to;
		return current;
	}
	
	public boolean isFinished() {
		return time >= duration;
	}
	
}
