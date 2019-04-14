package demo.console.command;

import com.programmer.igoodie.utils.log.ConsolePrinter;

import demo.console.GameConsole;
import lib.core.GameBase;

public class CommandTerminal extends Command {

	public CommandTerminal() {
		super("terminal");
	}

	@Override
	public String getUsage() {
		return "Usage: terminal <p> <msg>";
	}

	@Override
	public String execute(GameBase game, String[] cmdArgs) {
		switch (cmdArgs[0]) { // Terminal cmd type
		case "p":
			return subPrint(cmdArgs);
		default:
			return getUsage();
		}
	}

	private String subPrint(String[] cmdArgs) {
		if (cmdArgs.length <= 2)
			return getUsage();

		// Build string with given arguments
		String[] msgArgs = new String[cmdArgs.length - 1];
		System.arraycopy(cmdArgs, 1, msgArgs, 0, msgArgs.length);
		ConsolePrinter.info(String.join(" ", msgArgs));

		return null;
	}
}
