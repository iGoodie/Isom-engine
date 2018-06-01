package lib.resources;

import java.util.ArrayList;

import igoodie.utils.math.MathUtils;

public class ResourceLoader implements Runnable {
	
	private static ArrayList<String> lines = new ArrayList<>();
	
	public static String randomLine() {
		if(lines.size() == 0) return "Loading..";
		int randi = MathUtils.randomInt(0, lines.size()-1);
		return lines.get(randi);
	}
	
	public static void submitLine(String line) {
		lines.add(line);
	}

	protected String loadingInfo = "Fetching..";
	protected boolean loading = true;
	
	@Override
	public void run() {}
	
	public String getInfo() {
		return loadingInfo;
	}
	
	public boolean isLoading() {
		return loading;
	}

	public void start() {
		new Thread(this).start();
	}
}
