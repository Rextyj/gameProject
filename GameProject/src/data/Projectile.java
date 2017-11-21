package data;

import static helpers.Artist.checkCollision;
import static helpers.Artist.drawQuadTex;
import static helpers.Clock.delta;

import org.newdawn.slick.opengl.Texture;

import helpers.Artist;

public abstract class Projectile implements Entity{
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
	
	public void damage(){
		//this prevents the coins from increasing after the target is dead but the projectile is still flying
		if(target.isAlive()){
			target.getDamaged(damage);//does damage to the target 
		}
		alive = false;//set the projectile to dead so it won't get updated 
	}
	
	public void update() {
		if (alive) {
//			calculateDirection();// so the projectile will follow the target
									// even if the bullet is slow
			x += xVelocity * speed * delta();
			y += yVelocity * speed * delta();
			if (checkCollision(x, y, width, height, target.getX(), target.getY(), target.getWidth(),
					target.getHeight())) {
					damage();	
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

	public Texture getTexture() {
		return texture;
	}

	public float getSpeed() {
		return speed;
	}

	public float getxVelocity() {
		return xVelocity;
	}

	public float getyVelocity() {
		return yVelocity;
	}

	public int getDamage() {
		return damage;
	}

	public Enemy getTarget() {
		return target;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	
}
