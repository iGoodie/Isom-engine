package lib.camera;

import lib.core.GameBase;
import lib.maths.IsoVector;

/*
 * World -> Canvas -> Camera Buffer(aka Screen) -> Monitor(Camera + other buffers)
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

	/* Screen Parameterized */
	
	/**
	 * Converts screen coordinates into canvas coordinates.
	 * <br/> <b>Formula:</b> [Cx, Cy] = [Px, Py] + 1/z * ([Sx, Sy] - 1/2*[w, h])
	 * @param c Camera to be considered
	 * @param screenPos Screen position to be converted
	 * @return Canvas position of given screen position according to the camera position and screen size.
	 */
	public static IsoVector screenToCanvas(Camera c, IsoVector screenPos) {
		int halfWidth=parent.width/2, halfHeight=parent.height/2; // size = 1/2*[w, h]
		IsoVector deltaScreen = IsoVector.sub(screenPos, halfWidth, halfHeight); //  [Sx, Sy] - size = dS
		return deltaScreen.div(c.getZoom()).add(c.getCanvasPos()); // 1/z*dS + [Px, Py]
	}

	/**
	 * Converts screen coordinates to world coordinates
	 * <br/> <b>Formula:</b> b = [(Px + 1/z*(Sx-w/2))/Tw, (Py + 1/z*(Sy-h/2)/Th)]
	 * <br/> W = [round(bx+by), round(bx-by)]
	 * @param c The camera displaying world.
	 * @param screenPos Screen coordinates to be converted.
	 * @return World position of given screen position
	 */
	public static IsoVector screenToWorld(Camera c, IsoVector screenPos) {
		IsoVector camPos = c.getCanvasPos();
		// b = [(Px + 1/z*(Sx-w/2))/Tw, (Py + 1/z*(Sy-h/2)/Th)]
		float bx = (camPos.x + (screenPos.x - parent.width/2)/c.getZoom()) / TILE_WIDTH;
		float by = (camPos.y + (screenPos.y - parent.height/2)/c.getZoom()) / TILE_HEIGHT;
		return new IsoVector(Math.round(bx+by), Math.round(bx-by)); // W = [round(bx+by), round(bx-by)]
	}

	/* Canvas Parameterized */
	
	/**
	 * Reverse function of screen to canvas. Converts canvas coordinates into screen coordinates.
	 * <br/> <b>Formula:</b> [Sx, Sy] = 1/2*[w, h] + z*([Cx, Cy]-[Px, Py])
	 * @param c The camera displaying world.
	 * @param canvasPos Canvas coordinates to be converted.
	 * @return Screen position of given canvas position
	 */
	public static IsoVector canvasToScreen(Camera c, IsoVector canvasPos) {
		int halfWidth=parent.width/2, halfHeight=parent.height/2; // size = 1/2*[w, h]
		IsoVector deltaCanvas = IsoVector.sub(canvasPos, c.getCanvasPos()); // dC =  [Cx, Cy]-[Px, Py]
		return deltaCanvas.mult(c.getZoom()).add(halfWidth, halfHeight); // dC*z + size
	}

	/**
	 * Converts given canvas position to world position
	 * <br/> <b>Formula:</b> b = [Cx/Tw, Cy/Th)]; W = [round(bx+by), round(bx-by)]
	 * @param canvasPos Canvas position to be converted
	 * @return World position of given canvas position
	 */
	public static IsoVector canvasToWorld(IsoVector canvasPos) {
		float bx = canvasPos.x / TILE_WIDTH;
		float by = canvasPos.y / TILE_HEIGHT;
		return new IsoVector(Math.round(bx+by), Math.round(bx-by)); // W = [round(bx+by), round(bx-by)]
	}

	/**
	 * Converts given canvas position to world position in exact coordinates. It won't round to return tile coordinates.
	 * <br/> <b>Formula:</b> b = [Cx/Tw, Cy/Th)]; WE= [bx+by, bx-by]
	 * @param canvasPos Canvas position to be converted
	 * @return Exact world position of given canvas position
	 */
	public static IsoVector canvasToWorldExact(IsoVector canvasPos) {
		float bx = canvasPos.x / TILE_WIDTH;
		float by = canvasPos.y / TILE_HEIGHT;
		return new IsoVector(bx+by, bx-by);
	}

	/* World Parameterized */
	
	/**
	 * Converts world coordinates into canvas coordinates.
	 * <br/> <b>Formula:</b> [Cx, Cy] = [Wx+Wy, Wx-Wy] * 1/2 * [Th, Tw]
	 * @param worldPos World position to be converted
	 * @return Canvas position of given world position
	 */
	public static IsoVector worldToCanvas(IsoVector worldPos) {
		return worldToCanvas(worldPos.x, worldPos.y);
	}

	/**
	 * Converts world coordinates into canvas coordinates.
	 * <br/> <b>Formula:</b> [Cx, Cy] = [Wx+Wy, Wx-Wy] * 1/2 * [Th, Tw]
	 * @param wx X value of the world coordinate
	 * @param wy Y value of the world coordinate
	 * @return Canvas position of given world position
	 */
	public static IsoVector worldToCanvas(float wx, float wy) {
		float cx = (wx + wy) * HALF_TILE_WIDTH;
		float cy = (wx - wy) * HALF_TILE_HEIGHT;
		return new IsoVector(cx, cy);
	}
}
