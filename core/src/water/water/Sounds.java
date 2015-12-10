package water.water;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sounds {

	public static Sound loadSound(String path) {
		try {
			return Gdx.audio.newSound(Gdx.files.internal("sounds/"+path));
		} catch (Exception e) {
			System.err.println("Failed to load sound from: " + path);
		}
		
		return null;
	}
	
}
