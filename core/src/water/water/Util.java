package water.water;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Util {

	public static final float lengthSq(float x, float y) {
		return x * x + y * y;
	}
	
	public static final float length(float x, float y) {
		return (float)Math.sqrt(lengthSq(x, y));
	}

	public static void enableBlend() {
		Gdx.gl20.glEnable(GL20.GL_BLEND);
		Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}
	
}

