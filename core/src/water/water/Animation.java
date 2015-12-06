package water.water;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {

	private static final float playerRunFrameDelay = 0.075f;
	public static Animation playerRunNormal = new Animation("player-run-normal.png", 32, 32,playerRunFrameDelay);
	public static Animation playerRunShoot = new Animation("player-run-shoot.png", 32, 32, playerRunFrameDelay);
	public static Animation waterItem = new Animation("platform.png", 16, 32, 0.1f);
	public static Animation flyingGoose = new Animation("flying_goose.png", 90, 32, 0.1f);
	public static Animation runningGoose = new Animation("goose_run.png", 90, 32, 0.05f);
	public static Animation idleGoose = new Animation("goose_idle.png", 90, 32, 0.05f);
	
	private TextureRegion[] frames;
	public float anim = 0;
	private float frameDelay;
	
	public Animation(String spriteSheetPath, int frameWidth, int frameHeight, float frameDelay) {
		try {
			TextureRegion spriteSheet = Textures.loadTexture(spriteSheetPath);
			frames = new TextureRegion[spriteSheet.getRegionWidth() / frameWidth];
			
			for(int i = 0; i < frames.length; i++) {
				frames[i] = new TextureRegion(spriteSheet, i * frameWidth, 0, frameWidth, frameHeight);
			}
			
			this.frameDelay = frameDelay;
		} catch (Exception e) {
			System.err.println("Failed to load animation from: " + spriteSheetPath);
		}
	}
	
	public Animation(Animation other) {
		this.frames = other.frames;
		this.frameDelay = other.frameDelay;
	}
	
	public void update(float dt) {
		anim += dt;
	}
	
	public TextureRegion getRegion() {
		int index = ((int)(anim / frameDelay));
		return frames[index % frames.length];
	}
	
}
