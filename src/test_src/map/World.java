package map;

import core.TestGame;
import entity.Entity;
import igoodie.utils.math.MathUtils;
import lib.camera.Camera;
import lib.camera.Coordinator;
import lib.core.Drawable;
import lib.graphics.DebugRenderer;
import lib.maths.IsoVector;

public class World implements Drawable {

	public static byte[] serializeWorld() {
		return null; // TODO
	}

	public static World deserializeMap() {
		return null; // TODO
	}

	public int width, height;
	public String name;

	public TestGame parent;

	public Tile[][] groundLayer;
	public Entity[] entities;

	public World(TestGame parent, int width, int height) {
		this.parent = parent;
		this.width = width;
		this.height = height;

		this.groundLayer = new Tile[width][height];
	}

	@Override
	public void update(float dt) {
		for(Entity e : entities) {
			e.update(dt);
		}
	}

	@Override
	public void render() {
		renderTileLayer();
	}

	private void renderTileLayer() {
		Camera c = parent.getCamera(); // Fetch current cam
		Coordinator coord = parent.getCoordinator(); // Fetch coordinator
		IsoVector camPos = c.getWorldPos();
		
		int radius = (int) (10 / c.getZoom());

		parent.grid(20, 0xFF_303030);

		int ax = (int) (camPos.x - radius);
		int ay = (int) (camPos.y - radius);
		int bx = (int) (camPos.x + radius);
		int by = (int) (camPos.y + radius);
		
		int frustrumCount = 0;
		
		c.attachCamera();
		for(int x=MathUtils.clamp(ax, 0, width), lx=MathUtils.clamp(bx, 0, width); x<lx; x++) { 
			for(int y=MathUtils.clamp(ay, 0, height), ly=MathUtils.clamp(by, 0, height); y<ly; y++) {
				// Continue if tile is not in range
				if(radius*radius < (camPos.x-x)*(camPos.x-x) + (camPos.y-y)*(camPos.y-y)) continue;
				
				IsoVector tileCanvasPos = coord.worldToCanvas(x, y);
				parent.image(groundLayer[x][y].getSprite(), tileCanvasPos.x, tileCanvasPos.y);
				if(parent.debugEnabled) parent.circle(tileCanvasPos.x, tileCanvasPos.y, 10);
				frustrumCount++;
			}
		}
		c.deattachCamera();
		
		DebugRenderer.appendLine(DebugRenderer.UPPER_RIGHT, "Radius: " + radius);
		DebugRenderer.appendLine(DebugRenderer.UPPER_RIGHT, "Cam World Pos: " + camPos);
		DebugRenderer.appendLine(DebugRenderer.UPPER_RIGHT, "Rendered Tiles: " + frustrumCount);
	}
}

//public void render() {
////	Coordinator coord = parent.getCoordinator();
////
////	//Pre debug
////	if(parent.debugEnabled) {
////		//Draw grid
////		parent.grid(20, 0xFF_303030);
////	}
////
////	parent.getCamera().attachCamera(); //Render by camera options
////	{
////		parent.pushMatrix();
////		parent.translate(-64, -32); //-Tw/2, Th/2
////		for(int i=0; i<map_size.x; i++) for(int j=0; j<map_size.y; j++) {
////			IsoVector canvasPos = coord.worldToCanvas(i, j); //W(i, j) -> Canvas
////			parent.image(test_tile, canvasPos.x, canvasPos.y);
////		}
////
////		{ //Draw tile cursor on the mouse position
////			IsoVector mousePos = new IsoVector(parent.mouseX, parent.mouseY);
////			mousePos = coord.screenToWorld(parent.getCamera(), mousePos);
////			mousePos = coord.worldToCanvas(mousePos);
////			parent.image(test_tile_cursor, mousePos.x, mousePos.y);
////
////			mousePos = coord.canvasToScreen(parent.getCamera(), mousePos);
////			//DebugRenderer.appendLine(3, mousePos.toCastedString2D());
////		}
////		parent.popMatrix();
////		if(parent.debugEnabled) { //Draw X and Y axises for debug 
////			parent.pushStyle();
////			parent.stroke(255);
////			IsoVector xAxis = coord.worldToCanvas(3, 0);
////			IsoVector yAxis = coord.worldToCanvas(0, 1);
////			parent.line(0, 0, xAxis.x, xAxis.y);
////			parent.line(0, 0, yAxis.x, yAxis.y);
////			parent.text("X+", xAxis.x, xAxis.y);
////			parent.text("Y+", yAxis.x, yAxis.y);
////			parent.popStyle();
////		}			
////	}
////	parent.getCamera().deattachCamera();
////
////	//Post debug
////	if(parent.debugEnabled) {
////		//Midpoint
////		parent.ellipse(parent.width/2, parent.height/2, 10, 10);
////
////		//Debug
////		DebugRenderer.appendLine("FPS: " + (int)parent.frameRate);
////		DebugRenderer.appendLine("FC: " + parent.frameCount);
////
////		DebugRenderer.appendLine(1, "World Size " + (int)map_size.x + "x" + (int)map_size.y);
////
////		DebugRenderer.appendLine(2, parent.getCamera().toString());
////		DebugRenderer.appendLine(2, "Camera World Pos: " + coord.canvasToWorld(parent.getCamera().getCanvasPos()).toCastedString2D());
////		DebugRenderer.appendLine(2, "Camera Zoom: " + parent.getCamera().getZoom());
////
////		String[] activeKeys = Keyboard.getKeyList();
////		for(String k : activeKeys) DebugRenderer.appendLine(3, k);
////	}
//}
