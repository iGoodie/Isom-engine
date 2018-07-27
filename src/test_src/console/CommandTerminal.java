package console;

import igoodie.utils.log.ConsolePrinter;

public class CommandTerminal extends Command {

	public CommandTerminal(GameConsole console) {
		super(console, "terminal");
	}
	
	@Override
	public String getUsage() {
		return "Usage: terminal <p> <msg>";
	}

	@Override
	public String execute(String[] args) {
		if(args.length != 2) {
			return getUsage();
		}
		
		switch(args[0]) { // Terminal cmd type
		case "p": ConsolePrinter.info(args[1]); break;
		default: return getUsage();
		}
		
		return null;
	}
	
}
