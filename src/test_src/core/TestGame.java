package core;

import java.util.Arrays;

import lib.GameBase;
import lib.Timer.TickTimer;
import lib.animation.Animation2v;
import lib.camera.Coordinator;
import lib.config.LaunchBuilder;
import lib.graphics.CursorRenderer;
import lib.graphics.DebugRenderer;
import lib.input.keyboard.Keyboard;
import lib.input.keyboard.Keys;
import lib.maths.IsoMath;
import lib.maths.IsoVector;
import lib.util.ConsoleLogger;
import processing.core.PImage;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.opengl.PJOGL;

public class TestGame extends GameBase implements TestConstants {
	/* Singleton */
	private static TestGame game;

	public static TestGame getGame() {
		return game;
	}

	/* Mechanic Methods */
	private static TickTimer tickTimer = new TickTimer();

	private PImage test_tile;
	private PImage test_tile_cursor;
	private IsoVector map_size = new IsoVector(30, 20);

	public void settings() {
		game = this;
		size(ST_WIDTH, ST_HEIGHT, P2D);
		PJOGL.setIcon("test.png", "test.png"); //favicon
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

		test_tile = loadImage("test.png");
		test_tile_cursor = loadImage("cursor_tile.png");
	}

	public void draw() {
		background(0xFF_000000);
		CursorRenderer.setDefaultCursor();
		
		if(Keyboard.isKeyActive(Keys.KEY_ALT, Keys.KEY_F4)) { //ALT+F4 to exit
			exit();
		}

		update();
		render();

		//Takes input after this line
	}

	/* Game Loop */
	private void update() {
		tickTimer.update();
		float dt = tickTimer.deltaSec();
		float tick = tickTimer.getTick();
		
		DebugRenderer.appendLine("Significant Tick: " + (int)(tick/20)); //Testing 20 tick updates

		if(tick % 20 == 0) {
			//TODO tick update here
		}
		
		//TODO update here
		
		//TODO remove
		getCamera().update(dt);
		//getCamera().move(10*dt, 10*dt);
		//getCamera().rotate(PI/360 * dt * 10);
		//getCamera().zoomTo(cos(millis() / 500f) + 1.5f);

		//Boundary Cursor Test
		IsoVector mousePos = new IsoVector(mouseX, mouseY);
		mousePos = mousePos.toCanvas(getCamera()).toWorld();
		DebugRenderer.appendLine(3, "Mouse World Pos: " + mousePos.toCastedString2D());
		if((mousePos.x >= 0 && mousePos.x < map_size.x) && (mousePos.y >= 0 && mousePos.y < map_size.y)) {
			CursorRenderer.setCursor("map");
		}

		//Debug Toggler
		if(Keyboard.isKeyActiveOnce(Keys.KEY_F11)) {
			debugEnabled = !debugEnabled;
		}

		//Keyboard Input Handler
		IsoVector velocity = new IsoVector(); 
		velocity.plane = IsoVector.WORLD;
		if(Keyboard.isKeyActive(Keys.KEY_W)) {
			velocity.add(0, 1);
		}
		if(Keyboard.isKeyActive(Keys.KEY_S)) {
			velocity.add(0, -1);
		}
		if(Keyboard.isKeyActive(Keys.KEY_D)) {
			velocity.add(1, 0);
		}
		if(Keyboard.isKeyActive(Keys.KEY_A)) {
			velocity.add(-1, 0);
		}
		DebugRenderer.appendLine(1, "W-Velocity Unit: " + velocity.toCastedString2D());
		velocity.rotate(QUARTER_PI);
		int speed = 10; //tile per sec
		velocity.len(speed * dt); // Normalize, then mult
		velocity = velocity.toCanvas(); //World movement
		
		if(mousePressed && mouseButton==RIGHT) {
			velocity.set(width/2 - mouseX, height/2 - mouseY);
			velocity.mult(5 * -dt);
		}
		
		DebugRenderer.appendLine(1, "C-Velocity: " + velocity.toCastedString2D());
		getCamera().move(velocity.x, velocity.y);
	}

