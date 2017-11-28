package data;

import static helpers.Artist.HEIGHT;
import static helpers.Artist.TILE_SIZE;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import helpers.Clock;

public class Player {
	
	private TileGrid grid;
	private TileType[] types;
	private WaveManager waveManager;
	private ArrayList<Tower> towerList;
	private boolean leftMouseButtonDown, rightMouseButtonDown;
	public static int coins, lives;
	
	public Player(TileGrid grid, WaveManager waveManager){
		this.grid = grid;
		this.types = new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		this.waveManager = waveManager;
		this.towerList = new ArrayList<Tower> ();
		this.leftMouseButtonDown = false;
		this.rightMouseButtonDown = false;
		coins = 0;
		lives = 0;
	}
	
	//start lives and money
	public void setup(){
		coins = 100;
		lives = 10;
	}
	
	//return true if the amount is affordable to build the tower or modify the tower
	public static boolean modifyCoins(int amount){
		if(coins + amount >= 0){
			coins += amount;
			System.out.println(coins);
			return true;
		}
		System.out.println(coins);
		return false;
	}
	
	public static void modifyLives(int amount){
		lives += amount;
	}
	
	public void update(){
		//loop through all the towers and update
		for(Tower t : towerList){
			t.update();
			t.draw();
			t.updateEnemyList(waveManager.getCurrentWave().getEnemyList());
		}
		
		//mouse
		if(Mouse.isButtonDown(1) && !rightMouseButtonDown){//0 is for left button, 1 right
			if(modifyCoins(-20)){
				towerList.add(new TowerIce(TowerType.IceCannon, grid.getTile(Mouse.getX() / TILE_SIZE, (HEIGHT - Mouse.getY() - 1)/ TILE_SIZE), waveManager.getCurrentWave().getEnemyList()));
			}
			
		}
		
		if(Mouse.isButtonDown(0) && !leftMouseButtonDown){//0 is for left button, 1 right
			if(modifyCoins(-10)){
				towerList.add(new TowerCannonBlue(TowerType.RedCannon, grid.getTile(Mouse.getX() / TILE_SIZE, (HEIGHT - Mouse.getY() - 1)/ TILE_SIZE), waveManager.getCurrentWave().getEnemyList()));
		
			}
		}
		
		//this is very important, otherwise, there will be more than one tower placed at the same position
		//execute the update method only once per click, because the mouseButtonDown flag will stay true until
		//the button is released.
		leftMouseButtonDown = Mouse.isButtonDown(0);
		rightMouseButtonDown = Mouse.isButtonDown(1);
		
		
		//keyboard input
		while(Keyboard.next()){
			if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()){
				Clock.changeMultiplier(0.2f);//speed up
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()){
				Clock.changeMultiplier(-0.2f);//slow down
			}
			
		}
	}
	
}
