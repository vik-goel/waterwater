package water.water;

import com.badlogic.gdx.Gdx;

public class PoopHitText extends Entity {

	public PoopHitText init() {
		float height = Gdx.graphics.getHeight() * 0.075f;
		float width = Util.getWidth(height, Textures.poopHitText);
		
		init(x, y, width, height, Textures.poopHitText);
		
		alpha = 2;
		
		return this;
	}
	
	public void draw(float dt) {
		x = game.player.x + drawWidth * 0.5f;
		y = game.player.y + drawHeight * 0.8f;
		
		super.draw(dt);
		
		alpha -= dt;
		
		if(alpha < 0) {
			alpha = 0;
			removed = true;
		}
	}
	
	public boolean collidesWith(Entity other) {
		return false;
	}
	
}
