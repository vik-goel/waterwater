package water.water;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RandomTexture {

	public static RandomTexture groundObstacle = new RandomTexture(new TextureRegion[] {
		Textures.poop,
		Textures.poop2,
		Textures.egg,
		Textures.egg2,
	});
	
	public static RandomTexture clouds = new RandomTexture(new TextureRegion[]{
			Textures.loadTexture("cloud1.png"),
			Textures.loadTexture("cloud2.png"),
			Textures.loadTexture("cloud3.png"),
		});
	
	private static Random random = new Random();
	
	private TextureRegion[] regions;
	
	public RandomTexture(TextureRegion[] regions) {
		this.regions = regions;
	}
	
	public TextureRegion getRegion() {
		return regions[random.nextInt(regions.length)];
	}
	
}
