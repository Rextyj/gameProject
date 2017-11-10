package UI;

import java.util.ArrayList;
import static helpers.Artist.*;
public class UI {
	
	private ArrayList<Button> buttonList;
	
	public UI(){
		buttonList = new ArrayList<Button>();
	}
	
	public void addButton(String name, String textureName, int x, int y){
		buttonList.add(new Button(name, quickLoad(textureName), x, y));
	}
	
	public void draw(){
		for(Button b : buttonList){
			drawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
		}
	}
}
