package water.water;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RandomTexture {

	public static RandomTexture groundObstacle = new RandomTexture(new TextureRegion[]{
		Textures.poop,
		Textures.bricks, 
		Textures.egg
	});
	
	public static RandomTexture clouds = new RandomTexture(new TextureRegion[]{
			Textures.loadTexture("cloud_1.png"),
			Textures.loadTexture("cloud_2.png"),
			Textures.loadTexture("cloud_3.png"),
			Textures.loadTexture("cloud_4.png"),
			Textures.loadTexture("cloud_5.png"),
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
