package net.programmer.igoodie.engine.world;

import java.util.LinkedList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector2i;

import lombok.Getter;
import net.programmer.igoodie.gameloop.Drawable;
import net.programmer.igoodie.gameloop.IsomEngineLoop;
import net.programmer.igoodie.graphic.Texture;
import net.programmer.igoodie.graphic.camera.OrthoCamera;
import net.programmer.igoodie.utils.Mathf;

public class TileMap implements Drawable {

	private static final Texture TEST_TEXTURE = new Texture("./data/top.png");

	private IsomEngineLoop parent;

	private @Getter Tile[][] tiles;
	private int tileWidth, tileHeight;

	public TileMap(IsomEngineLoop parent, int width, int height) {
		this.parent = parent;
		this.tiles = new Tile[width][height];

		this.tileWidth = 128;
		this.tileHeight = 64;

		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				Tile tile = new Tile(new Vector2f(x, y), 128, 64, TEST_TEXTURE);
				tiles[x][y] = tile;
			}
		}
	}

	public int getWidth() {
		return tiles[0].length;
	}

	public int getHeight() {
		return tiles.length;
	}

	public List<Tile> cull(OrthoCamera camera) {
		List<Tile> culledTiles = new LinkedList<>();

		Vector2f cameraWorldPos = canvasToWorldExact(camera.getPosition().x, camera.getPosition().y);

		float radiusSq = canvasToWorldExact(camera.getWidth() / 2f, camera.getHeight() / 2f)
				.mul(.5f / camera.getZoom()).lengthSquared();
		float radius = Mathf.sqrt(radiusSq);

		float startX = Mathf.clamp(cameraWorldPos.x - radius, 0, getWidth());
		float startY = Mathf.clamp(cameraWorldPos.y - radius, 0, getHeight());
		float finishX = Mathf.clamp(cameraWorldPos.x + radius, 0, getWidth());
		float finishY = Mathf.clamp(cameraWorldPos.y + radius, 0, getHeight());

		for (float y = startY; y <= finishY; y++) {
			for (float x = startX; x <= finishX; x++) {
				Tile tile = tiles[(int) x][(int) y];

				if (tile.getWorldPosition().distanceSquared(cameraWorldPos.x, cameraWorldPos.y) >= radiusSq)
					continue;

				culledTiles.add(tile);
			}
		}

		return culledTiles;
	}

	public Vector2f canvasToWorldExact(float x, float y) {
		float dx = x / tileWidth;
		float dy = y / tileHeight;

		return new Vector2f(dx + dy, dx - dy);
	}

	public Vector2i canvasToWorld(float x, float y) {
		float dx = x / tileWidth;
		float dy = y / tileHeight;

		return new Vector2i(Math.round(dx + dy), Math.round(dx - dy));
	}

	public Vector2i screenToWorld(OrthoCamera camera, float x, float y) {
		Vector2f cameraPosition = new Vector2f(camera.getPosition().x, camera.getPosition().y);

		float bx = (cameraPosition.x + (x - camera.getWidth() / 2f) / camera.getZoom()) / tileWidth;
		float by = (cameraPosition.y + (y - camera.getHeight() / 2f) / camera.getZoom()) / tileHeight;
		return new Vector2i(Math.round(bx + by), Math.round(bx - by)); // W = [round(bx+by), round(bx-by)]
	}

	public Vector2f screenToWorldExact(OrthoCamera camera, float x, float y) {
		Vector2f cameraPosition = new Vector2f(camera.getPosition().x, camera.getPosition().y);

		float bx = (cameraPosition.x + (x - camera.getWidth() / 2f) / camera.getZoom()) / tileWidth;
		float by = (cameraPosition.y + (y - camera.getHeight() / 2f) / camera.getZoom()) / tileHeight;
		return new Vector2f(bx + by, bx - by); // W = [round(bx+by), round(bx-by)]
	}

}
