package net.programmer.igoodie.graphic.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import lombok.Getter;
import lombok.Setter;

public abstract class Camera {

	protected @Getter Vector3f position;
	protected @Getter Vector3f rotation;

	protected @Getter @Setter float zNear;
	protected @Getter @Setter float zFar;
	protected @Getter @Setter int width, height;

	public Camera(int width, int height, float zNear, float zFar) {
		this.position = new Vector3f(0, 0, 0);
		this.rotation = new Vector3f(0, 0, 0);
		this.width = width;
		this.height = height;
		this.zNear = zNear;
		this.zFar = zFar;
	}

	public abstract Matrix4f createProjectionMatrix();

	public Matrix4f createViewMatrix() {
		Vector3f negativePosition = new Vector3f(position).mul(-1f);

		return new Matrix4f()
				.rotateXYZ(rotation)
				.translate(negativePosition);
	}
	
	/* Translation */

	public void setPosition(float x, float y, float z) {
		position.set(x, y, z);
	}

	public void setPosition(float x, float y) {
		position.set(x, y, 0);
	}

	public void setPosition(float n) {
		position.set(n);
	}

	public void setPosition(Vector3f v) {
		position.set(v);
	}

	public void move(float dx, float dy, float dz) {
		position.add(dx, dy, dz);
	}

	/* Rotation */

	public void setRotation(float x, float y, float z) {
		rotation.set(x, y, z);
	}

	public void setRotation(float x, float y) {
		rotation.set(x, y, 0);
	}

	public void setRotation(float n) {
		rotation.set(n);
	}

	public void setRotation(Vector3f v) {
		rotation.set(v);
	}

	public void rotate(float dx, float dy, float dz) {
		rotation.add(dx, dy, dz);
	}

}
