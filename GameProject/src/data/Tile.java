package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public class Tile {
	//tile's x and y are for col num and row num respectively
	//i.e already inverted when created
	private float x, y, width, height;
	private Texture texture;
	private TileType type;
	
	public Tile(float x, float y, float width, float height, TileType type){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.type = type;
		this.texture = quickLoad(type.textureName);
	}

	public void draw() {
		drawQuadTex(texture, x, y, width, height);
	}
	
	public float getX() {
		return x;
	}
	
	public int getXPlace(){
		return (int) x / TILE_SIZE;
	}
	
	public int getYPlace(){
		return (int) y / TILE_SIZE;
	}
	
	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}
	
	
}
