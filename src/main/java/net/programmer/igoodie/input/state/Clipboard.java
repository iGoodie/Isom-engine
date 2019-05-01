package net.programmer.igoodie.input.state;

import static org.lwjgl.glfw.GLFW.*;

import lombok.AllArgsConstructor;
import net.programmer.igoodie.window.Window;

@AllArgsConstructor
public class Clipboard {

	private Window parentWindow;
	
	public String getContent() {
		return glfwGetClipboardString(parentWindow.getId());
	}
	
	public void setContent(String content) {
		glfwSetClipboardString(parentWindow.getId(), content);
	}
	
}
