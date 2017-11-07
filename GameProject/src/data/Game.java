package data;

import static helpers.Artist.quickLoad;

public class Game {
	
	private TileGrid grid;
	private Player player;
	private WaveManager waveManager;
	
	//Temp variable
	TowerCannon tower;
	
	
	public Game(int[][] map){
		grid = new TileGrid(map);
		player = new Player(grid);
		waveManager = new WaveManager(new Enemy(quickLoad("UFO64"), grid.getTile(10, 10), grid, 64, 64, 40),
					4, 5);
		
		tower = new TowerCannon(quickLoad("cannonBase"), grid.getTile(11,10), 10);
	}
	
	public void update(){
		grid.draw();
		waveManager.update();
		player.update();
		tower.update();
	}
}
