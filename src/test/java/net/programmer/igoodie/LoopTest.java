package net.programmer.igoodie;

import static org.lwjgl.opengl.GL11.*;

import java.text.NumberFormat;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import net.programmer.igoodie.engine.world.TileMap;
import net.programmer.igoodie.gameloop.IsomEngineLoop;
import net.programmer.igoodie.graphic.Entity;
import net.programmer.igoodie.graphic.Mesh;
import net.programmer.igoodie.graphic.Renderer;
import net.programmer.igoodie.graphic.Shader;
import net.programmer.igoodie.graphic.Texture;
import net.programmer.igoodie.graphic.camera.Camera;
import net.programmer.igoodie.graphic.camera.OrthoCamera;
import net.programmer.igoodie.graphic.camera.PerspectiveCamera;
import net.programmer.igoodie.input.subscriber.KeyboardListener;
import net.programmer.igoodie.input.subscriber.MouseListener;
import net.programmer.igoodie.input.subscriber.WindowListener;
import net.programmer.igoodie.utils.Mathf;
import net.programmer.igoodie.utils.WavefrontUtils;

public class LoopTest extends IsomEngineLoop implements MouseListener, WindowListener, KeyboardListener {

	boolean wireframeMode = false;

	Camera camera;

	Renderer renderer;

	Mesh mesh;
	Shader shader;
	Texture texture;

	Entity teapotEntity;
	
	TileMap tileMap;

	@Override
	public void setup() {
		createWindow(1280, 720);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glFrontFace(GL_CW);

		camera = new PerspectiveCamera(getWindow().getWidth(), getWindow().getHeight());
		camera = new OrthoCamera(getWindow().getWidth(), getWindow().getHeight());
		camera.move(0, 0, 100f);

		renderer = new Renderer();

		mesh = WavefrontUtils.readMesh("./data/models/quad.obj");

		shader = new Shader();
		shader.addVertexShader("./data/shaders/vertexShader.vert");
		shader.addFragmentShader("./data/shaders/fragmentShader.frag");

		texture = new Texture("./data/top.png");

		teapotEntity = new Entity();
		teapotEntity.setMesh(WavefrontUtils.readMesh("./data/models/teapot.obj"));
		teapotEntity.setShader(shader);
		teapotEntity.setTexture(new Texture("./data/textures/psycho.png"));
		teapotEntity.getTransformation().setTranslation(100, 10);
		teapotEntity.getTransformation().scale(4);
		teapotEntity.getTransformation().rotate((float) Math.PI / 6, (float) Math.PI / 6, 0);

		getWindow().setIcon("./data/basic.png");

		getWindow().getEventManager().subscribe(this);

		getWindow().setIcon("./data/isocog32.png");
		
		tileMap = new TileMap(this, 1000, 1000);
	}

	@Override
	public void update(float dt) {
		super.update(dt);
		
		teapotEntity.getTransformation().rotate(0, 3.14f / 2 * dt, 0);
		
		Vector3f camMove = new Vector3f();
		float speed = 2500;
		
		if(getKeyboard().isKeyDown('W')) {
			camMove.add(0, 1, 0);
		}
		
		if(getKeyboard().isKeyDown('S')) {
			camMove.add(0, -1, 0);
		}
		
		if(getKeyboard().isKeyDown('A')) {
			camMove.add(-1, 0, 0);
		}
		
		if(getKeyboard().isKeyDown('D')) {
			camMove.add(1, 0, 0);
		}
		
		if(!camMove.equals(0, 0, 0)) {
			camMove.normalize().mul(dt * speed);
			camera.getPosition().add(camMove);
		}
		
		teapotEntity.getTransformation().setTranslation(camera.getPosition().x, camera.getPosition().y, 0);
	}

	@Override
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glPolygonMode(GL_FRONT_AND_BACK, wireframeMode ? GL_LINE : GL_FILL);

//		glDisable(GL_DEPTH_TEST);
//		renderer.renderEntities(camera, entities);

		glDisable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);
		renderer.renderTileMap((OrthoCamera)camera, tileMap);
//
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_FRONT);
		renderer.renderEntity(camera, teapotEntity);

//		glDisable(GL_DEPTH_TEST);
//		renderer.renderInstancedTest(camera, entities[0]);
		
//		renderer.renderEntities(camera, tileMap.getTiles()[0]);
	}

	/* -------- INPUT --------- */

	@Override
	public void wheelMoved(float horizontalOffset, float verticalOffset) {
		if(camera instanceof OrthoCamera) {
			float currentZoom = ((OrthoCamera) camera).getZoom();
			currentZoom = Mathf.clamp(currentZoom + verticalOffset * 0.125f, 0.250f, 2.875f);
			((OrthoCamera) camera).setZoom(currentZoom);
		}
		
//		camera.move(0, 0, -verticalOffset);
	}

	@Override
	public void keyTyped(int unicodeCodePoint) {
//		char[] c = Character.toChars(unicodeCodePoint);
//		System.out.printf("Typed unicode: %s\n", new String(c));
	}

	@Override
	public void mousePressed(int button, int mods) {
		System.out.println(tileMap.screenToWorldExact((OrthoCamera)camera, 
				(float)getMouse().getMouseX(), (float)getMouse().getMouseY())
				.toString(NumberFormat.getNumberInstance()));
		
		if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
			if (camera instanceof PerspectiveCamera)
				camera.getPosition().set(0f);
			else if (camera instanceof OrthoCamera) {
				camera.getRotation().x = (float) (Math.PI / 4f);
				camera.getRotation().y = (float) (-Math.PI / 4f);
			}

		}
	}

	@Override
	public void mouseMoved(double fromX, double fromY, double toX, double toY) {
		if (getMouse().isDown()) {
			float angleY = (float) (toX - fromX) * 0.01f;
			float angleX = (float) (toY - fromY) * 0.01f;
			camera.rotate(angleX, angleY, 0);
		}
	}

}
