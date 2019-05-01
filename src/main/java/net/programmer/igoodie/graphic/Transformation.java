package net.programmer.igoodie.graphic;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import lombok.Getter;

public class Transformation {

	private Vector3f translation;
	private Vector3f rotation;
	private Vector3f scale;

	public Transformation() {
		this.translation = new Vector3f(0, 0, 0);
		this.rotation = new Vector3f(0, 0, 0);
		this.scale = new Vector3f(1, 1, 1);
	}

	public Matrix4f createMatrix() {
		return new Matrix4f()
				.translate(translation)
				.rotateXYZ(rotation)
				.scale(scale);
	}
	
	/* Translation */
	public void setTranslation(float x, float y, float z) {
		translation.set(x, y, z);
	}
	
	public void setTranslation(float x, float y) {
		translation.set(x, y, 0);
	}
	
	public void setTranslation(float n) {
		translation.set(n);
	}

	public void setTranslation(Vector3f vec) {
		translation.set(vec);
	}
	
	public void setTranslation(Vector2f vec) {
		translation.set(vec, 0);
	}
	
	public void translate(float dx, float dy, float dz) {
		translation.add(dx, dy, dz);
	}

	/* Rotation */
	public void rotate(float dx, float dy, float dz) {
		rotation.add(dx, dy, dz);
	}

	/* Scale */
	public void setScale(float x, float y, float z) {
		scale.set(x, y, z);
	}

	public void setScale(float x, float y) {
		scale.set(x, y, 1);
	}
	
	public void setScale(float n) {
		scale.set(n);
	}
	
	public void scale(float dx, float dy, float dz) {
		scale.mul(dx, dy, dz);
	}

	public void scale(float dx, float dy) {
		scale.mul(dx, dy, 1);
	}
	
	public void scale(float n) {
		scale.mul(n);
	}
	
}
