package lib.config;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import lib.util.ArrayUtils;
import lib.util.Stringifier;

/**
 * Builder class for launching arguments.
 * Call the methods to include various settings <br>
 * Then call build() to build args array
 */
public class LaunchBuilder {
	private static final String DELIMITER="&&&";
	
	Class<?> launchingClass;
	String[] userArgs;
	StringBuilder recipe = new StringBuilder();
	
	/**
	 * Returns the index of primary monitor
	 * @return Index of primary monitor
	 */
	private static int primaryMonitorIndex() {
		GraphicsDevice[] monitors = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		GraphicsDevice primaryMonitor = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		for(int i=0; i<monitors.length; i++) {
			if(monitors[i].equals(primaryMonitor)) {
				return i+1;
			}
		}
		return -1;
	}
	
	/**
	 * Initializes builder for given class and with given user args
	 * @param launchingClass Class to be launched.
	 * @param args Args got from the user.
	 */
	public LaunchBuilder(Class<?> launchingClass, String[] args) {
		this.launchingClass = launchingClass;
		this.userArgs = args;
	}
	
	/**
	 * Includes <b>--location=x,y</b> arg:: Upper-lefthand corner of where the applet
     * should appear on screen. If not used,
     * the default is to center on the main screen
	 * @param x X-coord
	 * @param y Y-coord
	 */
	public void argLocation(int x, int y) {
		recipe.append(String.format("--location=%d,%d%s", x, y, DELIMITER));
	}
	
	/**
	 * Includes <b>--present</b> arg:: Presentation mode: blanks the entire screen and
     * shows the sketch by itself. If the sketch is
     * smaller than the screen, the background around it
     * will use the --window-color setting.
	 */
	public void argPresentMode() {
		recipe.append(String.format("--present%s", DELIMITER));
	}
	
	/**
	 * Includes <b>--hide-stop</b> arg:: Use to hide the stop button in situations where
     * you don't want to allow users to exit. also
     * see the FAQ on information for capturing the ESC
     * key when running in presentation mode.
	 */
	public void argHideStop() {
		recipe.append(String.format("--hide-stop%s", DELIMITER));
	}
	
	/**
	 * Includes <b>--stop-color=#xxxxxx</b> arg:: Color of the 'stop' text used to quit an
     * sketch when it's in present mode.
     * @param rgb RGB color for stop color
	 */
	public void argStopColor(int rgb) {
		recipe.append(String.format("--stop-color=#%s%s", Stringifier.asHex(rgb, 6), DELIMITER));
	}
	
	/**
	 * Includes <b>--window-color=#xxxxxx</b> arg:: Background color of the window. The color used
     * around the sketch when it's smaller than the
     * minimum window size for the OS, and the matte
     * color when using 'present' mode.
	 * @param rgb RGB color for window color
	 */
	public void argWindowColor(int rgb) {
		recipe.append(String.format("--window-color=#%s%s", Stringifier.asHex(rgb, 6), DELIMITER));
	}
	
	/**
	 * Includes <b>--sketch-path</b> arg:: Location of where to save files from functions
     * like saveStrings() or saveFrame(). defaults to
     * the folder that the java application was
     * launched from, which means if this isn't set by
     * the pde, everything goes into the same folder
     * as processing.exe.
	 * @param path Sketch path
	 */
	public void argSketchPath(String path) {
		recipe.append(String.format("--sketch-path=%s%s", path, DELIMITER));
	}
	
	/**
	 * Includes <b>--display=n</b> arg:: Set what display should be used by this sketch.
     * Displays are numbered starting from 1. This will
     * be overridden by fullScreen() calls that specify
     * a display. Omitting this option will cause the
     * default display to be used.
	 * @param index Monitor index
	 */
	public void argDisplay(int index) {
		recipe.append(String.format("--display=%d%s", index, DELIMITER));
	}
	
	/**
	 * Includes <b>--display=primaryn</b> arg:: Set primary monitor is going to be used by this sketch.
	 */
	public void argDisplayPrimaryMonitor() {
		argDisplay(primaryMonitorIndex());
	}
	
	/**
	 * Builds the final args array, which includes desired launching settings and user entered args.
	 * @return Launching configs
	 */
	public String[] build() {
		String[] build = {};
		if(recipe.length() != 0) {
			build = recipe.toString().split(DELIMITER);
		}
		build = ArrayUtils.addElement(build, launchingClass.getName());
		build = ArrayUtils.merge(build, userArgs);
		return build;
	}
}
