package core;

import igoodie.utils.benchmark.Performance;
import igoodie.utils.log.ConsolePrinter;
import lib.camera.Coordinator;
import lib.config.LaunchBuilder;
import lib.core.GameBase;
import lib.graphics.CursorRenderer;
import lib.graphics.DebugRenderer;
import lib.input.keyboard.Keyboard;
import lib.resources.ResourceLoader;
import processing.opengl.PJOGL;
import stages.IntroStage;

public class TestGame extends GameBase implements TestConstants {
	
	/* Singleton */
	private static TestGame game;
	
	public static TestGame getGame() {
		return game;
	}

	/* Mechanic Methods */
	@Override
	public void settings() {
		game = this;
		
		size(ST_WIDTH, ST_HEIGHT, P2D);
		PJOGL.setIcon( "icons/icon16.png",
				"icons/icon32.png",
				"icons/icon48.png",
				"icons/icon128.png",
				"icons/icon256.png"); //favicons
	}

	@Override
	public void setup() {
		surface.setTitle(WINDOW_TITLE);
		surface.setResizable(false);
		
		frameRate(ST_FPS_LIMIT);

		smooth();

		selectCamera(0);
		getCamera().resize(ST_WIDTH, ST_HEIGHT);

		// Some example splash info msges
		ResourceLoader.submitLine("Feeding the beast..");
		ResourceLoader.submitLine("Prepping the lava pits..");
		ResourceLoader.submitLine("Sharpening the horns of demons..");
		ResourceLoader.submitLine("Straightening out the halos..");
		
		DebugRenderer.setParent(this);
		CursorRenderer.setParent(this);

		coordinator = new Coordinator(this, 128, 64);
		currentStage = new IntroStage();
		deltaTimer.reset(); //Ignore blackscreen dt before gameloop
	}

	/* Game Loop */
	@Override
	public void update(float dt) {
		CursorRenderer.setDefaultCursor();
		
		if(Keyboard.isKeyActive(Keyboard.KEY_ALT, Keyboard.KEY_F4)) { //ALT+F4 to exit
			exit();
		}
		
		//Testing 20 tick updates
		if(frameCount % 20 == 0) currentStage.updateTick();
		
		//Updates
		getCamera().update(dt);
		currentStage.update(dt);
	}

	@Override
	public void render() {
		background(0xFF_000000);

		if(debugEnabled) { // Pre-debug rendering
			DebugRenderer.appendLine("Stage: " + currentStage.name);
		}
		
		currentStage.render();
		
		if(debugEnabled) { // Post-debug rendering
			DebugRenderer.render();
		}
	}
	
	/* Unique Main Method */
	public static void main(String[] args) {
		if(Performance.getOS() != "windows") { // Check OS compatibility
			ConsolePrinter.error(GAME_NAME + " only supports Windows OS.");
			return;
		}
		
		// Initialize launch builder
		LaunchBuilder builder = new LaunchBuilder(TestGame.class, args);
		builder.argDisplayPrimaryMonitor();
		builder.argWindowColor(0xFF_000000);

		GameBase.main(builder); //Continues on other thread (sync)
	}

}
