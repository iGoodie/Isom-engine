package net.programmer.igoodie.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import de.javagl.obj.FloatTuple;
import de.javagl.obj.Obj;
import de.javagl.obj.ObjFace;
import de.javagl.obj.ObjReader;
import net.programmer.igoodie.graphic.Mesh;

public class WavefrontUtils {

	public static Mesh readMesh(String filepath) {
		try {
			Obj obj = ObjReader.read(new FileInputStream(new File(filepath)));

			List<Float> vertices = new LinkedList<>();
			List<Integer> indices = new LinkedList<>();
			List<Float> texCoords = new LinkedList<>();

			for (int i = 0; i < obj.getNumVertices(); i++) {
				putVals(vertices, obj.getVertex(i));
			}

			for (int i = 0; i < obj.getNumTexCoords(); i++) {
				putVals(texCoords, obj.getTexCoord(i));
			}

			for (int i = 0; i < obj.getNumFaces(); i++) {
				putIndices(indices, obj.getFace(i));
			}

			Mesh mesh = new Mesh(BufferUtils.createFloatBuffer(vertices), BufferUtils.createIntBuffer(indices));
			mesh.setTextureCoordinates(BufferUtils.createFloatBuffer(texCoords));

			return mesh;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void putVals(List<Float> list, FloatTuple tuple) {
		for (int i = 0; i < tuple.getDimensions(); i++) {
			list.add(tuple.get(i));
		}
	}

	private static void putIndices(List<Integer> list, ObjFace face) {
		for (int i = 0; i < face.getNumVertices(); i++) {
			list.add(face.getVertexIndex(i));
		}
	}

}
