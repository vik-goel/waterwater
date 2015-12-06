package water.water;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Button {

	TextureRegion tex;
	float x, y, width, height;
	float scale = 1;
	float minScale = 0.8f;
	boolean down = false;
	
	public Button(float x, float y, float width, float height, TextureRegion tex) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.tex = tex;
	}
	
	public boolean draw(float dt, SpriteBatch batch) {
		boolean result = false;
		
		float tx = Gdx.input.getX();
		float ty = Gdx.input.getY();
		
		
		
		if(Gdx.input.isTouched()) {
			if(tx >= x && tx <= x + width && ty >= y && ty <= y + height) {
				scale = Math.max(minScale, scale - dt * 1.1f);
				down = true;
			} else {
				scale = Math.min(1, scale + dt * 0.3f);
				down = false;
			}
		} else if(down){
			result = true;
			down = false;
		} else {
			down = false;
		}
		

		
		batch.begin();
		batch.draw(tex, x, y, width * 0.5f, height * 0.5f, width, height, scale, scale, 0);
		batch.end();
		
		return result;
	}
	
}
