package lib.world;

import java.io.File;

import com.programmer.igoodie.utils.math.MathUtils;
import com.programmer.igoodie.utils.math.Randomizer;

import lib.camera.Camera;
import lib.camera.Coordinator;
import lib.core.Drawable;
import lib.core.GameBase;
import lib.graphics.DebugRenderer;
import lib.maths.IsoVector;
import lib.registry.TileRegistry;
import lib.world.entitiy.Entity;
import lib.world.entitiy.PropEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class IsometricWorld implements Drawable {

	public static IsometricWorld loadWorld(GameBase parent, File file) {
		// TODO feat: Deserialize from file
		IsometricWorld world = new IsometricWorld(parent, 0, 3, 1000, 1000);
		world.setName("UNKNOWN_MAP");

		// TODO: Load tiles from file
		for (int i = 0; i < world.width; i++)
			for (int j = 0; j < world.height; j++)
				world.tiles[i][j] = TileRegistry.getTileByID(Randomizer.randomInt(1, 3));

		// TODO: Load entities from file
		world.spawnEntity(0, new PropEntity(parent, 0, IsoVector.createOnWorld(0, 0)));
		world.spawnEntity(0, new PropEntity(parent, 0, IsoVector.createOnWorld(1, 1)));
		world.spawnEntity(0, new PropEntity(parent, 0, IsoVector.createOnWorld(2, 2)));
		world.spawnEntity(0, new PropEntity(parent, 0, IsoVector.createOnCanvas(3.123f, 15.1233f)));
		for (int i = 0; i < 10_000; i++) {
			int propId = Randomizer.randomInt(0, 1);
			float x = parent.random(1000);
			float y = parent.random(1000);
			world.spawnEntity(0, new PropEntity(parent, propId, IsoVector.createOnWorld(x, y)));
		}

		return world;
	}

	protected @Getter int id;
	protected @Getter @Setter String name;
	protected GameBase parent;

	public Tile[][] tiles;
	private IsometricLayer[] layers;
	private @Getter int width, height;

	public IsometricWorld(GameBase parent, int id, int layerCount, int width, int height) {
		this.id = id;
		this.parent = parent;
		this.width = width;
		this.height = height;
		this.tiles = new Tile[width][height];

		this.layers = new IsometricLayer[layerCount];
		for (int i = 0; i < layerCount; i++)
			this.layers[i] = new IsometricLayer(this, 20, 20);
	}

	public void spawnEntity(int layer, Entity entity) {
		if (layer < 0 || layer >= layers.length)
			throw new IllegalArgumentException(
					String.format("Invalid layer number %i. Should be between [0,%d).", layer, layers.length));

		layers[layer].spawnEntity(entity);
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		renderTileLayer();

//		for (IsometricLayer layer : layers)
//			layer.render();
	}

	private void renderTileLayer() { // TODO: Optimize latency caused by Processing's image callback
		Camera cam = parent.getCamera(); // Fetch current cam
		Coordinator coord = parent.getCoordinator(); // Fetch coordinator
		IsoVector camPos = cam.getWorldPos();

		// TODO: Parametrize even better, pls :D For also a better performance
		int radius = (int) (((2.4f * parent.width) / (2f * coord.getTileWidth())) / cam.getZoom());

		if (parent.debugEnabled)
			parent.grid(20, 0xFF_303030);

		int ax = (int) (camPos.x - radius);
		int ay = (int) (camPos.y - radius);
		int bx = (int) (camPos.x + radius);
		int by = (int) (camPos.y + radius);

		int frustumCount = 0;

		cam.attachCamera();
		for (int x = MathUtils.clamp(ax, 0, width), lx = MathUtils.clamp(bx, 0, width); x < lx; x++) {
			for (int y = MathUtils.clamp(ay, 0, height), ly = MathUtils.clamp(by, 0, height); y < ly; y++) {
//				if (radius * radius < (camPos.x - x) * (camPos.x - x) + (camPos.y - y) * (camPos.y - y))
				if(!cam.inRadius(x, y, radius))	
					continue;

				// TODO: Optimize PApplet::image ?
				IsoVector tileCanvasPos = coord.worldToCanvas(x, y);
				parent.imageOnPivot(tiles[x][y].getImage(), tileCanvasPos.x, tileCanvasPos.y);
				// parent.image(IMG, tileCanvasPos.x, tileCanvasPos.y);

				// XXX: Costs a lot to render:
//				if (parent.debugEnabled)
//					parent.circle(tileCanvasPos.x, tileCanvasPos.y, 10);

				frustumCount++;
			}
		}
		cam.deattachCamera();

		DebugRenderer.appendLine(DebugRenderer.UPPER_RIGHT, "Radius: " + radius);
		DebugRenderer.appendLine(DebugRenderer.UPPER_RIGHT, "Cam World Pos: " + camPos);
		DebugRenderer.appendLine(DebugRenderer.UPPER_RIGHT, "Rendered Tiles: " + frustumCount);
	}

}
