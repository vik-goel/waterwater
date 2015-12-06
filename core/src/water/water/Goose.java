package water.water;

import com.badlogic.gdx.Gdx;

import water.water.ParticleGrid.Cell;

public class Goose extends Entity {

	enum State {
		CHASING,
		FLYING,
		IDLE
	}
	
	private static float width = 0.27f * Gdx.graphics.getHeight();
	private static float height = 0.27f * Gdx.graphics.getHeight();
	
	private State state;
	
	public Goose init(float x, float y) {
		init(x + width * 0.5f, y + height * 0.5f, width, height, Textures.goose);
		state = State.IDLE;
		return this;
	}
	
	public void draw(float dt) {
		super.draw(dt);
		
		switch(state) {
		case CHASING:
			chasingUpdate(dt);
			break;
		case FLYING:
			flyingUpdate(dt);
			break;
		case IDLE:
			idleUpdate(dt);
			break;
		}
	}
	
	private void chasingUpdate(float dt) {
		x += game.dCameraX * dt;
	}
	
	private void flyingUpdate(float dt) {
		x += game.dCameraX * dt;
		
		float leftOffset = Gdx.graphics.getWidth() * 0.05f + (game.player.drawWidth + drawWidth) * 0.5f;
		
		if (x > game.player.x - leftOffset) {
			x -= dt * 700;
		} else {
			state = State.CHASING;
		}
	}
	
	private void idleUpdate(float dt) {
		offscreenRemove();
		
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
						state = State.FLYING;
					}
				}
			}
		}
	}
	
	public boolean collidesWith(Entity other) {
		return !(other instanceof Player) || (other instanceof Player && state == State.IDLE);
	}

}
