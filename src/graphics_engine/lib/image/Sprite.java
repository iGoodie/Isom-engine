package lib.image;

import lib.registry.SpriteRegistry;
import lombok.Getter;

public class Sprite {
	
	public static Sprite generateSprite(PivotImage image) {
		Sprite sprite = new Sprite();
		sprite.image = image;
		sprite.id = SpriteRegistry.spriteCount();
		return sprite;
	}
	
	private @Getter int id;
	private @Getter PivotImage image;
	
	@Override
	public String toString() {
		String sizeString = String.format("{w:%d, h:%d}", image.width, image.height);
		return super.toString() + " " + sizeString;
	}

}
