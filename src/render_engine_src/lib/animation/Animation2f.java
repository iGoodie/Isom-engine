package lib.animation;

import lib.maths.IsoVector;
import processing.core.PApplet;

public class Animation2f {
	public static enum Easing2f {
		LINEAR {
			@Override
			public IsoVector interpolate(IsoVector from, IsoVector distance, float time, float duration) {
				return distance
						.mult(time / duration)
						.add(from);
			}
		},
		SINE_OUT {
			@Override
			public IsoVector interpolate(IsoVector from, IsoVector distance, float time, float duration) {
				return distance
						.mult(PApplet.sin(PApplet.HALF_PI * time/duration))
						.add(from);
			}
		},
		SINE_IN {
			@Override
			public IsoVector interpolate(IsoVector from, IsoVector distance, float time, float duration) {
				return distance
						.mult(1 - PApplet.cos(PApplet.HALF_PI * time/duration))
						.add(from);
			}
		},
		SINE_IN_OUT {
			@Override
			public IsoVector interpolate(IsoVector from, IsoVector distance, float time, float duration) {
				return distance
						.div(-2)
						.mult(PApplet.cos(PApplet.PI * time/duration) - 1)
						.add(from);
			}
		};
		
		public IsoVector interpolate(IsoVector from, IsoVector to, float time, float duration) { return null; }
	}
	
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