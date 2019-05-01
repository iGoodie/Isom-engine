package net.programmer.igoodie.gameloop;

public class GameClock {

	private static final double RATE_ALPHA = 0.05;

	long startTimeUnix;

	long prevTime;

	float averageRate;

	public GameClock() {
		this.startTimeUnix = System.nanoTime();
		this.prevTime = 0L;
		this.averageRate = 60f;
	}

	public float deltaTime() {
		long now = elapsedTime();
		long dt = now - prevTime;
		prevTime = now;

		// Thanks to average FPS calculation logic of Processing 3.X
		double averageTimeSecs = 1.0 / averageRate;
		averageTimeSecs = (1.0 - RATE_ALPHA) * averageTimeSecs
				+ RATE_ALPHA * toSeconds(dt);
		averageRate = (float) (1.0 / averageTimeSecs);

		return (float) toSeconds(dt);
	}

	private long elapsedTime() {
		return System.nanoTime() - startTimeUnix;
	}

	public float clockRate() {
		return averageRate;
	}

	private double toSeconds(long nano) {
		return nano / 1e9;
	}

	
}
