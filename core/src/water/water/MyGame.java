package water.water;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGame extends Game {
	
	private SpriteBatch batch;
	
	public void create () {
		setScreen(new GameScreen(this));
		//setScreen(new DeathScreen(this));
		batch = new SpriteBatch();
	}
	
	

	public void render () {
		batch.begin();
		batch.draw(Textures.backdrop, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		
		super.render();
	}
	
	
}
