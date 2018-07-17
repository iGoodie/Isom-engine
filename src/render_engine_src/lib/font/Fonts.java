package lib.font;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import processing.core.PFont;

// TODO
public final class Fonts {
	
	public static final PFont DEFAULT_FONT = new PFont(Font.decode("Arial"), true);

	private static HashMap<String, PFont> fonts = new HashMap<>();
	
	public static void pushFont(String name, PFont font) {
		fonts.put(name, font);
	}
	
	public static void pushFont(String name, File fontFile) {
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
			fonts.put(name, new PFont(font, true));
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
}
