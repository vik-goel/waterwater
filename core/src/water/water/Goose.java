package water.water;

import com.badlogic.gdx.Gdx;

public class Goose extends Entity {

	enum State {
		CHASING,
		FLYING,
		IDLE,
		DEAD
	}
	
	private State state;
	
	Animation idleAnim, chaseAnim, deadAnim;
	
	public Goose() {
		idleAnim = new Animation(Animation.idleGoose);
		chaseAnim = new Animation(Animation.runningGoose);
		deadAnim = new Animation(Animation.deathGoose);
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
		
		return this;
	}
	
	public void draw(float dt) {
		super.draw(dt);
		
		switch(state) {
		case CHASING:
			chasingUpdate(dt);
			break;
		case FLYING:
			flyingUpdate(dt);
			break;
		case IDLE:
			idleUpdate(dt);
			break;
		}
	}
	
	private void chasingUpdate(float dt) {
		flipX = false;
		x += game.dCameraX * dt;
	}
	
	private void flyingUpdate(float dt) {
		flipX = true;
		x += game.dCameraX * dt;
		
		float leftOffset = Gdx.graphics.getWidth() * 0.05f + (game.player.drawWidth + drawWidth) * 0.5f;
		
		if (x > game.player.x - leftOffset) {
			x -= dt * 700;
		} else {
			state = State.CHASING;
			
			animation = chaseAnim;
			game.numGeeseChasing++;
		}
	}
	
	private void idleUpdate(float dt) {
		offscreenRemove();
		
		if(isHitByWater()) {
			state = State.FLYING;
		}
	}
	
	public boolean playerHit() {
		if(state == State.IDLE) {
			game.die();
			state = State.DEAD;
			animation = deadAnim;
		}
		
		return true;
	}
	
	public boolean collidesWith(Entity other) {
		return (state != State.DEAD) && (!(other instanceof Player) || (other instanceof Player && state == State.IDLE));
	}

}
