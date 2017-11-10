package data;

import static helpers.Artist.*;

import org.newdawn.slick.opengl.Texture;

import UI.UI;

public class MainMenu {
	private Texture background;
	private UI menuUI;
	
	public MainMenu(){
		background = quickLoad("mainMenu");
		menuUI = new UI();
		menuUI.addButton("Play", "playButton", WIDTH / 2 - 128, (int) (HEIGHT * 0.45f));
	}
	
	public void update(){
		drawQuadTex(background, 0, 0, 2048, 1024);
		menuUI.draw();
	}
}
