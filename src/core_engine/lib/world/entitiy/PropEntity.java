package lib.world.entitiy;

import lib.core.GameBase;
import lib.maths.IsoVector;

public class PropEntity extends Entity implements Comparable<Entity> {
	
	public Prop prop;
	
	private IsoVector canvasPos;
	
	public PropEntity(GameBase parent, int propId, IsoVector worldPos) {
		super(parent, worldPos);
		this.prop = Prop.getByID(propId);
		this.canvasPos = worldPos.toCanvas(parent.getCoordinator(), parent.getCamera());
	}

	@Override
	public void render() {
		parent.imageOnPivot(prop.getSprite(), canvasPos.x, canvasPos.y);
	}

	@Override
	public void update(float dt) {}

	public IsoVector getCanvasPos() {
		return canvasPos;
	}
	
	/**
	 * Compares entities with their canvas y-coordinations <br>
	 * In order to sort them for proper rendering order.
	 */
	@Override
	public int compareTo(Entity o) {
		if(o instanceof PropEntity) {
			PropEntity ope = (PropEntity) o;
			if(ope.canvasPos.y > this.canvasPos.y) return -1;
			else if(ope.canvasPos.y < this.canvasPos.y) return 1;
			else return 0;
		}
		
		IsoVector canvasPos = o.worldPos.toCanvas(parent.getCoordinator(), parent.getCamera());
		if(canvasPos.y > this.canvasPos.y) return -1;
		else if(canvasPos.y < this.canvasPos.y) return 1;
		else return 0;
	}

}
