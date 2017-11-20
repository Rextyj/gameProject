package data;

import static helpers.Artist.drawQuadTex;
import static helpers.Artist.drawQuadTexRot;
import static helpers.Artist.quickLoad;
import static helpers.Clock.delta;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

import helpers.Artist;

public class TowerCannon {
	
	private float x, y, timeSinceLastShot, firingSpeed, angle;
	private double range;
	private int width, height, damage;
	private Texture baseTexture, cannonTexture;
	private Tile startTile;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Enemy> enemies;
	private Enemy target;
	private boolean hasTarget;
	
	public TowerCannon(Texture baseTexture, Tile startTile, int damage, double range, ArrayList<Enemy> enemies){
		this.baseTexture = baseTexture;
		this.cannonTexture = quickLoad("cannonGun");
		this.startTile = startTile;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = (int) startTile.getWidth();
		this.height = (int) startTile.getHeight();
		this.damage = damage;
		firingSpeed = 1;
		this.timeSinceLastShot = 0;
		this.projectiles = new ArrayList<Projectile>();
		this.enemies = enemies;
//		this.target = aquireTarget();
//		this.angle = calculateAngle();
		this.range = range;
		this.hasTarget = false;
	}
	
	private Enemy aquireTarget(){
		Enemy closest = null;
		double closestDistance = 10000;
		for(Enemy e : enemies) {
			if(!e.isAlive()){
				continue;
			}
			if(isInRange(e) && findDistance(e) < closestDistance){
				closestDistance = findDistance(e);
				closest = e;
			}
		}
		if(closest != null){
			hasTarget = true;
		}
		
		return closest;
	}
	
	private boolean isInRange(Enemy e){
		float xDistance = Math.abs(e.getX() - x);
		float yDistance = Math.abs(e.getY() - y);
		double radiusDistance =  Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
		if(radiusDistance <= range){
			return true;
		}
		return false;
	}
	
	private double findDistance(Enemy e){
		float xDistance = Math.abs(e.getX() - x);
		float yDistance = Math.abs(e.getY() - y);
		double radiusDistance =  Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
		return radiusDistance;
	}
	
	private float calculateAngle(){
		double angleTemp = Math.atan2(target.getY() - y, target.getX() - x);
		return (float) Math.toDegrees(angleTemp) - 90;//
	}
	
	private void shoot(){
		timeSinceLastShot = 0;
		//projectile texture is size 32 so we need to move back 16 in both direction
		projectiles.add(new ProjectileCannon(quickLoad("bullet"), target, x + Artist.TILE_SIZE / 2 - Artist.TILE_SIZE / 4, y + Artist.TILE_SIZE / 2 - Artist.TILE_SIZE / 4, 32, 32, 500, damage));
	}
	
	public void updateEnemyList(ArrayList<Enemy> newList){
		enemies = newList;
	}
	
	public void update(){
		if(target == null || target.isAlive() == false || !isInRange(target)){
			hasTarget = false;
		} 
		
		if(!hasTarget){
			target = aquireTarget();
		} else {
			timeSinceLastShot += delta();
			if(timeSinceLastShot > firingSpeed){
				shoot();
			}

			for(Projectile p : projectiles){
				p.update();
			}
			angle = calculateAngle();
			
		}
		
		
		draw();
	}
	
	public void draw(){
		drawQuadTex(baseTexture, x, y, width, height);
		drawQuadTexRot(cannonTexture, x, y, width, height, angle);
	}
}
