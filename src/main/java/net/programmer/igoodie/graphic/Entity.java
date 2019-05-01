package net.programmer.igoodie.graphic;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class Entity {

	private @Getter @Setter Mesh mesh;
	private @Getter @Setter Texture texture;
	private @Getter @Setter Shader shader;
	private @Getter Transformation transformation;

	public Entity() {
		this.transformation = new Transformation();
	}

}
