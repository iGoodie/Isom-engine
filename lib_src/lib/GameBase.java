package lib;

import lib.camera.Camera;
import lib.image.PivotImage;
import processing.core.PApplet;

public class GameBase extends PApplet {
	/* Cameras */
	private Camera[] cameras = new Camera[2];
	private int selectedCam = 0;
	
	/* Flags */
	public boolean debugEnabled = true;
	
	/* Constructors */
	public GameBase() {
		for(int i=0; i<cameras.length; i++) cameras[i] = new Camera(0, 0);
	}
	
	/* Methods */
	public Camera getCamera() {
		return cameras[selectedCam];
	}
	
	public void selectCamera(int index) {
		selectedCam = index;
	}
	
	public PivotImage loadImage(String filename, float x, float y) {
		return new PivotImage(loadImage(filename), x, y);
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

	public void grid(int offset, int strokeColor) {
		pushStyle();
		stroke(strokeColor);
		for(int i=offset; i<width; i+=offset) { //Vertical lines
			line(i, 0, i, height);
		}
		for(int i=offset; i<height; i+=offset) { //Horizontal lines
			line(0, i, width, i);			
		}
		popStyle();
	}
}

