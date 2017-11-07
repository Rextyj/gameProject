package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import static helpers.Artist.*;

import java.util.ArrayList;

public class Player {
	
	private TileGrid grid;
	private TileType[] types;
	private int index;
	private WaveManager waveManager;
	private ArrayList<TowerCannon> towerList;
	
	public Player(TileGrid grid, WaveManager waveManager){
		this.grid = grid;
		this.types = new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		this.index = 0;
		this.waveManager = waveManager;
		this.towerList = new ArrayList<TowerCannon> ();
	}
	
	public void setTile(){
		
		grid.setTile((int) Math.floor(Mouse.getX() / 64), (int) Math.floor((HEIGHT - Mouse.getY() - 1)/ 64), types[index]);
	}
	
	public void update(){
		
		for(TowerCannon t : towerList){
			t.update();
		}
		
		//mouse
		if(Mouse.isButtonDown(0)){//0 is for left button, 1 right
			setTile();
		}
		
		//keyboard input
		while(Keyboard.next()){
			if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()){
				moveIndex();
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
