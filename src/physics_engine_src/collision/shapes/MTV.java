package collision.shapes;

import lib.maths.IsoVector;

/**
 * <b>Minimum Translation Vector (MTV)</b> used for collision resolution.
 */
public class MTV {

	boolean collided = false;
	IsoVector mtv = new IsoVector();

	public void setCollided(boolean collided) {
		this.collided = collided;
	}

	public boolean isCollided() {
		return collided;
	}

	public IsoVector getMtv() {
		return mtv;
	}

}
