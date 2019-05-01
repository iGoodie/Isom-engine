package net.programmer.igoodie.graphic.low;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL33.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;

public class VertexArray implements Bindable, Deconstructable {

	private @Getter int vaoID;
	private List<Integer> vboIDs;

	public VertexArray() {
		vaoID = glGenVertexArrays();
		vboIDs = new LinkedList<Integer>();
	}

	public int putAttribute(int attribIndex, FloatBuffer data, int size) {
		bind();

		int bufferID = glGenBuffers();
		vboIDs.add(bufferID);

		glBindBuffer(GL_ARRAY_BUFFER, bufferID);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);

		glVertexAttribPointer(attribIndex, size, GL_FLOAT, false, 0, 0);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		unbind();

		return bufferID;
	}
	
	public void putAttribute(int attribIndex, int vbo, int size) {
		bind();
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		
		glVertexAttribPointer(attribIndex, size, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		unbind();
	}

	public int putInstancedAttribute(int attribIndex, FloatBuffer data, int size) {
		bind();

		int bufferID = glGenBuffers();
		vboIDs.add(bufferID);

		glBindBuffer(GL_ARRAY_BUFFER, bufferID);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STREAM_DRAW);

		glVertexAttribPointer(attribIndex, size, GL_FLOAT, false, 0, 0);
		glVertexAttribDivisor(attribIndex, 1);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		unbind();

		return bufferID;
	}

	public int putIndices(IntBuffer indices) {
		bind();

		int bufferID = glGenBuffers();
		vboIDs.add(bufferID);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		unbind();

		return bufferID;
	}

	@Override
	public void bind() {
		glBindVertexArray(vaoID);
	}

	@Override
	public void unbind() {
		glBindVertexArray(0);
	}

	@Override
	public void deconstruct() {
		for (int vboID : vboIDs)
			glDeleteBuffers(vboID);

		glDeleteVertexArrays(vaoID);
	}

}
 