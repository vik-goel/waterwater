package water.water;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LevelSpawner {

	private GameScreen game;
	private Random random = new Random();
	
	private float obstacleDelay;
	private float cloudDelay;
	private float flyingGooseDelay;
	
	private Platform ground;
	private float groundHeight;
	
	public LevelSpawner(GameScreen game) {
		this.game = game;
		groundHeight = 0.07f * Gdx.graphics.getHeight();
		
	}
	
	public void reset() {
		makeGround(0, Gdx.graphics.getWidth());
		obstacleDelay = 0.2f;
		cloudDelay = 0;
		flyingGooseDelay = 0.5f;
	}
	
	private void makeGround(float x, float width) {
		ground = Pool.get(Platform.class).init(x, 0, width, groundHeight, false, Textures.grass, -0.25f, 0.5f, 0, 1);
		game.addObject(ground);
	}
	
	public void spawn(float dt) {
		obstacleDelay -= dt;
		cloudDelay -= dt;
		flyingGooseDelay -= dt;
		
		float offscreenX = game.cameraX + Gdx.graphics.getWidth();
		
		ground.drawWidth += game.dCameraX * dt;
		ground.x += game.dCameraX * dt * 0.5f;
		
		ground.collideWidth = ground.drawWidth;
		
		if(obstacleDelay <= 0) {
			switch(random.nextInt(4)) {
			case 0: //ground obstacle
				TextureRegion tex = RandomItem.groundObstacle.get();
				
				float collideYC = 0, collideHeightC = 1, collideXC = 0, collideWidthC = 1, y = 0.2f;
				
				float shrubHeight = 50;
				
				if(tex == Textures.poop) {
					shrubHeight = 0.14f * Gdx.graphics.getHeight();
					collideHeightC = 0.75f;
					y = 0.01f;
				}
				else if(tex == Textures.poop2) {
					shrubHeight = 0.69f * Gdx.graphics.getHeight();
					y = -0.295f;
					collideHeightC = 0.06f;
					collideWidthC = 0.2f;
					collideXC = 0.1f;
					collideYC = 0.08f;
				}
				else if(tex == Textures.egg) {
					y = -0.015f;
					collideHeightC = 0.5f;
					collideWidthC *= 0.7f;
					shrubHeight = 0.17f * Gdx.graphics.getHeight();
				}
				else if(tex == Textures.egg2) {
					shrubHeight = 0.25f * Gdx.graphics.getHeight();
					y = -0.075f;
					collideWidthC = 0.48f;
					collideHeightC = 0.3f;
					collideYC = 0.05f;
					collideXC = -0.05f;
				}
				
				float shrubWidth = shrubHeight * ((float)tex.getRegionWidth() / (float)tex.getRegionHeight());
				
				game.addObject(Pool.get(Platform.class).init(offscreenX, y * Gdx.graphics.getHeight(), shrubWidth, shrubHeight, true, tex, collideYC, collideHeightC, collideXC, collideWidthC));
				break;
			case 1: //gap
				makeGround(offscreenX + Gdx.graphics.getWidth() * (0.15f + random.nextFloat() * 0.175f), 0);
				break;
			case 2: //goose
				game.addObject(Pool.get(Goose.class).init(offscreenX, -groundHeight * 0.5f));
				break;
			case 3: //water item
				game.addObject(Pool.get(WaterItem.class).init(offscreenX, 0.28f * Gdx.graphics.getHeight()));
				break;
			}
			
			obstacleDelay = 0.75f + random.nextFloat() * 1f;
		}
		
		if(cloudDelay <= 0) {
			float cloudY = Gdx.graphics.getHeight() * (0.7f + random.nextFloat() * 0.25f);
			game.addCloud(Pool.get(Cloud.class).init(offscreenX, cloudY));
			cloudDelay = 1.1f + random.nextFloat() * 0.7f;
		}
		
		if(flyingGooseDelay <= 0) {
			float gooseY = Gdx.graphics.getHeight() * (0.7f + random.nextFloat() * 0.15f);
			game.addObject(Pool.get(FlyingGoose.class).init(offscreenX, gooseY));
			
			flyingGooseDelay = random.nextFloat() * 0.9f + 0.5f;
		}
	}
	
}
