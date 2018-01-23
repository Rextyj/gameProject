package data;

import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.quickLoad;
import static helpers.Artist.drawQuadTex;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import UI.UI.Menu;
import helpers.StateManager;
import helpers.StateManager.GameState;

public class Game {
	
	private TileGrid grid;
	private Tile startTile;
	private Player player;
	private WaveManager waveManager;
	private UI gameUI;
	private Menu towerPickerMenu, functionMenu;
	private Texture menuBackground;
	private Enemy[] enemyTypes;
	private boolean isPaused, leftMouseButtonDown;
	
	public Game(TileGrid grid){
		this.grid = grid;
		this.startTile = grid.getStartTile();
		this.leftMouseButtonDown = false;
		//Note that the texture needs to have a dimension of power of 2!!!!
		//we need to make the texture larger than necessary, so choose the closest 256 * 1024
		this.menuBackground = quickLoad("sideMenuBackground");
		this.isPaused = true;
		enemyTypes = new Enemy[3];
		//Specifies the default starting tile coordinates
		//Change the start tiles in Wave class when adding enemies into the wave
		enemyTypes[0] = new EnemyAlien(startTile.getXPlace(), startTile.getYPlace(), grid);
		enemyTypes[1] = new EnemyUFO(startTile.getXPlace(), startTile.getYPlace(), grid);
		enemyTypes[2] = new EnemyInfantry(startTile.getXPlace(), startTile.getYPlace(), grid);
		/*
		//create a wavemanager and add the ufo type enemy wave to the manager
		waveManager = new WaveManager(new Enemy(quickLoad("UFO64"), grid.getTile(1, 0), grid, TILE_SIZE, TILE_SIZE, 40, 25),
					2, 5);
					*/
		waveManager = new WaveManager(enemyTypes, 2, 6);
		player = new Player(grid, waveManager);
		player.setup();
		setupUI();
	}
	
	private void setupUI(){
		gameUI = new UI();	
		gameUI.createMenu("TowerPicker", 1280, 100, 192, 960, 2, 0);//menu width is set to 192
		//menu for pause, continue and pauseMenu
		gameUI.createMenu("FunctionMenu", 1280, 832, 192, 128, 3, 0);
		towerPickerMenu = gameUI.getMenu("TowerPicker");
		towerPickerMenu.quickAdd("IceCannon", "iceTowerBase");
		towerPickerMenu.quickAdd("BlueCannon", "cannonBaseBlue");
		functionMenu = gameUI.getMenu("FunctionMenu");
		//change the texture according to the game state
		functionMenu.quickAdd("StartPause", "startButton");
		functionMenu.quickAdd("PauseMenu", "menuList");
	}
	
	private void updateUI(){
		gameUI.draw();
		gameUI.drawString(1310,  650, "Lives: " + Player.lives);
		gameUI.drawString(1310,  700, "Coins: " + Player.coins);
		gameUI.drawString(1310,  600, "Wave " + waveManager.getWaveNumber());
		gameUI.drawString(1310,  750, "Alive " + waveManager.getCurrentWave().getEnemyList().size());
//		gameUI.drawString(0,  0, StateManager.framesInLastSecond + "fps");
		if(Mouse.next()){
			boolean mouseClicked = Mouse.isButtonDown(0) && !leftMouseButtonDown;
			if(mouseClicked){
				if(towerPickerMenu.isButtonClicked("IceCannon")){
					player.pickTower(new TowerIce(TowerType.IceCannon, grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList()));
				}
//				if(towerPickerUI.isButtonClicked("RedCannon")){
//					player.pickTower(new TowerCannon(TowerType.RedCannon, grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList()));
//				}
				if(towerPickerMenu.isButtonClicked("BlueCannon")){
					player.pickTower(new TowerCannonBlue(TowerType.BlueCannon, player.getMouseTile(), waveManager.getCurrentWave().getEnemyList()));
				}
				if(functionMenu.isButtonClicked("PauseMenu")) {
					StateManager.setState(GameState.PAUSEMENU);
				}
				if(functionMenu.isButtonClicked("StartPause")) {
					isPaused = !isPaused;
					if(!isPaused) {
						functionMenu.getButton("StartPause").setTexture("pauseButton");
					} else {
						functionMenu.getButton("StartPause").setTexture("startButton");
					}
					
				}
			}
			leftMouseButtonDown = Mouse.isButtonDown(0);
		}
				
	}
	
	public void update(){
		
		grid.draw();
		drawQuadTex(menuBackground, 1280, 0 ,192, 960);
		if(!isPaused) {
			waveManager.update();
			player.update();
		} else {
			waveManager.keepDrawing();
			player.keepDrawing();
		}
		updateUI();
		
	}
	
	public void keepDrawing() {
		grid.draw();
		waveManager.keepDrawing();
		player.keepDrawing();
		updateUI();
	}
}
