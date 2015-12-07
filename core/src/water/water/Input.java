package water.water;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Input {

	private Vector2 shootVec = new Vector2();
	private GameScreen game;
	
	public Input(GameScreen game) {
		this.game = game;
	}
	
	public abstract boolean jump();
	public abstract boolean duck();
	
	//Null if shouldn't shoot, otherwise returns the coordinates of where to aim
	public Vector2 shoot() {
		if(Gdx.input.isTouched()) {
			shootVec.x = Gdx.input.getX() + game.cameraX;
			shootVec.y = Gdx.graphics.getHeight() - Gdx.input.getY();
			
			return shootVec;
		} else {
			return null;
		}
	}
	
	public void draw(float dt, SpriteBatch batch) {
		
	}
	
}
