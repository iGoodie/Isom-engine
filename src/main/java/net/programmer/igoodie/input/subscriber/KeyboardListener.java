package net.programmer.igoodie.input.subscriber;

public interface KeyboardListener extends EventSubscriber {

	default void keyPressed(int key, int keyCode, int mods) {}

	default void keyRepeated(int key, int keyCode, int mods) {}

	default void keyReleased(int key, int keyCode, int mods) {}

	default void keyTyped(int unicodeCodePoint) {}

}
