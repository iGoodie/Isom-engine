package lib.graphics;

import java.util.LinkedList;
import java.util.List;

import lib.core.GameBase;

public final class DebugRenderer {

	private static final int TEXT_COLOR = 0xFF_FFFFFF;
	private static final int OUTLINE_COLOR = 0xFF_000000;
	private static final int CONTAINER_COLOR = 0xFF_000000;

	private static final int GAP_A = 10; // px
	private static final int GAP_B = 5; // px

	public static final int UPPER_LEFT = 0;
	public static final int UPPER_RIGHT = 1;
	public static final int LOWER_LEFT = 2;
	public static final int LOWER_RIGHT = 3;

	private static GameBase parent;
	private static List<String>[] lines;

	@SuppressWarnings("unchecked")
	public static void setParent(GameBase parent) {
		DebugRenderer.parent = parent;
		DebugRenderer.lines = new LinkedList[4];
		for (int i = 0; i < 4; i++)
			lines[i] = new LinkedList<String>();
	}

	public static void appendLine(Object line) {
		appendLine(UPPER_LEFT, line);
	}

	public static void appendLine(int placing, Object line) {
		if (!parent.debugEnabled)
			return;

		if (placing > LOWER_RIGHT || placing < UPPER_LEFT)
			throw new IllegalArgumentException("Given placing enum is not defined!");

		lines[placing].add(line.toString());
	}

	public static void render() {
		if (!parent.debugEnabled)
			return;

		int size;
		float maxWidth;
		float textHeight = parent.textHeight();

		List<String> upperLeftLines = lines[UPPER_LEFT];
		List<String> lowerLeftLines = lines[LOWER_LEFT];
		List<String> upperRightLines = lines[UPPER_RIGHT];
		List<String> lowerRightLines = lines[LOWER_RIGHT];

		// Render top left
		if (!upperLeftLines.isEmpty()) {
			maxWidth = maxWidth(upperLeftLines);
			size = upperLeftLines.size();
			renderContainer(GAP_A, GAP_A, maxWidth + 2 * GAP_B, size * textHeight + GAP_B);
			for (int i = 0; i < upperLeftLines.size(); i++) {
				String line = upperLeftLines.get(i);
				float x = GAP_A + GAP_B;
				float y = GAP_A + (i + 1) * textHeight;
				parent.textWithStroke(line, x, y, TEXT_COLOR, OUTLINE_COLOR);
			}
		}

		// Render bottom left
		if (!lowerLeftLines.isEmpty()) {
			maxWidth = maxWidth(lowerLeftLines);
			size = lowerLeftLines.size();
			renderContainer(GAP_A, parent.height - GAP_A - size * textHeight - GAP_B, maxWidth + 2 * GAP_B,
					size * textHeight + GAP_B);
			for (int i = 0; i < lowerLeftLines.size(); i++) {
				String line = lowerLeftLines.get(i);
				float x = GAP_A + GAP_B;
				float y = parent.height - GAP_A - (i * textHeight) - GAP_B;
				parent.textWithStroke(line, x, y, TEXT_COLOR, OUTLINE_COLOR);
			}
		}

		// Render right top
		if (!upperRightLines.isEmpty()) {
			maxWidth = maxWidth(upperRightLines);
			size = upperRightLines.size();
			renderContainer(parent.width - GAP_A - 2 * GAP_B - maxWidth, GAP_A, 2 * GAP_B + maxWidth,
					GAP_B + size * textHeight);
			for (int i = 0; i < upperRightLines.size(); i++) {
				String line = upperRightLines.get(i);
				float lineWidth = parent.textWidth(line);
				float x = parent.width - GAP_A - GAP_B - lineWidth;
				float y = GAP_A + (i + 1) * textHeight;
				parent.textWithStroke(line, x, y, TEXT_COLOR, OUTLINE_COLOR);
			}
		}

		// Render right bottom
		if (!lowerRightLines.isEmpty()) {
			maxWidth = maxWidth(lowerRightLines);
			size = lowerRightLines.size();
			renderContainer(parent.width - GAP_A - 2 * GAP_B - maxWidth,
					parent.height - GAP_A - GAP_B - size * textHeight, 2 * GAP_B + maxWidth, size * textHeight + GAP_B);

			for (int i = 0; i < lowerRightLines.size(); i++) {
				String line = lowerRightLines.get(i);
				float lineWidth = parent.textWidth(line);
				float x = parent.width - GAP_A - GAP_B - lineWidth;
				float y = parent.height - GAP_A - GAP_B - (i * textHeight);
				parent.textWithStroke(line, x, y, TEXT_COLOR, OUTLINE_COLOR);
			}
		}

		// Clean for next frame
		upperRightLines.clear();
		lowerRightLines.clear();
		upperLeftLines.clear();
		lowerLeftLines.clear();
	}

	private static void renderContainer(float x, float y, float w, float h) {
		// 2 * GAP_B + maxWidth
		// GAP_B + size * textHeight
		parent.pushStyle();

		parent.noStroke();
		parent.fill(CONTAINER_COLOR, 100);
		parent.rect(x, y, w, h);

		parent.popStyle();
	}

	private static float maxWidth(List<String> lines) {
		return lines.stream().map(line -> parent.textWidth(line)).reduce(0f, (len1, len2) -> Math.max(len1, len2));
	}

}
