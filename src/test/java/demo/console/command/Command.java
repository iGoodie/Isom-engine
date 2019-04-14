package demo.console.command;

import lib.core.GameBase;

public abstract class Command {

	public String name;
	
	public Command(String name) {
		this.name = name;
	}
	
	public abstract String execute(GameBase game, String[] cmdArgs);
	
	public abstract String getUsage();
}
