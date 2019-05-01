package demo.console;

import lib.core.Drawable;
import lib.core.IsomApp;
import lib.graphics.Fonts;
import lombok.Getter;

@Getter
public class GameConsole implements Drawable {

	IsomApp parent;

	ConsoleKernel kernel;

	public GameConsole(IsomApp parent) {
		this.parent = parent;
		this.kernel = new ConsoleKernel(parent);
	}

	public boolean enabled = false;

	public void toggle() {
		setEnabled(!enabled);
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		
		if(!enabled)
			kernel.clearBuffer();
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		float mid_y = parent.height / 2f;

		parent.pushStyle();
		parent.noStroke();
		parent.fill(0xFF_000000, 200);
		parent.rect(0, 0, parent.width, mid_y);
		parent.popStyle();

		parent.pushStyle();
		parent.textFont(Fonts.getFont("console"));
		parent.fill(0xFF_CCCCCC);

		float textHeight = parent.textHeight();
		int size = kernel.getHistory().size();

		float A_GAP = 30;
		float B_GAP = 5;

		parent.fill(0xFF_FFFFFF);
		parent.text(kernel.getInputBuffer().toString(),
				A_GAP,
				mid_y - A_GAP);

		parent.fill(0xFF_AAAAAA);
		for (int i = size - 1; i >= 0; i--) {
			String line = kernel.getHistory().get(i);
			parent.text(line,
					A_GAP,
					mid_y - A_GAP - B_GAP - 20 - (size - 1 - i) * textHeight);
		}
		parent.popStyle();
	}

}
