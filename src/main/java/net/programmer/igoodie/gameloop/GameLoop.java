package net.programmer.igoodie.gameloop;

import lombok.Getter;
import lombok.Setter;

public abstract class GameLoop implements Runnable, Drawable {
	
	public static void main(GameLoop loop) {
		new Thread(loop).start();
	}

	private GameClock clock;

	protected @Getter boolean running;

	private @Setter float targetFPS;
	private float prevSleepSecs;

	protected int frameCount;
	protected float frameRate;

	protected float elapsedTime;

	protected GameLoop() {
		this.running = true;
		this.targetFPS = 60f;
		this.prevSleepSecs = 0f;
	}

	@Override
	public void run() {
		setup();

		this.clock = new GameClock();
		
		while (running) {
			loop();
		}
		
		onExit();
	}
	
	public void loop() {
		float dt = clock.deltaTime();

		pollInput();
		
		preUpdate(dt);
		update(dt);
		postUpdate(dt);

		preRender();
		render();
		postRender();

		frameCount++;
		frameRate = clock.clockRate();
		elapsedTime += dt;

		final float idealSleepSecs = 1f / targetFPS;
		float sleepSecs = idealSleepSecs - (dt - prevSleepSecs);
		if (sleepSecs > 0)
			sleep(sleepSecs);
		prevSleepSecs = sleepSecs;
	}
	
	private void sleep(float seconds) {
		try {
			long millis = (long) (seconds * 1000L);
			Thread.sleep(millis);
		} catch (InterruptedException e) {}
	}

	/* Setup */
	public abstract void setup();

	/* Update */
	protected void preUpdate(float dt) {}

	@Override
	public abstract void update(float dt);

	protected void postUpdate(float dt) {}

	/* Render */
	protected void preRender() {}

	@Override
	public abstract void render();

	protected void postRender() {}

	/* ------- */
	protected abstract void pollInput();

	protected void onExit() {}

	public void exit() {
		running = false;
	}

}
