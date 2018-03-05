package lib.core;

import igoodie.utils.io.FileUtils;
import lib.camera.Camera;
import lib.image.PivotImage;
import lib.stage.Stage;
import lib.util.time.DeltaTimer;
import processing.core.PApplet;
import processing.core.PImage;

public class GameBase extends PApplet {
	
	public static void main (final String args[]) {
		FileUtils.setExternalDataPath(IsoConstants.EXTERNAL_DATA_PATH);
		PApplet.main(args);
	}
	
	/* Game time */
	protected DeltaTimer deltaTimer = new DeltaTimer();
	protected float blackScreenTime;
	
	/* Stage */
	protected Stage currentStage;	
	
	/* Cameras */
	private Camera[] cameras = new Camera[2];
	private int selectedCam = 0;
	
	/* Flags */
	public boolean debugEnabled = true;
	
	/* Constructors */
	public GameBase() {
		for(int i=0; i<cameras.length; i++) cameras[i] = new Camera("Cam#"+i, 0, 0);
	}
	
	/* Methods */
	public Camera getCamera() {
		return cameras[selectedCam];
	}
	
	public void selectCamera(int index) {
		selectedCam = index;
	}
	
	public void selectCamera(String label) {
		for(int i=0; i<cameras.length; i++) {
			if(cameras[i].getLabel().equals(label)) {
				selectedCam = i;
			}
		}
	}
	
	public PivotImage loadImage(String filename, float x, float y) {
		return new PivotImage(loadImage(filename), x, y);
	}
	
	/* General Methods */
	public void changeStage(Stage s) {
		currentStage = s;
	}
	
	/* Text-related methods */
	public void text(Object o, float x, float y) {
		text(o.toString(), x, y);
	}
	
	public void textWithStroke(String str, float x, float y) { //TODO: Parameterize fill colors
		fill(0);
		text(str, x-1, y);
		text(str, x+1, y);
		text(str, x, y-1);
		text(str, x, y+1);
		fill(255);
		text(str, x, y);
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

	public void image(PivotImage img, float x, float y) {
		image((PImage)img, x-img.pivot.x, y-img.pivot.y);
	}
}

