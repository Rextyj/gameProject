package data;

import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.quickLoad;

import org.lwjgl.input.Mouse;

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
		towerPickerUI.addButton("IceCannon", "iceTowerBase", 0, 0);
	}
	
	private void updateUI(){
		towerPickerUI.draw();
		if(Mouse.next()){
			boolean mouseClicked = Mouse.isButtonDown(0);
			if(mouseClicked){
				if(towerPickerUI.isButtonClicked("IceCannon")){
					player.pickTower(new TowerIce(TowerType.IceCannon, grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList()));
				}
			}
		}
				
	}
	
	public void update(){
		grid.draw();
		waveManager.update();
		player.update();
		updateUI();
	}
}
