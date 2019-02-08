package lib.world;

import org.dyn4j.dynamics.World;

import core.TestGame;
import lib.core.Drawable;

public class IsometricPlane extends World implements Drawable {
	
	private int mapId;
	private TestGame parent;
	private Tile[][] tiles;

	public IsometricPlane(TestGame parent) {
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
