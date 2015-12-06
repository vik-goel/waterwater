package water.water;

import com.badlogic.gdx.Gdx;

public class Goose extends Entity {

	enum State {
		CHASING,
		FLYING,
		IDLE
	}
	
	private State state;
	
	public Goose init(float x, float y) {
		float width = Gdx.graphics.getWidth() * 0.3f;
		float height = Util.getHeight(width, Animation.runningGoose.getRegion());
		
		init(x + width * 0.5f, y + height * 0.5f, width, height, Animation.runningGoose);
		
		collideX = drawWidth * 0.02f;
		collideWidth *= 0.27f;
		collideHeight *= 0.5f;
		
		state = State.IDLE;
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
		x += game.dCameraX * dt;
	}
	
	private void flyingUpdate(float dt) {
		x += game.dCameraX * dt;
		
		float leftOffset = Gdx.graphics.getWidth() * 0.05f + (game.player.drawWidth + drawWidth) * 0.5f;
		
		if (x > game.player.x - leftOffset) {
			x -= dt * 700;
		} else {
			state = State.CHASING;
			game.numGeeseChasing++;
		}
	}
	
	private void idleUpdate(float dt) {
		offscreenRemove();
		
		if(isHitByWater()) {
			state = State.FLYING;
		}
	}
	
	public boolean collidesWith(Entity other) {
		return !(other instanceof Player) || (other instanceof Player && state == State.IDLE);
	}

}
