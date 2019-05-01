package lib.resource;

import java.util.ArrayList;

import com.programmer.igoodie.utils.math.Randomizer;

import lombok.Getter;

// TODO Put sub task classes for percentage info
@Getter
public class ResourceLoader implements Runnable {
	
	private static ArrayList<String> lines = new ArrayList<>();
	
	public static String randomLine() {
		if(lines.size() == 0) return "Loading..";
		int randi = Randomizer.randomInt(0, lines.size()-1);
		return lines.get(randi);
	}
	
	public static void submitLine(String line) {
		lines.add(line);
	}

	protected String loadingInfo = "Fetching..";
	protected float percentage = 0f; // interval: [0,1]
	protected boolean loading = true;
	
	@Override
	public void run() {
		percentage = 1f;
		loading = false;
	}

	public void start() {
		new Thread(this, "Resource Loader").start();
	}
}
