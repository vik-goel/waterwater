package water.water;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Platform extends Entity {
	
	//TODO: Remove platforms that have fallen off the screen
	
	public boolean dieOnHit;
	
	public Platform(float x, float y, float width, float height, boolean dieOnHit, TextureRegion texture) {
		super(x + width * 0.5f, y + height * 0.5f, width, height, texture);
		this.dieOnHit = dieOnHit;
		seamlessTexture = !dieOnHit;
		
		if(!dieOnHit) {
			collideY = collideHeight * -0.25f;
			collideHeight *= 0.5f;
		}
	}
	
	public void draw(float dt) {
		super.draw(dt);
	}

}
