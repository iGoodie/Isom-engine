package lib.input.keyboard;

public interface KeyboardListener {

	public default void keyPressed(KeyPair pair) {}
	
	public default void keyReleased(KeyPair pair) {}
	
	public default void keyTyped(KeyPair pair) {}

}
