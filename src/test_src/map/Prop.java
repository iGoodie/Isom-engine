package map;

import java.util.ArrayList;

import lib.image.PivotImage;

public class Prop {

	private static ArrayList<Prop> props = new ArrayList<>();
	
	public static Prop getByID(int id) {
		return props.get(id);
	}
	
	private int id;
	private PivotImage sprite;
	
	public Prop(PivotImage sprite) {
		this.id = props.size();
		this.sprite = sprite;
		props.add(this);
	}
	
	public int getID() {
		return id;
	}
	
	public PivotImage getSprite() {
		return sprite;
	}
}
