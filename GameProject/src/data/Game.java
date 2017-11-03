package data;

import static helpers.Artist.quickLoad;

public class Game {
	
	private TileGrid grid;
	private Player player;
	private Wave wave;
	
	//Temp variable
	TowerCannon tower;
	
	
	public Game(int[][] map){
		grid = new TileGrid(map);
		player = new Player(grid);
		wave = new Wave(20, new Enemy(quickLoad("UFO64"), grid.getTile(10, 10), grid, 64, 64, 3));
		
		tower = new TowerCannon(quickLoad("cannonBase"), grid.getTile(11,10), 10);
	}
	
	public void update(){
		grid.draw();
		wave.update();
		player.update();
		tower.update();
	}
}
