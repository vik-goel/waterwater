package water.water;

import java.util.Random;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RandomItem<T> {

	public static RandomItem<TextureRegion> groundObstacle = new RandomItem<TextureRegion>(new TextureRegion[] {
		Textures.poop,
		Textures.poop2,
		Textures.egg,
		Textures.egg2,
	});
		
	public static RandomItem<TextureRegion> clouds = new RandomItem<TextureRegion>(new TextureRegion[]{
		Textures.loadTexture("cloud1.png"),
		Textures.loadTexture("cloud2.png"),
		Textures.loadTexture("cloud3.png"),
	});
	
	public static RandomItem<Animation> flyingPoop = new RandomItem<Animation>(new Animation[] {
		new Animation("flying-poop-1.png", 200, 200, 0.75f),
		new Animation("flying-poop-2.png", 200, 200, 0.75f),
		new Animation("flying-poop-3.png", 200, 200, 0.75f),
	});
	
	public static RandomItem<Sound> geeseSounds = new RandomItem<Sound>(new Sound[] {
		Sounds.loadSound("geese1.wav"),	
		Sounds.loadSound("geese2.wav"),	
	});
	
	public static RandomItem<Sound> hitSounds = new RandomItem<Sound>(new Sound[] {
		Sounds.loadSound("grunt.wav"),	
	});
	
	public static RandomItem<Sound> powerupSounds = new RandomItem<Sound>(new Sound[] {
		Sounds.loadSound("powerup.wav"),	
	});
	
	private static Random random = new Random();
	
	private T[] elems;
	
	public RandomItem(T[] elems) {
		this.elems = elems;
	}
	
	public T get() {
		return elems[random.nextInt(elems.length)];
	}
	
}
