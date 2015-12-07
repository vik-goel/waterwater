package water.water;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Button {

	TextureRegion regular, pressed;
	float x, y, width, height;
//	float scale = 1;
//	float minScale = 0.8f;
	boolean down = false;
	
	public Button(float x, float y, float width, float height, TextureRegion regular, TextureRegion pressed) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.regular = regular;
		this.pressed = pressed;
	}
	
	public boolean draw(float dt, SpriteBatch batch) {
		boolean result = false;
		
		if(Gdx.input.isTouched()) {
			if(inside()) {
				//scale = Math.max(minScale, scale - dt * 1.1f);
				down = true;
			} else {
				//scale = Math.min(1, scale + dt * 0.3f);
				down = false;
			}
		} else if(down){
			result = true;
			down = false;
		} else {
			down = false;
		}
		
		batch.begin();
		
		TextureRegion tex = down ? pressed : regular;
		batch.draw(tex, x, y, width * 0.5f, height * 0.5f, width, height, 1, 1, 0);
		batch.end();
		
		return result;
	}
	
	boolean inside() {
		float tx = Gdx.input.getX();
		float ty = Gdx.graphics.getHeight() - Gdx.input.getY();
		return tx >= x && tx <= x + width && ty >= y && ty <= y + height;
	}
}
