package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

public class TowerCannon {
	
	private float x, y;
	private int width, height, damage;
	private Texture baseTexture, cannonTexture;
	private Tile startTile;
	
	public TowerCannon(Texture baseTexture, Tile startTile, int damage){
		this.baseTexture = baseTexture;
		this.cannonTexture = quickLoad("cannonGun");
		this.startTile = startTile;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = (int) startTile.getWidth();
		this.height = (int) startTile.getHeight();
		this.damage = damage;
	}
	
	public void update(){
		
	}
	
	public void draw(){
		drawQuadTex(baseTexture, x, y, width, height);
		drawQuadTex(cannonTexture, x, y, width, height);
	}
}
