package lib.camera;

import core.TestGame;
import entity.PropEntity;
import lib.animation.Animation2f;
import lib.animation.Animation2f.Easing2f;
import lib.core.GameBase;
import lib.core.Updatable;
import lib.image.PivotImage;
import lib.maths.IsoVector;

public class Camera implements Updatable {
	private static final float EASING_PX_PER_SEC = 400f; //canvas pixels 
	
	private GameBase parent;
	
	private String label;
	private IsoVector canvasPos = IsoVector.createOnCanvas(0, 0);
	
	private Animation2f anim = null;
	
	float width, height;
	float zoom = 1f;
	float rotation = 0f;

	public Camera(String label, GameBase parent, float width, float height) {
		this.width = width;
		this.height = height;
		this.label = label;
		this.parent = parent;
	}
	
	/* Update and render helpers */
	public void attachCamera() { //TODO test : https://gamedev.stackexchange.com/questions/74007/generating-transformation-matrix-for-2d-camera-with-pan-zoom-rotate
		float centerX = parent.width/(2*zoom);
		float centerY = parent.height/(2*zoom);
		parent.pushMatrix();
		parent.scale(zoom);
		parent.translate(centerX, centerY);
		parent.rotate(rotation);
		parent.translate(-canvasPos.x, -canvasPos.y);
	}
	
	public void discardRotation() {
		TestGame.getGame().rotate(-rotation);
	}
	
	public void discardZoom() {
		TestGame.getGame().scale(1f/rotation);		
	}
	
	public void deattachCamera() {
		TestGame.getGame().popMatrix();
	}
	
	public void update(float dt) {
		if(anim != null) {			
			canvasPos.set(anim.proceed(dt));
			if(anim.isFinished()) anim = null;
			return;
		}
	}
	
	/* Transformation methods */
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
		if(anim != null) return;
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
	
	/* Coordination */
	public boolean propOnScreen(PropEntity e) {
		IsoVector screenPos = e.getCanvasPos().toScreen(parent.getCoordinator(), this);
		PivotImage sprite = e.prop.getSprite();
		return !(screenPos.x < -(sprite.width-sprite.pivot.x)
				|| screenPos.x > parent.width+sprite.pivot.x
				|| screenPos.y < -(sprite.height-sprite.pivot.y)
				|| screenPos.y > parent.height+sprite.pivot.y);
	}
	
	/* Getters */
	public String getLabel() {
		return label;
	}
	
	public IsoVector getCanvasPos() {
		return canvasPos;
	}
	
	public IsoVector getWorldPos() {
		return canvasPos.toWorld(TestGame.getGame().getCoordinator(), this);
	}

	public float getRotation() {
		return rotation;
	}
	
	public float getZoom() {
		return zoom;
	}
	
	/* Special methods */
	public String toString() {
		return String.format("Camera Canvas Pos:{%.0f, %.0f}", canvasPos.x, canvasPos.y);
	}
}
