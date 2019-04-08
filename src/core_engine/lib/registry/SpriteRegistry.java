package lib.registry;

import java.util.ArrayList;
import java.util.List;

import com.programmer.igoodie.utils.log.ConsolePrinter;

import lib.graphics.Sprite;
import lib.image.PivotImage;

public class SpriteRegistry {

	private static final List<Sprite> SPRITES = new ArrayList<>();

	public static Sprite registerSprite(PivotImage image) {
		Sprite sprite = Sprite.generateSprite(image);
		SPRITES.add(sprite);
		
		ConsolePrinter.info("Prop#%d registered. (%s)", sprite.getId(), sprite);
		
		return sprite;
	}

	public static Sprite getSpriteByID(int id) {
		return SPRITES.get(id);
	}

	public static int spriteCount() {
		return SPRITES.size();
	}
	
}
