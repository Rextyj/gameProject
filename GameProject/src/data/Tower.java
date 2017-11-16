package data;

import org.newdawn.slick.opengl.Texture;

import helpers.Artist;

import static helpers.Artist.drawQuadTex;
import static helpers.Artist.drawQuadTexRot;
import static helpers.Artist.quickLoad;
import static helpers.Clock.delta;

import java.util.ArrayList;

public abstract class Tower implements Entity {

	private float x, y, timeSinceLastShot, firingSpeed, angle;
	private int width, height, damage, range;
	private Enemy target;
	private Texture[] textures;
	private ArrayList<Enemy> enemies;
	private boolean hasTarget;
	private ArrayList<Projectile> projectiles;
	
	public Tower(TowerType type, Tile startTile, ArrayList<Enemy> enemies){
		this.textures = type.textures;
		this.damage = type.damage;
		this.range = type.range;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = startTile.getWidth();
		this.height = startTile.getHeight();
		this.enemies = enemies;
		this.hasTarget = false;
		this.timeSinceLastShot = 0f;
		this.projectiles = new ArrayList<Projectile>();
		this.firingSpeed = type.firingSpeed;
		this.angle = 0f;
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
		projectiles.add(new Projectile(quickLoad("bullet"), target, x + Artist.TILE_SIZE / 2 - Artist.TILE_SIZE / 4, y + Artist.TILE_SIZE / 2 - Artist.TILE_SIZE / 4, 32, 32, 500, damage));
	}
	
	public void updateEnemyList(ArrayList<Enemy> newList){
		enemies = newList;
	}
	
	@Override
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
	}

	@Override
	public void draw() {
		drawQuadTex(textures[0], x, y, width, height);
		if(textures.length > 0){
			for(int i = 1; i < textures.length; i++){
				drawQuadTexRot(textures[i], x, y, width, height, angle);
			}
		}
		
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
