package lib.maths;

import com.programmer.igoodie.utils.math.Randomizer;

import lib.camera.Camera;
import lib.camera.Coordinator;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class IsoVector {
	
	public static final int SCREEN=0, CANVAS=1, WORLD=2;
	
	public static IsoVector createOnScreen(float x, float y) {
		IsoVector vec = new IsoVector(x, y);
		vec.space = SCREEN;
		return vec;
	}
	
	public static IsoVector createOnCanvas(float x, float y) {
		IsoVector vec = new IsoVector(x, y);
		vec.space = CANVAS;
		return vec;
	}
	
	public static IsoVector createOnWorld(float x, float y) {
		IsoVector vec = new IsoVector(x, y);
		vec.space = WORLD;
		return vec;
	}
	
	public static IsoVector add(IsoVector v1, IsoVector v2) {
		return new IsoVector(v1.x+v2.x, v1.y+v2.y);
	}
	
	public static IsoVector add(IsoVector v1, float n) {
		return new IsoVector(v1.x+n, v1.y+n);
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

	public static float angleBetween(IsoVector v1, IsoVector v2) {
		if (v1.x == 0 && v1.y == 0 && v1.z == 0) return 0.0f;
		if (v2.x == 0 && v2.y == 0 && v2.z == 0) return 0.0f;

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

	public int space = SCREEN;
	public float x, y, z;

	/* Constructors */
	public IsoVector() {
		super();
	}

	public IsoVector(IsoVector vector) {
		this.x = vector.x;
		this.y = vector.y;
		this.z = vector.z;
		this.space = vector.space;
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

	public IsoVector copy() {
		return new IsoVector(x, y, z);
	}
	
	/* Random 2D & 3D */
	public IsoVector randomize2D() {
		return normalizeWithAngle(Randomizer.randomFloat() * PConstants.PI * 2);
	}

	public IsoVector randomize3D() {
		float angle = Randomizer.randomFloat() * PConstants.PI * 2;
		float vz = Randomizer.randomFloat(-1, 1);
		float vx = PApplet.sqrt(1-vz*vz) * PApplet.cos(angle);
		float vy = PApplet.sqrt(1-vz*vz) * PApplet.sin(angle);
		return set(vx, vy, vz);
	}

	/* Mag & MagSq */
	public float mag() {
		return PApplet.sqrt(x*x + y*y + z*z);
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
		return PApplet.sqrt(dx*dx + dy*dy + dz*dz);
	}

	public float dist(float x, float y) {
		float dx = this.x - x;
		float dy = this.y - y;
		return PApplet.sqrt(dx*dx + dy*dy);
	}

	public float dist(float x, float y, float z) {
		float dx = this.x - x;
		float dy = this.y - y;
		float dz = this.z - z;
		return PApplet.sqrt(dx*dx + dy*dy + dz*dz);
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
	public IsoVector cross(IsoVector v) {
		return cross(v.x, v.y, v.z);
	}
	
	public IsoVector cross(float x, float y) {
		return cross(x, y, 0);
	}
	
	public IsoVector cross(float x, float y, float z) {
		float crossX = this.y * z - y * this.z;
		float crossY = this.z * x - z * this.x;
		float crossZ = this.x * y - x * this.y;
		
		return new IsoVector(crossX, crossY, crossZ);
	}

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
	public IsoVector normalizeWithAngle(float angle) {
		this.x = PApplet.cos(angle);
		this.y = PApplet.sin(angle);
		this.z = 0;
		return this;
	}

	public float headingAngle() {
		return PApplet.atan2(y, x);
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

	/* Plane Transformation Wrappers */
	public IsoVector toScreen(Coordinator coord, Camera c) {
		IsoVector screen;
		
		if(space == CANVAS) screen = coord.canvasToScreen(c, this);
		else if(space == WORLD) screen = coord.canvasToScreen(c, coord.worldToCanvas(this));
		else screen = this.copy();
		
		screen.space = SCREEN;
		return screen;
	}
	
	public IsoVector toCanvas(Coordinator coord, Camera...c) {
		IsoVector canvas;
		
		if(space == CANVAS) canvas = this.copy();
		else if(space == WORLD) canvas = coord.worldToCanvas(this);
		else canvas = coord.screenToCanvas(c[0], this);
		
		canvas.space = CANVAS;
		return canvas;
	}
	
	public IsoVector toWorld(Coordinator coord, Camera c) {
		IsoVector world;
		
		if(space == CANVAS) world = coord.canvasToWorld(this);
		else if(space == WORLD) world = this.copy();
		else world = coord.canvasToWorld(coord.screenToCanvas(c, this));
		
		world.space = WORLD;
		return world;
	}
	
	public IsoVector toWorldExact(Coordinator coord, Camera...c) {
		IsoVector world;
		
		if(space == CANVAS) world = coord.canvasToWorldExact(this);
		else if(space == WORLD) world = this.copy();
		else world = coord.canvasToWorldExact(coord.screenToCanvas(c[0], this));
		
		world.space = WORLD;
		return world;
	}
}