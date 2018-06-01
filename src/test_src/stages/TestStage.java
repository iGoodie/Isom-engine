package stages;

import core.TestGame;
import lib.camera.Coordinator;
import lib.graphics.CursorRenderer;
import lib.graphics.DebugRenderer;
import lib.input.keyboard.KeyPair;
import lib.input.keyboard.Keyboard;
import lib.input.keyboard.KeyboardListener;
import lib.maths.IsoVector;
import lib.stage.Stage;
import processing.core.PImage;

public class TestStage extends Stage implements KeyboardListener {

	private IsoVector map_size = new IsoVector(30, 20);

	private PImage test_tile;
	private PImage test_tile_cursor;

	public TestStage() {
		TestGame game = TestGame.getGame(); //Fetch game singleton

		name = "Test Stage";

		test_tile = game.loadImage("test.png");
		test_tile_cursor = game.loadImage("cursor_tile.png");
		
		Keyboard.subscribe(this);
	}

	@Override
	public void update(float dt) {
		TestGame game = TestGame.getGame(); //Fetch game singleton
		Coordinator coord = game.getCoordinator();

		//Boundary Cursor Test
		IsoVector mousePos = new IsoVector(game.mouseX, game.mouseY);
		mousePos = mousePos.toCanvas(coord, game.getCamera()).toWorld(coord);
		DebugRenderer.appendLine(3, "Mouse World Pos: " + mousePos.toCastedString2D());
		if((mousePos.x >= 0 && mousePos.x < map_size.x) && (mousePos.y >= 0 && mousePos.y < map_size.y)) {
			CursorRenderer.setCursor("map");
		}

		//Keyboard Input Handler
		IsoVector velocity = new IsoVector(); 
		velocity.plane = IsoVector.WORLD;
		if(Keyboard.isKeyActive(Keyboard.KEY_W)) {
			velocity.add(0, 1);
		}
		if(Keyboard.isKeyActive(Keyboard.KEY_S)) {
			velocity.add(0, -1);
		}
		if(Keyboard.isKeyActive(Keyboard.KEY_D)) {
			velocity.add(1, 0);
		}
		if(Keyboard.isKeyActive(Keyboard.KEY_A)) {
			velocity.add(-1, 0);
		}
		DebugRenderer.appendLine(1, "W-Velocity Unit: " + velocity.toCastedString2D());
		velocity.rotate(TestGame.QUARTER_PI);
		int speed = 10; //tile per sec
		velocity.len(speed * dt); // Normalize, then mult
		velocity = velocity.toCanvas(coord); //World movement

		if(game.mousePressed && game.mouseButton==TestGame.RIGHT) {
			velocity.set(game.width/2 - game.mouseX, game.height/2 - game.mouseY);
			velocity.mult(5 * -dt);
		}

		DebugRenderer.appendLine(1, "C-Velocity: " + velocity.toCastedString2D());
		game.getCamera().move(velocity.x, velocity.y);
	}

	@Override
	public void render() {
		TestGame game = TestGame.getGame(); //Fetch game singleton
		Coordinator coord = game.getCoordinator();

		//Pre debug
		if(game.debugEnabled) {
			//Draw grid
			game.grid(20, 0xFF_303030);
		}

		game.getCamera().attachCamera(); //Render by camera options
		{
			game.pushMatrix();
			game.translate(-64, -32); //-Tw/2, Th/2
			for(int i=0; i<map_size.x; i++) for(int j=0; j<map_size.y; j++) {
				IsoVector canvasPos = coord.worldToCanvas(i, j); //W(i, j) -> Canvas
				game.image(test_tile, canvasPos.x, canvasPos.y);
			}

			{ //Draw tile cursor on the mouse position
				IsoVector mousePos = new IsoVector(game.mouseX, game.mouseY);
				mousePos = coord.screenToWorld(game.getCamera(), mousePos);
				mousePos = coord.worldToCanvas(mousePos);
				game.image(test_tile_cursor, mousePos.x, mousePos.y);

				mousePos = coord.canvasToScreen(game.getCamera(), mousePos);
				//DebugRenderer.appendLine(3, mousePos.toCastedString2D());
			}
			game.popMatrix();
			{ //Draw X and Y axises for debug 
				game.pushStyle();
				game.stroke(255);
				IsoVector xAxis = coord.worldToCanvas(3, 0);
				IsoVector yAxis = coord.worldToCanvas(0, 1);
				game.line(0, 0, xAxis.x, xAxis.y);
				game.line(0, 0, yAxis.x, yAxis.y);
				game.popStyle();
			}			
		}
		game.getCamera().deattachCamera();

		//Post debug
		if(game.debugEnabled) {
			//Midpoint
			game.ellipse(game.width/2, game.height/2, 10, 10);

			//Debug
			DebugRenderer.appendLine("FPS: " + (int)game.frameRate);
			DebugRenderer.appendLine("FC: " + game.frameCount);

			DebugRenderer.appendLine(1, "World Size " + (int)map_size.x + "x" + (int)map_size.y);

			DebugRenderer.appendLine(2, game.getCamera().toString());
			DebugRenderer.appendLine(2, "Camera World Pos: " + coord.canvasToWorld(game.getCamera().getCanvasPos()).toCastedString2D());
			DebugRenderer.appendLine(2, "Camera Zoom: " + game.getCamera().getZoom());

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
	public void dispose() {
		Keyboard.unsubscribe(this);
	}
}
