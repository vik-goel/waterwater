package water.water.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import water.water.MyGame;

public class HtmlLauncher extends GwtApplication {

        public GwtApplicationConfiguration getConfig () {
        	GwtApplicationConfiguration config = new GwtApplicationConfiguration(1280, 720);
        	config.preferFlash = false;
        	return config;
        }

        public ApplicationListener getApplicationListener () {
                return new MyGame();
        }
}