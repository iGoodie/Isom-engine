package lib.camera;

import lib.animation.Animation2f;
import lib.animation.Easing2f;
import lib.core.IsomApp;
import lib.core.Updatable;
import lib.image.PivotImage;
import lib.maths.IsoVector;
import lib.world.entity.PropEntity;
import lombok.Getter;

public class Camera implements Updatable {
	private static final float EASING_PX_PER_SEC = 400f; // canvas pixels

	private IsomApp parent;

	private @Getter String label;
	private @Getter IsoVector canvasPos = IsoVector.createOnCanvas(0, 0);

	private Animation2f anim = null;

	private @Getter float width, height;
	private @Getter float zoom = 1f;
	private @Getter float rotation = 0f;

	public Camera(String label, IsomApp parent, float width, float height) {
		this.width = width;
		this.height = height;
		this.label = label;
		this.parent = parent;
	}

	public void update(float dt) {
		if (anim != null) {
			canvasPos.set(anim.proceed(dt));
			if (anim.isFinished())
				anim = null;
			return;
		}
	}

	public void attachCamera() { // TODO test :
									// https://gamedev.stackexchange.com/questions/74007/generating-transformation-matrix-for-2d-camera-with-pan-zoom-rotate
		float centerX = parent.width / (2 * zoom);
		float centerY = parent.height / (2 * zoom);
		parent.pushMatrix();
		parent.scale(zoom);
		parent.translate(centerX, centerY);
		parent.rotate(rotation);
		parent.translate(-canvasPos.x, -canvasPos.y);
	}

	public void discardRotation() {
		parent.rotate(-rotation);
	}

	public void discardZoom() {
		parent.scale(1f / rotation);
	}

	public void deattachCamera() {
		parent.popMatrix();
	}

	public void resize(float w, float h) {
		width = w;
		height = h;
	}

	public void moveTo(float canvasX, float canvasY, float duration) {
		anim = new Animation2f(canvasPos, IsoVector.createOnCanvas(canvasX, canvasY));
		anim.duration = duration;
		anim.easing = Easing2f.SINE_IN_OUT;
		anim.setTolerance(1);
	}

	public void moveTo(float canvasX, float canvasY) {
		anim = new Animation2f(canvasPos, IsoVector.createOnCanvas(canvasX, canvasY));
		anim.duration = canvasPos.dist(anim.to) / EASING_PX_PER_SEC;
		anim.easing = Easing2f.SINE_IN_OUT;
		anim.setTolerance(1);
	}

	public void move(float dx, float dy) {
		if (anim != null)
			return;
		canvasPos.add(dx, dy);
	}

	public void zoomTo(float scale) {
		zoom = scale;
	}

	public void zoom(float deltaScale) {
		zoom += deltaScale;
	}

	public void rotateTo(float rotate) {
		rotation = rotate;
	}

	public void rotate(float deltaRotation) {
		rotation += deltaRotation;
	}

	public boolean isPropInFrustum(PropEntity e) {
		IsoVector screenPos = e.getCanvasPos().toScreen(parent.getCoordinator(), this);
		PivotImage sprite = e.getSprite().getImage();
		return !(screenPos.x < -(sprite.width - sprite.pivot.x)
				|| screenPos.x > parent.width + sprite.pivot.x
				|| screenPos.y < -(sprite.height - sprite.pivot.y)
				|| screenPos.y > parent.height + sprite.pivot.y);
	}

	public boolean inRadius(float x, float y, float radius) {
		IsoVector worldPos = getWorldPos();
		float dx = worldPos.x - x;
		float dy = worldPos.y - y;
		return radius * radius >= dx * dx + dy * dy;
	}

	public IsoVector getWorldPos() {
		return canvasPos.toWorld(parent.getCoordinator(), this);
	}

	public boolean inMotion() {
		return anim != null && !anim.isFinished();
	}

	public String toString() {
		return String.format("Camera Canvas Pos:{%.0f, %.0f}", canvasPos.x, canvasPos.y);
	}

}
