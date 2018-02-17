package lib.stage;

public abstract class Stage {
	public String name = "Unnamed Stage";
	
	public abstract void update(float dt); //seconds
	
	public abstract void render();
	
	public void updateTick() {};
	
	public void dispose() {};
}
