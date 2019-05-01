package net.programmer.igoodie.gameloop;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import lombok.Getter;
import net.programmer.igoodie.IsomEngineConstants;
import net.programmer.igoodie.input.state.Clipboard;
import net.programmer.igoodie.input.state.Keyboard;
import net.programmer.igoodie.input.state.Mouse;
import net.programmer.igoodie.window.Window;

public abstract class IsomEngineLoop extends GameLoop implements IsomEngineConstants {

	private @Getter Window window;
	
	private @Getter Clipboard clipboard;
	private @Getter Keyboard keyboard;
	private @Getter Mouse mouse;
	
	@Override
	public void setup() {
		createWindow(500, 500);
	}

	@Override
	protected void pollInput() {
		glfwPollEvents();
	}

	@Override
	public void update(float dt) {
		String title = String.format("%s %s | %.0f FPS", ENGINE_NAME, ENGINE_VERSION, frameRate);
		window.setTitle(title);
		
		if(keyboard.isKeyDown(GLFW_KEY_ESCAPE)) {
			exit();
		}
	}

	@Override
	protected void preRender() {
		glClearColor(0, 0, 0, 1);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	@Override
	protected void postRender() {
		window.swapBuffers();

		if (window.shouldClose()) {
			window.deconstruct();
			running = false;
		}
	}

	@Override
	public void exit() {
		window.deconstruct();
		running = false;
	}

	protected void createWindow(int width, int height) {
		window = new Window("Isom-engine Window", width, height);
		clipboard = new Clipboard(window);
		keyboard = new Keyboard(window);
		mouse = new Mouse(window);
	}

}
