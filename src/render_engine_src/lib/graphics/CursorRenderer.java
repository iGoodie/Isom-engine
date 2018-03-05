package lib.graphics;

import java.util.HashMap;

import lib.core.GameBase;
import lib.image.PivotImage;

public class CursorRenderer {
	private static final String DEFAULT_CURSOR_NAME = "default";
	
	private static GameBase parent;
	
	public static HashMap<String, PivotImage> cursors = new HashMap<>();
	
	public static void setParent(GameBase p) {
		parent = p;
		initialize();
	}
	
	private static void initialize() {
		cursors.put(DEFAULT_CURSOR_NAME, parent.loadImage("cursors/cursor_default.png", 2, 2));
		cursors.put("map", parent.loadImage("cursors/cursor_map.png", 2, 2));
	}

	public static void setCursor(String name) {
		PivotImage img = cursors.get(name);
		if(img == null) img = cursors.get(DEFAULT_CURSOR_NAME);
		parent.cursor(img, (int)img.pivot.x, (int)img.pivot.y);
	}
	
	public static void setDefaultCursor() {
		setCursor(DEFAULT_CURSOR_NAME);
	}
}
