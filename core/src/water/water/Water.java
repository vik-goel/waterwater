package water.water;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Water extends Entity {
	
	public float ddx, ddy;
	float dAlpha;
	
	Vector2 pastPos = new Vector2();
	Color[] colors = new Color[3];
	
	boolean justSpawned = true;
	
	public Entity init(float x, float y, float dx, float dy) {
		init(0, x, y, getDim(), getDim(), Textures.water);
		
		ddx = ddy = dAlpha = 0;
		
		this.dx = dx;
		this.dy = dy;
		
		collideWidth *= 0.1f;
		collideHeight *= 0.1f;
		
		pastPos.set(x, y);
		
		for(int i = 0; i < colors.length; i++) {
			colors[i] = new Color();
		}
		
		draw(random.nextFloat() * 0.15f);
		
		return this;
	}
	
	private static float getDim() {
		return random.nextFloat() * 0.011f * Gdx.graphics.getWidth() + 0.008f * Gdx.graphics.getWidth();
	}

	public void draw(float dt) {
		float ddAlpha = -0.33f;
		alpha += dAlpha * dt + 0.5f * ddAlpha * dt * dt;
		dAlpha += ddAlpha * dt;
		
		if(alpha < 0) {
			alpha = 0;
			removed = true;
		}
		

		float mx = dx * dt + 0.5f * ddx * dt * dt;
		float my = dy * dt + 0.5f * (ddy - GRAVITY) * dt * dt;
		
		dx += ddx * dt;
		dy += (ddy - GRAVITY) * dt;
		
		ddx = ddy = 0;
		
		//TODO: If the water is going too fast, it can pass through platforms. solve that
		if(checkCollisions(mx, 0) == null) {
			x += mx;
		} else {
			dx *= -.6f;
			
			float disperse = 1000;
			dy += random.nextFloat()*disperse - disperse/2;
		}
		
		if(checkCollisions(0, my) == null) {
			y += my;
		} else {
			dx *= 0.7f;
			dy *= -0.1f;
		}
		
		if(justSpawned) {
			justSpawned = false;
		} else if(false){
			batch.begin();
			batch.setColor(1, 1, 1, alpha);
			batch.draw(tex, x - drawWidth * 0.5f - game.cameraX, y - drawHeight * 0.5f, drawWidth, drawHeight);
			batch.end();
			
			float xDiff = pastPos.x - x;
			float yDiff = pastPos.y - y;
			float len = Util.length(xDiff, yDiff);
			
			Util.enableBlend();
			
			if(len > 0) {
				xDiff /= len;
				yDiff /= len;
				
				float drawC = 0.4f;
				float tx = -yDiff * drawWidth * drawC;
				float ty = xDiff * drawHeight * drawC;
				
				float x2 = x + tx;
				float y2 = y + ty;
				float x3 = x - tx;
				float y3 = y - ty;
				
				colors[0].set(119f/255f, 167/255f, 248f/255f, 0);
				colors[1].set(119f/255f, 167/255f, 248f/255f, 0.5f * alpha);
				colors[2].set(119f/255f, 167/255f, 248f/255f, 0.5f * alpha);
				
				sr.begin(ShapeType.Filled);
				sr.setColor(119f/255f, 167/255f, 248f/255f, 1);
				sr.triangle(pastPos.x + xDiff * 50, pastPos.y + yDiff * 50, x2, y2, x3, y3, colors[0], colors[1], colors[2]);
				sr.end();
			}
		}
		
		pastPos.set(x, y);
	}
	
	public boolean collidesWith(Entity other) {
		return !(other instanceof Player || other instanceof WaterItem);
	}

}
