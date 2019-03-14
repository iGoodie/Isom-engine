package lib.world;

import org.dyn4j.dynamics.World;

import lib.core.Drawable;
import lib.core.GameBase;

public class IsometricPlane extends World implements Drawable {
	
	private int mapId;
	private GameBase parent;
	private Tile[][] tiles;

	public IsometricPlane(GameBase parent) {
		super();
		this.parent = parent;
		setGravity(ZERO_GRAVITY);
	}
	
	@Override
	public void update(float dt) {
		update(dt);
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}
	
}
