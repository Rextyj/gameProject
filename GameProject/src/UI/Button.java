package UI;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.quickLoad;

public class Button {
	
	private String name;
	private Texture texture;
	private int x, y, width, height;
	
	public Button(String name, Texture texture, int x, int y, int width, int height){
		this.name = name;
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
				
	}
	
	public Button(String name, Texture texture, int x, int y){
		this.name = name;
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.width = texture.getImageWidth();
		this.height = texture.getImageHeight();
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(String textureName) {
		this.texture = quickLoad(textureName);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
