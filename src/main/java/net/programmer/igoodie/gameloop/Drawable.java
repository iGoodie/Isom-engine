package net.programmer.igoodie.gameloop;

public interface Drawable {

	default void update(float dt) {}
	
	default void render() {}
	
}
