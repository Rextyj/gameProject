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
		wave = new Wave(4, new Enemy(quickLoad("UFO64"), grid.getTile(10, 10), grid, 64, 64, 40));
		
		tower = new TowerCannon(quickLoad("cannonBase"), grid.getTile(11,10), 10);
	}
	
	public void update(){
		grid.draw();
		wave.update();
		player.update();
		tower.update();
	}
}