	private void render() {
		//Pre debug
		if(debugEnabled) {
			//Draw grid
			grid(20, 0xFF_303030);
		}

		getCamera().attachCamera(); //Render by camera options
		{
			pushMatrix();
			translate(-64, -32); //-Tw/2, Th/2
			DebugRenderer.appendLine(1, "World Size 5x3");
			for(int i=0; i<map_size.x; i++) for(int j=0; j<map_size.y; j++) {
				IsoVector canvasPos = Coordinator.worldToCanvas(i, j); //W(i, j) -> Canvas
				image(test_tile, canvasPos.x, canvasPos.y);
			}

			{ //Draw tile cursor on the mouse position
				IsoVector mousePos = new IsoVector(mouseX, mouseY);
				mousePos = Coordinator.screenToWorld(getCamera(), mousePos);
				mousePos = Coordinator.worldToCanvas(mousePos);
				image(test_tile_cursor, mousePos.x, mousePos.y);
				
				mousePos = Coordinator.canvasToScreen(getCamera(), mousePos);
				//DebugRenderer.appendLine(3, mousePos.toCastedString2D());
			}
			popMatrix();
			{ //Draw X and Y axises for debug 
				pushStyle();
				stroke(255);
				IsoVector xAxis = Coordinator.worldToCanvas(3, 0);
				IsoVector yAxis = Coordinator.worldToCanvas(0, 1);
				line(0, 0, xAxis.x, xAxis.y);
				line(0, 0, yAxis.x, yAxis.y);
				popStyle();
			}
		}
		getCamera().deattachCamera();

		//Post debug
		if(debugEnabled) {
			//Midpoint
			ellipse(width/2, height/2, 10, 10);

			//Debug
			DebugRenderer.appendLine("FPS: " + (int)frameRate);
			DebugRenderer.appendLine("FC: " + frameCount);

			DebugRenderer.appendLine(2, getCamera().toString());
			DebugRenderer.appendLine(2, "Camera World Pos: " + Coordinator.canvasToWorld(getCamera().getCanvasPos()).toCastedString2D());
			DebugRenderer.appendLine(2, "Camera Zoom: " + getCamera().getZoom());

			String[] activeKeys = Keyboard.getKeyList();
			for(String k : activeKeys) DebugRenderer.appendLine(3, k);

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
		float z = getCamera().getZoom() + e.getCount() * -0.5f; //Form input
		z = IsoMath.resolveError(z, 1); //Remove error;
		z = IsoMath.clamp(z, 0.5f, 2f); //Clamping bw [0.5 , 2.0]
		getCamera().zoomTo(z);
	}

	@Override
	public void keyPressed(KeyEvent event) {
		//String printable = "ABCDEFGHIJKLMNOPQRSTUWXYZ1234567890ÖÇŞİĞÜéß.,!? _^~-+/\\*=()[]{}<>$₺@£#%½&'\";`";
		//ConsoleLogger.debug("KeyPressed: %s %b", Keyboard.getKeyString(key, keyCode), printable.indexOf(Character.toUpperCase(key)) != -1); //Log pressed key
		//ConsoleLogger.debug(event.getModifiers());
		//ConsoleLogger.debug(event.getNative());
		//ConsoleLogger.debug("Key Pressed: %s", Keyboard.getKeyString(key, keyCode));
		
		if(key == '.') {
			getCamera().moveTo(0, 0);
		}
		
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

	/* Unique Main Method */
	public static void main(String[] args) {
		LaunchBuilder builder = new LaunchBuilder(TestGame.class, args);
		builder.argDisplayPrimaryMonitor();
		builder.argWindowColor(0xFF_000000);
		
		args = builder.build();
		
		ConsoleLogger.info("Launch Arguments: " + Arrays.toString(args));
		GameBase.main(args); //Continues on other thread (sync)
	}
}
