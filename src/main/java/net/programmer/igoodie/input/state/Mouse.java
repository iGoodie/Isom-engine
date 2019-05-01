package net.programmer.igoodie.input.state;

import static org.lwjgl.glfw.GLFW.*;

import java.util.Set;
import java.util.TreeSet;

import org.joml.Vector2d;
import org.joml.Vector2f;

import lombok.Getter;
import net.programmer.igoodie.input.subscriber.MouseListener;
import net.programmer.igoodie.input.subscriber.WindowListener;
import net.programmer.igoodie.window.Window;

public class Mouse implements MouseListener, WindowListener {

	private Window parentWindow;

	private Set<MouseStroke> downButtons;
	private @Getter double mouseX, mouseY;

	public Mouse(Window parentWindow) {
		this.parentWindow = parentWindow;
		this.mouseX = this.mouseY = 0;
		this.downButtons = new TreeSet<>();

		// Subscribe self to the event bus
		this.parentWindow.getEventManager().subscribe(this);
	}

	public void setMousePos(int x, int y) {
		mouseX = x;
		mouseY = y;
		glfwSetCursorPos(parentWindow.getId(), x, y);
	}

	public Vector2d getMousePos() {
		return new Vector2d(mouseX, mouseY);
	}

	public Vector2f getMousePosf() {
		return new Vector2f((float) mouseX, (float) mouseY);
	}

	public int downButtonCount() {
		return downButtons.size();
	}

	public boolean isDown() {
		return downButtons.size() != 0;
	}

	public boolean isButtonDown(int button) {
		return isButtonDown(new MouseStroke(button));
	}

	public boolean isButtonDown(MouseStroke pair) {
		return downButtons.contains(pair);
	}

	/* -------------------- */

	@Override
	public void mouseMoved(double fromX, double fromY, double toX, double toY) {
		this.mouseX = toX;
		this.mouseY = toY;
	}

	@Override
	public void mousePressed(int button, int mods) {
		MouseStroke pair = new MouseStroke(button);
		downButtons.add(pair);
	}

	@Override
	public void mouseReleased(int button, int mods) {
		MouseStroke pair = new MouseStroke(button);
		downButtons.remove(pair);
	}

	@Override
	public void focusLost() {
		downButtons.clear();
	}

}
