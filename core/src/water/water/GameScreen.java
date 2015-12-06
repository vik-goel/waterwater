package water.water;

import java.util.ArrayList;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {

	public ArrayList<Entity> entities = new ArrayList<Entity>(1000);
	public ArrayList<Entity> objects = new ArrayList<Entity>(100);
	public ArrayList<Entity> particles = new ArrayList<Entity>(900);
	public ArrayList<Entity> clouds = new ArrayList<Entity>(100);
	
	public Input input;
	
	public float scrollX = 500;
	public float cameraX = 0;
	
	public ParticleGrid particleGrid;
	public LevelSpawner levelSpawner;
	
	public MyGame myGame;
	
	public GameScreen(MyGame myGame) {
		this.myGame = myGame;
	}
	
	public void show() {
		Entity.game = this;
		
		particleGrid = new ParticleGrid(this);
		
		addObject(new Platform(0, 0, Gdx.graphics.getWidth(), 50, false, Textures.grass));
		//addObject(new Platform(750, 150));
		//addObject(new Platform(1100, 250));
		//addObject(new Platform(1300, 0, 50, 5000));
		
		addObject(new Goose(1000, 50));
		
		addObject(new Player(120, 400));
		
		levelSpawner = new LevelSpawner(this);
		
		if(Gdx.app.getType() == ApplicationType.Desktop || 
		   Gdx.app.getType() == ApplicationType.WebGL ||
		   Gdx.app.getType() == ApplicationType.Applet) {
			input = new KeyboardInput(this);
		} else {
			System.err.println("input not implemented yet");
		}
	}
	
	public void addObject(Entity e) {
		if(e instanceof Cloud) {
			clouds.add(e);
		} else {
			objects.add(e);
		}
		
		
		entities.add(e);
	}
	
	public void addParticle(Entity e) {
		particles.add(e);
		entities.add(e);
	}

	public void render(float delta) {
		final float maxDt = 5;
		float dt = Math.min(maxDt, Gdx.graphics.getDeltaTime());
		
		cameraX += scrollX * dt;
		
		particleGrid.draw(dt);
		
		updateEntityList(clouds, dt);
		updateEntityList(particles, dt);
		updateEntityList(objects, dt);
		
		levelSpawner.spawn(dt);
	}
	
	public void updateEntityList(ArrayList<Entity> entities, float dt) {
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			
			if(e.removed) {
				entities.remove(i--);
				particles.remove(e);
				objects.remove(e);
				clouds.remove(e);
				
				//TODO: Keep a free list of removed entities
			} else {
				e.draw(dt);	
			}
		}
	}

	public void resize(int width, int height) {
		
	}

	public void pause() {
		
	}

	public void resume() {
		
	}

	public void hide() {
		
	}

	public void dispose() {
		
	}
	
	public void die() {
		myGame.setScreen(new DeathScreen(myGame));
	}

}
