package lib.graphics;

import java.util.ArrayList;

import lib.GameBase;

public class DebugRenderer {
	private static final int GAP = 12; //px
	
	public static final int UPPER_LEFT=0;
	public static final int UPPER_RIGHT=1;
	public static final int LOWER_LEFT=2;
	public static final int LOWER_RIGHT=3;

	private static GameBase parent;
	
	private static ArrayList<String> lines = new ArrayList<>();
	private static ArrayList<Integer> placings = new ArrayList<>();
	
	public static void setParent(GameBase p) {
		parent = p;
	}
	
	public static void appendLine(int placing, String line) {
		lines.add(line);
		placings.add(placing);
	}
	
	public static void appendLine(String line) {
		lines.add(line);
		placings.add(UPPER_LEFT);
	}
	
	public static void render() {
		int ul=0, ur=0, ll=0, lr=0;
		for(int i=0; i<lines.size(); i++) {
			String text = lines.get(i);
			int placing = placings.get(i);
			
			if(placing == UPPER_LEFT) {
				parent.text(text, 10, 20 + ul*GAP);
				ul++;
			}
			else if(placing == UPPER_RIGHT) {
				parent.text(text, parent.width-parent.textWidth(text)-10, 20 + ur*GAP);
				ur++;
			}
			else if(placing == LOWER_LEFT) {
				parent.text(text, 10, parent.height - 10 - ll*GAP);
				ll++;
			}
			else if(placing == LOWER_RIGHT) {
				parent.text(text, parent.width-parent.textWidth(text)-10, parent.height - 10 - lr*GAP);
				lr++;
			}
		}
		lines.clear();
	}
}
