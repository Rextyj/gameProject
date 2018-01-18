package data;

import static helpers.Artist.drawQuadTexRot;

public class EnemyInfantry extends Enemy{

	public EnemyInfantry(int tileX, int tileY, TileGrid grid) {
		super(tileX, tileY, grid);
		this.setTexture("greenInfantry");
	}
	
	
//	public void draw() {
//		System.out.println("Overriden method");
//		drawQuadTexRot(this.getTexture(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), 90);
//	}
}
