package water.water;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {

	public float poopTime;
	
	public Player(float x, float y) {
		super(x, y, Gdx.graphics.getHeight() * 0.25f, Gdx.graphics.getHeight() * 0.25f, Animation.playerRunNormal);
		
		collideX = collideHeight * -0.1f;
		collideY = collideHeight * -0.1f;
		
		collideWidth *= 0.25f;
		collideHeight *= 0.7f;
		
		poopTime = 0;
	}
	
	public float stepGravity(float dt) {
		float gravity = dy > 0 ? GRAVITY : 2f * GRAVITY;
		
		float my = dy * dt - 0.5f * gravity * dt * dt;
		dy -= gravity * dt;
		return my;
	}
	
	public void draw(float dt) {
		poopTime = Math.max(0, poopTime - dt);
		
		dx *= Math.pow(Math.E, -2 * dt);
		dy *= Math.pow(Math.E, -1.2 * dt);
		
		if(animation == Animation.playerRunNormal) {
			Animation.playerRunShoot.anim = Animation.playerRunNormal.anim;
		}
		else if(animation == Animation.playerRunShoot) {
			Animation.playerRunNormal.anim = Animation.playerRunShoot.anim;
		}
		
		boolean onGround = onGround();
		
		Vector2 shootTarget = game.input.shoot();
		boolean shooting = shootTarget != null;
		
		if(game.input.jump() && onGround()) {
			dy += 0.9f * Gdx.graphics.getHeight();
		}

		move((dx + game.dCameraX) * dt, stepGravity(dt));
		
		if(shooting) {
			shoot(x + drawWidth * 0.2f, y + drawHeight * 0.1f, shootTarget.x, shootTarget.y);
		}
		
		if (y + drawHeight * 0.5f < 0) {
			game.die();
		}
		
		if(onGround) {
			if(poopTime > 0) {
				animation = Animation.playerRunPoop;
			}
			else if (shooting) {
				animation = Animation.playerRunShoot;
			} else {
				animation = Animation.playerRunNormal;
			}
		} else {
			animation = null;
			
			if(poopTime > 0) {
				tex = Textures.playerJumpPoop;
			}
			else if(shooting) {
				tex = Textures.playerJumpShoot;
			} else {
				tex = Textures.playerJumpShoot;
			}
		}
		
		if(x + drawWidth * 0.5f < game.cameraX) {
			game.dieImmediate();
		}
		if(x - drawWidth * 0.5f > game.cameraX + Gdx.graphics.getWidth()) {
			x = game.cameraX + Gdx.graphics.getWidth() + drawWidth * 0.5f;
		}
		if(y + collideY + collideHeight * 0.5f > Gdx.graphics.getHeight()) {
			y = Gdx.graphics.getHeight() - collideY - collideHeight * 0.5f;
			dy = 0;
		}
		
		super.draw(dt);
	}
	
	public void shoot(float startX, float startY, float targetX, float targetY) {
		if(poopTime > 0) {
			return;
		}
		
		float deltaX = targetX - startX;
		float deltaY = targetY - startY;
		
		float theta = (float) Math.atan2(deltaY, deltaX);
		
		float v = Gdx.graphics.getHeight() * 1.06f;
		float dx = (float) (v * Math.cos(theta)) + game.dCameraX;
		float dy = (float) (v * Math.sin(theta));
		
		float spawnRadius = 5;
	
		//TODO: The maximum particle spawn limit should be a function of dt
		int particlesToSpawn = Math.min(30, game.water);
		
		for(int i = 0; i < particlesToSpawn; i++) {
			float spawnAngle = (float)(random.nextFloat() * Math.PI * 2);
			float spawnChangeX = (float)(Math.cos(spawnAngle) * spawnRadius);
			float spawnChangeY = (float)(Math.sin(spawnAngle) * spawnRadius);
			
			game.addParticle(Pool.water.get().init(startX + spawnChangeX, startY + spawnChangeY, dx, dy));
		}
		
		game.water -= particlesToSpawn;
		
//		float totalDx = particlesToSpawn * dx;
		float totalDy = particlesToSpawn * dy;
		
//		if(totalDx > 0) {
//			this.dx -= 0.00008f * totalDx;
//		}
//		else {
//			this.dx -= 0.0008f * totalDx;
//		}
		
		this.dy -= 0.002f * totalDy;
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
			if(!collision.playerHit()) {
				if(mx != 0) {
					dx = 0;
				}
				else if(my != 0) {
					dy = 0;
				}
				
				if(collision instanceof Platform && ((Platform)collision).dieOnHit) {
					game.die();
					collision.removed = true;
					RandomItem.hitSounds.get().play();
				}
				
				return false;
			} else {
				return true;
			}
		}
	}

}
