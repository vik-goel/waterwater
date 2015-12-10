package water.water;

import com.badlogic.gdx.Gdx;

public class WaterItem extends Entity {

	public WaterItem init(float x, float y) {
		if(animation == null) {
			animation = new Animation(Animation.waterItem);
		}
		
		final float size = 0.4f * Gdx.graphics.getHeight();
		init(DRAWORDER_WATER_ITEM, x, y, size, size, animation);
		
		collideWidth *= 0.15f;
		collideHeight *= 0.15f;
		collideY += collideHeight * 0.9f;
		
		return this;
	}
	
	public void draw(float dt) {
		super.draw(dt);
		offscreenRemove();
	}
	
	public boolean playerHit() {
		super.playerHit();
		removed = true;
		
		game.water = game.maxWater;
		RandomItem.powerupSounds.get().play();
		
		return true;
	}
}
