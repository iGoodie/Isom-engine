package console.command;

import console.GameConsole;

public abstract class Command {

	protected GameConsole console;
	
	public String name;
	
	public Command(GameConsole console, String name) {
		this.console = console;
		this.name = name;
	}
	
	public abstract String execute(String[] cmdArgs);
	
	public abstract String getUsage();
}
