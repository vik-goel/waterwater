package water.water;

import com.badlogic.gdx.Game;

public class MyGame extends Game {
	
	public void create () {
		setScreen(new GameScreen(this));
	}
	
	

	public void render () {
		super.render();
	}
	
	
}
