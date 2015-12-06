package water.water;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Textures {

	public static TextureRegion platform = loadTexture("platform.png");
	public static TextureRegion player = platform;
	public static TextureRegion water = loadTexture("water.png");
	
	//debug
	public static TextureRegion arrow = loadTexture("arrow.png");
	
	private static TextureRegion loadTexture(String path) {
		TextureRegion result = null;
		
		Texture texture = new Texture(path);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		result = new TextureRegion(texture);
		
		return result;
	}
	
}
