package lib.util;

import java.io.PrintStream;

public class ConsoleLogger {
	private static boolean debugMode = true;
	private static PrintStream consoleStream = System.out;
	private static PrintStream loggingStream = null;
	
	public static void toggleDebugMode() {
		debugMode = !debugMode;
	}
	
	public static void setDebugMode(boolean mode) {
		debugMode = mode;
	}
	
	public static void setLoggingSystem(PrintStream out) {
		loggingStream = out;
	}
	
	public static void setConsoleStream(PrintStream out) {
		consoleStream = out;
	}
	
	public static void warn(Object msg, Object...args) {
		warn(msg.toString(), args);
	}
	
	public static void warn(String msg, Object...args) {
		msg = String.format(msg, args);
		consoleStream.printf("[WARN][%d]%s\n", System.currentTimeMillis(), msg);
		if(loggingStream != null) {
			loggingStream.printf("[WARN][%d]%s\n", System.currentTimeMillis(), msg);
		}
	}
	
	public static void error(Object msg, Object...args) {
		error(msg.toString(), args);
	}
	
	public static void error(String msg, Object...args) {
		msg = String.format(msg, args);
		consoleStream.printf("[ERROR][%d]%s\n", System.currentTimeMillis(), msg);
		if(loggingStream != null) {
			loggingStream.printf("[ERROR][%d]%s\n", System.currentTimeMillis(), msg);
		}
	}
	
	public static void info(Object msg, Object...args) {
		info(msg.toString(), args);
	}
	
	public static void info(String msg, Object...args) {
		msg = String.format(msg, args);
		consoleStream.printf("[INFO][%d]%s\n", System.currentTimeMillis(), msg);
		if(loggingStream != null) {
			loggingStream.printf("[INFO][%d]%s\n", System.currentTimeMillis(), msg);
		}
	}
	
	public static void debug(Object msg, Object...args) {
		debug(msg.toString(), args);
	}
	
	public static void debug(String msg, Object...args) {
		if(!debugMode) return;
		msg = String.format(msg, args);
		consoleStream.printf("[DEBUG][%d]%s\n", System.currentTimeMillis(), msg);
		if(loggingStream != null) {
			loggingStream.printf("[DEBUG][%d]%s\n", System.currentTimeMillis(), msg);
		}
	}
}
