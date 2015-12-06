package water.water;

import com.badlogic.gdx.Gdx;

public class Poop extends Entity {

	public Poop init(float x, float y) {
		float size = Gdx.graphics.getHeight() * 0.1f;
		init(x, y, size, size, Textures.poopDrop);
		return this;
	}
	
	public void draw(float dt) {
		super.draw(dt);
		
		y += dy * dt - 0.5f * GRAVITY * dt * dt;
		dy -= GRAVITY * dt;
	}
	
	public boolean playerHit() {
		game.die();
		return false;
	}
	
}
