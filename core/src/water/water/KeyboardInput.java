package water.water;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class KeyboardInput extends Input {
	
	public KeyboardInput(GameScreen game) {
		super(game);
	}
	
	public boolean jump() {
		return Gdx.input.isKeyJustPressed(Keys.W) || Gdx.input.isKeyJustPressed(Keys.UP) || Gdx.input.isKeyJustPressed(Keys.SPACE);
	}

	public boolean duck() {
		return Gdx.input.isKeyJustPressed(Keys.S) || Gdx.input.isKeyJustPressed(Keys.DOWN);
	}

}
