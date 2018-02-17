package lib.camera;

import core.TestGame;
import lib.Updatable;
import lib.animation.Animation2v;
import lib.animation.Animation2v.Easing2v;
import lib.maths.IsoVector;

public class Camera implements Updatable {
	private static final float EASING_PX_PER_SEC = 400f; //canvas pixels 
	
	String label;
	IsoVector canvasPos = new IsoVector();
	
	Animation2v anim = null;
	
	float width, height;
	float zoom = 1f;
	float rotation = 0f;

	public Camera(String label, float width, float height) {
		this.width = width;
		this.height = height;
		this.label = label;
	}
	
	/**/
	public void attachCamera() { //TODO test : https://gamedev.stackexchange.com/questions/74007/generating-transformation-matrix-for-2d-camera-with-pan-zoom-rotate
		TestGame g = TestGame.getGame();
		float centerX = g.width/(2*zoom);
		float centerY = g.height/(2*zoom);
		g.pushMatrix();
		g.scale(zoom);
		g.translate(centerX, centerY);
		g.rotate(rotation);
		g.translate(-canvasPos.x, -canvasPos.y);
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
	
	@Override
	public void update(float dt) {
		if(anim != null) {			
			canvasPos.set(anim.proceed(dt));
			if(anim.isFinished()) anim = null;
			return;
		}
	}
	
	/**/
	public void resize(float w, float h) {
		width = w;
		height = h;
	}
	
	public void moveTo(float canvasX, float canvasY) {
		anim = new Animation2v();
		anim.from = canvasPos;
		anim.to = new IsoVector(canvasX, canvasY);
		anim.duration = canvasPos.dist(anim.to) / EASING_PX_PER_SEC;
		anim.easing = Easing2v.SINE_IN_OUT;
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
	
	/**/
	public String getLabel() {
		return label;
	}
	
	public IsoVector getCanvasPos() {
		return canvasPos;
	}

	public float getRotation() {
		return rotation;
	}
	
	public float getZoom() {
		return zoom;
	}
	
	/**/
	public String toString() {
		return String.format("Camera Canvas Pos:{%.0f, %.0f}", canvasPos.x, canvasPos.y);
	}
}
