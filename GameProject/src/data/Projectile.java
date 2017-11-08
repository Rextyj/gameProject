package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Clock.*;
import static helpers.Artist.*;

public class Projectile {
	private Texture texture;
	private float x, y, speed, xVelocity, yVelocity;
	private int damage;
	private Enemy target;
	
	public Projectile(Texture texture, Enemy target, float x, float y, float speed, int damage){
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.damage = damage;
		this.target = target;
		this.xVelocity = 0f;
		this.yVelocity = 0f;
		calculateDirection();
	}
	
	private void calculateDirection(){
		float totalAllowedMovement = 1.0f;
		float xDistanceFromTarget = Math.abs(target.getX() + Game.TILE_SIZE / 2 - (x  + Game.TILE_SIZE / 4));//plus 32 because we want to aim at the center
		float yDistanceFromTarget = Math.abs(target.getY() + Game.TILE_SIZE / 2 - (y  + Game.TILE_SIZE / 4));
		float totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;
		float xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTarget;
		xVelocity = xPercentOfMovement;
		yVelocity = totalAllowedMovement - xPercentOfMovement;
		
		if(target.getX() < x){
			xVelocity *= -1;
		}
		if(target.getY() < y){
			yVelocity *= -1;
		}
	}
	
	public void update(){
		calculateDirection();//so the projectile will follow the target even if the bullet is slow
		x += xVelocity * speed * delta();
		y += yVelocity * speed * delta();
		draw();
	}
	
	public void draw(){
		drawQuadTex(texture, x, y, 32, 32);
	}
}
