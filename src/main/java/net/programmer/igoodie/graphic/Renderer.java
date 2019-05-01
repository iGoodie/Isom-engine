package net.programmer.igoodie.graphic;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL31.*;

import java.util.List;

import org.joml.Matrix4f;

import net.programmer.igoodie.engine.world.Tile;
import net.programmer.igoodie.engine.world.TileMap;
import net.programmer.igoodie.graphic.camera.Camera;
import net.programmer.igoodie.graphic.camera.OrthoCamera;
import net.programmer.igoodie.utils.WavefrontUtils;

public class Renderer {

	public static final Mesh QUAD_MESH = WavefrontUtils.readMesh("./data/models/quad.obj");
	public static final Shader TILE_SHADER = new Shader()
			.addVertexShader("./data/shaders/tile.vert")
			.addFragmentShader("./data/shaders/tile.frag");

	public void renderModel(Mesh model, Texture texture) {
		model.bind();

		glActiveTexture(GL_TEXTURE0);
		texture.bind();

		glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);

		texture.unbind();

		model.unbind();
	}

	public void renderModel(Mesh model, Texture texture, Shader shader) {
		shader.bind();

		renderModel(model, texture);

		shader.unbind();
	}

	public void renderEntity(Camera camera, Entity entity) {
		Mesh mesh = entity.getMesh();
		Texture texture = entity.getTexture();
		Shader shader = entity.getShader();

		Matrix4f projectionMatrix = camera.createProjectionMatrix();
		Matrix4f viewMatrix = camera.createViewMatrix();
		Matrix4f transformationMatrix = entity.getTransformation().createMatrix();

		shader.bind();

		shader.setUniformMat4("transformationMatrix", transformationMatrix);
		shader.setUniformMat4("projectionMatrix", projectionMatrix);
		shader.setUniformMat4("viewMatrix", viewMatrix);

		renderModel(mesh, texture);

		shader.unbind();
	}

	public void renderEntities(Camera camera, Entity... entities) {
		Matrix4f projectionMatrix = camera.createProjectionMatrix();
		Matrix4f viewMatrix = camera.createViewMatrix();

		// TODO fix: Shader clustering for optimization
		entities[0].getShader().bind();
		entities[0].getMesh().bind();

		for (Entity entity : entities) {
			Shader shader = entity.getShader();

			Matrix4f transformationMatrix = entity.getTransformation().createMatrix();
			entities[0].getTexture().bind();

			shader.setUniformMat4("transformationMatrix", transformationMatrix);
			shader.setUniformMat4("projectionMatrix", projectionMatrix);
			shader.setUniformMat4("viewMatrix", viewMatrix);

			glDrawElements(GL_TRIANGLES, entity.getMesh().getVertexCount(), GL_UNSIGNED_INT, 0);
		}

		entities[0].getShader().unbind();
		entities[0].getTexture().unbind();
		entities[0].getMesh().unbind();
	}

	public void renderTileMap(OrthoCamera camera, TileMap tileMap) {
		Matrix4f projectionMatrix = camera.createProjectionMatrix();
		Matrix4f viewMatrix = camera.createViewMatrix();

		TILE_SHADER.bind();
		QUAD_MESH.bind();

		List<Tile> tiles = tileMap.cull(camera);
		
		for(Tile tile : tiles) {
			Matrix4f transformationMatrix = tile.getTransformationMatrix();
			TILE_SHADER.setUniformMat4("transformationMatrix", transformationMatrix);
			TILE_SHADER.setUniformMat4("projectionMatrix", projectionMatrix);
			TILE_SHADER.setUniformMat4("viewMatrix", viewMatrix);

			tile.getTexture().bind();

			glDrawElements(GL_TRIANGLES, QUAD_MESH.getVertexCount(), GL_UNSIGNED_INT, 0);
		}

		tileMap.getTiles()[0][0].getTexture().unbind();
		TILE_SHADER.unbind();
		QUAD_MESH.unbind();
	}

	public void renderInstancedTest(Camera camera, Entity entity) {
		Mesh mesh = entity.getMesh();
		Texture texture = entity.getTexture();
		Shader shader = entity.getShader();

		Matrix4f projectionMatrix = camera.createProjectionMatrix();
		Matrix4f viewMatrix = camera.createViewMatrix();
		Matrix4f transformationMatrix = entity.getTransformation().createMatrix();

		shader.bind();

		shader.setUniformMat4("transformationMatrix", transformationMatrix);
		shader.setUniformMat4("projectionMatrix", projectionMatrix);
		shader.setUniformMat4("viewMatrix", viewMatrix);

		mesh.bind();

		glActiveTexture(GL_TEXTURE0);
		texture.bind();

		glDrawElementsInstanced(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0, 4 * 140_000);

		texture.unbind();

		mesh.unbind();

		shader.unbind();
	}

}
