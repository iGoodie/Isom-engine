package stages;

import core.TestGame;
import lib.camera.Coordinator;
import lib.graphics.CursorRenderer;
import lib.graphics.DebugRenderer;
import lib.input.keyboard.Keyboard;
import lib.input.keyboard.Keys;
import lib.maths.IsoVector;
import lib.stage.Stage;
import processing.core.PImage;

public class TestStage extends Stage {
	private IsoVector map_size = new IsoVector(30, 20);
	
	private PImage test_tile;
	private PImage test_tile_cursor;
	
	public TestStage() {
		TestGame game = TestGame.getGame(); //Fetch game singleton
		
		name = "Test Stage";
		
		test_tile = game.loadImage("test.png");
		test_tile_cursor = game.loadImage("cursor_tile.png");
	}

	@Override
	public void update(float dt) {
		TestGame game = TestGame.getGame(); //Fetch game singleton

		//Boundary Cursor Test
		IsoVector mousePos = new IsoVector(game.mouseX, game.mouseY);
		mousePos = mousePos.toCanvas(game.getCamera()).toWorld();
		DebugRenderer.appendLine(3, "Mouse World Pos: " + mousePos.toCastedString2D());
		if((mousePos.x >= 0 && mousePos.x < map_size.x) && (mousePos.y >= 0 && mousePos.y < map_size.y)) {
			CursorRenderer.setCursor("map");
		}

		//Debug Toggler
		if(Keyboard.isKeyActiveOnce(Keys.KEY_F11)) {
			game.debugEnabled = !game.debugEnabled;
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
		velocity.rotate(TestGame.QUARTER_PI);
		int speed = 10; //tile per sec
		velocity.len(speed * dt); // Normalize, then mult
		velocity = velocity.toCanvas(); //World movement

		if(Keyboard.isKeyActiveOnce('.')) {
			game.getCamera().moveTo(0, 0);
		}

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
		
		//Pre debug
		if(game.debugEnabled) {
			//Draw grid
			game.grid(20, 0xFF_303030);
		}

		game.getCamera().attachCamera(); //Render by camera options
		{
			game.pushMatrix();
			game.translate(-64, -32); //-Tw/2, Th/2
			DebugRenderer.appendLine(1, "World Size 5x3");
			for(int i=0; i<map_size.x; i++) for(int j=0; j<map_size.y; j++) {
				IsoVector canvasPos = Coordinator.worldToCanvas(i, j); //W(i, j) -> Canvas
				game.image(test_tile, canvasPos.x, canvasPos.y);
			}

			{ //Draw tile cursor on the mouse position
				IsoVector mousePos = new IsoVector(game.mouseX, game.mouseY);
				mousePos = Coordinator.screenToWorld(game.getCamera(), mousePos);
				mousePos = Coordinator.worldToCanvas(mousePos);
				game.image(test_tile_cursor, mousePos.x, mousePos.y);

				mousePos = Coordinator.canvasToScreen(game.getCamera(), mousePos);
				//DebugRenderer.appendLine(3, mousePos.toCastedString2D());
			}
			game.popMatrix();
			{ //Draw X and Y axises for debug 
				game.pushStyle();
				game.stroke(255);
				IsoVector xAxis = Coordinator.worldToCanvas(3, 0);
				IsoVector yAxis = Coordinator.worldToCanvas(0, 1);
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

			DebugRenderer.appendLine(2, game.getCamera().toString());
			DebugRenderer.appendLine(2, "Camera World Pos: " + Coordinator.canvasToWorld(game.getCamera().getCanvasPos()).toCastedString2D());
			DebugRenderer.appendLine(2, "Camera Zoom: " + game.getCamera().getZoom());

			String[] activeKeys = Keyboard.getKeyList();
			for(String k : activeKeys) DebugRenderer.appendLine(3, k);
		}
	}
}
