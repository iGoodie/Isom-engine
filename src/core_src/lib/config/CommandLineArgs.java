package lib.config;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandLineArgs {
	
	private String[] rawArgs;
	private ArrayList<String> flags;
	private HashMap<String, String> args;
	
	public CommandLineArgs(String[] rawArgs) {
		this.rawArgs = rawArgs;
		this.flags = new ArrayList<>();
		this.args = new HashMap<>();
		
		//Parse args
		for(String arg : rawArgs) {
			if(arg.startsWith("--")) // --someflag
				this.flags.add(arg.substring(2));
			else if(arg.startsWith("-") && arg.contains("=")) { // -key=value
				String[] pair = arg.substring(1).split("=", 2);
				this.args.put(pair[0], pair[1]);
			}
		}
	}
	
	public int getFlagCount() {
		return flags.size();
	}
	
	public int getSubargumentCount() {
		return args.size();
	}

	public int getArgumentCount() {
		return rawArgs.length;
	}
	
	public boolean containsFlag(String flag) {
		return flags.contains(flag);
	}

	public boolean containsArgument(String arg) {
		return args.containsKey(arg);
	}
	
	public String getArgument(String argName) {
		return args.get(argName);
	}

	@Override
	public String toString() {
		return "isom-engine " + String.join(" ", rawArgs);
	}

}
