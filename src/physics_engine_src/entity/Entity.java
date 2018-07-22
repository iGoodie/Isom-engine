package entity;

import lib.core.Drawable;
import lib.maths.IsoVector;

public abstract class Entity implements Drawable {
	
	// TODO Parent here
	
	public IsoVector worldPos;
	
	public Entity() {
		worldPos = new IsoVector();
	}
}
