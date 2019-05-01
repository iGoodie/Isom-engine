package net.programmer.igoodie.graphic;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lombok.Getter;
import net.programmer.igoodie.graphic.low.Bindable;
import net.programmer.igoodie.utils.BufferUtils;

public class Texture implements Bindable {

	private @Getter int id;
	private @Getter int width;
	private @Getter int height;

	public Texture(String filepath) {
		try {
			BufferedImage image = ImageIO.read(new File(filepath));
			this.width = image.getWidth();
			this.height = image.getHeight();
			
			this.id = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, id);
			
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			
			glGenerateMipmap(GL_TEXTURE_2D);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtils.readImageToBuffer(image));
			
			glBindTexture(GL_TEXTURE_2D, 0);
			
		} catch (IOException e) {
			System.out.println("Oopsie :c");
		}
	}
	
	@Override
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	@Override
	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);		
	}

}
