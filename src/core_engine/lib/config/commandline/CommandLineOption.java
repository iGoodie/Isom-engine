package lib.config.commandline;

import lombok.Getter;

public class CommandLineOption extends CommandLineContainer {

	private @Getter String name;
	
	public CommandLineOption(String name) {
		super();
		this.name = name;
	}
	
}
