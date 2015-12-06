package water.water;

import water.water.ParticleGrid.Cell;

public class Goose extends Entity {

	private static float width = 200;
	private static float height = 200;
	
	public Goose(float x, float y) {
		super(x + width * 0.5f, y + height * 0.5f, width, height, Textures.goose);
	}
	
	public void draw(float dt) {
		super.draw(dt);
		
		ParticleGrid grid = game.particleGrid;
		
		int gridMinX = (int)(((x - collideWidth * 0.5f) - grid.xOffset) / grid.cellSize);
		int gridMinY = (int)((y - collideHeight * 0.5f) / grid.cellSize);
		int gridWidth = (int)Math.ceil(collideWidth / grid.cellSize);
		int gridHeight = (int)Math.ceil(collideHeight / grid.cellSize);
		
		int gridMaxX = gridMinX + gridWidth;
		int gridMaxY = gridMinY + gridHeight;
		
		for(int i = gridMinX; i < gridMaxX; i++) {
			for(int j = 0; j < gridMaxY; j++) {
				if(i >= 0 && j >= 0 && i < grid.cells.length && j < grid.cells[0].length) {
					Cell cell = grid.cells[i][j];
					
					if(!cell.particles.isEmpty()) {
						removed = true;
					}
				}
			}
		}
	}

}
