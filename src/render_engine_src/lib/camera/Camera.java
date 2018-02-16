package lib.camera;

import core.TestGame;
import lib.Updatable;
import lib.animation.Animation2v;
import lib.animation.Animation2v.Easing;
import lib.maths.IsoVector;

public class Camera implements Updatable {
	private static final float EASING_DURATION = 1f; //seconds 
	
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
	
	public void moveTo(float x, float y) {
		anim = new Animation2v(canvasPos, new IsoVector(x, y), EASING_DURATION, 1);
		anim.easing = Easing.SINE_IN_OUT;
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
