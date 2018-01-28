package lib.maths;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class IsoVector {
	public float x, y, z;

	public static IsoVector add(IsoVector v1, IsoVector v2) {
		return new IsoVector(v1.x+v2.x, v1.y+v2.y);
	}

	public static IsoVector sub(IsoVector v1, IsoVector v2) {
		return new IsoVector(v1.x-v2.x, v1.y-v2.y);
	}

	public static IsoVector sub(IsoVector v1, float x, float y) {
		return new IsoVector(v1.x-x, v1.y-y);
	}

	public static IsoVector sub(float x, float y, IsoVector v) {
		return new IsoVector(x-v.x, y-v.y);
	}
	
	public static IsoVector mult(IsoVector v1, float n) {
		return new IsoVector(v1.x*n, v1.y*n);
	}

	public static IsoVector cross(IsoVector v1, IsoVector v2) {
		return new IsoVector(v1.x*v2.x, v1.y*v2.y);
	}

	public static float dot(IsoVector v1, IsoVector v2) {
		return v1.x*v2.x + v1.y*v2.y + v1.z*v2.z;
	}

	public static IsoVector div(IsoVector v1, float n) {
		return new IsoVector(v1.x/n, v1.y/n);
	}

	public static float dist(IsoVector v1, IsoVector v2) {
		return v1.dist(v2);
	}

	static public float angleBetween(IsoVector v1, IsoVector v2) {
		if (v1.x == 0 && v1.y == 0 && v1.z == 0 ) return 0.0f;
		if (v2.x == 0 && v2.y == 0 && v2.z == 0 ) return 0.0f;

		double dot = v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
		double v1mag = Math.sqrt(v1.x * v1.x + v1.y * v1.y + v1.z * v1.z);
		double v2mag = Math.sqrt(v2.x * v2.x + v2.y * v2.y + v2.z * v2.z);
		double amt = dot / (v1mag * v2mag);
		if (amt <= -1) {
			return PConstants.PI;
		} else if (amt >= 1) {
			return 0;
		}
		return (float) Math.acos(amt);
	}

	/* Constructors */
	public IsoVector() {
		super();
	}

	public IsoVector(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public IsoVector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/* Set & Get */
	public IsoVector set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public IsoVector set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public IsoVector set(IsoVector v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
		return this;
	}

	public float[] get() {
		return new float[]{x, y, z};
	}

	public float[] get(float[] target) {
		if(target==null) return get();
		if(target.length == 2) {
			target[0] = x;
			target[1] = y;
		}
		if(target.length >= 3) {
			target[2] = z;
		}
		return target;
	}

	/* Random 2D & 3D */

	/* Mag & MagSq */
	public float mag() {
		return (float) Math.sqrt(x*x + y*y + z*z);
	}

	public float magSq() {
		return (x*x + y*y + z*z);
	}

	/* Add & Sub */
	public IsoVector add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}

	public IsoVector add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	public IsoVector add(IsoVector v) {
		return add(v.x, v.y, v.z);
	}

	public IsoVector sub(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	public IsoVector sub(float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	public IsoVector sub(IsoVector v) {
		return sub(v.x, v.y, v.z);
	}

	/* Mult & Div */
	public IsoVector mult(float n) {
		this.x *= n;
		this.y *= n;
		this.z *= n;
		return this;
	}

	public IsoVector div(float n) {
		this.x /= n;
		this.y /= n;
		this.z /= n;
		return this;
	}

	/* Dist */
	public float dist(IsoVector v) {
		float dx = x - v.x;
		float dy = y - v.y;
		float dz = z - v.z;
		return (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
	}

	public float dist(float x, float y) {
		float dx = this.x - x;
		float dy = this.y - y;
		return (float) Math.sqrt(dx*dx + dy*dy);
	}

	public float dist(float x, float y, float z) {
		float dx = this.x - x;
		float dy = this.y - y;
		float dz = this.z - z;
		return (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
	}

	/* Dot */
	public float dot(IsoVector v) {
		return x*v.x + y*v.y + z*v.z;
	}

	public float dot(float x, float y, float z) {
		return this.x*x + this.y*y + this.z*z;
	}

	public float dot(float x, float y) {
		return this.x*x + this.y*y;
	}

	/* Cross */

	/* Normalize & Limiters */
	public IsoVector normalize() {
		float mag = mag();
		if(mag!=0 && mag!=1) {
			div(mag);
		}
		return this;
	}

	public IsoVector limit(float max) {
		if(magSq() > max*max) {
			normalize();
			mult(max);
		}
		return this;
	}

	public IsoVector len(float len) {
		normalize();
		mult(len);
		return this;
	}

	/* From Angle & Angle related */
	public float heading() {
		return (float) Math.atan2(y, x);
	}

	public IsoVector rotate(float angle) {
		float temp = x;
		x = x*PApplet.cos(angle) - y*PApplet.sin(angle);
		y = temp*PApplet.sin(angle) + y*PApplet.cos(angle);
		return this;
	}

	public IsoVector lerp(IsoVector v, float amt) {
		x = PApplet.lerp(x, v.x, amt);
		y = PApplet.lerp(y, v.y, amt);
		z = PApplet.lerp(z, v.z, amt);
		return this;
	}

	/* Object Overrides */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append(x + ", ");
		sb.append(y + ", ");
		sb.append(z + "} ");
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof IsoVector) {
			final IsoVector v = (IsoVector) obj;
			return x==v.x && y==v.y && z==v.z;
		}
		else if(obj instanceof PVector) {
			final PVector v = (PVector) obj;
			return x==v.x && y==v.y && z==v.z;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + Float.floatToIntBits(x);
		result = 31 * result + Float.floatToIntBits(y);
		result = 31 * result + Float.floatToIntBits(z);
		return result;
	}

	/* Special Definitions */
	public String toCastedString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append((int)x + ", ");
		sb.append((int)y + ", ");
		sb.append((int)z + "} ");
		return sb.toString();
	}
	
	public String toCastedString2D() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append((int)x + ", ");
		sb.append((int)y + "} ");
		return sb.toString();
	}
}
