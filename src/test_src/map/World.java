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
