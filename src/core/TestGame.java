package core;

import java.util.Arrays;

import lib.GameBase;
import lib.Timer.Ticker;
import lib.camera.Coordinator;
import lib.config.LaunchBuilder;
import lib.graphics.DebugRenderer;
import lib.input.Keyboard;
import lib.maths.IsoMath;
import lib.maths.IsoVector;
import lib.util.ConsoleLogger;
import processing.core.PImage;
import processing.event.MouseEvent;

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
	}
	
	public void setup() {
		surface.setTitle(WINDOW_TITLE);
		surface.setResizable(false);
		
		smooth();
		
		selectCamera(0);
		getCamera().resize(ST_WIDTH, ST_HEIGHT);
		
		Coordinator.setParent(this);
		DebugRenderer.setParent(this);
		
		test_tile = loadImage("test.png");
	}
	
	public void draw() {
		background(0xFF_000000);
		
		update();
		
		render();
		
		//Takes input after this line
	}
	
	private void update() {
		tickTimer.update();
		float dt = tickTimer.deltaSec();
		float tick = tickTimer.getTick();
		
		//TODO update
		getCamera().update(dt);
		//getCamera().move(10*dt, 10*dt);
		//getCamera().rotate(PI/360 * dt * 10);
		//getCamera().zoomTo(cos(millis() / 500f) + 1.5f);
		
		if(Keyboard.isKeyActiveOnce(Keyboard.KEY_F11)) {
			debugEnabled = !debugEnabled;
		}
		
		IsoVector velocity = new IsoVector();
		if(Keyboard.isKeyActive(Keyboard.KEY_UP)) {
			velocity.add(0, -1);
		}
		if(Keyboard.isKeyActive(Keyboard.KEY_DOWN)) {
			velocity.add(0, 1);
		}
		if(Keyboard.isKeyActive(Keyboard.KEY_LEFT)) {
			velocity.add(-1, 0);
		}
		if(Keyboard.isKeyActive(Keyboard.KEY_RIGHT)) {
			velocity.add(1, 0);
		}
		velocity.len(200 * dt);
		
		getCamera().move(velocity.x, velocity.y);
	}
	
	private void render() {
		renderPreDebug();
		
		getCamera().attachCamera(); {
			circle(0, 0, 10);
		}
		getCamera().deattachCamera();
		
		getCamera().attachCamera(); { //Render by camera options
			translate(-64, -32);
			for(int i=0; i<5; i++) {
				for(int j=0; j<3; j++) {
					IsoVector pos = Coordinator.worldToCanvas(i, j);
					image(test_tile, pos.x, pos.y);
				}
			}
		}
		getCamera().deattachCamera();
		
		renderPostDebug();
	}
	
	/* Debug Renders */
	private void renderPreDebug() {
		if(!debugEnabled) return;
		
		//Draw grid
		grid(20, 0xFF_303030);
	}
	
	private void renderPostDebug() {
		if(!debugEnabled) return;
		
		//Midpoint
		ellipse(width/2, height/2, 10, 10);
		
		//Debug
		DebugRenderer.appendLine("FPS: " + (int)frameRate);
		DebugRenderer.appendLine("FC: " + frameCount);
		
		DebugRenderer.appendLine(2, getCamera().toString());
		DebugRenderer.appendLine(2, "Camera World Pos: " + Coordinator.canvasToWorld(getCamera().getCanvasPos()).toCastedString2D());
		DebugRenderer.appendLine(2, "Zoom: " + getCamera().getZoom());
		
		DebugRenderer.render();
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
