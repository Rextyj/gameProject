package data;

import org.newdawn.slick.opengl.Texture;

import helpers.Artist;
import static helpers.Clock.*;
import java.util.ArrayList;

public class Enemy {
	//same as tile, x, y are already inverted
	private int width, height, health, currentCheckPoint;
	private float speed, x, y;
	private Texture texture;
	private Tile startTile;
	private boolean first = true, alive = true;
	private TileGrid grid;
	
	private ArrayList<CheckPoint> checkpoints;
	private int[] directions;
	
	public Enemy(Texture texture, Tile startTile, TileGrid grid, int width, int height, float speed, int health){
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.startTile = startTile;
		this.texture = texture;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.health = health;
		this.grid = grid;
		
		this.checkpoints = new ArrayList<CheckPoint> ();
		//index 0 is x, 1 is y
		this.directions = new int[2];
		directions = findNextD(startTile);
		this.currentCheckPoint = 0;
		populateCheckPointList();
	}
	
	private void populateCheckPointList(){
		checkpoints.add(findNextC(startTile, directions = findNextD(startTile)));
		
		int counter = 0;
		boolean cont = true;
		while(cont){
			int[] currentD = findNextD(checkpoints.get(counter).getTile());
			if(currentD[0] == 2 || counter == 20){
				cont = false;
			} else {
				checkpoints.add(findNextC(checkpoints.get(counter).getTile(),
						directions = findNextD(checkpoints.get(counter).getTile())));
				counter++;
			}
			
		}
		
//		for(CheckPoint c : checkpoints){
//			System.out.println("" + c.getxDirection() + c.getyDirection());
//		}
		
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
		if(first){
			first = false;
		} else {
//			if(pathContinues()){
//				x += delta() * speed;//the booting of the game will result in a very large delta
//				//we want to skip the first update
//			}
			if(checkPointReached()){
//				System.out.println("increment");
				if(currentCheckPoint + 1 == checkpoints.size()){
					die();
				} else {
					currentCheckPoint++;
				}
				
			} 
			else {
				x += delta() * checkpoints.get(currentCheckPoint).getxDirection() * speed;
				y += delta() * checkpoints.get(currentCheckPoint).getyDirection() * speed;
			}
		}
		
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
		
		if(s.getType() == u.getType() && directions[1] != 1){//second condition stops the enemy from moving backwards
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
//			System.out.println("No Way");
		}
		return dir;
	}
	
//	private boolean pathContinues(){
//		boolean answer = true;
//		Tile myTile = grid.getTile((int) (x / 64), (int) (y / 64));
//		Tile nextTile = grid.getTile((int) (x / 64) + 1, (int) (y / 64));
//		
//		if(myTile.getType() != nextTile.getType()){
//			answer = false;
//		}
//		
//		return answer;
//	}
	
	public void getDamaged(int amount) {
		health -= amount;
		if(health <= 0){
			die();
		}
	}
	
	private void die(){
		alive = false;
		
	}
	
	public void draw(){
		Artist.drawQuadTex(texture, x, y, width, height);
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

	public int getHealth() {
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
