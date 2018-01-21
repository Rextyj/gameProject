package data;

import static helpers.Artist.*;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;

public class MainMenu {
	private Texture background;
	private UI menuUI;
	private boolean leftMouseButtonDown;
	
	public MainMenu(){
		this.leftMouseButtonDown = false;
		background = quickLoad("mainMenu");
		menuUI = new UI();
		menuUI.addButton("Play", "playButton", WIDTH / 2 - 128, (int) (HEIGHT * 0.45f));
		menuUI.addButton("Quit", "quitButton", WIDTH / 2 - 128, (int) (HEIGHT * 0.55f));
		menuUI.addButton("Editor", "editorButton", WIDTH / 2 - 128, (int) (HEIGHT * 0.65f));
		
	}
	
	
	//Execute the actions if user clicked the button
	private void updateButtons(){
		if(Mouse.isButtonDown(0) && !leftMouseButtonDown){
			if(menuUI.isButtonClicked("Play")){
				StateManager.setState(GameState.GAME);
			}
			if(menuUI.isButtonClicked("Quit")){
//				StateManager.setState(GameState.GAME);
				System.exit(0);
			}
			if(menuUI.isButtonClicked("Editor")){
				StateManager.setState(GameState.EDITOR);
			}
		}
		leftMouseButtonDown = Mouse.isButtonDown(0);
	}
	
	public void update(){
		drawQuadTex(background, 0, 0, 2048, 1024);
		menuUI.draw();
		updateButtons();
	}
}
