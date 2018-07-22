package map;

import java.util.ArrayList;

import lib.image.PivotImage;
import processing.core.PImage;

public class Tile {
	
	private static ArrayList<Tile> tiles = new ArrayList<>();

	public static Tile generateTile(PImage sprite) {
		Tile tile = new Tile(sprite);
		tiles.add(tile);
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
