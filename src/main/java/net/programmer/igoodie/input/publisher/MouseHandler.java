package net.programmer.igoodie.input.publisher;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.programmer.igoodie.input.subscriber.MouseListener;
import net.programmer.igoodie.window.Window;

public final class MouseHandler extends EventPublisher<MouseListener> {

	private MouseButtonHandler handlerButton;
	private MouseWheelHandler handlerWheel;
	private MouseMoveHandler handlerMove;

	public MouseHandler() {
		this.handlerButton = new MouseButtonHandler(this);
		this.handlerWheel = new MouseWheelHandler(this);
		this.handlerMove = new MouseMoveHandler(this);
	}

	@Override
	public void setCallbackToWindow(Window window) {
		glfwSetMouseButtonCallback(window.getId(), handlerButton);
		glfwSetScrollCallback(window.getId(), handlerWheel);
		glfwSetCursorPosCallback(window.getId(), handlerMove);
	}

	public void publishMouseClick(int button, int action, int mods) {
		subscribers.forEach(subber -> {
			switch (action) {
			case GLFW_PRESS:
				subber.mousePressed(button, mods);
				return;

			case GLFW_RELEASE:
				subber.mouseReleased(button, mods);
				return;

			case GLFW_REPEAT:
				subber.mouseRepeated(button, mods);
				return;
			}
		});
	}

	public void publishMouseWheel(double xoff, double yoff) {
		subscribers.forEach(subber -> subber.wheelMoved((float) xoff, (float) yoff));
	}

	public void publishMouseMove(double fromX, double fromY, double toX, double toY) {
		subscribers.forEach(subber -> subber.mouseMoved(fromX, fromY, toX, toY));
	}

}

/* Callback sub-classes due to confliction of invoke methods */

@AllArgsConstructor
class MouseButtonHandler extends GLFWMouseButtonCallback {

	MouseHandler parent;

	@Override
	public void invoke(long window, int button, int action, int mods) {
		parent.publishMouseClick(button, action, mods);
	}

}

@AllArgsConstructor
class MouseWheelHandler extends GLFWScrollCallback {

	MouseHandler parent;

	@Override
	public void invoke(long window, double xoffset, double yoffset) {
		parent.publishMouseWheel(xoffset, yoffset);
	}

}

@RequiredArgsConstructor
class MouseMoveHandler extends GLFWCursorPosCallback {

	protected @NonNull MouseHandler parent;

	double xposOld, yposOld;

	@Override
	public void invoke(long window, double xpos, double ypos) {
		parent.publishMouseMove(xposOld, yposOld, xpos, ypos);
		xposOld = xpos;
		yposOld = ypos;
	}

}
