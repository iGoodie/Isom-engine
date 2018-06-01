package core;

import igoodie.utils.benchmark.Performance;
import igoodie.utils.log.ConsolePrinter;
import igoodie.utils.math.MathUtils;
import lib.camera.Coordinator;
import lib.config.LaunchBuilder;
import lib.core.GameBase;
import lib.graphics.CursorRenderer;
import lib.graphics.DebugRenderer;
import lib.input.keyboard.Keyboard;
import lib.input.keyboard.Keys;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
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
		
		if(Keyboard.isKeyActive(Keys.KEY_ALT, Keys.KEY_F4)) { //ALT+F4 to exit
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
	
	/* Input Listeners */
	@Override
	public void mousePressed() {
		/*IsoVector c = Coordinator.screen2Camera(getCamera(), mouseX, mouseY);
		System.out.println(c);*/
		/*IsoVector s2c = Coordinator.screenToCanvas(getCamera(), new IsoVector(mouseX, mouseY));
		System.out.println(s2c);*/
		/*IsoVector w2c = Coordinator.worldToCanvas(new IsoVector(0, 1));
		System.out.println(w2c);*/
	}

	@Override
	public void mouseWheel(MouseEvent e) {
		float z = getCamera().getZoom() + e.getCount() * -0.125f; //Form input
		z = MathUtils.resolveError(z, 3); //Remove error;
		z = MathUtils.clamp(z, 0.25f, 2f); //Clamping bw [0.5 , 2.0]
		getCamera().zoomTo(z);
	}

	@Override
	public void keyPressed(KeyEvent event) {
		//String printable = "ABCDEFGHIJKLMNOPQRSTUWXYZ1234567890ÖÇŞİĞÜéß.,!? _^~-+/\\*=()[]{}<>$₺@£#%½&'\";`";
		//ConsoleLogger.debug("KeyPressed: %s %b", Keyboard.getKeyString(key, keyCode), printable.indexOf(Character.toUpperCase(key)) != -1); //Log pressed key
		//ConsoleLogger.debug(event.getModifiers());
		//ConsoleLogger.debug(event.getNative());
		//ConsoleLogger.debug("Key Pressed: %s", Keyboard.getKeyString(key, keyCode));
		
		if(event.getModifiers()!=0) { //If CTRL, ALT, META or SHIFT is on
			if(keyCode!=0x00000010 && keyCode!=0x00000011 && keyCode!=0x00000012) { //And it's not CTRL, ALT or SHIFT
				if(key!='\uFFFF' && key!='\u0000') {
					key = (char) keyCode;
				}
			}
		}

		Keyboard.keyActivated(key, keyCode);

		if(Keys.KEY_ESC.equals(key, keyCode)) { //ESC pressed
			key = (char) (keyCode = 0); //Reset signal
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		//ConsoleLogger.debug("KeyReleased: %s", Keyboard.getKeyString(key, keyCode)); //Log released key

		if(event.getModifiers()!=0) { //If CTRL, ALT, META or SHIFT is on
			if(keyCode!=0x00000010 && keyCode!=0x00000011 && keyCode!=0x00000012) { //And it's not CTRL, ALT or SHIFT
				if(key!='\uFFFF' && key!='\u0000') {
					key = (char) keyCode;
				}
			}
		}

		Keyboard.keyDeactivated(key, keyCode);
	}

	@Override
	public void focusLost() {
		Keyboard.reset();
	}

	@Override
	public void focusGained() {
		Keyboard.reset(); //TODO: Check validity
	}

	@Override
	public void exit() {
		currentStage.dispose();
		super.exit();
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
