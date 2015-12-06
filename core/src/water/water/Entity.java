package water.water;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import water.water.ParticleGrid.Cell;

public abstract class Entity {

	public static final int DRAWORDER_GROUND = -100;
	public static final int DRAWORDER_GROUND_OBSTACLE = 10;
	public static final int DRAWORDER_GOOSE = 25;
	public static final int DRAWORDER_WATER_ITEM = 50;
	public static final int DRAWORDER_POOP = 75;
	public static final int DRAWORDER_PLAYER = 100;
	
	public static final float GRAVITY = 9.8f * 0.14f * Gdx.graphics.getHeight();
	
	public static SpriteBatch batch = new SpriteBatch();
	public static ShapeRenderer sr = new ShapeRenderer();
	public static Random random = new Random();
	public static GameScreen game;
	
	public float x, y;
	public float dx, dy;
	public float drawWidth, drawHeight;
	public float collideX, collideY;
	public float collideWidth, collideHeight;
	public TextureRegion tex;
	public Animation animation;
	
	public boolean removed;
	public float alpha; //TODO: Nothing uses alpha anymore?
	public boolean seamlessTexture;

	public int drawOrder;
	public boolean flipX;
	
	public Entity(){}
	
	public Entity(float x, float y, float drawWidth, float drawHeight, Animation animation) {
		init(x, y, drawWidth, drawHeight, animation);
	}
	
	public Entity(float x, float y, float drawWidth, float drawHeight, TextureRegion tex) {
		init(x, y, drawWidth, drawHeight, tex);
	}
	
	public Entity init(float x, float y, float drawWidth, float drawHeight, Animation animation) {
		init(x, y, drawWidth, drawHeight, animation.getRegion());
		this.animation = animation;
		return this;
	}
	
	public Entity init(float x, float y, float drawWidth, float drawHeight, TextureRegion tex) {
		this.x = x;
		this.y = y;
		this.drawWidth = drawWidth;
		this.drawHeight = drawHeight;
		this.tex = tex;
		
		collideWidth = drawWidth;
		collideHeight = drawHeight;
		
		dx = dy = 0;
		collideX = collideY = 0;
		removed = false;
		alpha = 1;
		seamlessTexture = false;
		flipX = false;
		drawOrder = 0;
		
		return this;
	}
	
	public void draw(float dt) {
		if(animation != null) {
			animation.update(dt);
			tex = animation.getRegion();
		}
		
		if(tex != null) {
			batch.begin();
			batch.setColor(1, 1, 1, alpha);
			
			float xStart = x - drawWidth * 0.5f - game.cameraX;
			float yStart = y - drawHeight * 0.5f;
			
			if(seamlessTexture) {
				batch.draw(tex.getTexture(), xStart, yStart, drawWidth, drawHeight, 0, 1, drawWidth / tex.getRegionWidth(), drawHeight / tex.getRegionHeight());
			}
			else {
				if (flipX) {
					batch.draw(tex, xStart, yStart, drawWidth * 0.5f, drawHeight * 0.5f, drawWidth, drawHeight, -1, 1, 0);
				} else {
					batch.draw(tex, xStart, yStart, drawWidth, drawHeight);
				}
			}
			
			
			
			batch.end();
		}
		
//		sr.begin(ShapeType.Line);
//		sr.setColor(1, 0, 1, 1);
//		sr.rect(x - drawWidth * 0.5f - game.cameraX, y - drawHeight * 0.5f, drawWidth, drawHeight);
//		
//		Gdx.gl20.glLineWidth(1);
//		sr.setColor(0, 1, 1, 1);
//		sr.rect(x + collideX - collideWidth * 0.5f - game.cameraX, y + collideY - collideHeight * 0.5f, collideWidth, collideHeight);
//		
//		
//		sr.end();
//		Gdx.gl20.glLineWidth(1);
	}
	
	public Entity checkCollisions(float mx, float my) {
		ArrayList<Entity> entities = game.objects;
		Entity result = null;
		
		x += mx;
		y += my;
		
		for(int entityIndex = 0; entityIndex < entities.size(); entityIndex++) {
			Entity test = entities.get(entityIndex);
			if(test != this && collidesWith(test) && test.collidesWith(this) && overlaps(test)) {
				result = test;
				break;
			}
		}
		
		x -= mx;
		y -= my;
		
		return result;
	}
	
	public boolean overlaps(Entity e) {
		float radiusX = (collideWidth + e.collideWidth) * 0.5f;
		float radiusY = (collideHeight + e.collideHeight) * 0.5f;
		
		float xDiff = (e.x + e.collideX) - (x + collideX);
		float yDiff = (e.y + e.collideY) - (y + collideY);
		
		return Math.abs(xDiff) < radiusX && Math.abs(yDiff) < radiusY;
	}
	
	public boolean isHitByWater() {
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
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public boolean collidesWith(Entity other) {
		return true;
	}
	
	public boolean playerHit() {
		return false;
	}
	
	public void offscreenRemove() {
		if(x + drawWidth * 0.5f < game.cameraX) {
			removed = true;
		}
	}
	
}
