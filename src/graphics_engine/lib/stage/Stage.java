package lib.stage;

import lib.core.Drawable;
import lib.core.IsomApp;

public abstract class Stage<P extends IsomApp> implements Drawable {
	
	public P parent;
	
	public String name = "Unnamed Stage";
	
	public Stage(P parent) {
		this.parent = parent;
	}
	
	public void updateTick() {};
	
	public void dispose() {};
}
