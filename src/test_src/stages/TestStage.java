package stages;

import core.TestGame;
import igoodie.utils.math.MathUtils;
import lib.graphics.DebugRenderer;
import lib.input.keyboard.KeyPair;
import lib.input.keyboard.Keyboard;
import lib.input.keyboard.KeyboardListener;
import lib.input.mouse.Mouse;
import lib.input.mouse.MouseListener;
import lib.input.mouse.MousePress;
import lib.maths.IsoVector;
import lib.stage.Stage;
import map.Tile;
import map.World;

public class TestStage extends Stage<TestGame> implements KeyboardListener, MouseListener {
	
	private World world;
	
//	private IsoVector camGrabPoint;
//	private IsoVector camBoundPos;

	public TestStage(TestGame game) {
		super(game);

		name = "Test Stage";
		
		world = new World(game, 100, 100);
		for(int i=0; i<100; i++) {
			for(int j=0; j<100; j++) {
				world.groundLayer[i][j] = Tile.getByID(0);
			}
		}
		
		IsoVector mid = IsoVector.createOnWorld(50, 50).toCanvas(parent.getCoordinator(), parent.getCamera());
		parent.getCamera().getCanvasPos().set(mid);
		
		Keyboard.subscribe(this);
		Mouse.subscribe(this);
	}

	@Override
	public void update(float dt) {
		updateOld(dt);
	}
	
	private void updateOld(float dt) {
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
		world.render();
		
		DebugRenderer.appendLine("FPS: " + (int)parent.frameRate);
		
		DebugRenderer.appendLine(DebugRenderer.LOWER_LEFT, parent.getCamera().toString());
	}

	@Override
	public void keyPressed(KeyPair pair) {
		if(pair.equals(Keyboard.KEY_F11)) {	//Debug Toggler	
			parent.debugEnabled = !parent.debugEnabled;
		}
		else if(pair.getKey() == '.') { // Reset camera pos
			parent.getCamera().moveTo(0, 0);
		}
	}
	
	@Override
	public void mousePressed(MousePress pressed) {
		parent.getCamera().move(5, 5);
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
		float zoom = parent.getCamera().getZoom() + downCount * -0.125f; //Form input
		zoom = MathUtils.resolveError(zoom, 3); //Remove error;
		zoom = MathUtils.clamp(zoom, 0.25f, 2f); //Clamping bw [0.5 , 2.0]
	
		parent.getCamera().zoomTo(zoom);
	}
	
	@Override
	public void dispose() {
		Keyboard.unsubscribe(this);
		Mouse.unsubscribe(this);
	}
}
