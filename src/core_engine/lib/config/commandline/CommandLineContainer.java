package lib.config.commandline;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommandLineContainer {

	protected Set<String> flags;
	protected Map<String, String> values;
	
	public CommandLineContainer() {
		this.flags = new HashSet<>();
		this.values = new HashMap<>();
	}
	
	public boolean containsFlag(String flag) {
		return flags.contains(flag);
	}
	
	public boolean containsKey(String key) {
		return values.containsKey(key);
	}
	
	public String getValue(String key) {
		return values.get(key);
	}
	
	public Set<String> getKeys() {
		return values.keySet();
	}
	
	public Set<String> getFlags() {
		return new HashSet<String>(flags);
	}
	
}
