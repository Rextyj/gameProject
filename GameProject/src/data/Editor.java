package data;

import static helpers.Artist.HEIGHT;
import static helpers.Artist.TILE_SIZE;
import static helpers.LevelDesign.saveMap;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import UI.UI;
import UI.UI.Menu;

public class Editor{
	
	private TileGrid grid;
	private int index;
	private TileType[] types;
	private UI editorUI;
	private Menu editorMenu;
	
	public Editor(){
		this.grid = new TileGrid();
		this.index = 0;
		this.types = new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		setupUI();
	}
	
	private void setupUI() {
		editorUI = new UI();
		editorUI.createMenu("TilePicker", 1280, 0, 192, 960, 2, 0);
		editorMenu = editorUI.getMenu("TilePicker");
		editorMenu.quickAdd("Grass", "grass64");
		editorMenu.quickAdd("Dirt", "dirt64");
	}
	
	public void update(){
		draw();
		// mouse
		if(Mouse.next()){
			boolean mouseClicked = Mouse.isButtonDown(0);
			if(mouseClicked){
				if(editorMenu.isButtonClicked("Grass")){
					index = 0;//grass has an index of 0
				} else if(editorMenu.isButtonClicked("Dirt")){
					index = 1;
				} else {
					setTile();
				}
			}
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
	
	private void draw() {
		grid.draw();
		editorUI.draw();
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

