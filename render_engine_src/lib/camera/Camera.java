package lib.camera;

import core.TestGame;
import lib.Updatable;
import lib.maths.IsoVector;

public class Camera implements Updatable {
	public static final float TWEENING_FACTOR = 5f; //0.2s delay to target pos | 1/TF seconds delay
	
	IsoVector canvasPos = new IsoVector(), targetPos = new IsoVector();
	float width, height;
	float zoom = 1f;
	float cameraRotate = 0f;

	public Camera(float w, float h) {
		width = w;
		height = h;
	}
	
	/**/
	public void attachCamera() { //TODO test : https://gamedev.stackexchange.com/questions/74007/generating-transformation-matrix-for-2d-camera-with-pan-zoom-rotate
		TestGame g = TestGame.getGame();
		float centerX = g.width/(2*zoom), 
				centerY = g.height/(2*zoom);
		g.pushMatrix();
		g.scale(zoom);
		g.translate(centerX, centerY);
		g.rotate(cameraRotate);
		g.translate(-canvasPos.x, -canvasPos.y);
	}
	
	public void discardRotation() {
		TestGame.getGame().rotate(-cameraRotate);
	}
	
	public void discardZoom() {
		TestGame.getGame().scale(1f/cameraRotate);		
	}
	
	public void deattachCamera() {
		TestGame.getGame().popMatrix();
	}
	
	@Override
	public void update(float dt) {
		IsoVector vel = IsoVector.sub(targetPos, canvasPos);
		vel.mult(TWEENING_FACTOR * dt);
		canvasPos.add(vel);
	}
	
	/**/
	public void resize(float w, float h) {
		width = w;
		height = h;
	}
	
	public void moveTo(float x, float y) {
		targetPos.set(x, y);
	}

	public void move(float dx, float dy) {
		targetPos.add(dx, dy);
	}
	
	public void zoomTo(float scale) {
		zoom = scale;
	}
	
	public void zoom(float deltaScale) {
		zoom += deltaScale;
	}
	
	public void rotateTo(float rotate) {
		cameraRotate = rotate;
	}
	
	public void rotate(float deltaRotation) {
		cameraRotate += deltaRotation;
	}
	
	/**/
	public IsoVector getCanvasPos() {
		return canvasPos;
	}

	public float getRotation() {
		return cameraRotate;
	}
	
	public float getZoom() {
		return zoom;
	}
	
	/**/
	public String toString() {
		return String.format("Camera Canvas Pos:{%.0f, %.0f}", canvasPos.x, canvasPos.y);
	}

	public void renderDebug(TestGame game) {
		game.text(this, 10, 20);
		game.text("Zoom: " + zoom, 10, 31);
	}
}
