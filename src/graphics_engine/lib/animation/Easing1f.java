package lib.animation;

import com.programmer.igoodie.utils.math.MathUtils;

import processing.core.PApplet;

public enum Easing1f {
	
	LINEAR {
		@Override
		public float interpolate(float from, float distance, float time, float duration) {
			return distance * (time / duration) + from;
		}
	},
	SINE_OUT {
		@Override
		public float interpolate(float from, float distance, float time, float duration) {
			return distance * MathUtils.sin(PApplet.HALF_PI * time/duration) + from;
		}
	},
	SINE_IN {
		@Override
		public float interpolate(float from, float distance, float time, float duration) {
			return distance * (1 - MathUtils.cos(PApplet.HALF_PI * time/duration)) + from;
		}
	},
	SINE_IN_OUT {
		@Override
		public float interpolate(float from, float distance, float time, float duration) {
			return -distance/2 * (MathUtils.cos(PApplet.PI * time/duration) - 1) + from;
		}
	};

	public float interpolate(float from, float to, float time, float duration) { return 0; }
}