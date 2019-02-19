package map;

import java.util.ArrayList;
import java.util.Collections;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import com.programmer.igoodie.utils.math.MathUtils;

import core.TestGame;
import entity.Entity;
import entity.PropEntity;
import lib.camera.Camera;
import lib.camera.Coordinator;
import lib.core.Drawable;
import lib.graphics.DebugRenderer;
import lib.maths.IsoVector;
import lib.world.Tile;

public class WorldOld implements Drawable {

	public static byte[] serializeWorld() {
		return null; // TODO serialize world
	}

	public static WorldOld deserializeMap(byte[] serial_data) {
		return null; // TODO deserialize map
	}
	
	public String name;
	public int width, height;

	public TestGame parent;

	public Tile[][] groundLayer;
	
	public ArrayList<Entity> entities;

	public WorldOld(TestGame parent, int width, int height) {
		this.parent = parent;
		this.width = width;
		this.height = height;

		this.groundLayer = new Tile[width][height];
		this.entities = new ArrayList<>();
	}

	org.dyn4j.dynamics.World w = new org.dyn4j.dynamics.World(); {
		w.setGravity(new Vector2());
		Body b = new Body();
		b.addFixture(Geometry.createCircle(15));
		b.translate(1, 0);
		b.setMass(MassType.NORMAL);
		w.addBody(b);
	}
	
	@Override
	public void update(float dt) {
		w.step(1, dt);
		//System.out.println(w.getBodies().get(0).getWorldCenter());
		for(Entity e : entities) {
			e.update(dt);
		}
	}

	@Override
	public void render() {
		renderTileLayer();
		renderEntities();
	}

	private void renderEntities() {
		/*
		 * TODO: Optimization on y-sorting
		 * 1 - Query props on frustum
		 * 2 - Sort only queries entities.
		 * 3 - Render them
		 */
		Collections.sort(entities);
		
		int frustumCount = 0;
		
		parent.getCamera().attachCamera();
		for(Entity e : entities) {
			if(e instanceof PropEntity && parent.getCamera().propOnScreen((PropEntity)e)) {
				e.render();
				frustumCount++;
			}
		}
		parent.getCamera().deattachCamera();

		DebugRenderer.appendLine(DebugRenderer.UPPER_RIGHT, "Rendered Props: " + frustumCount);
	}

	private void renderTileLayer() { // TODO: Optimize latency caused by Processing's image callback
		Camera cam = parent.getCamera(); // Fetch current cam
		Coordinator coord = parent.getCoordinator(); // Fetch coordinator
		IsoVector camPos = cam.getWorldPos();
		
		//TODO: Parametrize even better, pls :D For also a better performance
		int radius = (int) (((2.4f*parent.width)/(2f*coord.getTileWidth())) / cam.getZoom());

		if(parent.debugEnabled)
			parent.grid(20, 0xFF_303030);

		int ax = (int) (camPos.x - radius);
		int ay = (int) (camPos.y - radius);
		int bx = (int) (camPos.x + radius);
		int by = (int) (camPos.y + radius);
		
		int frustumCount = 0;
		
		cam.attachCamera();
		for(int x=MathUtils.clamp(ax, 0, width), lx=MathUtils.clamp(bx, 0, width); x<lx; x++) { 
			for(int y=MathUtils.clamp(ay, 0, height), ly=MathUtils.clamp(by, 0, height); y<ly; y++) {
				// Continue if tile is not in range
				if(radius*radius < (camPos.x-x)*(camPos.x-x) + (camPos.y-y)*(camPos.y-y)) continue;
				
				// TODO: Optimize PApplet::image ?
				IsoVector tileCanvasPos = coord.worldToCanvas(x, y);
				parent.imageOnPivot(groundLayer[x][y].getSprite(), tileCanvasPos.x, tileCanvasPos.y);
				//parent.image(IMG, tileCanvasPos.x, tileCanvasPos.y);
				
				//XXX: Costs a lot to render: if(parent.debugEnabled) parent.circle(tileCanvasPos.x, tileCanvasPos.y, 10);
				
				frustumCount++;
			}
		}
		cam.deattachCamera();
		
		DebugRenderer.appendLine(DebugRenderer.UPPER_RIGHT, "Radius: " + radius);
		DebugRenderer.appendLine(DebugRenderer.UPPER_RIGHT, "Cam World Pos: " + camPos);
		DebugRenderer.appendLine(DebugRenderer.UPPER_RIGHT, "Rendered Tiles: " + frustumCount);
	}
	
	/*private static final PivotImage IMG = new PivotImage(new PImage(1,1)); {
		IMG.pixels[0] = 0xFF_FFFFFF;
	}*/
}
