package data;

import static helpers.Artist.HEIGHT;
import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.drawQuadTex;
import static helpers.Artist.quickLoad;
import static helpers.LevelDesign.saveMap;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import UI.UI.Menu;

public class Editor{
	
	private TileGrid grid;
	private Tile[] startEndTiles;
	private int index;
	private TileType[] types;
	private UI editorUI;
	private Menu editorMenu;
	private Texture menuBackground;
	
	public Editor(){
		this.grid = new TileGrid();
		this.startEndTiles = new Tile[2];
		this.index = 0;
		this.types = new TileType[5];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		this.types[3] = TileType.Start;
		this.types[4] = TileType.End;
		this.menuBackground = quickLoad("menuBackground");
		setupUI();
	}
	
	private void setupUI() {
		editorUI = new UI();
		editorUI.createMenu("TilePicker", 1280, 100, 192, 960, 2, 0);
		editorMenu = editorUI.getMenu("TilePicker");
		editorMenu.quickAdd("Grass", "grass64");
		editorMenu.quickAdd("Dirt", "dirt64");
		editorMenu.quickAdd("Water", "water64");
		editorMenu.quickAdd("Start", "startTile");
		editorMenu.quickAdd("End", "baseTile");
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
				} else if(editorMenu.isButtonClicked("Water")){
					index = 2;
				} else if(editorMenu.isButtonClicked("Start")){
					index = 3;
				} else if(editorMenu.isButtonClicked("End")) {
					index = 4;
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
				//need to save the start and end tiles too!
				/*
				 * try to embed the start and end tile into the tilegrid. Anywhere under the side menu
				 */
				for(int i = 0; i < 2; i++) {
					grid.setTile(grid.getTilesWide() - grid.getInvalidWidth() + i, 0, startEndTiles[i]);
				}
				
				saveMap("newMap1", grid);
			}

		}
	}
	
	private void draw() {
		grid.draw();
		for(Tile t : startEndTiles) {
			if(t != null) {
				t.draw();
			}
		}
		drawQuadTex(menuBackground, 1280, 0 ,192, 960);
		editorUI.draw();
	}
	
	private void setTile(){
		//when we are setting the starting point and end point, create new tiles every time the mouse is clicked
		//Tile constructor uses pixel coordinates so we need to multiply by the TILE_SIZE after flooring to make it snap to the tile grid
		if(index == 3 || index == 4) {
			startEndTiles[index - 3] = new Tile((int) Math.floor(Mouse.getX() / TILE_SIZE) * TILE_SIZE, (int) Math.floor((HEIGHT - Mouse.getY() - 1)/ TILE_SIZE) *TILE_SIZE, TILE_SIZE, TILE_SIZE, types[index]);
		} else {
			grid.setTile((int) Math.floor(Mouse.getX() / TILE_SIZE), (int) Math.floor((HEIGHT - Mouse.getY() - 1)/ TILE_SIZE), types[index]);
		}
		
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

