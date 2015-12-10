package water.water;

import com.badlogic.gdx.Gdx;

public class FlyingGoose extends Entity {

	float poopDelay;
	
	public FlyingGoose init(float x, float y) {
		float width = Gdx.graphics.getWidth() * 0.25f;
		float height = Util.getHeight(width, Animation.flyingGoose.getRegion());
				
		init(DRAWORDER_GOOSE, x, y, width, height, Animation.flyingGoose);
		
		collideX -= drawWidth * 0.15f;
		collideY -= drawHeight * 0.1f;
		
		collideWidth *= 0.3f;
		collideHeight *= 0.25f;
		
		dx = Gdx.graphics.getWidth() * 0.2f;
		setPoopDelay();
		
		flipX = true;
		
		return this;
	}
	
	public void draw(float dt) {
		super.draw(dt);
		
		x -= dx * dt;
		poopDelay -= dt;
		
		if(poopDelay <= 0) {
			game.addObject(Pool.get(Poop.class).init(x, y));
			setPoopDelay();
		}
		
		offscreenRemove();
	}
	
	void setPoopDelay() {
		poopDelay = random.nextFloat() * 0.9f + 0.25f;
	}
	
	public boolean collidesWith(Entity other) {
		return !(other instanceof Player);
	}
	
}
