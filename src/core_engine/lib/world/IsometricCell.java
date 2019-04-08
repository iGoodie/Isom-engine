package lib.world;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.list.TreeList;

import lib.camera.Camera;
import lib.core.Renderable;
import lib.maths.Boundary;
import lib.world.entitiy.Entity;
import lib.world.entitiy.PropEntity;

public class IsometricCell implements Renderable {

	private IsometricLayer parentLayer;

	private Boundary boundary;
	private List<Entity> entities;

	public IsometricCell(IsometricLayer parentLayer, float x, float y, float width, float height) {
		this.parentLayer = parentLayer;
		this.entities = new TreeList<>();
		this.boundary = new Boundary(x, y, width, height);
	}

	public void addEntity(Entity entity) {
		assert boundary.inRange(entity.getWorldPos());

		entities.add(entity);
	}

	public void updateForEntity(Entity entity) {
		// TODO Check boundary
		// TODO Act accordingly
	}

	@Override
	public void render() {
		// TODO Y-sort entities
		// TODO Render entities
		Camera cam = parentLayer.parentWorld.parent.getCamera();

		Collections.sort(entities);

		cam.attachCamera();
		for (Entity e : entities) {
			if (e instanceof PropEntity && cam.propOnScreen((PropEntity) e)) {
				e.render();
			}
		}
		cam.deattachCamera();
	}

}
