package water.water;

import java.util.Random;

import com.badlogic.gdx.Gdx;

public class LevelSpawner {

	private GameScreen game;
	private Random random = new Random();
	
	private float shrubDelay = 0;
	private float spaceDelay = 0;
	private float cloudDelay = 0;
	
	public LevelSpawner(GameScreen game) {
		this.game = game;
	}
	
	public void spawn(float dt) {
		shrubDelay -= dt;
		spaceDelay -= dt;
		cloudDelay -= dt;
		
		float offscreenX = game.cameraX + Gdx.graphics.getWidth();
		
		if(shrubDelay <= 0) {
			float shrubWidth = 30 + 50 * random.nextFloat();
			float shrubHeight = 75 + 50 * random.nextFloat();
			Platform shrub = new Platform(offscreenX, 0, shrubWidth, shrubHeight, true, RandomTexture.groundObstacle.getRegion());
			
			game.addObject(shrub);
			shrubDelay = 1f + random.nextFloat() * 1.5f;
		}
		
		if(spaceDelay <= 0) {
			float groundWidth = 175 + 200 * random.nextFloat();
			float groundHeight = 50;
			
			Platform ground = new Platform(offscreenX, 0, groundWidth, groundHeight, false, Textures.grass);
			game.addObject(ground);
			
			spaceDelay = 0.3f + random.nextFloat() * 0.5f;
		}
		
		if(cloudDelay <= 0) {
			float cloudY = Gdx.graphics.getHeight() * (0.55f + random.nextFloat() * 0.3f);
			game.addObject(new Cloud(offscreenX, cloudY));
			cloudDelay = 0.8f + random.nextFloat() * 0.7f;
		}
	}
	
}
