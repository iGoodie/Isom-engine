package demo.stages;

import com.programmer.igoodie.utils.math.MathUtils;

import demo.TestGame;
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
import lib.world.IsometricWorld;

public class YetAnotherWorldStage extends Stage<TestGame> implements KeyboardListener, MouseListener {

	protected IsometricWorld map;

	public YetAnotherWorldStage(TestGame parent) {
		super(parent);

		name = "Yet Another World Stage";

		map = IsometricWorld.loadWorld(parent, parent.dataFile("pseudo_mapfile.map"));

		Keyboard.subscribe(this);
		Mouse.subscribe(this);
	}
	
	@Override
	public void update(float dt) {
		map.update(dt);
	}

	@Override
	public void render() {
		parent.grid(20, 0xFF_303030);
		map.render();

		DebugRenderer.appendLine(map.getName());
		
		IsoVector origin = IsoVector.createOnWorld(0, 0).toScreen(parent.getCoordinator(), parent.getCamera());
		IsoVector xAxis = IsoVector.createOnWorld(2, 0).toScreen(parent.getCoordinator(), parent.getCamera());
		IsoVector yAxis = IsoVector.createOnWorld(0, 1).toScreen(parent.getCoordinator(), parent.getCamera());
		parent.stroke(255);
		parent.line(origin.x, origin.y, xAxis.x, xAxis.y);
		parent.line(origin.x, origin.y, yAxis.x, yAxis.y);
		parent.textWithStroke("X-axis", xAxis.x, xAxis.y, 255, 0);
		parent.textWithStroke("Y-axis", yAxis.x, yAxis.y, 255, 0);
	}

	@Override
	public void keyPressed(KeyPair pair) {
		if (pair.equals(Keyboard.KEY_F11)) { // Debug Toggler
			parent.debugEnabled = !parent.debugEnabled;
			
		} else if (pair.getKey() == '.') { // Reset camera pos
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
		cam.moveTo(clickedPos.x, clickedPos.y, 0.1f);
	}

	@Override
	public void wheelMoved(float downCount) {
		float zoom = parent.getCamera().getZoom() + downCount * -0.125f; // Form input
		zoom = MathUtils.resolveError(zoom, 3); // Remove error;
		zoom = MathUtils.clamp(zoom, 0.125f, 2f); // Clamping bw [0.5 , 2.0]

		parent.getCamera().zoomTo(zoom);
	}

	@Override
	public void dispose() {
		Keyboard.unsubscribe(this);
		Mouse.unsubscribe(this);
	}

}
