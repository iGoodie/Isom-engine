package demo.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import demo.console.command.Command;
import demo.console.command.CommandMoveCam;
import demo.console.command.CommandTerminal;
import lib.core.GameBase;
import lombok.Getter;

@Getter
public class ConsoleKernel {

	public static final int CMD_HISTORY_LINE_LIMIT = 50;
	
	private static Map<String, Command> commandList = new HashMap<>();
	{
		registerCommand(new CommandMoveCam());
		registerCommand(new CommandTerminal());
	}
	
	public static void registerCommand(Command cmd) {
		commandList.put(cmd.name, cmd);
	}
	
	public GameBase parent;
	
	public StringBuffer inputBuffer = new StringBuffer("> ");
	private List<String> history = new ArrayList<>();
	
	public ConsoleKernel(GameBase parent) {
		this.parent = parent;

		print("< Command line initialized.");
		print("< Command line is ready to execute commands.");
	}
	
	public void print(String line) {
		history.add(line);

		if (history.size() > CMD_HISTORY_LINE_LIMIT) {
			history.remove(0);
		}
	}
	
	public void parseAndExecute(String input) {
		int argsIndex = input.indexOf(' ');
		String cmdName, cmdArgs[];

		print("> " + input); // Indicate executed input

		// Parse cmd name and args
		if (argsIndex != -1) {
			cmdName = input.substring(0, input.indexOf(' '));
			cmdArgs = input.substring(input.indexOf(' ') + 1).split(" ");
		} else {
			cmdName = input;
			cmdArgs = new String[0];
		}

		// Search and execute command
		Command cmd = commandList.get(cmdName);
		if (cmd == null) {
			print(String.format("< Cannot find command '%s'", cmdName));
			return;
		}

		// Print
		String info = cmd.execute(parent, cmdArgs);
		if (info != null)
			print("< " + info);
	}

	public void parseAndExecute() {
		String input = inputBuffer.substring(inputBuffer.indexOf(" ") + 1).trim();
		parseAndExecute(input);

		inputBuffer.setLength(0);
		inputBuffer.append("> ");
	}

	public void clearBuffer() {
		inputBuffer.setLength(0);
		inputBuffer.append("> ");
	}
	
}
