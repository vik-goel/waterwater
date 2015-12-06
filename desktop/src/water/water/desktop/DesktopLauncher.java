package water.water.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import water.water.MyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.backgroundFPS = -1;
		config.foregroundFPS = 60;
		config.resizable = false;
		config.title = "Water Water Water";
		config.width = 1280;
		config.height = 720;
		
		new LwjglApplication(new MyGame(), config);
	}
}
