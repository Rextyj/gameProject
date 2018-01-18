package data;

import static helpers.Artist.quickLoad;
public class EnemyAlien extends Enemy{

	public EnemyAlien(int tileX, int tileY, TileGrid grid) {
		super(tileX, tileY, grid);
		this.setTexture(quickLoad("alienPink"));
	}
	
}
