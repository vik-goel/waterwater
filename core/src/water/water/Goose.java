package water.water;

import com.badlogic.gdx.Gdx;

public class Goose extends Entity {

	//Goose Gunner
	
	enum State {
		IDLE,
		FLYING,
		CATCHING_UP,
		CHASING,
		DEAD
	}
	
	private State state;
	
	Animation idleAnim, chaseAnim, deadAnim, flyAnim;
	float startY;
	float playerStager;
	
	public Goose() {
		idleAnim = new Animation(Animation.idleGoose);
		chaseAnim = new Animation(Animation.runningGoose);
		deadAnim = new Animation(Animation.deathGoose);
		flyAnim = new Animation(Animation.flyingGoose);
	}
	
	public Goose init(float x, float y) {
		float width = Gdx.graphics.getWidth() * 0.3f;
		float height = Util.getHeight(width, Animation.runningGoose.getRegion());
		
		init(x + width * 0.5f, y + height * 0.5f, width, height, idleAnim);
		
		collideX = drawWidth * 0.02f;
		collideWidth *= 0.27f;
		collideHeight *= 0.5f;
		
		state = State.IDLE;
		flipX = random.nextBoolean();
		
		startY = this.y;
		playerStager = random.nextFloat() * Gdx.graphics.getWidth() * 0.2f;
		
		return this;
	}
	
	public void draw(float dt) {
		super.draw(dt);
		
		switch(state) {
		case IDLE:
			idleUpdate(dt);
			break;
		case FLYING:
			flyingUpdate(dt);
			break;
		case CATCHING_UP:
			catchingUpdate(dt);
			break;
		case CHASING:
			chasingUpdate(dt);
			break;
		}
	}
	
	private void idleUpdate(float dt) {
		animation = idleAnim;
		
		offscreenRemove();
		
		if(isHitByWater()) {
			state = State.FLYING;
			RandomItem.geeseSounds.get().play();
		}
	}
	
	private void flyingUpdate(float dt) {
		animation = flyAnim;
		flipX = true;

		x += game.dCameraX * dt;
		x -= Gdx.graphics.getWidth() * 0.65f * dt;
		
		if(x + drawWidth * 0.5f < game.cameraX) {
			state = State.CATCHING_UP;
		}
		else if(y < Gdx.graphics.getHeight() * 0.4f) {
			y += dt * Gdx.graphics.getHeight() * 0.5f;
		}
	}
	
	private void catchingUpdate(float dt) {
		chasingUpdate(dt * 1.4f);
		
		if(Math.abs(x - game.player.x) < Gdx.graphics.getWidth() * 0.1f + playerStager) {
			state = State.CHASING;
		}
	}
	
	//TODO: Make geese jump over gaps and obstacles
	private void chasingUpdate(float dt) {
		if(onGround()) {
			animation = chaseAnim;
		} else {
			animation = flyAnim;
		}
		
		flipX = false;
		x += game.dCameraX * dt;
		
		if(y > startY) {
			y -= dt * Gdx.graphics.getHeight() * 0.6f;
		}
		
		if(y < startY) {
			y = startY;
		}
	}
	
	public boolean playerHit() {
		if(state == State.IDLE) {
			game.die();
			state = State.DEAD;
			animation = deadAnim;
			animation.anim = 0;
		}
		
		return true;
	}
	
	public boolean collidesWith(Entity other) {
		return (state != State.DEAD) && (!(other instanceof Player) || (other instanceof Player && state == State.IDLE));
	}

}
