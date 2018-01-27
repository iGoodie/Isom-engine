package lib.camera;

import lib.GameBase;
import lib.maths.IsoVector;

/*
 * World -> Canvas -> Camera Buffer -> Monitor
 */
public class Coordinator {
	private static final int TILE_WIDTH = 128;
	private static final int TILE_HEIGHT = 64;
	
	private static final int HALF_TILE_WIDTH = TILE_WIDTH/2;
	private static final int HALF_TILE_HEIGHT = TILE_HEIGHT/2;
	
	private static GameBase parent;
	
	public static void setParent(GameBase p) {
		parent = p;
	}

	/**
	 * Formula for C:CanvasPos where O:CameraCanvasPos and S:ScreenPos
	 * <br/> [Cx, Cy] = [Ox, Oy] + 1/s * ([w/2, h/2]-[Sx, Sy])
	 * @param c Camera to be considered
	 * @param screenPos Screen position to be converted
	 * @return Canvas position of given screen position according to the camera position and screen size.
	 */
	public static IsoVector screenToCanvas(Camera c, IsoVector screenPos) {
		int halfWidth=parent.width/2, halfHeight=parent.height/2;
		IsoVector deltaScreen = IsoVector.sub(screenPos, halfWidth, halfHeight); // [w/2, h/2] - [Sx, Sy] = dS
		return deltaScreen.div(c.getZoom()).add(c.getCanvasPos()); // dS/z + [Ox, Oy]
	}
	
	/**
	 * TODO: Fix
	 * Assumed the formula is M = [w/2, h/2] - z*([Cx, Cy]-[Ox, Oy])
	 * @param c
	 * @param canvasPos
	 * @return
	 */
	public static IsoVector canvasToScreen(Camera c, IsoVector canvasPos) {
		int halfWidth=parent.width/2, halfHeight=parent.height/2;
		IsoVector deltaCanvas = IsoVector.sub(canvasPos, c.getCanvasPos()); // [Cx, Cy] - [Ox, Oy] = dC
		deltaCanvas.mult(c.getZoom()); // dC2 <- dC*z
		return IsoVector.sub(halfWidth, halfHeight, deltaCanvas); // [w/2, h/2] - dC2
	}

	/**
	 * Converts given world position to canvas position.
	 * @param worldPos World position to be converted
	 * @return Canvas position of given world position
	 */
	public static IsoVector worldToCanvas(IsoVector worldPos) {
		float cx = (worldPos.x + worldPos.y) * HALF_TILE_WIDTH;
		float cy = (worldPos.x - worldPos.y) * -HALF_TILE_HEIGHT;
		return new IsoVector(cx, cy);
	}
	
	public static IsoVector worldToCanvas(float wx, float wy) {
		float cx = (wx + wy) * HALF_TILE_WIDTH;
		float cy = (wx - wy) * -HALF_TILE_HEIGHT;
		return new IsoVector(cx, cy);
	}
	
	/**
	 * Converts given canvas position to world position
	 * @param canvasPos Canvas position to be converted
	 * @return World position of given canvas position
	 */
	public static IsoVector canvasToWorld(IsoVector canvasPos) {
		float xu = canvasPos.x / HALF_TILE_WIDTH;
		float yv = canvasPos.y / HALF_TILE_HEIGHT;
		float worldx = (xu - yv) / 2;
		float worldy = (xu + yv) / 2;
		return new IsoVector(worldx, worldy);
	}

	/*
	public static IsoVector camera2Canvas(Camera c, IsoVector camPos) {
		return camera2Canvas(c, camPos.x, camPos.y);
	}

	public static IsoVector camera2Canvas(Camera c, float camx, float camy) {
		float x = camx * c.getZoom() + c.getCanvasPos().x;
		float y = camy * c.getZoom() + c.getCanvasPos().y;
		return new IsoVector(x, y);
	}
	
	public static IsoVector screen2Camera(Camera c, float mousex, float mousey) {
		return new IsoVector((mousex-c.width/2) / c.zoom, (mousey-c.height/2) / c.zoom);
	}
	
	public static IsoVector camera2Screen(Camera c, float camx, float camy) {
		float sx = (camx * c.zoom) + c.width / 2;
		float sy = (camy * c.zoom) + c.height / 2;
		return new IsoVector(sx, sy);
	} */
}
