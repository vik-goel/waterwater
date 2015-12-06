package water.water;

import com.badlogic.gdx.Gdx;

public class Poop extends Entity {

	public Poop init(float x, float y) {
		float height = Gdx.graphics.getHeight() * 0.3f;
		float width = Util.getWidth(height, Textures.poopDrop);
		
		y -= height * 0.25f;
		
		init(x, y, width, height, Textures.poopDrop);
		
		collideHeight *= 0.2f;
		collideWidth *= 0.25f;
		
		collideY += collideHeight;
		
		return this;
	}
	
	public void draw(float dt) {
		super.draw(dt);
		
		y += dy * dt - 0.5f * GRAVITY * dt * dt;
		dy -= GRAVITY * dt;
		
		if(isHitByWater()) {
			removed = true;
		}
	}
	
	public boolean playerHit() {
		game.die();
		return false;
	}
	
}
