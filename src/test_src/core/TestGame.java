package core;

import java.util.Arrays;

import lib.camera.Coordinator;
import lib.config.LaunchBuilder;
import lib.core.GameBase;
import lib.graphics.CursorRenderer;
import lib.graphics.DebugRenderer;
import lib.input.keyboard.Keyboard;
import lib.input.keyboard.Keys;
import lib.maths.IsoMath;
import lib.util.ConsoleLogger;
import lib.util.Performance;
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
	public void settings() {
		game = this;
		
		size(ST_WIDTH, ST_HEIGHT, P2D);
		PJOGL.setIcon("icon.png", "icon.png"); //favicon
	}

	public void setup() {
		surface.setTitle(WINDOW_TITLE);
		surface.setResizable(false);
		
		frameRate(ST_FPS_LIMIT);

		smooth();

		selectCamera(0);
		getCamera().resize(ST_WIDTH, ST_HEIGHT);

		Coordinator.setParent(this);
		DebugRenderer.setParent(this);
		CursorRenderer.setParent(this);

		currentStage = new IntroStage();
		deltaTimer.reset(); //Ignore blackscreen dt before gameloop
	}

	public void draw() { //A.K.A Gameloop
		CursorRenderer.setDefaultCursor();
		
		if(Keyboard.isKeyActive(Keys.KEY_ALT, Keys.KEY_F4)) { //ALT+F4 to exit
			exit();
		}
		
		update();
		render();
		//input();
	}

	/* Game Loop */
	private void update() {
		deltaTimer.update();
		float dt = deltaTimer.deltaSec();
		//Testing 20 tick updates
		if(frameCount % 20 == 0) currentStage.updateTick();
		
		//Updates
		getCamera().update(dt);
		currentStage.update(dt);
	}

	private void render() {
		if(debugEnabled) {
			DebugRenderer.appendLine("Stage: " + currentStage.name);
		}
		
		background(0xFF_000000);
		currentStage.render();
		
		if(debugEnabled) {
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
		z = IsoMath.resolveError(z, 3); //Remove error;
		z = IsoMath.clamp(z, 0.25f, 2f); //Clamping bw [0.5 , 2.0]
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
		if(Performance.getOS() != "windows") {
			ConsoleLogger.error(GAME_NAME + " only supports Windows OS.");
			return;
		}
		
		LaunchBuilder builder = new LaunchBuilder(TestGame.class, args);
		builder.argDisplayPrimaryMonitor();
		builder.argWindowColor(0xFF_000000);
		
		args = builder.build();
		
		ConsoleLogger.info("Launch Arguments: " + Arrays.toString(args));
		GameBase.main(args); //Continues on other thread (sync)
	}
}
