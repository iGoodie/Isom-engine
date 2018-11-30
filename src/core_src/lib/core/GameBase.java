package lib.core;

import java.util.Arrays;
import java.util.NoSuchElementException;

import com.programmer.igoodie.utils.io.FileUtils;
import com.programmer.igoodie.utils.log.ConsolePrinter;

import lib.camera.Camera;
import lib.camera.Coordinator;
import lib.config.LaunchBuilder;
import lib.graphics.Cursors;
import lib.graphics.DebugRenderer;
import lib.input.keyboard.Keyboard;
import lib.input.mouse.Mouse;
import lib.stage.Stage;
import lib.util.time.DeltaTimer;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public abstract class GameBase extends AppletBase implements Drawable, IsoConstants {

	public static void main(LaunchBuilder lb) {
		FileUtils.setExternalDataPath(IsoConstants.EXTERNAL_DATA_PATH);

		String[] args = lb.build(); // Build with given builder

		ConsolePrinter.info("Launch Arguments: " + Arrays.toString(args));

		AppletBase.main(args);
	}

	/* Elements */
	protected Stage<? extends GameBase> currentStage;
	protected DeltaTimer deltaTimer = new DeltaTimer();
	protected Coordinator coordinator;

	/* Cameras */
	private Camera[] cameras = new Camera[STD_CAMERA_COUNT];
	private int selectedCam = 0;

	/* Flags */
	public boolean debugEnabled = DEVELOPER_MODE;

	/* Initializers */
	public GameBase() {
		for(int i=0; i<cameras.length; i++) // Init cameras
			cameras[i] = new Camera("Cam#"+i, this, 0, 0);
	}
	
	@Override
	public void settings() {
		// TODO Auto-generated method stub
		super.settings();
	}
	
	/* Game-loop */
	@Override
	public void draw() {
		deltaTimer.update();
		float dt = deltaTimer.deltaSec();

		update(dt);
		render();
		//input();
	}

	@Override
	public void update(float dt) {
		cursor(Cursors.getDefaultCursor());

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

		DebugRenderer.appendLine("Stage: " + currentStage.name);
		DebugRenderer.appendLine("FPS: " + (int)frameRate);
		
		currentStage.render();
		
		if(debugEnabled) { // Render debug if enabled
			DebugRenderer.render();
		}
	}

	/* Getter/Setter Methods */
	public Camera getCamera() {
		return this.cameras[selectedCam];
	}

	public Coordinator getCoordinator() {
		return this.coordinator;
	}

	/* General Methods */
	public void changeStage(Stage<? extends GameBase> s) {
		currentStage.dispose();
		currentStage = s;
	}

	public void selectCamera(int index) {
		this.selectedCam = index;
	}

	public void selectCamera(String label) {
		for(int i=0; i<cameras.length; i++) {
			if(cameras[i].getLabel().equals(label)) {
				this.selectedCam = i;
				return;
			}
		}
		throw new NoSuchElementException("There is no cam with the label: " + label);
	}
	
	/* Overriding Methods */
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
	public void mousePressed(MouseEvent event) {
		Mouse.buttonActivated(event.getX(), event.getY(),
				event.getCount(), event.getButton());
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		Mouse.buttonDeactivated(event.getX(), event.getY(),
				event.getCount(), event.getButton());
	}

	@Override
	public void mouseWheel(MouseEvent e) {
		Mouse.wheelMoved(e.getCount());
	}

	@Override
	public void keyPressed(KeyEvent event) {
		//String printable = "ABCDEFGHIJKLMNOPQRSTUWXYZ1234567890ÖÇŞİĞÜéß.,!? _^~-+/\\*=()[]{}<>$₺@£#%½&'\";`";
		//ConsoleLogger.debug("KeyPressed: %s %b", Keyboard.getKeyString(key, keyCode), printable.indexOf(Character.toUpperCase(key)) != -1); //Log pressed key
		//ConsoleLogger.debug(event.getModifiers());
		//ConsoleLogger.debug(event.getNative());
		//ConsoleLogger.debug("Key Pressed: %s", Keyboard.getKeyString(key, keyCode));

		//If a meta is on. TODO: regulate this
		if(event.getModifiers()!=0) {
			if(keyCode!=0x00000010 && keyCode!=0x00000011 && keyCode!=0x00000012) { //And it's not CTRL, ALT or SHIFT
				if(key!='\uFFFF' && key!='\u0000') {
					key = (char) keyCode;
				}
			}
		}

		Keyboard.keyActivated(key, keyCode);

		if(Keyboard.KEY_ESC.equals(key, keyCode)) { //ESC pressed
			key = (char) (keyCode = 0); //Reset signal
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		//ConsoleLogger.debug("KeyReleased: %s", Keyboard.getKeyString(key, keyCode)); //Log released key

		//If a meta is on. TODO: regulate this
		if(event.getModifiers()!=0) {
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
		Mouse.reset();
	}

	@Override
	public void focusGained() {
		Keyboard.reset(); //TODO: Check validity
		Mouse.reset();
	}

	@Override
	public void dispose() {
		currentStage.dispose();
		ConsolePrinter.debug("Terminating the game...");
		super.dispose();
	}
	
	@Override
	public void exit() {
		currentStage.dispose();
		ConsolePrinter.debug("Terminating the game...");
		super.exit();
	}
}

