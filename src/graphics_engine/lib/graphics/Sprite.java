package lib.graphics;

import lib.image.PivotImage;
import lib.registry.SpriteRegistry;
import lombok.Getter;

public class Sprite {
	
	public static Sprite generateSprite(PivotImage image) {
		Sprite sprite = new Sprite(image);
		sprite.id = SpriteRegistry.spriteCount();
		return sprite;
	}
	
	private @Getter int id;
	private @Getter PivotImage image;
	
	private Sprite(PivotImage image) {
		this.image = image;
	}
	
	@Override
	public String toString() {
		String sizeString = String.format("{w:%d, h:%d}", image.width, image.height);
		return super.toString() + " " + sizeString;
	}

}
