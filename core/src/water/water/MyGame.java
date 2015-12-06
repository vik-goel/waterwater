package water.water;

import java.util.ArrayList;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class MyGame extends ApplicationAdapter {
	
	public ArrayList<Entity> entities = new ArrayList<Entity>(1000);
	public ArrayList<Entity> objects = new ArrayList<Entity>(100);
	public ArrayList<Entity> particles = new ArrayList<Entity>(900);
	
	public Input input;
	
	public float scrollX = 500;
	public float cameraX = 0;
	
	public ParticleGrid particleGrid;
	public LevelSpawner levelSpawner;
	
	public void create () {
		Entity.game = this;
		
		particleGrid = new ParticleGrid(this);
		
		addObject(new Platform(5000, 25, 10000, 50));
		//addObject(new Platform(750, 150));
		//addObject(new Platform(1100, 250));
		//addObject(new Platform(1300, 0, 50, 5000));
		
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
		objects.add(e);
		entities.add(e);
	}
	
	public void addParticle(Entity e) {
		particles.add(e);
		entities.add(e);
	}

	public void render () {
		Gdx.gl.glClearColor(0.5f, 0.9f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		final float maxDt = 5;
		float dt = Math.min(maxDt, Gdx.graphics.getDeltaTime());
		
		cameraX += scrollX * dt;
		
		particleGrid.draw(dt);
		
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
				
				//TODO: Keep a free list of removed entities
			} else {
				e.draw(dt);	
			}
		}
	}
}
