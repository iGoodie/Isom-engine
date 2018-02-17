package lib.graphics;

import static lib.core.GameBase.*;

import lib.core.GameBase;

public class StyleManager {
	public static GameBase base;
	
	public static void init(GameBase gb) {
		base = gb;
	}
	
	public static void rectModeCorner() {
		base.rectMode(CORNER);
	}
	
	public static void rectModeCenter() {
		base.rectMode(CENTER);
	}
	
	public static void rectModeCorners() {
		base.rectMode(CORNERS);
	}
	
	public static void rectModeRadius() {
		base.rectMode(RADIUS);
	}
	
	public static void ellipseModeCenter() {
		base.ellipseMode(CENTER);
	}
	
	public static void ellipseModeRadius() {
		base.ellipseMode(RADIUS);
	}
	
	public static void ellipseModeCorner() {
		base.ellipseMode(CORNER);
	}
	
	public static void ellipseModeCorners() {
		base.ellipseMode(CORNERS);
	}
	
	public static void strokeCapRound() {
		base.strokeCap(ROUND);
	}
	
	public static void strokeCapSquare() {
		base.strokeCap(SQUARE);
	}
	
	public static void strokeCapProject() {
		base.strokeCap(PROJECT);
	}
	
	public static void strokeJoinMiter() {
		base.strokeJoin(MITER);
	}
	
	public static void strokeJoinBevel() {
		base.strokeJoin(BEVEL);
	}
	
	public static void strokeJoinRound() {
		base.strokeJoin(ROUND);
	}
	
	public static void imgModeCorner() {
		base.imageMode(CORNER);
	}
	
	public static void imgModeCorners() {
		base.imageMode(CORNERS);
	}
	
	public static void imgModeCenter() {
		base.imageMode(CENTER);
	}
	
	public static void textureModeImage() {
		base.textureMode(IMAGE);
	}
	
	public static void textureModeNormal() {
		base.textureMode(NORMAL);
	}
	
	public static void shapeModeCenter() {
		base.shapeMode(CENTER);
	}
	
	public static void shapeModeCorner() {
		base.shapeMode(CORNER);
	}
	
	public static void blendModeBlend() {
		base.blendMode(BLEND);
	}
	
	public static void blendModeAdd() {
		base.blendMode(ADD);
	}
	
	public static void blendModeSubtract() {
		base.blendMode(SUBTRACT);
	}

	public static void colorModeRGB() {
		base.colorMode(RGB, 255, 255, 255);
	}
	
	public static void colorModeHSB() {
		base.colorMode(HSB, 360, 100, 100);
	}
}
