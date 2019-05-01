package lib.config.commandline;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandLine extends CommandLineContainer {

	public static final String OPTION_PREFIX = "/";
	public static final String ENTRY_PREFIX_REGEX = "--?";
	public static final String PAIR_DELIMITER = ":";

	public static Map<String, String[]> generateRules(Object... structure) {
		return null;
	}

	private Map<String, String[]> acceptanceTable;
	private Map<String, CommandLineOption> options;

	public CommandLine() {
		this.acceptanceTable = new HashMap<>();
		this.options = new HashMap<>();
	}

	public CommandLine options(String name, String[] acceptance) {
		acceptanceTable.put(name, acceptance);
		return this;
	}

	public CommandLine parse(String[] args) {
		CommandLineOption option = null;

		for (String arg : args) {
			// Argument is an option
			if (arg.startsWith(OPTION_PREFIX)) {
				option = new CommandLineOption(arg.replaceFirst(OPTION_PREFIX, ""));
				this.options.put(option.getName(), option);
				continue;
			}

			// Argument is an entry
			if (arg.matches(ENTRY_PREFIX_REGEX + ".*")) {
				arg = arg.replaceFirst(ENTRY_PREFIX_REGEX, "");

				// Argument is a pair entry
				if (arg.contains(PAIR_DELIMITER)) {
					String[] pair = arg.split(PAIR_DELIMITER, 2);
					if (option != null && optionAccepts(option, pair[0]))
						option.values.put(pair[0], pair[1]);
					else {
						this.values.put(pair[0], pair[1]);
						option = null;
					}

					// Argument is a regular pair
				} else {
					if (option != null && optionAccepts(option, arg))
						option.flags.add(arg);
					else {
						this.flags.add(arg);
						option = null;
					}
				}
			}
		}

		return this;
	}

	private boolean optionAccepts(CommandLineOption opt, String argument) {
		return Arrays.stream(acceptanceTable.get(opt.getName()))
				.anyMatch(argument::equals);
	}

	public boolean containsOption(String option) {
		return options.containsKey(option);
	}

	public CommandLineOption getOption(String option) {
		return options.get(option);
	}

}
