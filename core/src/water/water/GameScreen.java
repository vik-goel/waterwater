package water.water;

import java.util.ArrayList;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GameScreen implements Screen {

	public ArrayList<Entity> entities = new ArrayList<Entity>(1000);
	public ArrayList<Entity> objects = new ArrayList<Entity>(100);
	public ArrayList<Entity> particles = new ArrayList<Entity>(900);
	public ArrayList<Entity> clouds = new ArrayList<Entity>(100);
	
	public Input input;
	
	public float dCameraX = 0.4f * Gdx.graphics.getWidth();
	public float cameraX = 0;
	
	public ParticleGrid particleGrid;
	public LevelSpawner levelSpawner;
	
	public MyGame myGame;
	
	public Player player;
	
	public int maxWater = 10000;
	public int water;
	
	public int numGeeseChasing;
	public float score;
	
	public BitmapFont font;
	
	public GameScreen(MyGame myGame) {
		this.myGame = myGame;
		
		if(Gdx.app.getType() == ApplicationType.Desktop || 
		   Gdx.app.getType() == ApplicationType.WebGL ||
		   Gdx.app.getType() == ApplicationType.Applet) {
				input = new KeyboardInput(this);
		} else {
			System.err.println("input not implemented yet");
			input = new KeyboardInput(this);
		}
		
		font = new BitmapFont();
	}
	
	public void show() {
		Entity.game = this;
		
		particleGrid = new ParticleGrid(this);
		
		addObject(player = new Player(Gdx.graphics.getWidth() * 0.33f, 0.5f * Gdx.graphics.getHeight()));
		
		levelSpawner = new LevelSpawner(this);
		
		water = maxWater;
		score = 0;
		numGeeseChasing = 1;
		
//		for(int i = 0; i < 100; i++) {
//			levelSpawner.spawn(0.1f);
//			cameraX += dCameraX * 0.02f;
//		}
//		
//		player.removed = true;
//		dCameraX = 0;
	}
	
	public void addObject(Entity e) {
		if(e instanceof Platform) {
			if(((Platform)e).dieOnHit) {
				e.drawOrder = Entity.DRAWORDER_GROUND_OBSTACLE;
			} else {
				e.drawOrder = Entity.DRAWORDER_GROUND;
			}
		}
		else if(e instanceof Goose) {
			e.drawOrder = Entity.DRAWORDER_GOOSE;
		}
		else if (e instanceof Player) {
			e.drawOrder = Entity.DRAWORDER_PLAYER;
		}
		else if(e instanceof WaterItem) {
			e.drawOrder = Entity.DRAWORDER_WATER_ITEM;
		}
		else if(e instanceof FlyingGoose) {
			e.drawOrder = Entity.DRAWORDER_GOOSE;
		}
		else if(e instanceof Poop) {
			e.drawOrder = Entity.DRAWORDER_POOP;
		}
		
		boolean added = false;
		
		for(int i = 0; i < objects.size(); i++) {
			Entity test = objects.get(i);
			
			if(test.drawOrder >= e.drawOrder) {
				objects.add(i, e);
				added = true;
				break;
			}
		}
		
		if(!added) {
			objects.add(e);
		}
		
		entities.add(e);
	}
	
	public void addCloud(Entity e) {
		clouds.add(e);
		entities.add(e);
	}
	
	public void addParticle(Entity e) {
		particles.add(e);
		entities.add(e);
	}

	public void render(float delta) {
		score += delta * numGeeseChasing;
		
		final float maxDt = 5;
		float dt = Math.min(maxDt, Gdx.graphics.getDeltaTime());
		
		cameraX += dCameraX * dt;
		
		levelSpawner.spawn(dt);
		particleGrid.draw(dt);
		
		updateEntityList(clouds, dt);
		updateEntityList(particles, dt);
		updateEntityList(objects, dt);
		
		Entity.batch.begin();
		font.draw(Entity.batch, "Score: " + (int)(score), 100, 100);
		Entity.batch.end();
	}
	
	public void updateEntityList(ArrayList<Entity> entities, float dt) {
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			
			if(e.removed) {
				entities.remove(i--);
				
				if (e instanceof Water) {
					Pool.water.put(e);
					particles.remove(e);
				}
				else if (e instanceof Cloud) {
					Pool.cloud.put(e);
					clouds.remove(e);
				}
				else {
					objects.remove(e);
					if (e instanceof Goose) {
						Pool.goose.put(e);
					}
					else if (e instanceof Platform) {
						Pool.platform.put(e);
					}
					
					else if (e instanceof WaterItem) {
						Pool.waterItem.put(e);
					}
					else if (e instanceof FlyingGoose) {
						Pool.flyingGoose.put(e);
					}
					else if(e instanceof Poop) {
						Pool.poop.put(e);
					}
				}
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
