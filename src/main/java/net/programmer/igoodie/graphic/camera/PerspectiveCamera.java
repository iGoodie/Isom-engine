package net.programmer.igoodie.graphic.camera;

import org.joml.Matrix4f;

import lombok.Getter;
import lombok.Setter;

public class PerspectiveCamera extends Camera {

	private @Getter @Setter float fov;

	private Matrix4f projection;

	public PerspectiveCamera(int width, int height) {
		this(width, height, (float) Math.PI / 3, 0.01f, 10_000f);
	}

	public PerspectiveCamera(int width, int height, float fov, float zNear, float zFar) {
		super(width, height, zNear, zFar);
		this.fov = fov;
		this.projection = new Matrix4f();
	}

	@Override
	public Matrix4f createProjectionMatrix() {
		float aspectRatio = (float) width / height;

		return projection.identity()
				.perspective(fov, aspectRatio, zNear, zFar);
	}

	/* ------------ */

	public void increaseFov(float df) {
		fov += df;
	}
	
}
