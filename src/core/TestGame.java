package core;

import java.util.Arrays;

import lib.GameBase;
import lib.Timer.Ticker;
import lib.camera.Coordinator;
import lib.config.LaunchBuilder;
import lib.graphics.CursorRenderer;
import lib.graphics.DebugRenderer;
import lib.input.Keyboard;
import lib.maths.IsoMath;
import lib.maths.IsoVector;
import lib.util.ConsoleLogger;
import processing.core.PImage;
import processing.event.MouseEvent;
import processing.opengl.PJOGL;

public class TestGame extends GameBase implements TestConstants {
	/* Singleton */
	private static TestGame game;

	public static TestGame getGame() {
		return game;
	}

	/* Mechanic Methods */
	private static Ticker tickTimer = new Ticker();

	private PImage test_tile;

	public void settings() {
		game = this;
		size(ST_WIDTH, ST_HEIGHT, P2D);
		PJOGL.setIcon("test.png", "test.png"); //favicon
	}

	public void setup() {
		surface.setTitle(WINDOW_TITLE);
		surface.setResizable(false);
		
		smooth();

		selectCamera(0);
		getCamera().resize(ST_WIDTH, ST_HEIGHT);

		Coordinator.setParent(this);
		DebugRenderer.setParent(this);
		CursorRenderer.setParent(this);

		test_tile = loadImage("test.png");
	}

	public void draw() {
		background(0xFF_000000);
		CursorRenderer.setDefaultCursor();

		update();
		render();

		//Takes input after this line
	}

	/* Game Loop */
	private void update() {
		tickTimer.update();
		float dt = tickTimer.deltaSec();
		float tick = tickTimer.getTick();

		//TODO update
		getCamera().update(dt);
		//getCamera().move(10*dt, 10*dt);
		//getCamera().rotate(PI/360 * dt * 10);
		//getCamera().zoomTo(cos(millis() / 500f) + 1.5f);

		//Boundary Cursor Test
		IsoVector mousePos = new IsoVector(mouseX, mouseY);
		mousePos = Coordinator.canvasToWorld(Coordinator.screenToCanvas(getCamera(), mousePos));
		DebugRenderer.appendLine(3, "Mouse World Pos: " + mousePos.toCastedString2D());
		if((mousePos.x >= 0 && mousePos.x < 5) && (mousePos.y >= 0 && mousePos.y < 3)) {
			CursorRenderer.setCursor("map");
		}
		
		//Debug Toggler
		if(Keyboard.isKeyActiveOnce(Keyboard.KEY_F11)) {
			debugEnabled = !debugEnabled;
		}

		//Keyboard Input Handler
		IsoVector velocity = new IsoVector();
		if(Keyboard.isKeyActive(Keyboard.KEY_UP)) {
			velocity.add(0, 1);
		}
		if(Keyboard.isKeyActive(Keyboard.KEY_DOWN)) {
			velocity.add(0, -1);
		}
		if(Keyboard.isKeyActive(Keyboard.KEY_RIGHT)) {
			velocity.add(1, 0);
		}
		if(Keyboard.isKeyActive(Keyboard.KEY_LEFT)) {
			velocity.add(-1, 0);
		}
		DebugRenderer.appendLine(1, "W-Direction Unit: " + velocity.toCastedString2D());
		velocity.rotate(QUARTER_PI);
		velocity = Coordinator.worldToCanvas(velocity); //World movement
		DebugRenderer.appendLine(1, "C-Direction: " + velocity.toCastedString2D());
		velocity.len(200 * dt); // Normalize, then mult

		getCamera().move(velocity.x, velocity.y);
	}

	private void render() {
		if(debugEnabled) {
			//Draw grid
			grid(20, 0xFF_303030);
		}

		getCamera().attachCamera(); { //Render by camera options
			translate(-64, -32); //-Tw/2, Th/2
			DebugRenderer.appendLine(1, "World Size 5x3");
			for(int i=0; i<5; i++) for(int j=0; j<3; j++) {
				IsoVector canvasPos = Coordinator.worldToCanvas(i, j); //W(i, j) -> Canvas
				image(test_tile, canvasPos.x, canvasPos.y);
			}
			translate(64, 32);
			stroke(255);
			IsoVector xAxis = Coordinator.worldToCanvas(3, 0);
			IsoVector yAxis = Coordinator.worldToCanvas(0, 1);
			line(0, 0, xAxis.x, xAxis.y);
			line(0, 0, yAxis.x, yAxis.y);
			stroke(0);
		}
		getCamera().deattachCamera();

		if(debugEnabled) {
			//Midpoint
			ellipse(width/2, height/2, 10, 10);

			//Debug
			DebugRenderer.appendLine("FPS: " + (int)frameRate);
			DebugRenderer.appendLine("FC: " + frameCount);

			DebugRenderer.appendLine(2, getCamera().toString());
			DebugRenderer.appendLine(2, "Camera World Pos: " + Coordinator.canvasToWorld(getCamera().getCanvasPos()).toCastedString2D());
			DebugRenderer.appendLine(2, "Camera Zoom: " + getCamera().getZoom());

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
	public void keyPressed() {
		//ConsoleLogger.debug("KeyPressed: %s", Keyboard.getKeyString(key, keyCode)); //Log pressed key

		Keyboard.keyPressed(key, keyCode);

		if(Keyboard.KEY_ESC.equals(key, keyCode)) { //ESC pressed
			key = (char) (keyCode = 0); //Reset signal
		}
	}

	@Override
	public void keyReleased() {
		//ConsoleLogger.debug("KeyReleased: %s", Keyboard.getKeyString(key, keyCode)); //Log released key

		Keyboard.keyReleased(key, keyCode);
	}

	/* Unique Main Method */
	public static void main(String[] args) {
		LaunchBuilder builder = new LaunchBuilder(TestGame.class, args);
		builder.argDisplayPrimaryMonitor();
		builder.argWindowColor(0xFF_000000);
		args = builder.build();
		ConsoleLogger.info("Launch Arguments: " + Arrays.toString(args));
		GameBase.main(args); //Continues on other thread (sync)
		//ConsoleProgram.start(new String[]{});
	}
}
