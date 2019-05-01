package net.programmer.igoodie.input.subscriber;

public interface WindowListener extends EventSubscriber {

	default void focusGained() {}

	default void focusLost() {}
	
	default void windowMoved(int x, int y) {}
	
	default void windowResized(int width, int height) {}
	
	default void windowMaximized() {}
	
	default void windowMinimized() {}

	default void windowRestored(boolean wasMinimized, boolean wasMaximized) {}
	
	default void windowDroppedFiles(String[] filePaths) {}
	
}
