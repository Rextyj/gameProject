package data;

import static helpers.Artist.checkCollision;
import static helpers.Artist.drawQuadTex;
import static helpers.Clock.delta;

import org.newdawn.slick.opengl.Texture;

import helpers.Artist;

public class Projectile implements Entity{
	private Texture texture;
	private float x, y, speed, xVelocity, yVelocity;
	private int damage, width, height;
	private Enemy target;
	private boolean alive;

	public Projectile(Texture texture, Enemy target, float x, float y, int width, int height, float speed,
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
		float xDistanceFromTarget = Math.abs(target.getX() + Artist.TILE_SIZE / 2 - (x + Artist.TILE_SIZE / 4));
		float yDistanceFromTarget = Math.abs(target.getY() + Artist.TILE_SIZE / 2 - (y + Artist.TILE_SIZE / 4));
		float totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;
		float xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTarget;
		xVelocity = xPercentOfMovement;
		yVelocity = totalAllowedMovement - xPercentOfMovement;

		if (target.getX() + Artist.TILE_SIZE / 2 < x + Artist.TILE_SIZE / 4) {
			xVelocity *= -1;
		}
		if (target.getY() + Artist.TILE_SIZE / 2 < y + Artist.TILE_SIZE / 4) {
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

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setX(float x) {
		this.x = x;
		
	}

	@Override
	public void setY(float y) {
		this.y = y;
		
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
		
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
		
	}
}
