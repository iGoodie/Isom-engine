package lib.entity;

import java.util.ArrayList;

import com.programmer.igoodie.utils.log.ConsolePrinter;

import lib.image.PivotImage;

public class Prop {

	private static ArrayList<Prop> props = new ArrayList<>();
	
	public static Prop getByID(int id) {
		return props.get(id);
	}
	
	public static Prop generateProp(PivotImage sprite) {
		Prop prop = new Prop(sprite);
		props.add(prop);
		ConsolePrinter.info("Prop#%d registered. (%s)", prop.id, prop);
		return prop;
	}
	
	private int id;
	private PivotImage sprite;
	
	private Prop(PivotImage sprite) {
		this.id = props.size();
		this.sprite = sprite;
	}
	
	public int getID() {
		return id;
	}

	public PivotImage getSprite() {
		return sprite;
	}
	
	@Override
	public String toString() {
		String sizeString = String.format("{w:%d, h:%d}", sprite.width, sprite.height);
		return super.toString() + " " + sizeString;
	}
}
