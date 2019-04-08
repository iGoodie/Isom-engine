package lib.world.entitiy;

import lib.core.Drawable;
import lib.core.GameBase;
import lib.maths.IsoVector;
import lib.world.IsometricCell;
import lombok.Getter;

public abstract class Entity implements Drawable, Comparable<Entity> {

	protected GameBase parent;
	protected IsometricCell parentCell;
	protected @Getter IsoVector worldPos;

	public Entity(GameBase parent, IsoVector worldPos) {
		this.worldPos = worldPos;
		this.parent = parent;
	}

	public Entity(GameBase parent) {
		this(parent, IsoVector.createOnWorld(0, 0));
	}

	public IsoVector getCanvasPos() {
		return worldPos.toCanvas(parent.getCoordinator(), parent.getCamera());
	}
	
	public void move(float dx, float dy) {
		worldPos.add(dx, dy);
		parentCell.updateForEntity(this);
	}

}
