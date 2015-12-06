package water.water;

import java.util.Random;

import com.badlogic.gdx.Gdx;

public class LevelSpawner {

	private MyGame game;
	private Random random = new Random();
	
	private float shrubDelay = 0;
	
	public LevelSpawner(MyGame game) {
		this.game = game;
	}
	
	public void spawn(float dt) {
		shrubDelay -= dt;
		
		if(shrubDelay <= 0) {
			float shrubWidth = 30 + 50 * random.nextFloat();
			float shrubHeight = 100 + 75 * random.nextFloat();
			Platform shrub = new Platform(game.cameraX + Gdx.graphics.getWidth() + shrubWidth * 0.5f, shrubHeight * 0.5f, shrubWidth, shrubHeight);
			
			game.addObject(shrub);
			shrubDelay = 1f + random.nextFloat() * 1.5f;
		}
	}
	
}
