package water.water;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Textures {

	public static TextureRegion playerJumpNormal = loadTexture("player-jump-normal.png");
	public static TextureRegion playerJumpShoot = loadTexture("player-jump-shoot.png");
	public static TextureRegion water = loadTexture("water.png");
	public static TextureRegion goose = loadTexture("platform.png");
	public static TextureRegion grass = loadTexture("grass.png");
	public static TextureRegion backdrop = loadTexture("backdrop.png");
	public static TextureRegion playAgainButton = loadTexture("platform.png");
	
	public static TextureRegion poop = loadTexture("poop.png");
	public static TextureRegion poop2 = loadTexture("poop2.png");
	public static TextureRegion egg = loadTexture("egg.png");
	public static TextureRegion egg2 = loadTexture("eggs-2.png");
	
	public static TextureRegion waterEmpty = loadTexture("water-empty.png");
	public static TextureRegion waterFull = loadTexture("water-full.png");
	public static TextureRegion waterBar = loadTexture("water-bar.png");
	
	public static TextureRegion heart = loadTexture("heart.png");
	
	public static TextureRegion jumpButtonRegular = loadTexture("jump-button-regular.png");
	public static TextureRegion jumpButtonPressed = loadTexture("jump-button-pressed.png");
	
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
