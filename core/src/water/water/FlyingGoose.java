package water.water;

import com.badlogic.gdx.Gdx;

public class FlyingGoose extends Entity {

	float poopDelay;
	
	public FlyingGoose init(float x, float y) {
		float size = Gdx.graphics.getWidth() * 0.1f;
		init(x, y, size, size, Animation.flyingGoose);
		
		dx = Gdx.graphics.getWidth() * 0.2f;
		setPoopDelay();
		
		return this;
	}
	
	public void draw(float dt) {
		super.draw(dt);
		
		x -= dx * dt;
		poopDelay -= dt;
		
		if(poopDelay <= 0) {
			game.addObject(Pool.poop.get().init(x, y));
			setPoopDelay();
		}
		
		offscreenRemove();
	}
	
	void setPoopDelay() {
		poopDelay = random.nextFloat() * 0.5f + 0.7f;
	}
	
}
