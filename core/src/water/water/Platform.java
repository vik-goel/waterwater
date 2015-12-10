package water.water;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Platform extends Entity {
	
	public boolean dieOnHit;
	
	public Platform init(float x, float y, float width, float height, boolean dieOnHit, TextureRegion texture, float collideYC, float collideHeightC, float collideXC, float collideWidthC) {
		if(dieOnHit) {
			drawOrder = Entity.DRAWORDER_GROUND_OBSTACLE;
		} else {
			drawOrder = Entity.DRAWORDER_GROUND;
		}
		
		init(drawOrder, x + width * 0.5f, y + height * 0.5f, width, height, texture);
		this.dieOnHit = dieOnHit;
		seamlessTexture = !dieOnHit;
		
		collideY = collideHeight * collideYC;
		collideHeight *= collideHeightC;
		collideX = collideWidth * collideXC;
		collideWidth *= collideWidthC;
		
		if(dieOnHit) {
			this.y -= collideY;
		}
		
		return this;
	}
	
	public void draw(float dt) {
		super.draw(dt);
		offscreenRemove();
	}

}
