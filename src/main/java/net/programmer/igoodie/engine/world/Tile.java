package net.programmer.igoodie.engine.world;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.programmer.igoodie.graphic.Texture;
import net.programmer.igoodie.graphic.Transformation;

@Accessors(chain = true)
public class Tile {

	public static Tile createTile(Vector2f worldPosition, int textureID) {
		return new Tile(worldPosition, -1, -1, null); // TODO
	}

	/* ----------------- */

	private @Getter @Setter Texture texture;
	private @Getter Vector2f worldPosition;
	private Transformation transformation;

	public Tile(Vector2f worldPosition, int width, int height, Texture texture) {
		this.texture = texture;

		this.worldPosition = worldPosition;

		float x = worldPosition.x;
		float y = worldPosition.y;

		this.transformation = new Transformation();
		this.transformation.setTranslation(width / 2f * (x + y), height / 2f * (x - y));
		this.transformation.setScale(width, height);
	}

	public Matrix4f getTransformationMatrix() {
		return transformation.createMatrix();
	}

}
