package data;

import static helpers.Artist.drawQuadTex;
import static helpers.Artist.drawQuadTexRot;
import static helpers.Clock.delta;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.opengl.Texture;

public abstract class Tower implements Entity {

	private float x, y, timeSinceLastShot, firingSpeed, angle;
	private int width, height, damage, range, cost;
	public Enemy target;
	private Texture[] textures;
	private CopyOnWriteArrayList<Enemy> enemies;
	private boolean hasTarget;
	public ArrayList<Projectile> projectiles;
	public TowerType type;
	
	public Tower(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies){
		this.textures = type.textures;
		this.damage = type.damage;
		this.range = type.range;
		this.cost = type.cost;
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
		this.type = type;
	}
	
	private Enemy aquireTarget(){
		Enemy closest = null;
		double closestDistance = Integer.MAX_VALUE;
		//return the nearest enemy in the enemy list that is alive
		for(Enemy e : enemies) {
//			if(!e.isAlive()){
//				continue;
//			}
			if(e.getHiddenHealth() <= 0) {
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
	
	//Must be overridden in subclasses
	public abstract void shoot(Enemy target);
//		timeSinceLastShot = 0;
//		//projectile texture is size 32 so we need to move back 16 in both direction
//		projectiles.add(new ProjectileIce(quickLoad("iceBullet"), target, x + Artist.TILE_SIZE / 2 - Artist.TILE_SIZE / 4, y + Artist.TILE_SIZE / 2 - Artist.TILE_SIZE / 4, 32, 32, 500, damage));
	
	
	public void updateEnemyList(CopyOnWriteArrayList<Enemy> newList){
		enemies = newList;
	}
	
	@Override
	public void update(){
		if(target == null || target.isAlive() == false || !isInRange(target)){
			hasTarget = false;
		} 
		//if the target's hidden health is already below 0, don't shoot the new projectile to it
		if(!hasTarget || target.getHiddenHealth() <= 0){
//		if(!hasTarget){
			target = aquireTarget();
		} else {
			timeSinceLastShot += delta();
			if(timeSinceLastShot > firingSpeed){
				shoot(target);
				timeSinceLastShot = 0;
			}
			angle = calculateAngle();
			
		}
		//update projectile regardless of the current target being valid or not
		for(Projectile p : projectiles){
				p.update();
		}
	}

	@Override
	//this draw method was stripped from update method
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

	public Enemy getTarget() {
		return target;
	}

	public float getTimeSinceLastShot() {
		return timeSinceLastShot;
	}

	public float getFiringSpeed() {
		return firingSpeed;
	}

	public float getAngle() {
		return angle;
	}

	public int getDamage() {
		return damage;
	}

	public int getRange() {
		return range;
	}

	public Texture[] getTextures() {
		return textures;
	}

	public CopyOnWriteArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public boolean isHasTarget() {
		return hasTarget;
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public int getCost() {
		return cost;
	}

	

}
