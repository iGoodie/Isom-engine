package entity;

import lib.core.Drawable;
import lib.core.GameBase;
import lib.maths.IsoVector;

public abstract class Entity implements Drawable, Comparable<Entity> {
	
	public GameBase parent;
	
	public IsoVector worldPos;
	
	public Entity(GameBase parent) {
		this.worldPos = IsoVector.createOnWorld(0, 0);
		this.parent = parent;
	}
	
	public Entity(GameBase parent, IsoVector worldPos) {
		this.worldPos = worldPos;
		this.parent = parent;
	}
	
	public IsoVector getWorldPos() {
		return worldPos;
	}
	
	public IsoVector getCanvasPos() {
		return worldPos.toCanvas(parent.getCoordinator(), parent.getCamera());
	}
}
