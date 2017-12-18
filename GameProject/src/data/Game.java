package data;

import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.quickLoad;
import static helpers.Artist.drawQuadTex;

import org.lwjgl.input.Mouse;

import UI.Button;
import UI.UI;

public class Game {
	
	private TileGrid grid;
	private Player player;
	private WaveManager waveManager;
	private UI towerPickerUI;
	
	
	public Game(int[][] map){
		grid = new TileGrid(map);
		//create a wavemanager and add the ufo type enemy wave to the manager
		waveManager = new WaveManager(new Enemy(quickLoad("UFO64"), grid.getTile(10, 10), grid, TILE_SIZE, TILE_SIZE, 40, 25),
					2, 2);
		player = new Player(grid, waveManager);
		player.setup();
		setupUI();
	}
	
	private void setupUI(){
		towerPickerUI = new UI();
		//need to change the hard coded numbers in the future
//		towerPickerUI.addButton("IceCannon", "iceTowerBase", 0, 0);
////		towerPickerUI.addButton("RedCannon", "cannonBase", 0, 64);
//		towerPickerUI.addButton("BlueCannon", "cannonBaseBlue", 0, 128);
		
		towerPickerUI.createMenu("TowerPicker", 1312, 0, 2, 0);//1312 so that it's centered at the side menu
		towerPickerUI.getMenu("TowerPicker").addButton(new Button("IceCannon", quickLoad("iceTowerBase"), 0, 0));
		towerPickerUI.getMenu("TowerPicker").addButton(new Button("BlueCannon", quickLoad("cannonBaseBlue"), 0, 0));
	}
	
	private void updateUI(){
		towerPickerUI.draw();
		if(Mouse.next()){
			boolean mouseClicked = Mouse.isButtonDown(0);
			if(mouseClicked){
				if(towerPickerUI.getMenu("TowerPicker").isButtonClicked("IceCannon")){
					player.pickTower(new TowerIce(TowerType.IceCannon, grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList()));
				}
//				if(towerPickerUI.isButtonClicked("RedCannon")){
//					player.pickTower(new TowerCannon(TowerType.RedCannon, grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList()));
//				}
				if(towerPickerUI.getMenu("TowerPicker").isButtonClicked("BlueCannon")){
					player.pickTower(new TowerCannonBlue(TowerType.BlueCannon, grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList()));
				}
			}
		}
				
	}
	
	public void update(){
		drawQuadTex(quickLoad("menuBackground"), 1280, 0 ,192, 960);
		grid.draw();
		waveManager.update();
		player.update();
		updateUI();
	}
}
