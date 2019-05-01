package net.programmer.igoodie.graphic.camera;

import org.joml.Matrix4f;

import lombok.Getter;
import lombok.Setter;

public class OrthoCamera extends Camera {

	private @Getter @Setter float zoom;

	private Matrix4f projection;

	public OrthoCamera(int width, int height) {
		this(width, height, .5f, 0.01f, 10_000f);
	}

	public OrthoCamera(int width, int height, float zoom, float zNear, float zFar) {
		super(width, height, zNear, zFar);
		this.zoom = zoom;
		this.projection = new Matrix4f();
	}

	@Override
	public Matrix4f createProjectionMatrix() {
		return projection.identity()
				.orthoSymmetric(width, height, zNear, zFar)
				.scale(zoom, zoom, 1f);
	}

	/* ------------ */
	
	public void zoom(float delta) {
		zoom *= delta;
	}
	
}
