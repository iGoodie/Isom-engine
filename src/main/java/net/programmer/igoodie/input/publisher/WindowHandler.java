package net.programmer.igoodie.input.publisher;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWDropCallback;
import org.lwjgl.glfw.GLFWWindowFocusCallback;
import org.lwjgl.glfw.GLFWWindowIconifyCallback;
import org.lwjgl.glfw.GLFWWindowMaximizeCallback;
import org.lwjgl.glfw.GLFWWindowPosCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

import lombok.AllArgsConstructor;
import net.programmer.igoodie.input.subscriber.WindowListener;
import net.programmer.igoodie.window.Window;

public final class WindowHandler extends EventPublisher<WindowListener> {

	private FocusHandler handlerFocus;
	private MoveHandler handlerMove;
	private ResizeHandler handlerResize;
	private MaximizeHandler handlerMaximize;
	private MinimizeHandler handlerMinimize;
	private DropHandler handlerDrop;

	public WindowHandler() {
		this.handlerFocus = new FocusHandler(this);
		this.handlerMove = new MoveHandler(this);
		this.handlerResize = new ResizeHandler(this);
		this.handlerMaximize = new MaximizeHandler(this);
		this.handlerMinimize = new MinimizeHandler(this);
		this.handlerDrop = new DropHandler(this);
	}

	@Override
	public void setCallbackToWindow(Window window) {
		glfwSetWindowFocusCallback(window.getId(), handlerFocus);
		glfwSetWindowPosCallback(window.getId(), handlerMove);
		glfwSetWindowSizeCallback(window.getId(), handlerResize);
		glfwSetWindowMaximizeCallback(window.getId(), handlerMaximize);
		glfwSetWindowIconifyCallback(window.getId(), handlerMinimize);
		glfwSetDropCallback(window.getId(), handlerDrop);
	}

	public void publishFocus(boolean focused) {
		subscribers.forEach(subber -> {
			if (focused)
				subber.focusGained();
			else
				subber.focusLost();
		});
	}

	public void publishMove(int x, int y) {
		subscribers.forEach(subber -> subber.windowMoved(x, y));
	}

	public void publishResize(int width, int height) {
		subscribers.forEach(subber -> subber.windowResized(width, height));
	}

	public void publishMaximize(boolean maximized) {
		subscribers.forEach(subber -> {
			if (maximized)
				subber.windowMaximized();
			else
				subber.windowRestored(false, true);
		});
	}

	public void publishMinimize(boolean minimized) {
		subscribers.forEach(subber -> {
			if (minimized)
				subber.windowMinimized();
			else
				subber.windowRestored(true, false);
		});
	}

	public void publishFileDrop(String[] filePaths) {
		subscribers.forEach(subber -> subber.windowDroppedFiles(filePaths));
	}

}

/* Callback sub-classes due to confliction of invoke methods */

@AllArgsConstructor
class FocusHandler extends GLFWWindowFocusCallback {

	WindowHandler parent;

	@Override
	public void invoke(long window, boolean focused) {
		parent.publishFocus(focused);
	}

}

@AllArgsConstructor
class MoveHandler extends GLFWWindowPosCallback {

	WindowHandler parent;

	@Override
	public void invoke(long window, int xpos, int ypos) {
		parent.publishMove(xpos, ypos);
	}

}

@AllArgsConstructor
class ResizeHandler extends GLFWWindowSizeCallback {

	WindowHandler parent;

	@Override
	public void invoke(long window, int width, int height) {
		parent.publishResize(width, height);
	}

}

@AllArgsConstructor
class MaximizeHandler extends GLFWWindowMaximizeCallback {

	WindowHandler parent;

	@Override
	public void invoke(long window, boolean maximized) {
		parent.publishMaximize(maximized);
	}

}

@AllArgsConstructor
class MinimizeHandler extends GLFWWindowIconifyCallback {

	WindowHandler parent;

	@Override
	public void invoke(long window, boolean iconified) {
		parent.publishMinimize(iconified);
	}

}

@AllArgsConstructor
class DropHandler extends GLFWDropCallback {

	WindowHandler parent;

	@Override
	public void invoke(long window, int count, long names) {
		String[] filePaths = new String[count];

		for (int i = 0; i < count; i++)
			filePaths[i] = getName(names, i);

		parent.publishFileDrop(filePaths);
	}

}
