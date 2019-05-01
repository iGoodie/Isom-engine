package net.programmer.igoodie.window;

import static org.lwjgl.glfw.GLFW.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import lombok.Getter;
import net.programmer.igoodie.graphic.low.Bindable;
import net.programmer.igoodie.graphic.low.Deconstructable;
import net.programmer.igoodie.input.EventManager;
import net.programmer.igoodie.utils.BufferUtils;

public class Window implements Bindable, Deconstructable {

	private @Getter long id;
	private @Getter int width, height;
	private @Getter String title;

	private @Getter EventManager eventManager;

	public Window(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		
		create();
		
		this.eventManager = new EventManager(this);
		this.eventManager.createHandlers();
	}

	@Override
	public void bind() {
		glfwMakeContextCurrent(id);
	}

	@Override
	public void unbind() {
		glfwMakeContextCurrent(0);
	}

	protected void create() {
		if (!glfwInit()) {
			System.out.println("Couldn't init GLFW..");
			System.exit(-1);
		}

		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

		id = glfwCreateWindow(width, height, title, 0, 0);

		if (id == MemoryUtil.NULL) {
			System.out.println("Couldn't create window..");
			System.exit(-1);
		}

		bind();
		GL.createCapabilities();

		// Center window
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(id, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);

		glfwShowWindow(id);
	}

	public void swapBuffers() {
		glfwSwapBuffers(id);
	}

	public boolean shouldClose() {
		return glfwWindowShouldClose(id);
	}

	@Override
	public void deconstruct() {
		glfwDestroyWindow(id);
	}

	/* ------- */
	
	public void setTitle(String title) {
		glfwSetWindowTitle(id, title);
	}

	public void setIcon(String... iconPaths) {
		assert iconPaths != null;
		
		List<BufferedImage> images = new LinkedList<>();

		for (int i = 0; i < iconPaths.length; i++) {
			try {
				images.add(ImageIO.read(new File(iconPaths[i])));

			} catch (IOException e) {
				System.out.printf("No file found within the path -> %s\n", iconPaths[i]);
			}
		}

		try (GLFWImage.Buffer icons = GLFWImage.malloc(images.size())) {
			for (int i = images.size() - 1; i >= 0; i--) {
				icons.put(i, BufferUtils.imageToGLFWImage(images.get(i)));
			}

			glfwSetWindowIcon(id, icons);
		}

	}

}
