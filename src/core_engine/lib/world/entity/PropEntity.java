package lib.world.entity;

import lib.core.IsomApp;
import lib.image.Sprite;
import lib.maths.IsoVector;
import lib.registry.SpriteRegistry;
import lombok.Getter;
import lombok.Setter;

public class PropEntity extends Entity implements Comparable<Entity> {

	private @Getter @Setter Sprite sprite;

	private @Getter IsoVector canvasPos;

	public PropEntity(IsomApp parent, int spriteID, IsoVector worldPos) {
		super(parent, worldPos);
		this.sprite = SpriteRegistry.getSpriteByID(spriteID);
		this.canvasPos = worldPos.toCanvas(parent.getCoordinator(), parent.getCamera());
	}

	@Override
	public void render() {
		parent.imageOnPivot(sprite.getImage(), canvasPos.x, canvasPos.y);
	}

	@Override
	public void update(float dt) {}

	/**
	 * Compares entities with their canvas y-coordinations <br>
	 * In order to sort them for proper rendering order.
	 */
	@Override
	public int compareTo(Entity o) {
		if (o instanceof PropEntity) {
			PropEntity other = (PropEntity) o;
			if (other.canvasPos.y > this.canvasPos.y)
				return -1;
			else if (other.canvasPos.y < this.canvasPos.y)
				return 1;
			else
				return 0;
		}

		IsoVector canvasPos = o.worldPos.toCanvas(parent.getCoordinator(), parent.getCamera());
		if (canvasPos.y > this.canvasPos.y)
			return -1;
		else if (canvasPos.y < this.canvasPos.y)
			return 1;
		else
			return 0;
	}

}
