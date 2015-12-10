package water.water;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class ParticleGrid {

	ShapeRenderer sr;
	
	Cell[][] cells;
	GameScreen game;
	
	float cellSize;
	float xOffset;
	
	public ParticleGrid(GameScreen game) {
		this.game = game;
		
		reset();
		
		float totalWidth = Gdx.graphics.getWidth() + xOffset * -2;
		float totalHeight = Gdx.graphics.getHeight() * 2;
		
		cellSize = Gdx.graphics.getHeight() * 0.0125f;
		
		int cellCols = (int)Math.ceil(totalWidth / cellSize);
		int cellRows = (int)Math.ceil(totalHeight / cellSize);
		
		cells = new Cell[cellCols][cellRows];
		
		for(int i = 0; i < cells.length; i++) {
			for(int j = 0; j < cells[0].length; j++) {
				cells[i][j] = new Cell();
			}
		}
		
		sr = new ShapeRenderer();
	}
	
	public void reset() {
		xOffset = -Gdx.graphics.getWidth();
	}
	
	public void draw(float dt) {
		xOffset += game.dCameraX * dt;
		
		ArrayList<Entity> particles = game.particles;
		
		for(int i = 0; i < cells.length; i++) {
			for(int j = 0; j < cells[0].length; j++) {
				cells[i][j].reset();
			}
		}
		
		for(int i = 0; i < particles.size(); i++) {
			Entity p = particles.get(i);
			
			int x = (int)((p.x - xOffset) / cellSize);
			int y = (int)(p.y / cellSize);
			
			if(x >= 0 && x < cells.length && y >= 0 && y < cells[0].length) {
				cells[x][y].addParticle(p);
			}
		}
		
		for(int i = 0; i < cells.length; i++) {
			for(int j = 0; j < cells[0].length; j++) {
				cells[i][j].calculate();
			}
		}
		
		
			Entity.batch.setColor(0.2f, 0.2f, 1f, 0.5f);
			
			Util.enableBlend();
			
			for(int i = 0; i < cells.length; i++) {
				float x = xOffset + i * cellSize;
				float drawX = x - game.cameraX;
				
				for(int j = 0; j < cells[0].length; j++) {
					float y = j * cellSize;
					
					Cell cell = cells[i][j];
					int numParticles = cell.particles.size();
					
					if(numParticles > 0) {
						float grey = Math.max(0, 1 - numParticles * 0.2f);
						
						sr.begin(ShapeType.Filled);
						sr.setColor(119f/255f, 167/255f, 248f/255f, (grey * 0.6f) + 0.4f);
						sr.rect(drawX, y, cellSize, cellSize);
						sr.end();
						
						sr.begin(ShapeType.Line);
						sr.setColor(119f/255f, 167/255f, 248f/255f, (grey * 0.5f) + 0.5f);
						sr.rect(drawX, y, cellSize, cellSize);
						sr.end();
					}
				}
			}
			
			Entity.batch.setColor(1, 1, 1, 1);
		
		for(int i = 0; i < cells.length; i++) {
			for(int j = 0; j < cells[0].length; j++) {
				Cell cell = cells[i][j];
				
				int density = cell.particles.size();
				int westDensity = i > 0 ? cells[i - 1][j].particles.size() : 0;
				int eastDensity = i + 1 < cells.length ? cells[i + 1][j].particles.size() : 0;
				int southDensity = j > 0 ? cells[i][j - 1].particles.size() : 0;
				int northDensity = j + 1 < cells[0].length ? cells[i][j + 1].particles.size() : 0;
				
				float dy = 0;
				float dc = 5 / (0.0008f*Util.length(cell.dx, cell.dy) + 0.1f);
				
				//TODO: Take velocities into account here (no need to apply a force pushing the fluid where it is already going)
				//Bug: When dispersion is high on right and left, want to go both ways, not neither
				
				float dWest = dc * (westDensity - density);
				float dEast = dc * (eastDensity - density);

				dy += dc * (southDensity - density);
				dy -= dc * (northDensity - density);
				
				for(int particleIndex = 0; particleIndex < cell.particles.size(); particleIndex++) {
					Water particle = (Water)cell.particles.get(particleIndex);
					
					if(Math.random() > 0.5f) {
						particle.ddx = dEast;
					}
					else {
						particle.ddx = dWest;
					}
					
					
					particle.ddy = dy;
				}
			}
		}
	}
	
	class Cell {
		ArrayList<Entity> particles;
		float dx, dy;
		
		Cell() {
			particles = new ArrayList<Entity>(100);
		}
		
		void reset() {
			particles.clear();
			dx = dy = 0;
		}
		
		void calculate() {
			int numParticles = particles.size();
			
			if(numParticles > 0) {
				dx /= numParticles;
				dy /= numParticles;
			}	
		}
		
		void addParticle(Entity p) {
			particles.add(p);
			dx += p.dx;
			dy += p.dy;
		}
	}
	
}
