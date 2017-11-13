package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Clock.*;
import static helpers.Artist.*;

public class Projectile {
	private Texture texture;
	private float x, y, width, height, speed, xVelocity, yVelocity;
	private int damage;
	private Enemy target;
	private boolean alive;

	public Projectile(Texture texture, Enemy target, float x, float y, float width, float height, float speed,
			int damage) {
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.damage = damage;
		this.target = target;
		this.xVelocity = 0f;
		this.yVelocity = 0f;
		calculateDirection();
		this.alive = true;
	}

	private void calculateDirection() {
		float totalAllowedMovement = 1.0f;
		// plus 32 because we want to aim at the center
		float xDistanceFromTarget = Math.abs(target.getX() + Game.TILE_SIZE / 2 - (x + Game.TILE_SIZE / 4));
		float yDistanceFromTarget = Math.abs(target.getY() + Game.TILE_SIZE / 2 - (y + Game.TILE_SIZE / 4));
		float totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;
		float xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTarget;
		xVelocity = xPercentOfMovement;
		yVelocity = totalAllowedMovement - xPercentOfMovement;

		if (target.getX() + Game.TILE_SIZE / 2 < x + Game.TILE_SIZE / 4) {
			xVelocity *= -1;
		}
		if (target.getY() + Game.TILE_SIZE / 2 < y + Game.TILE_SIZE / 4) {
			yVelocity *= -1;
		}
	}

	public void update() {
		if (alive) {
			calculateDirection();// so the projectile will follow the target
									// even if the bullet is slow
			x += xVelocity * speed * delta();
			y += yVelocity * speed * delta();
			if (checkCollision(x, y, width, height, target.getX(), target.getY(), target.getWidth(),
					target.getHeight())) {
				target.getDamaged(damage);
				alive = false;
			}

			draw();
		}
	}

	public void draw() {
		drawQuadTex(texture, x, y, 32, 32);
	}
}
