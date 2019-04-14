package demo;

import com.programmer.igoodie.utils.benchmark.Performance;
import com.programmer.igoodie.utils.log.ConsolePrinter;

import demo.console.GameConsole;
import demo.stages.IntroStage;
import lib.camera.Coordinator;
import lib.config.LaunchBuilder;
import lib.core.GameBase;
import lib.graphics.Fonts;
import lib.input.keyboard.KeyPair;
import lib.input.keyboard.Keyboard;
import lib.input.keyboard.KeyboardListener;
import processing.opengl.PJOGL;

public class TestGame extends GameBase implements TestConstants, KeyboardListener {

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
		super.settings(); // TODO: Find less-ugly way to handle

		game = this;
		
		Keyboard.subscribe(this);

		size(ST_WIDTH, ST_HEIGHT, P2D);

		PJOGL.setIcon("icons/icon16.png", // set default favicons
				"icons/icon32.png",
				"icons/icon48.png",
				"icons/icon128.png",
				"icons/icon256.png");
	}

	@Override
	public void setup() {
		surface.setTitle(WINDOW_TITLE);
		surface.setResizable(true);

		// Render related options
		frameRate(ST_FPS_LIMIT);
		smooth();
		selectCamera(0);
		getCamera().resize(ST_WIDTH, ST_HEIGHT);

		console = new GameConsole(this);

		// Load Default Fonts
		textFont(Fonts.getFont("default"));
		Fonts.pushFont("intro-f1", createFont("fonts/Freshman.ttf", 40, true));
		Fonts.pushFont("console", createFont("fonts/Modes.ttf", 12, true));

		coordinator = new Coordinator(this, 128, 64);
		currentStage = new IntroStage(this);
		deltaTimer.reset(); // Ignore blackscreen dt before gameloop
	}

	@Override
	public void update(float dt) {
		super.update(dt);

		if (console.enabled)
			console.update(dt);
	}

	@Override
	public void render() {
		super.render();

		if (console.enabled)
			console.render();
	}

	@Override
	public void keyPressed(KeyPair pair) {
		if (pair.equals(Keyboard.KEY_F12)) // Console Toggler
			console.toggle();

		if (console.enabled) {
			StringBuffer inputBuffer = console.getKernel().getInputBuffer();
			if (pair.equals(Keyboard.KEY_WIN_ENTER)) {
				console.getKernel().parseAndExecute();

			} else if (pair.equals(Keyboard.KEY_WIN_BACKSPACE)) {
				if (inputBuffer.length() > "> ".length())
					inputBuffer.deleteCharAt(inputBuffer.length() - 1);

			} else if (pair.equals(Keyboard.KEY_ESC)) {
				console.setEnabled(false);

			} else {
				if (pair.isPrintable())
					inputBuffer.append(pair.getKey());
			}
		}
	}

	@Override
	public void dispose() {
		Keyboard.unsubscribe(this);
		super.dispose();
	}

	/* Unique Main Method */
	public static void main(String[] args) {
		if (!Performance.getOS().equals("windows")) { // Check OS compatibility
			ConsolePrinter.error(GAME_NAME + " only supports Windows OS.");
			return;
		}

		// Initialize launch builder
		LaunchBuilder builder = new LaunchBuilder(TestGame.class, args);
		builder.argDisplayPrimaryMonitor();
		builder.argWindowColor(0xFF_000000);

		GameBase.main(TestGame.class, builder); // Continues on other thread (async)
	}

}
