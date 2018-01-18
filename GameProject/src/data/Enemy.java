package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;
import static helpers.Clock.*;
import java.util.ArrayList;

public class Enemy implements Entity{
	//same as tile, x, y are already inverted
	private int width, height, currentCheckPoint;
	private float speed, x, y, health, startHealth, hiddenHealth;
	private Texture texture, healthBackground, healthForeground, healthBorder;
	private Tile startTile;
	private boolean first, alive;
	private TileGrid grid;
	
	private ArrayList<CheckPoint> checkpoints;
	private int[] directions;
	
	//default
	public Enemy(int tileX, int tileY, TileGrid grid) {
		this.texture = quickLoad("UFO64");
		this.healthBackground = quickLoad("healthBarBackground");
		this.healthForeground = quickLoad("healthBarForeground");
		this.healthBorder = quickLoad("healthBarBorder");
		this.startTile = grid.getTile(tileX, tileY);
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = TILE_SIZE;
		this.height = TILE_SIZE;
		this.speed = 50;
		this.startHealth = 50;
		this.health = startHealth;
		this.hiddenHealth = startHealth;
		this.grid = grid;
		this.first = true;
		this.alive = true;
		
		this.checkpoints = new ArrayList<CheckPoint> ();
		//index 0 is x, 1 is y
		this.directions = new int[2];
		
		directions = findNextD(startTile);
		this.currentCheckPoint = 0;
		populateCheckPointList();
	}
	
	//this constructor is used in wave class to add different types of enemies to the wave
	public Enemy(Texture texture, Tile startTile, TileGrid grid, int width, int height, float speed, float health){
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.startTile = startTile;
		this.texture = texture;
		this.healthBackground = quickLoad("healthBarBackground");
		this.healthForeground = quickLoad("healthBarForeground");
		this.healthBorder = quickLoad("healthBarBorder");
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.health = health;
		this.startHealth = health;
		this.hiddenHealth = health;
		this.grid = grid;
		this.first = true;
		this.alive = true;
		
		this.checkpoints = new ArrayList<CheckPoint> ();
		//index 0 is x, 1 is y
		this.directions = new int[2];
		
		directions = findNextD(startTile);
		this.currentCheckPoint = 0;
		populateCheckPointList();
	}
	
	private void populateCheckPointList(){
		//Add the first checkpoint manually
		checkpoints.add(findNextC(startTile, directions = findNextD(startTile)));
		
		int counter = 0;
		boolean cont = true;
		while(cont){
			int[] currentD = findNextD(checkpoints.get(counter).getTile());
			//check if there is a next direction and the number of checkpoints reaches 21 (arbitrary ?)
			if(currentD[0] == 2 || counter == 20){
				cont = false;
			} else {
				checkpoints.add(findNextC(checkpoints.get(counter).getTile(),
						directions = findNextD(checkpoints.get(counter).getTile())));
				counter++;
			}
			
		}
		
	}
	
	
	private boolean checkPointReached(){
		boolean reached = false;
		
		Tile t = checkpoints.get(currentCheckPoint).getTile();
		//check if position reached tile within variance of 3
		if(x > t.getX() - 3 && 
			x < t.getX() + 3 && 
			y > t.getY() - 3 && 
			y < t.getY() + 3){
			
			reached = true;
			x = t.getX();
			y = t.getY();
		}
		
		return reached;
	}
	
	public void update(){
		//do nothing if the class is being updated for the first time
		//this prevents the enemy from jumping ahead due to the amount of time used to set up
		if(first) {
			first = false;
		} else {
			if(checkPointReached()){
				//check if there is more check points ahead
				if(currentCheckPoint + 1 == checkpoints.size()){
					endOfMazeReached();
				} else {
					currentCheckPoint++;
				}				
			} else {
				//if it hasn't reached a checkpoint, continue in the current direction
				x += delta() * checkpoints.get(currentCheckPoint).getxDirection() * speed;
				y += delta() * checkpoints.get(currentCheckPoint).getyDirection() * speed;
			}
		}
	}
	
	
	//run if the enemy has reached the end of the maze
	private  void endOfMazeReached(){
		Player.modifyLives(-1);
		die();
	}
	
	private CheckPoint findNextC(Tile s, int[] dir){
		Tile next = null;
		CheckPoint c = null;
		
		boolean found = false;
		int counter = 1;
		
		while(!found){

			if(s.getXPlace() + dir[0] * counter == grid.getTilesWide() || 
					s.getYPlace() + dir[1] * counter == grid.getTilesHigh() ||
					s.getType() != grid.getTile(s.getXPlace() + dir[0] * counter, s.getYPlace() + dir[1] * counter).getType()){
				
				found = true;
				counter -= 1;
				next = grid.getTile(s.getXPlace() + dir[0] * counter, s.getYPlace() + dir[1] * counter);
			}
			
			counter++;
		}
		
		c = new CheckPoint(next, dir[0], dir[1]);
		return c;
	}
	
	private int[] findNextD(Tile s){//find direction, s is start tile
		int[] dir = new int[2];
		Tile u = grid.getTile(s.getXPlace(), s.getYPlace() - 1);
		Tile r = grid.getTile(s.getXPlace() + 1, s.getYPlace());
		Tile d = grid.getTile(s.getXPlace(), s.getYPlace() + 1);
		Tile l = grid.getTile(s.getXPlace() - 1, s.getYPlace());
		
		//check if the current tile is the same type as the next tile (above, right, down, left)
		//second condition stops the enemy from moving backwards
		if(s.getType() == u.getType() && directions[1] != 1){
			dir[0] = 0;
			dir[1] = -1;
		} else if(s.getType() == r.getType() && directions[0] != -1){
			dir[0] = 1;
			dir[1] = 0;
		} else if(s.getType() == d.getType() && directions[1] != -1){
			dir[0] = 0;
			dir[1] = 1;
		} else if(s.getType() == l.getType() && directions[0] != 1){
			dir[0] = -1;
			dir[1] = 0;
		} else {
			dir[0] = 2;
			dir[1] = 2;
		}
		return dir;
	}
	
	
	//take damage (called from external class)
	public void getDamaged(int amount) {
		health -= amount;
		if(health <= 0){
			die();
			//add 10 coins killing an enemy
			Player.modifyCoins(10);
		}
	}
	
	private void die(){
		alive = false;
		
	}
	
	public void draw(){
		float healthPercent = health / startHealth;
		//Enemy tex
		drawQuadTex(texture, x, y, width, height);
		//health bar tex
		drawQuadTex(healthBackground, x, y - 16, width, 8);
		drawQuadTex(healthForeground, x, y - 16, TILE_SIZE * healthPercent, 8);
		drawQuadTex(healthBorder, x, y - 16, width, 8);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public float getHiddenHealth() {
		return hiddenHealth;
	}

	public void reduceHiddenHealth(float amount) {
		this.hiddenHealth -= amount;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Tile getStartTile() {
		return startTile;
	}

	public void setStartTile(Tile startTile) {
		this.startTile = startTile;
	}

	public TileGrid getGrid() {
		return grid;
	}

	public void setGrid(TileGrid grid) {
		this.grid = grid;
	}
	
	public boolean isAlive(){
		return alive;
	}
}
