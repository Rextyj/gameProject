package UI;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import static helpers.Artist.*;
public class UI {
	
	private ArrayList<Button> buttonList;
	private ArrayList<Menu> menuList;
	
	public UI(){
		buttonList = new ArrayList<Button>();
		menuList = new ArrayList<Menu>();
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
	
	private Button getButton(String buttonName){
		for(Button b : buttonList){
			if(b.getName().equals(buttonName)){
				return b;
			}
		}
		return null;
	}
	
	public void createMenu(String name, int x, int y) {
		menuList.add(new Menu(name, x, y));
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
		private int x, y, numberOfButtons;
		private String name;
		
		public Menu(String name, int x, int y) {
			this.name = name;
			this.x = x;
			this.y = y;
			this.numberOfButtons = 0;
			this.menuButtons = new ArrayList<Button> ();
		}
		
		public void addButton(Button b) {
			b.setX(x + numberOfButtons * TILE_SIZE);
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
		
		private Button getButton(String buttonName){
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
