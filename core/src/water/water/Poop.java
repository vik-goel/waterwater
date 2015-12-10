package water.water;

import com.badlogic.gdx.Gdx;

public class Poop extends Entity {

	public Poop init(float x, float y) {
		animation = RandomItem.flyingPoop.get(); 
		
		float height = Gdx.graphics.getHeight() * 0.2f;
		float width = Util.getWidth(height, animation.getRegion());
		
		y -= height * 0.25f;
		
		init(x, y, width, height, animation);
		
		collideHeight *= 0.2f;
		collideWidth *= 0.25f;
		
		collideY += collideHeight;
		
		drawOrder = Entity.DRAWORDER_POOP;
		
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
		removed = true;
		
		PoopHitText poopText;
		game.addObject(poopText = Pool.get(PoopHitText.class).init());
		game.player.poopTime = poopText.alpha;
		
		return false;
	}
	
}
