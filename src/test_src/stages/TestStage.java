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
	public void update(float dt) {}

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
	public void mousePressed(MousePress pressed) {}
	
	@Override
	public void mouseReleased(MousePress released) {}
	
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
