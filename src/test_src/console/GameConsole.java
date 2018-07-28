package console;

import java.util.ArrayList;

import lib.core.Drawable;
import lib.core.GameBase;
import lib.graphics.Fonts;

public class GameConsole implements Drawable {
	
	public static int CMD_HISTORY_LINE_LIMIT = 50;
	
	protected GameBase parent;
	
	public boolean enabled = false;
	public StringBuffer inputBuffer = new StringBuffer("> ");
	private ArrayList<String> cmdHistory = new ArrayList<>();
	private ArrayList<Command> commandList = new ArrayList<>(); {
		commandList.add(new CommandMoveCam(this));
		commandList.add(new CommandTerminal(this));
		
		cmdHistory.add("< Command line initialized.");
		cmdHistory.add("< Command line is ready to execute commands.");
	}
	
	public GameConsole(GameBase parent) {
		this.parent = parent;
	}
	
	public void print(String line) {
		cmdHistory.add(line);
		
		if(cmdHistory.size() > CMD_HISTORY_LINE_LIMIT) {
			cmdHistory.remove(0);
		}
	}
	
	public void parseAndExecute(String input) {
		int prewordIndex = input.indexOf(' ');
		String preword;
		String[] args;
		
		if(prewordIndex != -1) {			
			preword = input.substring(0, input.indexOf(' '));
			args = input.substring(input.indexOf(' ')+1).split(" ");
		}
		else {
			preword = input;
			args = new String[0];
		}
		
		Command cmd = findCommand(preword);
		
		print("> " + input);
		
		if(cmd == null) {
			print(String.format("< Cannot find command '%s'", preword));
			return;
		}
		
		String info = cmd.execute(args);
		if(info != null)
			print("< " + info);
	}
	
	public void parseAndExecute() {
		parseAndExecute(inputBuffer.substring(inputBuffer.indexOf(" ")+1).trim());
		
		inputBuffer.setLength(0);
		inputBuffer.append("> ");
	}
	
	private Command findCommand(String preword) {
		for(Command c : commandList) {
			if(c.preword.equals(preword)) {
				return c;
			}
		}
		
		return null;
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