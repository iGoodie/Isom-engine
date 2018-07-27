package console;

import lib.maths.IsoVector;

public class CommandMoveCam extends Command {

	public CommandMoveCam(GameConsole console) {
		super(console, "movecam");
	}

	@Override
	public String getUsage() {
		return "Usage: movecam <x> <y> OR movecam <x> <y> <w/c>";
	}
	
	@Override
	public String execute(String[] args) {
		if(args.length != 2 && args.length != 3) {
			return getUsage();
		}
		
		try {
			float x = Float.parseFloat(args[0]);
			float y = Float.parseFloat(args[1]);
			
			if(args.length == 3) {
				IsoVector vec2f = new IsoVector(x, y);
				switch(args[2]) {
				case "w": vec2f.space = IsoVector.WORLD; break;
				case "c": vec2f.space = IsoVector.CANVAS; break;
				default: return getUsage();
				}
				vec2f = vec2f.toCanvas(console.parent.getCoordinator(), console.parent.getCamera());
				x = vec2f.x;
				y = vec2f.y;
			}
			
			console.parent.getCamera().moveTo(x, y, 1);
			
			return null; // No info msg to be shown
		}
		catch(NumberFormatException e) {
			return "Given arguments should be a valid number format.";
		}
	}
}
