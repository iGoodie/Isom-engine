package lib.registry;

import java.util.ArrayList;
import java.util.List;

import com.programmer.igoodie.utils.log.ConsolePrinter;

import lib.world.Tile;
import processing.core.PImage;

public class TileRegistry {

	private static final List<Tile> TILES = new ArrayList<>();
	
	public static Tile registerTile(PImage image) {
		Tile tile = Tile.generateTile(image);
		TILES.add(tile);
		
		ConsolePrinter.info("Tile#%d registered (%s)", tile.getId(), tile);
		
		return tile;
	}
	
	public static Tile getTileByID(int id) {
		return TILES.get(id);
	}
	
	public static int tileCount() {
		return TILES.size();
	}
	
}
