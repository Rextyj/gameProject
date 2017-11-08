package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import helpers.Clock;

import static helpers.Artist.*;

import java.util.ArrayList;

public class Player {
	
	private TileGrid grid;
	private TileType[] types;
	private int index;
	private WaveManager waveManager;
	private ArrayList<TowerCannon> towerList;
	private boolean leftMoustButtonDown;
	
	public Player(TileGrid grid, WaveManager waveManager){
		this.grid = grid;
		this.types = new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		this.index = 0;
		this.waveManager = waveManager;
		this.towerList = new ArrayList<TowerCannon> ();
		this.leftMoustButtonDown = false;
	}
	
	public void setTile(){
		
		grid.setTile((int) Math.floor(Mouse.getX() / 64), (int) Math.floor((HEIGHT - Mouse.getY() - 1)/ 64), types[index]);
	}
	
	public void update(){
		
		for(TowerCannon t : towerList){
			t.update();
		}
		
		//mouse
		if(Mouse.isButtonDown(0) && !leftMoustButtonDown){//0 is for left button, 1 right
		
			towerList.add(new TowerCannon(quickLoad("cannonBase"), grid.getTile(Mouse.getX() / 64, (HEIGHT - Mouse.getY() - 1)/ 64), 10, waveManager.getCurrentWave().getEnemyList()));
//			setTile();
		}
		leftMoustButtonDown = Mouse.isButtonDown(0);//execute the update method only once per click
		
		//keyboard input
		while(Keyboard.next()){
			if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()){
				Clock.changeMultiplier(0.2f);//speed up
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()){
				Clock.changeMultiplier(-0.2f);//slow down
			}
			
			if(Keyboard.getEventKey() == Keyboard.KEY_T && Keyboard.getEventKeyState()){
				towerList.add(new TowerCannon(quickLoad("cannonBase"), grid.getTile(9, 9), 10, waveManager.getCurrentWave().getEnemyList()));
			}
		}
	}
	
	private void moveIndex(){
		index++;
		if(index > types.length - 1){
			index = 0;
		}
	}
}
