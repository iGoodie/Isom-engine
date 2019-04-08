package lib.world;

import lib.image.PivotImage;
import lib.registry.TileRegistry;
import lombok.Getter;
import processing.core.PImage;

public class Tile {

	public static Tile generateTile(PImage image) {
		Tile tile = new Tile();
		tile.image = new PivotImage(image);
		tile.id = TileRegistry.tileCount();
		return tile;
	}

	private @Getter int id;
	private @Getter PivotImage image;

	@Override
	public String toString() {
		String sizeString = String.format("{w:%d, h:%d}", image.width, image.height);
		return super.toString() + " " + sizeString;
	}
	
}
