package water.water;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Cloud extends Entity {
	
	public Cloud init(float x, float y) {
		TextureRegion tex =  RandomTexture.clouds.getRegion();
		drawWidth = tex.getRegionWidth();
		drawHeight = tex.getRegionHeight();
		init(x + drawWidth * 0.5f, y, drawWidth, drawHeight, tex);
		return this;
	}
	
	public boolean collidesWith(Entity other) {
		return false;
	}

}
