package lib.graphics;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import processing.core.PFont;

public final class Fonts {

	private static HashMap<String, PFont> fonts = new HashMap<>();
	
	static {
		pushFont("default", new PFont(Font.decode("Arial"), true));
	}
	
	public static void pushFont(String name, PFont font) {
		fonts.put(name, font);
	}
	
	public static void pushFont(String name, File fontFile) {
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
			PFont pfont = new PFont(font, true);
			fonts.put(name, pfont);
		}
		catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static PFont getFont(String name) {
		return fonts.get(name);
	}

	public static PFont getDefaultFont() {
		return getFont("default");
	}
}
