package water.water;

import java.util.ArrayList;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GameScreen implements Screen {

	public ArrayList<Entity> entities = new ArrayList<Entity>(1000);
	public ArrayList<Entity> objects = new ArrayList<Entity>(100);
	public ArrayList<Entity> particles = new ArrayList<Entity>(900);
	public ArrayList<Entity> clouds = new ArrayList<Entity>(100);
	
	public Input input;
	
	public float dCameraX;
	public float cameraX;
	
	public ParticleGrid particleGrid;
	public LevelSpawner levelSpawner;
	
	public MyGame myGame;
	
	public Player player;
	
	public int maxWater = 6000;
	public int water;
	public int life;
	
	public int numGeeseChasing;
	public float score;
	
	public BitmapFont font;
	private DeathScreen deathScreen;
	
	public GameScreen(MyGame myGame) {
		this.myGame = myGame;
		
		if(Gdx.app.getType() == ApplicationType.Desktop || 
		   Gdx.app.getType() == ApplicationType.WebGL ||
		   Gdx.app.getType() == ApplicationType.Applet) {
				input = new KeyboardInput(this);
		} else {
			input = new TouchInput(this);
		}
		
		font = new BitmapFont();
		
		particleGrid = new ParticleGrid(this);
		levelSpawner = new LevelSpawner(this);
	}
	
	public void show() {
		Entity.game = this;
		
		for(int i = 0; i < entities.size(); i++) {
			Pool.put(entities.get(i));
		}
		
		entities.clear();
		objects.clear();
		particles.clear();
		clouds.clear();
		
		particleGrid.reset();
		levelSpawner.reset();
		
		addObject(player = Pool.get(Player.class).init(Gdx.graphics.getWidth() * 0.33f, 0.5f * Gdx.graphics.getHeight()));
		
		water = maxWater;
		score = 0;
		numGeeseChasing = 1;
		
		cameraX = 0;
		dCameraX = 0.4f * Gdx.graphics.getWidth();
		
		life = 3;
		
//		for(int i = 0; i < 100; i++) {
//			levelSpawner.spawn(0.1f);
//			cameraX += dCameraX * 0.02f;
//		}
//		
//		player.removed = true;
//		dCameraX = 0;
	}
	
	public void addObject(Entity e) {
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
		
		drawGUIBar(dt);
		input.draw(dt, Entity.batch);
	}
	
	public void drawGUIBar(float dt) {
		ShapeRenderer sr = Entity.sr;
		SpriteBatch batch = Entity.batch;
		
		float barHeight = Gdx.graphics.getHeight() * 0.15f;
		
		Util.enableBlend();
		
		float minY = Gdx.graphics.getHeight() - barHeight;
		
		sr.begin(ShapeType.Filled);
		sr.setColor(0, 0, 0, 0.5f);
		sr.rect(0, minY, Gdx.graphics.getWidth(), barHeight);
		sr.end();
		
		batch.begin();
		font.draw(batch, "Score: " + (int)(score), Gdx.graphics.getWidth() * 0.2f, minY + 50);
		
		float barHeightSpacing = Gdx.graphics.getWidth() * 0.01f;
		float dropletHeight = barHeight - 4 * barHeightSpacing;
		float dropletY = minY + 2 * barHeightSpacing;
		
		{ //draws the water bar
			float barX = Gdx.graphics.getWidth() * 0.3f;
			float barWidth = Gdx.graphics.getWidth() * 0.4f;
			batch.draw(Textures.waterBar, barX, minY + barHeightSpacing, barWidth, barHeight - barHeightSpacing * 2);
			batch.end();
			
			int numDroplets = 10;
			float dropletWidth = barWidth * 0.07f;
			float dropletSpacing = (barWidth - numDroplets * dropletWidth) / (numDroplets + 1);
			
			float dropletX = barX + dropletSpacing;
			
			Gdx.gl20.glEnable(GL20.GL_SCISSOR_TEST);
			
			int waterPerDroplet = maxWater / numDroplets;
			int waterDraw = water;
			
			for(int i = 0; i < numDroplets; i++) {
				Gdx.gl20.glScissor((int)dropletX, (int)dropletY, (int)dropletWidth, (int)dropletHeight);
				batch.begin();
				batch.draw(Textures.waterEmpty, dropletX, dropletY, dropletWidth, dropletHeight);
				batch.end();
				
				float fullPercentage = Math.min(1, (float)waterDraw / (float)waterPerDroplet);
				
				Gdx.gl20.glScissor((int)dropletX, (int)dropletY, (int)dropletWidth, (int)(dropletHeight*fullPercentage));
				batch.begin();
				batch.draw(Textures.waterFull, dropletX, dropletY, dropletWidth, dropletHeight);
				batch.end();
				
				dropletX += dropletWidth + dropletSpacing;
				
				waterDraw -= waterPerDroplet;
				if(waterDraw < 0) {
					waterDraw = 0;
				}
			}
			
			Gdx.gl20.glDisable(GL20.GL_SCISSOR_TEST); //TODO: Scissor to window?
		}
		
		{ //draws hearts
			float minX = Gdx.graphics.getWidth() * 0.8f;
			float heartHeight = dropletHeight;
			float heartWidth = Util.getWidth(heartHeight, Textures.heart);
			float heartSpacing = heartWidth * 0.1f;
			
			batch.begin();
			for(int i = 0; i < life; i++) {
				batch.draw(Textures.heart, minX, dropletY, heartWidth, heartHeight);
				minX += heartSpacing + heartWidth;
			}
			batch.end();
		}
		
	}
	
	public void updateEntityList(ArrayList<Entity> entities, float dt) {
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			
			if(e.removed) {
				entities.remove(i--);
				Pool.put(e);
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
		life--;
		
		if(life <= 0) {
			dieImmediate();
		}
	}
	
	public void dieImmediate() {
		if(deathScreen == null) {
			deathScreen = new DeathScreen(myGame);
		}
		
		myGame.setScreen(deathScreen);
	}

}
