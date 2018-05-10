package lib.stage;

import lib.core.Drawable;

public abstract class Stage implements Drawable {
	public String name = "Unnamed Stage";
	
	public void updateTick() {};
	
	public void dispose() {};
}
