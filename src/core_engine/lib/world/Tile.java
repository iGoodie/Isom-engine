package lib.world;

import java.util.ArrayList;

import com.programmer.igoodie.utils.log.ConsolePrinter;

import lib.image.PivotImage;
import processing.core.PImage;

public class Tile {
	
	private static ArrayList<Tile> tiles = new ArrayList<>();

	public static Tile generateTile(PImage sprite) {
		Tile tile = new Tile(sprite);
		tiles.add(tile);
		ConsolePrinter.info("Tile#%d registered. (%s)", tile.id, tile);
		return tile;
	}
	
	public static Tile generateTile(int id, PImage sprite) {
		if(id >= tiles.size()) {
			throw new IllegalArgumentException("Cannot override a non-existing tile id(" + id + ")");
		}
		Tile tile = new Tile(sprite);
		tiles.set(id, tile);
		return tile;
	}
	
	public static Tile getByID(int id) {
		return tiles.get(id);
	}
	
	private int id;
	private PivotImage sprite;
	
	private Tile(PImage sprite) {
		this(new PivotImage(sprite));
	}
	
	private Tile(PivotImage sprite) {
		this.id = tiles.size();
		this.sprite = sprite;
	}

	public int getID() {
		return id;
	}
	
	public PivotImage getSprite() {
		return sprite;
	}
}
