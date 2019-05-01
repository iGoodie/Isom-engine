package net.programmer.igoodie.input.subscriber;

public interface MouseListener extends EventSubscriber {

	default void mouseMoved(double fromX, double fromY, double toX, double toY) {}
	
	default void mousePressed(int button, int mods) {}
	
	default void mouseReleased(int button, int mods) {}
	
	default void mouseRepeated(int button, int mods) {}
	
	default void wheelMoved(float horizontalOffset, float verticalOffset) {}

}
