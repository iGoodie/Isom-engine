package stages;

import core.TestGame;
import igoodie.utils.math.MathUtils;
import lib.camera.Coordinator;
import lib.graphics.DebugRenderer;
import lib.input.keyboard.KeyPair;
import lib.input.keyboard.Keyboard;
import lib.input.keyboard.KeyboardListener;
import lib.input.mouse.Mouse;
import lib.input.mouse.MouseListener;
import lib.input.mouse.MousePress;
import lib.maths.IsoVector;
import lib.stage.Stage;
import processing.core.PImage;

public class TestStage extends Stage<TestGame> implements KeyboardListener, MouseListener {

	private IsoVector map_size = new IsoVector(30, 20);

	private PImage test_tile;
	private PImage test_tile_cursor;
	
//	private IsoVector camGrabPoint;
//	private IsoVector camBoundPos;

	public TestStage(TestGame game) {
		super(game);

		name = "Test Stage";

		test_tile = game.loadImage("test.png");
		test_tile_cursor = game.loadImage("cursor_tile.png");
		
		Keyboard.subscribe(this);
		Mouse.subscribe(this);
	}

	@Override
	public void update(float dt) {
//		Coordinator coord = parent.getCoordinator();
//
//		/*//Boundary Cursor Test
//		IsoVector mousePos = new IsoVector(game.mouseX, game.mouseY);
//		mousePos = mousePos.toCanvas(coord, game.getCamera()).toWorld(coord);
//		DebugRenderer.appendLine(3, "Mouse World Pos: " + mousePos.toCastedString2D());
//		if((mousePos.x >= 0 && mousePos.x < map_size.x) && (mousePos.y >= 0 && mousePos.y < map_size.y)) {
//			CursorRenderer.setCursor("map");
//		}*/
//
//		//Keyboard Input Handler
//		IsoVector velocity = IsoVector.createOnWorld(0, 0);
//		if(Keyboard.isKeyActive(Keyboard.KEY_W)) {
//			velocity.add(0, 1);
//		}
//		if(Keyboard.isKeyActive(Keyboard.KEY_S)) {
//			velocity.add(0, -1);
//		}
//		if(Keyboard.isKeyActive(Keyboard.KEY_D)) {
//			velocity.add(1, 0);
//		}
//		if(Keyboard.isKeyActive(Keyboard.KEY_A)) {
//			velocity.add(-1, 0);
//		}
//		DebugRenderer.appendLine(1, "W-Velocity Unit: " + velocity.toCastedString2D());
//		velocity.rotate(TestGame.QUARTER_PI);
//		int speed = 10; //tile per sec
//		velocity.len(speed * dt); // Normalize, then mult
//		velocity = velocity.toCanvas(coord); //World movement
//		DebugRenderer.appendLine(1, "C-Velocity: " + velocity.toCastedString2D());
//		
//		/*if(Mouse.isButtonActive(Mouse.BTN_RIGHT)) {
//			velocity.set(parent.width/2 - parent.mouseX, parent.height/2 - parent.mouseY);
//			velocity.mult(5 * -dt);
//		}*/
//		
//		// if camera grabbed
//		if(camGrabPoint != null) {
//			IsoVector curPos = IsoVector.createOnScreen(parent.mouseX, parent.mouseY);
//			curPos = curPos.toCanvas(parent.getCoordinator(), parent.getCamera());
//			
//			IsoVector diff = IsoVector.sub(camGrabPoint, curPos); // dp = p0 - p1;
//			diff.add(camBoundPos); // c0 + dp
//			
//			parent.getCamera().getCanvasPos().set(diff);
//		}
//		else {
//			parent.getCamera().move(velocity.x, velocity.y);
//		}
	}

