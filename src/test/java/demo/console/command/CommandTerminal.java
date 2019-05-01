package demo.console.command;

import java.util.Arrays;

import com.programmer.igoodie.utils.log.ConsolePrinter;

import lib.core.IsomApp;

public class CommandTerminal extends Command {

	public CommandTerminal() {
		super("terminal");
	}

	@Override
	public String getUsage() {
		return "Usage: terminal <p> <msg>";
	}

	@Override
	public String execute(IsomApp game, String[] cmdArgs) {
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
		String[] msgArgs = Arrays.copyOf(cmdArgs, cmdArgs.length);
		ConsolePrinter.info(String.join(" ", msgArgs));

		return null;
	}
}
