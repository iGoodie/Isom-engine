package lib.image;

import java.awt.Image;

import lib.maths.IsoVector;
import processing.core.PImage;

public class PivotImage extends PImage {
	public IsoVector pivot;

	public PivotImage(Image img, float x, float y) {
		this(img, new IsoVector(x, y));
	}

	public PivotImage(PImage img, float x, float y) {
		this(img.getImage(), new IsoVector(x, y));
	}
	
	public PivotImage(PImage img, IsoVector pivot) {
		this(img.getImage(), pivot);
	}
	
	public PivotImage(Image img, IsoVector pivot) {
		super(img);
		this.pivot = pivot;
	}

}
