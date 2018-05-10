package lib.util.time;

/**
 * Ticker class that stands for measuring delta time between time points
 * and measuring elapsed time. Unit for time is milliseconds
 */
public class DeltaTimer {
	
	long startTime, prevTime;
	int dt, elapsed;
	int tick;

	public DeltaTimer() {
		this.startTime = System.currentTimeMillis();
		this.prevTime = this.startTime;
	}

	public DeltaTimer(long startTime) {
		this.startTime = startTime;
		this.prevTime = startTime;
	}

	public int deltaMs() {
		return dt;
	}

	public float deltaSec() {
		return (dt/1000f);
	}

	public int elapsedMs() {
		return elapsed;
	}

	public float elapsedSec() {
		return (elapsed/1000f);
	}

	public long getStartTime() {
		return startTime;
	}

	public int getTick() {
		return tick;
	}

	public void reset() {
		startTime = System.currentTimeMillis();
		prevTime = startTime;
		dt = elapsed = tick = 0;
	}

	public void update() {
		long now = System.currentTimeMillis();
		dt = (int) (now - prevTime);
		elapsed = (int) (now - startTime);
		prevTime = now;
		tick++;
	}
}