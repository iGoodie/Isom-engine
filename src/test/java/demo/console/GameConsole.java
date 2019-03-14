package demo.console;

import java.util.ArrayList;
import java.util.HashMap;

import demo.console.command.Command;
import demo.console.command.CommandMoveCam;
import demo.console.command.CommandTerminal;
import lib.core.Drawable;
import lib.core.GameBase;
import lib.graphics.Fonts;

public class GameConsole implements Drawable {
	
	public static int CMD_HISTORY_LINE_LIMIT = 50;
	
	private static HashMap<String, Command> commandList = new HashMap<>(); {
		registerCommand(new CommandMoveCam(this));
		registerCommand(new CommandTerminal(this));
	}

	public GameBase parent;
	public boolean enabled = false;
	public StringBuffer inputBuffer = new StringBuffer("> ");
	private ArrayList<String> cmdHistory = new ArrayList<>();
	
	public GameConsole(GameBase parent) {
		this.parent = parent;
		
		print("< Command line initialized.");
		print("< Command line is ready to execute commands.");
	}
	
	public void registerCommand(Command cmd) {
		commandList.put(cmd.name, cmd);
	}
	
	public void print(String line) {
		cmdHistory.add(line);
		
		if(cmdHistory.size() > CMD_HISTORY_LINE_LIMIT) {
			cmdHistory.remove(0);
		}
	}
	
	public void parseAndExecute(String input) {
		int argsIndex = input.indexOf(' ');
		String cmdName, cmdArgs[];

		print("> " + input); // Indicate executed input
		
		// Parse cmd name and args
		if(argsIndex != -1) {			
			cmdName = input.substring(0, input.indexOf(' '));
			cmdArgs = input.substring(input.indexOf(' ')+1).split(" ");
		}
		else {
			cmdName = input;
			cmdArgs = new String[0];
		}
		
		// Search and execute command
		Command cmd = commandList.get(cmdName);
		if(cmd == null) {
			print(String.format("< Cannot find command '%s'", cmdName));
			return;
		}
		
		// Print
		String info = cmd.execute(cmdArgs);
		if(info != null) print("< " + info);
	}
	
	public void parseAndExecute() {
		parseAndExecute(inputBuffer.substring(inputBuffer.indexOf(" ")+1).trim());
		
		inputBuffer.setLength(0);
		inputBuffer.append("> ");
	}

	public void toggle() {
		if(enabled)
			close();
		else
			enabled = true;
	}
	
	public void close() {
		enabled = false;
		inputBuffer.setLength(0);
		inputBuffer.append("> ");
	}
	
	/* Drawables */
	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		float mid_y = parent.height/2f;
		
		parent.pushStyle();
		parent.noStroke();
		parent.fill(0xFF_000000, 200);
		parent.rect(0, 0, parent.width, mid_y);
		parent.popStyle();
		
		parent.pushStyle();
		parent.textFont(Fonts.getFont("console"));
		parent.fill(0xFF_CCCCCC);
		
		float textHeight = parent.textHeight();
		int size = cmdHistory.size();
		
		float A_GAP = 30;
		float B_GAP = 5;
		
		parent.fill(0xFF_FFFFFF);
		parent.text(inputBuffer.toString(), 
				A_GAP, 
				mid_y - A_GAP);
		
		parent.fill(0xFF_AAAAAA);
		for(int i=size-1; i>=0; i--) {
			String line = cmdHistory.get(i);
			parent.text(line, 
					A_GAP, 
					mid_y - A_GAP - B_GAP - 20 - (size-1-i)*textHeight);
		}
		parent.popStyle();
	}
}