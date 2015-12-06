package water.water;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Textures {

	public static TextureRegion platform = loadTexture("platform.png");
	public static TextureRegion playerJumpNormal = loadTexture("player-jump-normal.png");
	public static TextureRegion playerJumpShoot = loadTexture("player-jump-shoot.png");
	public static TextureRegion water = loadTexture("water.png");
	public static TextureRegion goose = loadTexture("platform.png");
	public static TextureRegion grass = loadTexture("grass.png");
	public static TextureRegion backdrop = loadTexture("backdrop.png");
	public static TextureRegion playAgainButton = loadTexture("platform.png");
	
	public static TextureRegion poopDrop = loadTexture("flying-poop.png");
	public static TextureRegion waterItem = loadTexture("power-up.png");
	
	public static TextureRegion poop = loadTexture("poop.png");
	public static TextureRegion egg = loadTexture("egg.png");
	public static TextureRegion egg2 = loadTexture("eggs-2.png");
	public static TextureRegion bricks = loadTexture("bricks.png");
	
	public static TextureRegion waterEmpty = loadTexture("water-empty.png");
	public static TextureRegion waterFull = loadTexture("water-full.png");
	public static TextureRegion waterBar = loadTexture("water-bar.png");
	
	//debug
	public static TextureRegion arrow = loadTexture("arrow.png");
	
	public static TextureRegion loadTexture(String path) {
		TextureRegion result = null;
		
		try {
			Texture texture = new Texture(path);
			texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
			texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
			
			result = new TextureRegion(texture);
		} catch (Exception e) {
			System.err.println("Failed to load texture from: " + path);
		}
		
		return result;
	}
	
}
