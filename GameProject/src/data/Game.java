package data;

import static helpers.Artist.quickLoad;

public class Game {
	
	private TileGrid grid;
	private Player player;
	private WaveManager waveManager;
	public static final int TILE_SIZE = 64;
	
	
	public Game(int[][] map){
		grid = new TileGrid(map);
		
		waveManager = new WaveManager(new Enemy(quickLoad("UFO64"), grid.getTile(10, 10), grid, 64, 64, 40, 25),
					4, 2);
		player = new Player(grid, waveManager);
	}
	
	public void update(){
		grid.draw();
		waveManager.update();
		player.update();
	}
}
