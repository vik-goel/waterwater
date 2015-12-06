package water.water;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DeathScreen implements Screen {

	BitmapFont font = new BitmapFont();
	SpriteBatch batch = new SpriteBatch();
	
	MyGame myGame;
	
	public DeathScreen(MyGame myGame) {
		this.myGame = myGame;
	}
	
	public void show() {
		
	}

	public void render(float delta) {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		font.draw(batch, "Press space to restart", 200, 200);
		batch.end();
		
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			myGame.setScreen(new GameScreen(myGame));
		}
	}

	public void resize(int width, int height) {
		
	}

	public void pause() {
		
	}

	public void resume() {
		
	}

	public void hide() {
		
	}

	public void dispose() {
		
	}

}
