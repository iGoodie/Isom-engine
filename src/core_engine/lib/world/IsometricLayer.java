package lib.world;

import lib.core.Renderable;
import lib.maths.IsoVector;
import lib.world.entitiy.Entity;

public class IsometricLayer implements Renderable {

	protected IsometricWorld parentWorld;
	private IsometricCell[][] cells;

	public IsometricLayer(IsometricWorld parent, int cellWidth, int cellHeight) {
		this.parentWorld = parent;
		
		int rowCount = (parentWorld.getHeight() / cellHeight) + 1;
		int colCount = (parentWorld.getWidth() / cellWidth) + 1;
		this.cells = new IsometricCell[rowCount][colCount];
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				float x = i * cellWidth;
				float y = j * cellHeight;
				this.cells[i][j] = new IsometricCell(this, x, y, cellWidth, cellHeight);
			}
		}
	}

	public void spawnEntity(Entity entity) {
		IsoVector pos = entity.getWorldPos();

		int belongingRow = (int) (pos.x / cells.length);
		int belongingCol = (int) (pos.y / cells[0].length);
		IsometricCell belongingCell = cells[belongingRow][belongingCol];

		belongingCell.addEntity(entity);
	}

	public void updateForEntity(Entity entity) {
		// TODO Check belonging cell
		// TODO Act accordingly
	}

	@Override
	public void render() {
		// TODO Render cells in zig-zag pattern
		for(IsometricCell[] rows : cells)
			for(IsometricCell cell : rows)
				cell.render();
	}

}
