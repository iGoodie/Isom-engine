package lib;

public class Timer {
	/**
	 * Ticker class that stands for measuring delta time between time points
	 * and measuring elapsed time. Unit for time is milliseconds
	 */
	public static class TickTimer {
		long startTime, prevTime;
		int dt, elapsed;
		int tick;
		
		public TickTimer() {
			this.startTime = System.currentTimeMillis();
			this.prevTime = this.startTime;
		}
		
		public TickTimer(long startTime) {
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
		
		public void update() {
			long now = System.currentTimeMillis();
			dt = (int) (now - prevTime);
			elapsed = (int) (now - startTime);
			prevTime = now;
			tick++;
		}
	}
	
	/**
	 * Counter class that stands for counting-down purposes.
	 * Provides pause feature. Unit for time is milliseconds.
	 */
	public static class Countdown {
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
}