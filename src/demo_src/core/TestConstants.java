package core;

import lib.core.IsoConstants;

public interface TestConstants {
	public static final String GAME_NAME = "Isometric Test Game";
	public static final String GAME_VERSION = "v0.0.0";
	public static final String WINDOW_TITLE = String.format("%s %s | %s %s", GAME_NAME, GAME_VERSION, IsoConstants.LIBRARY_NAME, IsoConstants.VERSION);

	public static final int ST_SIZE_FACTOR = 60; //sf=60 -> res=960x540
	public static final int ST_WIDTH = 16 * ST_SIZE_FACTOR;
	public static final int ST_HEIGHT = 9 * ST_SIZE_FACTOR;

	public static final int ST_FPS_LIMIT = 60;
}
