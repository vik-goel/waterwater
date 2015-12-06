package water.water;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public abstract class Entity {

	public static final float GRAVITY = 9.8f * 70;
	
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
	
	public boolean removed = false;
	public float alpha = 1;
	
	public Entity(float x, float y, float drawWidth, float drawHeight, Animation animation) {
		this(x, y, drawWidth, drawHeight, animation.getRegion());
		this.animation = animation;
	}
	
	public Entity(float x, float y, float drawWidth, float drawHeight, TextureRegion tex) {
		this.x = x;
		this.y = y;
		this.drawWidth = drawWidth;
		this.drawHeight = drawHeight;
		this.tex = tex;
		
		collideWidth = drawWidth;
		collideHeight = drawHeight;
	}
	
	public void draw(float dt) {
		if(animation != null) {
			animation.update(dt);
			tex = animation.getRegion();
		}
		
		if(tex != null) {
			batch.begin();
			batch.setColor(1, 1, 1, alpha);
			batch.draw(tex, x - drawWidth * 0.5f - game.cameraX, y - drawHeight * 0.5f, drawWidth, drawHeight);
			batch.end();
		}
		
//		sr.begin(ShapeType.Line);
//		sr.setColor(1, 0, 1, 1);
//		sr.rect(x - drawWidth * 0.5f - game.cameraX, y - drawHeight * 0.5f, drawWidth, drawHeight);
//		
//		sr.setColor(0, 1, 1, 1);
//		sr.rect(x + collideX - collideWidth * 0.5f - game.cameraX, y + collideY - collideHeight * 0.5f, collideWidth, collideHeight);
//		
//		sr.end();
	}
	
	public float stepGravity(float dt) {
		float my = dy * dt - 0.5f * GRAVITY * dt * dt;
		dy -= GRAVITY * dt;
		return my;
	}
	
	public Entity checkCollisions(float mx, float my) {
		ArrayList<Entity> entities = game.objects;
		Entity result = null;
		
		x += mx;
		y += my;
		
		for(int entityIndex = 0; entityIndex < entities.size(); entityIndex++) {
			Entity test = entities.get(entityIndex);
			if(test != this && collidesWith(test) && overlaps(test)) {
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
	
	
	public boolean collidesWith(Entity other) {
		return true;
	}
	
}
