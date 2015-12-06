package water.water;

public class Platform extends Entity {
	
	//TODO: Remove platforms that have fallen off the screen
	
	public Platform(float x, float y) {
		this(x, y, 300, 50);
	}
	
	public Platform(float x, float y, float width, float height) {
		super(x, y, width, height, Textures.platform);
	}
	
	public void draw(float dt) {
		super.draw(dt);
	}

}
