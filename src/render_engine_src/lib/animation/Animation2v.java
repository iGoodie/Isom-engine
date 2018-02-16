package lib.animation;

import lib.maths.IsoVector;
import processing.core.PApplet;

public class Animation2v {
	public static enum Easing {
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
	
	public Easing easing = Easing.LINEAR;
	
	IsoVector from, to;
	float duration;
	float time = 0;
	float toleranceSq;
	
	public Animation2v(IsoVector from, IsoVector to, float duration) {
		this(from, to, duration, 0);
	}
	
	public Animation2v(IsoVector from, IsoVector to, float duration, float tolerance) {
		this.from = from;
		this.to = to;
		this.duration = duration;
		this.toleranceSq = tolerance * tolerance;
	}

	public boolean isFinished() {
		return time >= duration;
	}
	
	public void finish() {
		time = duration;
	}
	
	public IsoVector proceed(float dt) {
		time += dt;
		if(isFinished()) return to;
		
		IsoVector distance = to.copy().sub(from);
		if(distance.magSq() < toleranceSq) finish();
		
		return easing.interpolate(from, distance, time, duration);
	}
}
