package water.water;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DeathScreen implements Screen {

	BitmapFont font = new BitmapFont();
	SpriteBatch batch = new SpriteBatch();
	
	MyGame myGame;
	
	Button playAgain;
	
	public DeathScreen(MyGame myGame) {
		this.myGame = myGame;
		
		float width = Gdx.graphics.getWidth() * 0.5f;
		float height = Gdx.graphics.getHeight() * 0.1f;
		playAgain = new Button((Gdx.graphics.getWidth() - width) * 0.5f, (Gdx.graphics.getHeight() - height) * 0.5f, width, height, Textures.playAgainButton);
	}
	
	public void show() {
		
	}

	public void render(float delta) {
		batch.begin();
		font.draw(batch, "Press space to restart", 200, 200);
		batch.end();
		
		if(playAgain.draw(delta, batch)) {
			myGame.setScreen(new GameScreen(myGame));
		}
		
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
