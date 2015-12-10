package water.water;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sounds {

	private static ArrayList<Sound> sounds = new ArrayList<Sound>(25);
	
	public static void mute() {
		for(int i = 0; i < sounds.size(); i++) {
			sounds.get(i).stop();
		}
	}
	
	public static Sound loadSound(String path) {
		try {
			Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/"+path));
			sounds.add(sound);
			return sound;
		} catch (Exception e) {
			System.err.println("Failed to load sound from: " + path);
		}
		
		return null;
	}
	
}
