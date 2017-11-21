package data;

import static helpers.Artist.quickLoad;
import static helpers.Artist.TILE_SIZE;

public class Game {
	
	private TileGrid grid;
	private Player player;
	private WaveManager waveManager;
	
	
	
	public Game(int[][] map){
		grid = new TileGrid(map);
		
		waveManager = new WaveManager(new Enemy(quickLoad("UFO64"), grid.getTile(10, 10), grid, TILE_SIZE, TILE_SIZE, 40, 25),
					2, 5);
		player = new Player(grid, waveManager);
		player.setup();
	}
	
	public void update(){
		grid.draw();
		waveManager.update();
		player.update();
	}
}
