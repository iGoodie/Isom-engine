package lib.world;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.list.TreeList;

import lib.camera.Camera;
import lib.core.Renderable;
import lib.maths.Boundary;
import lib.world.entity.Entity;
import lib.world.entity.PropEntity;

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
		assert boundary.inRange(entity.getWorldPos()) : "Entity must belong to this cell";

		entities.add(entity);
	}

	public void updateForEntity(Entity entity) {
		if(!boundary.inRange(entity.getWorldPos())) {
			entities.remove(entity);
			parentLayer.updateForEntity(entity);
		}
	}

	@Override
	public void render() {
		// TODO Y-sort entities
		// TODO Render entities
		Camera cam = parentLayer.parentWorld.parent.getCamera();

		Collections.sort(entities);

		cam.attachCamera();
		for (Entity entity : entities) {
			if (entity instanceof PropEntity && cam.isPropInFrustum((PropEntity) entity)) {
				entity.render();
			}
		}
		cam.deattachCamera();
	}

}