	@Override
	public void render() {
		Coordinator coord = parent.getCoordinator();

		//Pre debug
		if(parent.debugEnabled) {
			//Draw grid
			parent.grid(20, 0xFF_303030);
		}

		parent.getCamera().attachCamera(); //Render by camera options
		{
			parent.pushMatrix();
			parent.translate(-64, -32); //-Tw/2, Th/2
			for(int i=0; i<map_size.x; i++) for(int j=0; j<map_size.y; j++) {
				IsoVector canvasPos = coord.worldToCanvas(i, j); //W(i, j) -> Canvas
				parent.image(test_tile, canvasPos.x, canvasPos.y);
			}

			{ //Draw tile cursor on the mouse position
				IsoVector mousePos = new IsoVector(parent.mouseX, parent.mouseY);
				mousePos = coord.screenToWorld(parent.getCamera(), mousePos);
				mousePos = coord.worldToCanvas(mousePos);
				parent.image(test_tile_cursor, mousePos.x, mousePos.y);

				mousePos = coord.canvasToScreen(parent.getCamera(), mousePos);
				//DebugRenderer.appendLine(3, mousePos.toCastedString2D());
			}
			parent.popMatrix();
			{ //Draw X and Y axises for debug 
				parent.pushStyle();
				parent.stroke(255);
				IsoVector xAxis = coord.worldToCanvas(3, 0);
				IsoVector yAxis = coord.worldToCanvas(0, 1);
				parent.line(0, 0, xAxis.x, xAxis.y);
				parent.line(0, 0, yAxis.x, yAxis.y);
				parent.popStyle();
			}			
		}
		parent.getCamera().deattachCamera();

		//Post debug
		if(parent.debugEnabled) {
			//Midpoint
			parent.ellipse(parent.width/2, parent.height/2, 10, 10);

			//Debug
			DebugRenderer.appendLine("FPS: " + (int)parent.frameRate);
			DebugRenderer.appendLine("FC: " + parent.frameCount);

			DebugRenderer.appendLine(1, "World Size " + (int)map_size.x + "x" + (int)map_size.y);

			DebugRenderer.appendLine(2, parent.getCamera().toString());
			DebugRenderer.appendLine(2, "Camera World Pos: " + coord.canvasToWorld(parent.getCamera().getCanvasPos()).toCastedString2D());
			DebugRenderer.appendLine(2, "Camera Zoom: " + parent.getCamera().getZoom());

			String[] activeKeys = Keyboard.getKeyList();
			for(String k : activeKeys) DebugRenderer.appendLine(3, k);
		}
	}

	@Override
	public void keyPressed(KeyPair pair) {
		TestGame game = TestGame.getGame();

		if(pair.equals(Keyboard.KEY_F11)) {	//Debug Toggler	
			game.debugEnabled = !game.debugEnabled;
		}
		else if(pair.getKey() == '.') { // Reset camera pos
			game.getCamera().moveTo(0, 0);
		}
	}
	
	@Override
	public void mousePressed(MousePress pressed) {
//		if(pressed.getButton() == Mouse.BTN_RIGHT) {
//			camGrabPoint = new IsoVector(pressed.getX(), pressed.getY());
//			camBoundPos = parent.getCamera().getCanvasPos().copy();
//		}
	}
	
	@Override
	public void mouseReleased(MousePress released) {
//		if(released.getButton() == Mouse.BTN_RIGHT) {
//			camGrabPoint = null;
//			camBoundPos = null;
//		}
	}
	
	@Override
	public void wheelMoved(float downCount) {
		TestGame game = TestGame.getGame();
		
		float zoom = game.getCamera().getZoom() + downCount * -0.125f; //Form input
		
		zoom = MathUtils.resolveError(zoom, 3); //Remove error;
		zoom = MathUtils.clamp(zoom, 0.25f, 2f); //Clamping bw [0.5 , 2.0]
		game.getCamera().zoomTo(zoom);
	}
	
	@Override
	public void dispose() {
		Keyboard.unsubscribe(this);
		Mouse.unsubscribe(this);
	}
}
