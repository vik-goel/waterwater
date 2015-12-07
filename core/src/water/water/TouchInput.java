package water.water;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class TouchInput extends Input {

	private Button jumpButton;
	private boolean jump = false;
	
	public TouchInput(GameScreen game) {
		super(game);
		
		float width = 0.1f * Gdx.graphics.getWidth();
		float height = Util.getHeight(width, Textures.jumpButtonPressed);
		
		jumpButton = new Button(Gdx.graphics.getHeight() * 0.1f, Gdx.graphics.getHeight() * 0.1f, width, height, Textures.jumpButtonRegular, Textures.jumpButtonPressed);
	}

	public boolean jump() {
		return jump;
	}

	public boolean duck() {
		return false;
	}
	
	public void draw(float dt, SpriteBatch batch) {
		jump = jumpButton.draw(dt, batch);
	}

	public Vector2 shoot() {
		if(jumpButton.inside()) {
			return null;
		}
		
		return super.shoot();
	}
}
