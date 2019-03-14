package lib.graphics;

import java.util.HashMap;

import lib.image.PivotImage;

public class Cursors {
	
	public static HashMap<String, PivotImage> cursors = new HashMap<>();
	
	static {
		putCursor("default", null);
	}
	
	public static boolean putCursor(String name, PivotImage cursorImage) {
		if(cursors.containsKey(name)) return false;
		cursors.put(name, cursorImage);
		return true;
	}

	public static PivotImage getCursor(String name) {
		PivotImage img = cursors.get(name);
		if(img == null) img = cursors.get("default");
		return img;
	}
	
	public static PivotImage getDefaultCursor() {
		return getCursor("default");
	}
}
