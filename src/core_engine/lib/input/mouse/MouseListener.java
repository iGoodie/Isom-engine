package lib.input.mouse;

public interface MouseListener {

	public default void mousePressed(MousePress pressed) {};

	public default void mouseReleased(MousePress released) {};
	
	/**
	 * Callbacked from the class when wheel is moved
	 * @param downCount Negative for down, positive for up
	 */
	public default void wheelMoved(float downCount) {};
}
