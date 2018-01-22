package UI;

import java.awt.Font;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import static helpers.Artist.*;
public class UI {
	
	private ArrayList<Button> buttonList;
	private ArrayList<Menu> menuList;
	private TrueTypeFont font;
	private Font awtFont;
	
	public UI(){
		buttonList = new ArrayList<Button>();
		menuList = new ArrayList<Menu>();
		awtFont = new Font("Time New Roam", Font.BOLD, 24);
		font = new TrueTypeFont(awtFont, false);
	}
	
	public void drawString(int x, int y, String text) {
		font.drawString(x, y, text, Color.red);
	}
	
	//x and y are pixel coordinates
	public void addButton(String name, String textureName, int x, int y){
		buttonList.add(new Button(name, quickLoad(textureName), x, y));
	}
	
	public boolean isButtonClicked(String buttonName){
		Button b = getButton(buttonName);
		float mouseY = HEIGHT - Mouse.getY() - 1;//openGl considers the bottom left cornor as 0,0
		if(Mouse.getX() > b.getX() && Mouse.getX() < b.getX() + b.getWidth() && 
				mouseY > b.getY() && mouseY < b.getY() + b.getHeight()){
			return true;
		}
		return false;
	}
	
	protected Button getButton(String buttonName){
		for(Button b : buttonList){
			if(b.getName().equals(buttonName)){
				return b;
			}
		}
		return null;
	}
	
	public void createMenu(String name, int x, int y, int width, int height, int optionsWidth, int optionsHeight) {
		menuList.add(new Menu(name, x, y, width, height, optionsWidth, optionsHeight));
	}
	
	public Menu getMenu(String name) {
		for(Menu m : menuList) {
			if(m.getName().equals(name)) {
				return m;
			}
		}
		return null;
	}
	
	public void draw(){
		for(Menu m : menuList) {
			m.draw();
		}
		for(Button b : buttonList){
			drawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
		}
	}
	
	//menu subclass
	public class Menu {
		
		private ArrayList<Button> menuButtons;
		private int x, y, width, height, numberOfButtons, optionsWidth, optionsHeight, padding;
		private String name;
		
		/**
		 * 
		 * @param optionsWidth Number of columns of the side menu
		 * @param optionsHeight Number of rows of the side menu
		 */
		public Menu(String name, int x, int y, int width, int height, int optionsWidth, int optionsHeight) {
			this.name = name;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.optionsWidth = optionsWidth;
			this.optionsHeight = optionsHeight;
			this.padding = (width - (optionsWidth * TILE_SIZE)) / (optionsWidth + 1);
			this.numberOfButtons = 0;
			this.menuButtons = new ArrayList<Button> ();
		}
		//pass button in 
		public void addButton(Button b) {
			setButton(b);
		}
		//or quick add a button
		public void quickAdd(String name, String buttonTextureName) {
			Button b = new Button(name, quickLoad(buttonTextureName), 0, 0);
			setButton(b);
		}
		
		private void setButton(Button b) {
			//auto arrange the buttons
			if(optionsWidth != 0) {
				//remember, numberOfButtons will increment after this
				b.setY(y + (numberOfButtons / optionsWidth) * TILE_SIZE);
			}
			b.setX(x + padding + (numberOfButtons % 2) * (padding + TILE_SIZE));
			numberOfButtons++;
			menuButtons.add(b);
		}
		public boolean isButtonClicked(String buttonName){
			Button b = getButton(buttonName);
			float mouseY = HEIGHT - Mouse.getY() - 1;//openGl considers the bottom left cornor as 0,0
			if(Mouse.getX() > b.getX() && Mouse.getX() < b.getX() + b.getWidth() && 
					mouseY > b.getY() && mouseY < b.getY() + b.getHeight()){
				return true;
			}
			return false;
		}
		
		public Button getButton(String buttonName){
			for(Button b : menuButtons){
				if(b.getName().equals(buttonName)){
					return b;
				}
			}
			return null;
		}
		
		public void draw() {
			for(Button b : menuButtons) {
				drawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
			}
		}
		
		public String getName() {
			return name;
		}
	}
}
