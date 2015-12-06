package water.water;

public class Cloud extends Entity {

	public Cloud(float x, float y) {
		super(x, y, 0, 0, RandomTexture.clouds.getRegion());
		drawWidth = tex.getRegionWidth();
		drawHeight = tex.getRegionHeight();
		this.x += drawWidth * 0.5f;
	}
	
	public boolean collidesWith(Entity other) {
		return false;
	}

}
