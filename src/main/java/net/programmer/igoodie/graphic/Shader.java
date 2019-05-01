package net.programmer.igoodie.graphic;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import com.programmer.igoodie.utils.io.FileUtils;

import net.programmer.igoodie.graphic.low.Bindable;
import net.programmer.igoodie.graphic.low.Deconstructable;

public class Shader implements Bindable, Deconstructable {

	protected int program_id;
	protected final Map<String, Integer> uniformCache;

	public Shader() {
		program_id = glCreateProgram();
		uniformCache = new HashMap<>();
	}

	public Shader addVertexShader(String filepath) {
		parseShader(filepath, GL_VERTEX_SHADER);
		return this;
	}

	public Shader addFragmentShader(String filepath) {
		parseShader(filepath, GL_FRAGMENT_SHADER);
		return this;
	}

	public Shader addGeometryShader(String filepath) {
		parseShader(filepath, GL_GEOMETRY_SHADER);
		return this;
	}

	protected void parseShader(String filepath, int type) {
		String source = FileUtils.readString(filepath);

		if (source == null) {
			System.out.printf("Warning! Shader file couldn't be loaded. (%s)\n", filepath);
		}

		int shader_id = glCreateShader(type);

		glShaderSource(shader_id, source);
		glCompileShader(shader_id);

		if (glGetShaderi(shader_id, GL_COMPILE_STATUS) == GL_FALSE) {
			System.out.println(glGetShaderInfoLog(shader_id));
			System.out.println("Compilation error for " + filepath);
			System.exit(-1);
		}
		
		glAttachShader(program_id, shader_id);
		
		glLinkProgram(program_id);

		if (glGetProgrami(program_id, GL_LINK_STATUS) == GL_FALSE) {
			System.out.printf("Error linking shader code: " + glGetProgramInfoLog(program_id));
			System.exit(-1);
		}

		glDeleteShader(shader_id);
		
		glValidateProgram(program_id);
		
		if(glGetProgrami(program_id, GL_VALIDATE_STATUS) == GL_FALSE) {
			System.out.printf("Error validating shader code: " + glGetProgramInfoLog(program_id));
			System.exit(-1);
		}
	}

	@Override
	public void bind() {
		glUseProgram(program_id);
	}
	
	@Override
	public void unbind() {
		glUseProgram(0);
	}
	
	@Override
	public void deconstruct() {
		unbind();
		
		if (program_id != MemoryUtil.NULL)
			glDeleteProgram(program_id);
	}

	public void bindAttributeLocation(int index, String location) {
		glBindAttribLocation(program_id, index, location);
	}

	/* Uniform methods */
	private int getUniformLocation(String name) {
		Integer location = uniformCache.get(name);
		
		if(location == null) {
			location = glGetUniformLocation(program_id, name);
			
			if(location < 0)
				throw new IllegalArgumentException("No uniform found with name = " + name);
			
			uniformCache.put(name, location);
		}
		
		return location;
	}
	
	public void setUniformBoolean(String name, boolean value) {
		glUniform1i(getUniformLocation(name), value ? 1 : 0);
	}

	public void setUniformInt(String name, int value) {
		glUniform1i(getUniformLocation(name), value);
	}
	
	public void setUniformMat4(String name, Matrix4f matrix) {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer fb = stack.mallocFloat(4 * 4);
			matrix.get(fb);
			glUniformMatrix4fv(getUniformLocation(name), false, fb);
		}
	}

	// TODO: impl utility uniform methods

}
