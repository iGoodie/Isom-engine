package lib.graphics;

import java.util.ArrayList;

import lib.core.GameBase;

public final class DebugRenderer {
	private static final int TEXT_COLOR = 0xFF_FFFFFF;
	private static final int OUTLINE_COLOR = 0xFF_000000;

	private static final int A_GAP = 10; //px
	private static final int B_GAP = 5; //px

	// Location enum
	public static final int UPPER_LEFT = 0;
	public static final int UPPER_RIGHT = 1;
	public static final int LOWER_LEFT = 2;
	public static final int LOWER_RIGHT = 3;

	private static GameBase parent;

	private static ArrayList<String> rightTopLines = new ArrayList<>();
	private static ArrayList<String> rightBottomLines = new ArrayList<>();
	private static ArrayList<String> leftTopLines = new ArrayList<>();
	private static ArrayList<String> leftBottomLines = new ArrayList<>();

	public static void setParent(GameBase p) {
		parent = p;
	}

	public static void appendLine(String line) {
		if(!parent.debugEnabled) return;
		
		leftTopLines.add(line);
	}

	public static void appendLine(int placing, String line) {
		if(!parent.debugEnabled) return;
		
		if(placing > LOWER_RIGHT) throw new IllegalArgumentException("Given placing enum is not defined!");
		
		switch(placing) {
		case UPPER_RIGHT: rightTopLines.add(line); break;
		case LOWER_RIGHT: rightBottomLines.add(line); break;
		case UPPER_LEFT: leftTopLines.add(line); break;
		case LOWER_LEFT: leftBottomLines.add(line); break;
		}
	}

	public static void appendLineUL(String line) {
		if(!parent.debugEnabled) return;
		
		leftTopLines.add(line);
	}
	
	public static void appendLineLL(String line) {
		if(!parent.debugEnabled) return;
		
		leftBottomLines.add(line);
	}
	
	public static void appendLineUR(String line) {
		if(!parent.debugEnabled) return;
		
		rightTopLines.add(line);
	}
	
	public static void appendLineLR(String line) {
		if(!parent.debugEnabled) return;
		
		rightBottomLines.add(line);
	}
	
	public static void render() {
		if(!parent.debugEnabled) return;
		
		float maxWidth, textHeight = parent.textHeight();
		float sw=parent.width, sh=parent.height;
		int size;
		
		// Render top left
		if(!leftTopLines.isEmpty()) {
			maxWidth = maxWidth(leftTopLines);
			size = leftTopLines.size();
			parent.pushStyle();
			parent.noStroke();
			parent.fill(0xFF_000000, 100);
			parent.rect(A_GAP, A_GAP, maxWidth+2*B_GAP, size*textHeight+B_GAP);
			parent.popStyle();
			
			for(int i=0; i<leftTopLines.size(); i++) {
				String line = leftTopLines.get(i);
				parent.textWithStroke(line, A_GAP+B_GAP, A_GAP+(i+1)*textHeight, TEXT_COLOR, OUTLINE_COLOR);
			}
		}
		
		// Render bottom left
		if(!leftBottomLines.isEmpty()) {
			maxWidth = maxWidth(leftBottomLines);
			size = leftBottomLines.size();
			parent.pushStyle();
			parent.noStroke();
			parent.fill(0xFF_000000, 100);
			parent.rect(A_GAP, sh-A_GAP-size*textHeight-B_GAP, 
					maxWidth+2*B_GAP, size*textHeight+B_GAP);
			parent.popStyle();
			
			for(int i=0; i<leftBottomLines.size(); i++) {
				String line = leftBottomLines.get(i);
				parent.textWithStroke(line, A_GAP+B_GAP, sh-A_GAP-i*textHeight-B_GAP, TEXT_COLOR, OUTLINE_COLOR);
			}
		}
		
		// Render right top
		if(!rightTopLines.isEmpty()) {
			maxWidth = maxWidth(rightTopLines);
			size = rightTopLines.size();
			parent.pushStyle();
			parent.noStroke();
			parent.fill(0xFF_000000, 100);
			parent.rect(sw-A_GAP-2*B_GAP-maxWidth, A_GAP,
					2*B_GAP+maxWidth, B_GAP+size*textHeight);
			parent.popStyle();
			
			for(int i=0; i<rightTopLines.size(); i++) {
				String line = rightTopLines.get(i);
				float lineWidth = parent.textWidth(line);
				parent.textWithStroke(line, sw-A_GAP-B_GAP-lineWidth, A_GAP+(i+1)*textHeight, TEXT_COLOR, OUTLINE_COLOR);
			}
		}
		
		// Render right bottom
		if(!rightBottomLines.isEmpty()) {
			maxWidth = maxWidth(rightBottomLines);
			size = rightBottomLines.size();
			parent.pushStyle();
			parent.noStroke();
			parent.fill(0xFF_000000, 100);
			parent.rect(sw-A_GAP-2*B_GAP-maxWidth, sh-A_GAP-B_GAP-size*textHeight,
					2*B_GAP+maxWidth, size*textHeight+B_GAP);
			parent.popStyle();
			
			for(int i=0; i<rightBottomLines.size(); i++) {
				String line = rightBottomLines.get(i);
				float lineWidth = parent.textWidth(line);
				parent.textWithStroke(line, sw-A_GAP-B_GAP-lineWidth, sh-A_GAP-B_GAP-i*textHeight, TEXT_COLOR, OUTLINE_COLOR);
			}
		}
		
		// Clean for next frame
		rightTopLines.clear();
		rightBottomLines.clear();
		leftTopLines.clear();
		leftBottomLines.clear();
	}
	
	private static float maxWidth(ArrayList<String> lines) {
		float maxWidth = 0;
		for(String line : lines) 
			maxWidth = Math.max(maxWidth, parent.textWidth(line));
		return maxWidth;
	}
}
