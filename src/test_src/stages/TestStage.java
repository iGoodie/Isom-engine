package stages;

import core.TestGame;
import entity.PropEntity;
import igoodie.utils.math.MathUtils;
import lib.camera.Camera;
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
import map.Tile;
import map.World;

public class TestStage extends Stage<TestGame> implements KeyboardListener, MouseListener {
	
	private World world;

	public TestStage(TestGame game) {
		super(game);

		name = "Test Stage";
		
		// Prepare test world
		world = new World(game, 1000, 1000);
		for(int i=0; i<world.width; i++) {
			for(int j=0; j<world.height; j++) {
				world.groundLayer[i][j] = Tile.getByID(0);
			}
		}
		world.entities.add(new PropEntity(parent, 0, IsoVector.createOnWorld(0, 0)));
		world.entities.add(new PropEntity(parent, 0, IsoVector.createOnWorld(1, 1)));
		world.entities.add(new PropEntity(parent, 0, IsoVector.createOnWorld(2, 2)));
		for(int i=0; i<10_000; i++)
			world.entities.add(new PropEntity(parent, 0, IsoVector.createOnWorld(parent.random(50), parent.random(50))));
		
		IsoVector mid = IsoVector.createOnWorld(50, 50).toCanvas(parent.getCoordinator(), parent.getCamera());
		parent.getCamera().getCanvasPos().set(mid);
		
		Keyboard.subscribe(this);
		Mouse.subscribe(this);
	}

	@Override
	public void update(float dt) {}

	@Override
	public void render() {
		world.render();
		
		DebugRenderer.appendLine(DebugRenderer.LOWER_LEFT, parent.getCamera().toString());
	}

	@Override
	public void keyPressed(KeyPair pair) {
		// Temporary console solution. TODO: Impl layered handler for GUI
		if(parent.console.enabled) {
			StringBuffer inputBuffer = parent.console.inputBuffer;
			if(pair.equals(Keyboard.KEY_WIN_ENTER)) {
				parent.console.parseAndExecute();
			}
			else if(pair.equals(Keyboard.KEY_WIN_BACKSPACE)) {
				if(inputBuffer.length() > "> ".length())
					inputBuffer.deleteCharAt(inputBuffer.length()-1);
			}
			else if(pair.equals(Keyboard.KEY_ESC)) {
				parent.console.close();
			}
			else {
				if(pair.isPrintable())
					inputBuffer.append(pair.getKey());
			}
			
			return;
		}
		
		if(pair.equals(Keyboard.KEY_F11)) {	// Debug Toggler	
			parent.debugEnabled = !parent.debugEnabled;
		}
		else if(pair.equals(Keyboard.KEY_F12)) { // Console Toggler
			parent.console.toggle();
		}
		else if(pair.getKey() == '.') { // Reset camera pos
			parent.getCamera().moveTo(0, 0);
		}
	}
	
	@Override
	public void mousePressed(MousePress pressed) {
		Camera cam = parent.getCamera();
		Coordinator coord = parent.getCoordinator();
		
		IsoVector clickedPos = IsoVector.createOnScreen(pressed.getX(), pressed.getY());
		clickedPos = clickedPos.toWorld(coord, cam);
		clickedPos = clickedPos.toCanvas(coord, cam);
		cam.moveTo(clickedPos.x, clickedPos.y);
	}
	
	@Override
	public void mouseReleased(MousePress released) {}
	
	@Override
	public void wheelMoved(float downCount) {
		float zoom = parent.getCamera().getZoom() + downCount * -0.125f; //Form input
		zoom = MathUtils.resolveError(zoom, 3); //Remove error;
		zoom = MathUtils.clamp(zoom, 0.125f, 2f); //Clamping bw [0.5 , 2.0]
	
		parent.getCamera().zoomTo(zoom);
	}
	
	@Override
	public void dispose() {
		Keyboard.unsubscribe(this);
		Mouse.unsubscribe(this);
	}
}
