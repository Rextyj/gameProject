package data;

import static helpers.Artist.HEIGHT;
import static helpers.Artist.TILE_SIZE;
import static helpers.LevelDesign.saveMap;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Editor{
	
	private TileGrid grid;
	private int index;
	private TileType[] types;
	
	public Editor(){
		this.grid = new TileGrid();
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
		
		grid.setTile((int) Math.floor(Mouse.getX() / TILE_SIZE), (int) Math.floor((HEIGHT - Mouse.getY() - 1)/ TILE_SIZE), types[index]);
	}
	
	//Change the TileType selected
	//called in the update method
	private void moveIndex(){
		index++;
		if(index > types.length - 1){
			index = 0;
		}
	}
}

