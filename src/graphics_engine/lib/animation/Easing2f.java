package lib.animation;

import com.programmer.igoodie.utils.math.MathUtils;

import lib.maths.IsoVector;
import processing.core.PApplet;

public enum Easing2f {

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
					.mult(MathUtils.sin(PApplet.HALF_PI * time/duration))
					.add(from);
		}
	},
	SINE_IN {
		@Override
		public IsoVector interpolate(IsoVector from, IsoVector distance, float time, float duration) {
			return distance
					.mult(1 - MathUtils.cos(PApplet.HALF_PI * time/duration))
					.add(from);
		}
	},
	SINE_IN_OUT {
		@Override
		public IsoVector interpolate(IsoVector from, IsoVector distance, float time, float duration) {
			return distance
					.div(-2)
					.mult(MathUtils.cos(PApplet.PI * time/duration) - 1)
					.add(from);
		}
	};

	public IsoVector interpolate(IsoVector from, IsoVector to, float time, float duration) { return null; }
}