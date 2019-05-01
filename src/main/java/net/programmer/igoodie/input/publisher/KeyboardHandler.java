package net.programmer.igoodie.input.publisher;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

import lombok.AllArgsConstructor;
import net.programmer.igoodie.input.subscriber.KeyboardListener;
import net.programmer.igoodie.window.Window;

public final class KeyboardHandler extends EventPublisher<KeyboardListener> {

	public static String getKeyName(int key, int keyCode) {
		return glfwGetKeyName(key, keyCode);
	}

	private KeyHandler handlerKey;
	private CharHandler handlerChar;

	public KeyboardHandler() {
		this.handlerKey = new KeyHandler(this);
		this.handlerChar = new CharHandler(this);
	}

	@Override
	public void setCallbackToWindow(Window window) {
		glfwSetKeyCallback(window.getId(), handlerKey);
		glfwSetCharCallback(window.getId(), handlerChar);
	}

	public void publishKey(int key, int scancode, int action, int mods) {
		subscribers.forEach(subber -> {
			switch (action) {
			case GLFW_PRESS:
				subber.keyPressed(key, scancode, mods);
				return;

			case GLFW_RELEASE:
				subber.keyReleased(key, scancode, mods);
				return;

			case GLFW_REPEAT:
				subber.keyRepeated(key, scancode, mods);
				return;
			}
		});
	}

	public void publishChar(int codepoint) {
		subscribers.forEach(subber -> subber.keyTyped(codepoint));
	}

}

/* Callback sub-classes due to confliction of invoke methods */

@AllArgsConstructor
class KeyHandler extends GLFWKeyCallback {

	KeyboardHandler parent;

	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		parent.publishKey(key, scancode, action, mods);
	}

}

@AllArgsConstructor
class CharHandler extends GLFWCharCallback {

	KeyboardHandler parent;

	@Override
	public void invoke(long window, int codepoint) {
		parent.publishChar(codepoint);
	}

}
