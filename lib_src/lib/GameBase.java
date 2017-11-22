package lib;

import lib.camera.Camera;
import processing.core.PApplet;

public class GameBase extends PApplet {
	/* Cameras */
	private Camera[] cameras = new Camera[16];
	private int selectedCam = 0;
	
	/* Constructors */
	public GameBase() {
		for(int i=0; i<cameras.length; i++) cameras[i] = new Camera();
	}
	
	/* Methods */
	public Camera getCamera() {
		return cameras[selectedCam];
	}
	
	public void selectCamera(int index) {
		selectedCam = index;
	}
	
	/* Text-related methods */
	public void text(Object o, float x, float y) {
		text(o.toString(), x, y);
	}
	
	public float textHeight() {
		return textAscent() + textDescent();
	}
	
	/* Drawing methods */
	public void circle(float x, float y, float r) {
		ellipse(x, y, r, r);
	}
}
