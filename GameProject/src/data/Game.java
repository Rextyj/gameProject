package data;

import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.quickLoad;
import static helpers.Artist.drawQuadTex;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import UI.UI.Menu;
import helpers.StateManager;

public class Game {
	
	private TileGrid grid;
	private Player player;
	private WaveManager waveManager;
	private UI gameUI;
	private Menu towerPickerMenu;
	private Texture menuBackground;
	
	public Game(TileGrid grid){
		this.grid = grid;
		this.menuBackground = quickLoad("menuBackground");
		//create a wavemanager and add the ufo type enemy wave to the manager
		waveManager = new WaveManager(new Enemy(quickLoad("UFO64"), grid.getTile(1, 0), grid, TILE_SIZE, TILE_SIZE, 40, 25),
					2, 5);
		player = new Player(grid, waveManager);
		player.setup();
		setupUI();
	}
	
	private void setupUI(){
		gameUI = new UI();	
		gameUI.createMenu("TowerPicker", 1280, 100, 192, 960, 2, 0);//menu width is set to 192
		towerPickerMenu = gameUI.getMenu("TowerPicker");
		towerPickerMenu.quickAdd("IceCannon", "iceTowerBase");
		towerPickerMenu.quickAdd("BlueCannon", "cannonBaseBlue");
	}
	
	private void updateUI(){
		gameUI.draw();
		gameUI.drawString(1310,  700, "Lives: " + Player.lives);
		gameUI.drawString(1310,  800, "Coins: " + Player.coins);
		gameUI.drawString(1310,  600, "Wave " + waveManager.getWaveNumber());
		gameUI.drawString(0,  0, StateManager.framesInLastSecond + "fps");
		if(Mouse.next()){
			boolean mouseClicked = Mouse.isButtonDown(0);
			if(mouseClicked){
				if(towerPickerMenu.isButtonClicked("IceCannon")){
					player.pickTower(new TowerIce(TowerType.IceCannon, grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList()));
				}
//				if(towerPickerUI.isButtonClicked("RedCannon")){
//					player.pickTower(new TowerCannon(TowerType.RedCannon, grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList()));
//				}
				if(towerPickerMenu.isButtonClicked("BlueCannon")){
					player.pickTower(new TowerCannonBlue(TowerType.BlueCannon, grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList()));
				}
			}
		}
				
	}
	
	public void update(){
		drawQuadTex(menuBackground, 1280, 0 ,192, 960);
		grid.draw();
		waveManager.update();
		player.update();
		updateUI();
	}
}
