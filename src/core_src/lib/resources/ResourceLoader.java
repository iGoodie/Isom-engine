package lib.resources;

import java.util.ArrayList;

import com.programmer.igoodie.utils.math.Randomizer;

// TODO Put sub task classes for percentage info
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
	protected float percentage = 0f;
	protected boolean loading = true;
	
	@Override
	public void run() {}
	
	public String getInfo() {
		return loadingInfo;
	}
	
	public float getPercentage() {
		return percentage;
	}
	
	public boolean isLoading() {
		return loading;
	}

	public void start() {
		new Thread(this, "Resource Loader").start();
	}
}
