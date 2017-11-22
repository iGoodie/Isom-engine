package lib.camera;

import core.TestGame;
import lib.maths.IsoVector;

public class Camera {
	public static final float TWEENING_FACTOR = 5f; //0.2s delay to target pos
	
	IsoVector canvasPos = new IsoVector();
	IsoVector targetPos = new IsoVector();
	float cameraScale = 1;
	float cameraRotate = 0;

	/**/
	public void attachCamera() { //TODO test : https://gamedev.stackexchange.com/questions/74007/generating-transformation-matrix-for-2d-camera-with-pan-zoom-rotate
		TestGame g = TestGame.getGame();
		float cx = g.width/(2*cameraScale), 
				cy = g.height/(2*cameraScale);
		g.pushMatrix();
		g.scale(cameraScale);
		g.translate(cx, cy);
		g.rotate(cameraRotate);
		g.translate(-canvasPos.x, -canvasPos.y);
		g.scale(cameraScale);
	}
	
	public void deattachCamera() {
		TestGame g = TestGame.getGame();
		g.popMatrix();
	}
	
	public void update(float dt) {
		IsoVector vel = IsoVector.sub(targetPos, canvasPos);
		vel.mult(TWEENING_FACTOR * dt);
		canvasPos.add(vel);
	}
	
	/**/
	public void moveTo(float x, float y) {
		targetPos.set(x, y);
	}

	public void move(float dx, float dy) {
		targetPos.add(dx, dy);
	}
	
	public void zoomTo(float scale) {
		cameraScale = scale;
	}
	
	public void zoom(float deltaScale) {
		cameraScale += deltaScale;
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

	public float getCameraScale() {
		return cameraScale;
	}

	//TODO debug test
	public IsoVector getScreenPos(IsoVector worldPos) {
		TestGame game = TestGame.getGame();
		IsoVector mid = new IsoVector(game.width/2, game.height/2);
		IsoVector pos = IsoVector.sub(worldPos, this.canvasPos);
		pos.rotate(cameraRotate);
		pos.div(cameraScale);
		pos.add(mid);
		return pos;
	}
	
	public String toString() {
		return String.format("Camera:{%.0f, %.0f}", canvasPos.x, canvasPos.y);
	}

	public void renderDebug(TestGame game) {
		game.text(this, 10, 20);
		game.text("Zoom: " + cameraScale, 10, 31);
	}
}
