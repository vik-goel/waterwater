package water.water;

import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {

	public Player(float x, float y) {
		super(x, y, 100, 250, Textures.player);
	}
	
	public void draw(float dt) {
		super.draw(dt);
		
		if(game.input.jump() && onGround()) {
			dy += 550;
		}

		move(game.scrollX * dt, stepGravity(dt));
		
		Vector2 shootTarget = game.input.shoot();
		
		if(shootTarget != null) {
			shoot(x, y, shootTarget.x, shootTarget.y);
		}
	}
	
	public void shoot(float startX, float startY, float targetX, float targetY) {
		float deltaX = targetX - startX;
		float deltaY = targetY - startY;
		
		if(deltaX < 0) {
			dx *= -1;
		}
		float theta = (float) Math.atan2(deltaY, deltaX);
		
		
		float v = 750;
		float dx = (float) (v*Math.cos(theta));
		float dy = (float) (v*Math.sin(theta));
		
		float spawnRadius = 5;
		
		for(int i = 0; i < 30; i++) {
			float spawnAngle = (float)(random.nextFloat() * Math.PI * 2);
			float spawnChangeX = (float)(Math.cos(spawnAngle) * spawnRadius);
			float spawnChangeY = (float)(Math.sin(spawnAngle) * spawnRadius);
			
			game.addParticle(new Water(startX + spawnChangeX, startY + spawnChangeY, dx, dy));
		}
	}
	
	public boolean onGround() {
		return checkCollisions(0, -0.1f) != null;
	}
	
	public boolean move(float mx, float my) {
		if(mx != 0 && my != 0) {
			boolean moved = move(0, my);
			return move(mx, 0) && moved;
		}
		
		final float maxStepX = 0.4f;
		final float maxStepY = 0.4f;
		
		float xInc = mx > 0 ? maxStepX : -maxStepX;
		float yInc = my > 0 ? maxStepY : -maxStepY;
		
		while(Math.abs(mx) > maxStepX) {
			if(!move(xInc, 0)) {
				return false;
			}
			mx -= xInc;
		}
		
		while(Math.abs(my) > maxStepY) {
			if(!move(0, yInc)) {
				return false;
			}
			my -= yInc;
		}
		
		Entity collision = checkCollisions(mx, my);
		
		if(collision == null) {
			x += mx;
			y += my;
			return true;
		} else {
			if(mx != 0) {
				dx = 0;
			}
			else if(my != 0) {
				dy = 0;
			}
			
			return false;
		}
	}

}
