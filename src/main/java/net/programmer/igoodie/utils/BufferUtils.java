package net.programmer.igoodie.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.glfw.GLFWImage;

public class BufferUtils {

	public static ByteBuffer createByteBuffer(byte[] array) {
		ByteBuffer buffer = ByteBuffer.allocateDirect(array.length)
				.order(ByteOrder.nativeOrder());
		buffer.put(array).flip();
		return buffer;
	}

	public static ByteBuffer createByteBuffer(List<Byte> list) {
		ByteBuffer buffer = ByteBuffer.allocateDirect(list.size())
				.order(ByteOrder.nativeOrder());

		for (byte i : list)
			buffer.put(i);

		buffer.flip();

		return buffer;
	}

	public static FloatBuffer createFloatBuffer(float[] array) {
		FloatBuffer buffer = ByteBuffer.allocateDirect(array.length << 2)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		buffer.put(array).flip();
		return buffer;
	}

	public static FloatBuffer createFloatBuffer(List<Float> list) {
		FloatBuffer buffer = ByteBuffer.allocateDirect(list.size() << 2)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();

		for (float i : list)
			buffer.put(i);

		buffer.flip();

		return buffer;
	}

	public static IntBuffer createIntBuffer(int[] array) {
		IntBuffer buffer = ByteBuffer.allocateDirect(array.length << 2)
				.order(ByteOrder.nativeOrder()).asIntBuffer();
		buffer.put(array).flip();
		return buffer;
	}

	public static IntBuffer createIntBuffer(List<Integer> list) {
		IntBuffer buffer = ByteBuffer.allocateDirect(list.size() << 2)
				.order(ByteOrder.nativeOrder()).asIntBuffer();

		for (int i : list)
			buffer.put(i);

		buffer.flip();

		return buffer;
	}

	public static IntBuffer readImageToBuffer(String filepath) {
		try {
			BufferedImage image = ImageIO.read(new File(filepath));
			return readImageToBuffer(image);

		} catch (IOException e) {
			return null;
		}
	}

	public static IntBuffer readImageToBuffer(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();

		int[] pixels = new int[width * height];

		image.getRGB(0, 0, width, height, pixels, 0, width);

		for (int i = 0; i < pixels.length; i++) {
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0x00ff0000) >> 16;
			int g = (pixels[i] & 0x0000ff00) >> 8;
			int b = (pixels[i] & 0x000000ff);

			pixels[i] = a << 24 | b << 16 | g << 8 | r;
		}

		return createIntBuffer(pixels);
	}

	public static GLFWImage imageToGLFWImage(BufferedImage image) {
		if (image.getType() != BufferedImage.TYPE_INT_ARGB_PRE) {
			final BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(),
					BufferedImage.TYPE_INT_ARGB_PRE);
			final Graphics2D graphics = convertedImage.createGraphics();
			final int targetWidth = image.getWidth();
			final int targetHeight = image.getHeight();
			graphics.drawImage(image, 0, 0, targetWidth, targetHeight, null);
			graphics.dispose();
			image = convertedImage;
		}
		final ByteBuffer buffer = org.lwjgl.BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getWidth(); j++) {
				int colorSpace = image.getRGB(j, i);
				buffer.put((byte) ((colorSpace << 8) >> 24));
				buffer.put((byte) ((colorSpace << 16) >> 24));
				buffer.put((byte) ((colorSpace << 24) >> 24));
				buffer.put((byte) (colorSpace >> 24));
			}
		}
		buffer.flip();
		final GLFWImage result = GLFWImage.create();
		result.set(image.getWidth(), image.getHeight(), buffer);
		return result;
	}

}
