package lib.core;

import java.util.Arrays;
import java.util.NoSuchElementException;

import igoodie.utils.io.FileUtils;
import igoodie.utils.log.ConsolePrinter;
import lib.camera.Camera;
import lib.camera.Coordinator;
import lib.config.LaunchBuilder;
import lib.graphics.DebugRenderer;
import lib.image.PivotImage;
import lib.input.keyboard.Keyboard;
import lib.input.mouse.Mouse;
import lib.stage.Stage;
import lib.util.time.DeltaTimer;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public abstract class GameBase extends PApplet implements Drawable, IsoConstants {
	
	public static void main(LaunchBuilder lb) {
		FileUtils.setExternalDataPath(IsoConstants.EXTERNAL_DATA_PATH);
		
		String[] args = lb.build(); // Build with given builder
		
		ConsolePrinter.info("Launch Arguments: " + Arrays.toString(args));
		
		PApplet.main(args);
	}
	
	/* Game time */
	protected DeltaTimer deltaTimer = new DeltaTimer();
	protected float blackScreenTime;
	
	/* Stage */
	protected Stage currentStage;
	
	/* Coordinator */
	protected Coordinator coordinator;
	
	/* Cameras */
	private Camera[] cameras = new Camera[STD_CAMERA_COUNT];
	private int selectedCam = 0;
	
	/* Flags */
	public boolean debugEnabled = true;
	
	/* Constructors */
	public GameBase() {
		for(int i=0; i<cameras.length; i++) cameras[i] = new Camera("Cam#"+i, 0, 0);
	}
	
	/* Game-loop */
	@Override
	public void draw() {
		deltaTimer.update();
		float dt = deltaTimer.deltaSec();
		
		update(dt);
		render();
		//input();
		
		DebugRenderer.appendLine("Pressed Count: " + Mouse.getActivePresses().size());
	}
	
	/* Getter/Setter Methods */
	public Camera getCamera() {
		return this.cameras[selectedCam];
	}

	public Coordinator getCoordinator() {
		return this.coordinator;
	}
	
	/* General Methods */
	public void changeStage(Stage s) {
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
	
	/* Text-related methods */
	public void text(Object o, float x, float y) {
		text(o.toString(), x, y);
	}
	
	public void textWithStroke(String str, float x, float y) { //TODO: Parameterize fill colors
		fill(0);
		text(str, x-1, y);
		text(str, x+1, y);
		text(str, x, y-1);
		text(str, x, y+1);
		fill(255);
		text(str, x, y);
	}
	
	public float textHeight() {
		return textAscent() + textDescent();
	}
	
	/* Extension to Processing methods */
	public void circle(float x, float y, float r) {
		ellipse(x, y, r, r);
	}

	public void grid(int offset, int strokeColor) {
		pushStyle();
		stroke(strokeColor);
		for(int i=offset; i<width; i+=offset) { //Vertical lines
			line(i, 0, i, height);
		}
		for(int i=offset; i<height; i+=offset) { //Horizontal lines
			line(0, i, width, i);			
		}
		popStyle();
	}

	public void image(PivotImage img, float x, float y) {
		image((PImage)img, x-img.pivot.x, y-img.pivot.y);
	}

	public PivotImage loadImage(String filename, float x, float y) {
		return new PivotImage(loadImage(filename), x, y);
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
		
		if(event.getModifiers()!=0) { //If CTRL, ALT, META or SHIFT is on
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
		Mouse.reset();
	}

	@Override
	public void focusGained() {
		Keyboard.reset(); //TODO: Check validity
		Mouse.reset();
	}
	
	@Override
	public void exit() {
		currentStage.dispose();
		super.exit();
	}
}

