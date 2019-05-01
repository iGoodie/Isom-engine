package demo.console.command;

import lib.core.IsomApp;

public abstract class Command {

	public String name;
	
	public Command(String name) {
		this.name = name;
	}
	
	public abstract String execute(IsomApp game, String[] cmdArgs);
	
	public abstract String getUsage();
}
