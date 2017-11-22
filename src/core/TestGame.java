package core;

import lib.GameBase;
import lib.Timer.Ticker;
import lib.config.LaunchBuilder;
import lib.util.ArrayUtils;

public class TestGame extends GameBase implements TestConstants {
	private static TestGame game;
	public static TestGame getGame() {
		return game;
	}
	
	private static Ticker tickTimer = new Ticker();
	
	public void settings() {
		game = this;
		size(ST_WIDTH, ST_HEIGHT, P2D);
	}
	
	public void setup() {
		surface.setTitle(WINDOW_TITLE);
		surface.setResizable(false);
		
		selectCamera(0);
	}
	
	public void draw() {
		background(0xFF_000000);
		
		tickTimer.update();
		update(tickTimer.deltaSec(), tickTimer.getTick());
		
		renderPreDebug();
		
		render();
		
		renderPostDebug();
		
		//Takes input after this line
	}
	
	private void update(float dt, int tick) {
		//TODO update
		getCamera().update(dt);
		getCamera().move(10*dt, 10*dt);
		getCamera().rotate(PI/360 * dt * 10);
	}
	
	private void render() {
		getCamera().attachCamera(); { //Render by camera options
			//TODO render
			ellipse(0, 0, 50, 50);
			text("0, 0", 10, 50);
		}
		getCamera().deattachCamera();
	}
	
	private void renderPreDebug() {
		ellipse(width/2, height/2, 10, 10);
	}
	
	private void renderPostDebug() {
		getCamera().renderDebug(this);
	}
	
	/* Unique Main Method */
	public static void main(String[] args) {
		LaunchBuilder lc = new LaunchBuilder(TestGame.class, args);
		lc.argDisplayPrimaryMonitor();
		lc.argWindowColor(0xFF_000000);
		args = lc.build();
		ArrayUtils.printArray(args, "Launch Arguments");
		GameBase.main(args);
	}
}
