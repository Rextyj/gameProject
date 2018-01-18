package data;

public class EnemyUFO extends Enemy {

	public EnemyUFO(int tileX, int tileY, TileGrid grid) {
		super(tileX, tileY, grid);
		this.setTexture("UFO64");
		this.setHealth(200);
		this.setSpeed(80);
	}
	
}
