package console;

public abstract class Command {

	protected GameConsole console;
	
	public String preword;
	
	public Command(GameConsole console, String preword) {
		this.console = console;
		this.preword = preword;
	}
	
	public abstract String execute(String[] args);
	
	public abstract String getUsage();
}
