package lib.config;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.LinkedList;
import java.util.List;

import lib.util.Stringifier;

/**
 * Builder class for launching arguments. Call the methods to include various
 * settings <br>
 * Then call build() to build args array
 */
public class LaunchBuilder {

	Class<?> launchingClass;
	List<String> args;

	/**
	 * Returns the index of primary monitor
	 * 
	 * @return Index of primary monitor
	 */
	private static int primaryMonitorIndex() {
		GraphicsDevice[] monitors = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		GraphicsDevice primaryMonitor = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		for (int i = 0; i < monitors.length; i++) {
			if (monitors[i].equals(primaryMonitor)) {
				return i + 1;
			}
		}
		return -1;
	}

	/**
	 * Initializes builder for given class and with given user args
	 * 
	 * @param launchingClass Class to be launched.
	 * @param args           Args got from the user.
	 */
	public LaunchBuilder(Class<?> launchingClass, String[] args) {
		this.launchingClass = launchingClass;
		
		this.args = new LinkedList<>();
		this.args.add(launchingClass.getName());
		for (String arg : args)
			this.args.add(arg);
	}

	/**
	 * Includes <b>--location=x,y</b> arg:: Upper-lefthand corner of where the
	 * applet should appear on screen. If not used, the default is to center on the
	 * main screen
	 * 
	 * @param x X-coord
	 * @param y Y-coord
	 */
	public void argLocation(int x, int y) {
		String arg = String.format("--location=%d,%d", x, y);
		args.add(0, arg);
	}

	/**
	 * Includes <b>--present</b> arg:: Presentation mode: blanks the entire screen
	 * and shows the sketch by itself. If the sketch is smaller than the screen, the
	 * background around it will use the --window-color setting.
	 */
	public void argPresentMode() {
		String arg = "--present";
		args.add(0, arg);
	}

	/**
	 * Includes <b>--hide-stop</b> arg:: Use to hide the stop button in situations
	 * where you don't want to allow users to exit. also see the FAQ on information
	 * for capturing the ESC key when running in presentation mode.
	 */
	public void argHideStop() {
		String arg = "--hide-stop";
		args.add(0, arg);
	}

	/**
	 * Includes <b>--stop-color=#xxxxxx</b> arg:: Color of the 'stop' text used to
	 * quit an sketch when it's in present mode.
	 * 
	 * @param rgb RGB color for stop color
	 */
	public void argStopColor(int rgb) {
		String arg = "--stop-color=#" + Stringifier.asHex(rgb, 6);
		args.add(0, arg);
	}

	/**
	 * Includes <b>--window-color=#xxxxxx</b> arg:: Background color of the window.
	 * The color used around the sketch when it's smaller than the minimum window
	 * size for the OS, and the matte color when using 'present' mode.
	 * 
	 * @param rgb RGB color for window color
	 */
	public void argWindowColor(int rgb) {
		String arg = "--window-color=#" + Stringifier.asHex(rgb, 6);
		args.add(0, arg);
	}

	/**
	 * Includes <b>--sketch-path</b> arg:: Location of where to save files from
	 * functions like saveStrings() or saveFrame(). defaults to the folder that the
	 * java application was launched from, which means if this isn't set by the pde,
	 * everything goes into the same folder as processing.exe.
	 * 
	 * @param path Sketch path
	 */
	public void argSketchPath(String path) {
		String arg = "--sketch-path=" + path;
		args.add(0, arg);
	}

	/**
	 * Includes <b>--display=n</b> arg:: Set what display should be used by this
	 * sketch. Displays are numbered starting from 1. This will be overridden by
	 * fullScreen() calls that specify a display. Omitting this option will cause
	 * the default display to be used.
	 * 
	 * @param index Monitor index
	 */
	public void argDisplay(int index) {
		String arg = "--display=" + index;
		args.add(0, arg);
	}

	/**
	 * Includes <b>--display=primaryn</b> arg:: Set primary monitor is going to be
	 * used by this sketch.
	 */
	public void argDisplayPrimaryMonitor() {
		argDisplay(primaryMonitorIndex());
	}

	/**
	 * Builds the final args array, which includes desired launching settings and
	 * user entered args.
	 * 
	 * @return Launching configs
	 */
	public String[] build() {
		return args.toArray(String[]::new);
	}

}
