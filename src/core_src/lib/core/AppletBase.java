package lib.core;

import lib.config.CommandLineArgs;
import lib.image.PivotImage;
import processing.core.PApplet;
import processing.opengl.PShader;

/**
 * A wrapper class that contains
 * rendering extentions to Processing 
 * and some base methods.
 * @author iGoodie
 */
public class AppletBase extends PApplet {
	
	protected CommandLineArgs cmdLineArgs;
	
	public CommandLineArgs getCmdLineArgs() {
		return cmdLineArgs;
	}
	
	/* Overrides */
	@Override
	public void settings() {
		cmdLineArgs = new CommandLineArgs(args==null ? new String[0] : args);
	}
	
	@Override
	public void filter(PShader shader) { // A Processing bug
		super.filter(shader);
		blendMode(BLEND);
	}
	
	/* Text-related methods */
	public void text(Object o, float x, float y) {
		text(o.toString(), x, y);
	}

	public void textWithStroke(String str, float x, float y, int fillColor, int strokeColor) {
		fill(strokeColor);
		text(str, x-1, y);
		text(str, x+1, y);
		text(str, x, y-1);
		text(str, x, y+1);
		fill(fillColor);
		text(str, x, y);
	}

	public void textWithKerning(String str, float x, float y, int kerningPx) {
		for(char c : str.toCharArray()) {
			text(c, x, y);
			x += textWidth(c) - kerningPx;
		}
	}
	
	public float textHeight() {
		return textAscent() + textDescent();
	}

	/* Extension to Processing methods */
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

	public void imageOnPivot(PivotImage img, float x, float y) {
		image(img, x-img.pivot.x, y-img.pivot.y);
	}

	public PivotImage loadImage(String filename, float x, float y) {
		return new PivotImage(loadImage(filename), x, y);
	}

	public void cursor(PivotImage cursor) {
		if(cursor!=null) 
			cursor(cursor, (int)cursor.pivot.x, (int)cursor.pivot.x);
		else
			noClip();
	}
}
