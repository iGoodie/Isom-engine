package lib.util.time;

/**
 * Counter class that stands for counting-down purposes.
 * Provides pause feature. Unit for time is milliseconds.
 */
public class Countdown {
	
	boolean paused;
	long prevTime;
	int duration;

	public Countdown(int duration) {
		this.duration = duration;
		this.paused = true;
	}

	public boolean isFinished() {
		return duration < 0;
	}

	public int remainingMs() {
		return Math.max(duration, 0);
	}

	public float remainingSec() {
		return Math.max(duration/1000f, 0f);
	}

	public void start() {
		prevTime = System.currentTimeMillis();
		paused = false;
	}

	public void pause() {
		paused = true;
	}

	public void stop() {
		duration = 0;
		paused = true;
	}

	public void update() {
		if(paused) return;
		long now = System.currentTimeMillis();
		duration -= (int) (now -  prevTime);
		prevTime = now;
	}
}