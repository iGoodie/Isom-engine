package lib.stage;

import lib.core.Drawable;
import lib.core.GameBase;

public abstract class Stage<P extends GameBase> implements Drawable {
	
	public P parent;
	
	public String name = "Unnamed Stage";
	
	public Stage(P parent) {
		this.parent = parent;
	}
	
	public void updateTick() {};
	
	public void dispose() {};
}
