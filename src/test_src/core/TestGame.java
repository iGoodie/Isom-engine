package core;

import console.GameConsole;
import igoodie.utils.benchmark.Performance;
import igoodie.utils.log.ConsolePrinter;
import lib.camera.Coordinator;
import lib.config.LaunchBuilder;
import lib.core.GameBase;
import lib.graphics.CursorRenderer;
import lib.graphics.DebugRenderer;
import lib.graphics.Fonts;
import processing.opengl.PJOGL;
import stages.IntroStage;

public class TestGame extends GameBase implements TestConstants {

	/* Singleton */
	private static TestGame game;

	public static TestGame getGame() {
		return game;
	}

	/* Fields */
	public GameConsole console;
	
	/* Mechanic Methods */
	@Override
	public void settings() {
		game = this;

		size(ST_WIDTH, ST_HEIGHT, P2D);
		
		PJOGL.setIcon("icons/icon16.png", //set default favicons
				"icons/icon32.png",
				"icons/icon48.png",
				"icons/icon128.png",
				"icons/icon256.png");
	}

	@Override
	public void setup() {
		surface.setTitle(WINDOW_TITLE);
		surface.setResizable(false);

		// Render related options
		frameRate(ST_FPS_LIMIT);
		smooth();
		selectCamera(0);
		getCamera().resize(ST_WIDTH, ST_HEIGHT);
		
		console = new GameConsole(this);

		// Set Parents
		DebugRenderer.setParent(this);
		CursorRenderer.setParent(this);
		
		// Load Default Fonts
		textFont(Fonts.DEFAULT_FONT);
		Fonts.pushFont("intro-f1", createFont("fonts/Freshman.ttf", 40, true));
		Fonts.pushFont("console", createFont("fonts/Modes.ttf", 12, true));

		coordinator = new Coordinator(this, 128, 64);
		currentStage = new IntroStage(this);
		deltaTimer.reset(); //Ignore blackscreen dt before gameloop
	}

	@Override
	public void update(float dt) {
		super.update(dt);
		
		if(console.enabled)
			console.update(dt);
	}
	
	@Override
	public void render() {
		super.render();
		
		if(console.enabled)
			console.render();
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
