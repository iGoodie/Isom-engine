package net.programmer.igoodie.graphic;

import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import lombok.Getter;
import net.programmer.igoodie.graphic.low.Bindable;
import net.programmer.igoodie.graphic.low.Deconstructable;
import net.programmer.igoodie.graphic.low.VertexArray;
import net.programmer.igoodie.utils.BufferUtils;

public class Mesh implements Bindable, Deconstructable {

	private static final int ATTRIB_VERTEX = 0;
	private static final int ATTRIB_TEXTURE_COORD = 1;

	private @Getter VertexArray vao;
	private @Getter int vertexCount;

	public Mesh(float[] vertices, int[] indices) {
		this(BufferUtils.createFloatBuffer(vertices), BufferUtils.createIntBuffer(indices));
	}

	public Mesh(FloatBuffer vertices, IntBuffer indices) {
		this.vao = new VertexArray();
		this.vao.putAttribute(ATTRIB_VERTEX, vertices, 3);
		this.vao.putIndices(indices);
		this.vertexCount = indices.capacity();
	}
	
	public void setTextureCoordinates(float[] textureCoordinates) {
		setTextureCoordinates(BufferUtils.createFloatBuffer(textureCoordinates));
	}

	public void setTextureCoordinates(FloatBuffer textureCoordinates) {
		vao.putAttribute(ATTRIB_TEXTURE_COORD, textureCoordinates, 2);		
	}

	@Override
	public void bind() {
		vao.bind();
		glEnableVertexAttribArray(ATTRIB_VERTEX);
		glEnableVertexAttribArray(ATTRIB_TEXTURE_COORD);
	}

	@Override
	public void unbind() {
		glDisableVertexAttribArray(ATTRIB_TEXTURE_COORD);
		glDisableVertexAttribArray(ATTRIB_VERTEX);
		vao.unbind();
	}

	@Override
	public void deconstruct() {
		vao.deconstruct();
	}

}
