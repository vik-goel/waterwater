package water.water;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {

	public static Animation playerRun = new Animation(Textures.playerRun, 32, 32, 0.1f);
	
	private TextureRegion[] frames;
	private float anim = 0;
	private float frameDelay;
	
	public Animation(TextureRegion spriteSheet, int frameWidth, int frameHeight, float frameDelay) {
		frames = new TextureRegion[spriteSheet.getRegionWidth() / frameWidth];
		
		for(int i = 0; i < frames.length; i++) {
			frames[i] = new TextureRegion(spriteSheet, i * frameWidth, 0, frameWidth, frameHeight);
		}
		
		this.frameDelay = frameDelay;
	}
	
	public void update(float dt) {
		anim += dt;
	}
	
	public TextureRegion getRegion() {
		int index = ((int)(anim / frameDelay)) % frames.length;
		return frames[index];
	}
	
}
