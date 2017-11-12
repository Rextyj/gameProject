package data;

import static helpers.Artist.HEIGHT;
import static helpers.Artist.quickLoad;
import static helpers.LevelDesign.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import helpers.Clock;

public class Editor {
	
	private TileGrid grid;
	private int index;
	private TileType[] types;
	
	public Editor(){
		grid = new TileGrid();
		this.index = 0;
		this.types = new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
	}
	
	public void update(){
		grid.draw();
		// mouse
		if (Mouse.isButtonDown(0)) {// 0 is for left															// button, 1 right
			 setTile();
		}

		// keyboard input
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				moveIndex();
			}
			//save
			if (Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState()) {
				saveMap("newMap1", grid);
			}

		}
	}
	
	private void setTile(){
		
		grid.setTile((int) Math.floor(Mouse.getX() / 64), (int) Math.floor((HEIGHT - Mouse.getY() - 1)/ 64), types[index]);
	}
	
	private void moveIndex(){
		index++;
		if(index > types.length - 1){
			index = 0;
		}
	}
}

