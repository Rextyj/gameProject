package data;

import static helpers.Artist.HEIGHT;
import static helpers.Artist.WIDTH;
import static helpers.Artist.quickLoad;
import static helpers.Artist.drawQuadTexTrans;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;

public class PauseMenu {
	private Texture background;
	private UI menuUI;
	private boolean leftMouseButtonDown;
	
	public PauseMenu(){
		this.leftMouseButtonDown = false;
		background = quickLoad("pauseMenuBackground");
		menuUI = new UI();
		menuUI.addButton("Resume", "resumeButton", WIDTH / 2 - 128, (int) (HEIGHT * 0.45f));
		menuUI.addButton("Restart", "restartButton", WIDTH / 2 - 128, (int) (HEIGHT * 0.55f));
		menuUI.addButton("MainMenu", "mainMenuButton", WIDTH / 2 - 128, (int) (HEIGHT * 0.65f));
		
	}
	
	
	//Execute the actions if user clicked the button
	private void updateButtons(){
		if(Mouse.isButtonDown(0) && !leftMouseButtonDown){
			if(menuUI.isButtonClicked("Resume")){
				StateManager.setState(GameState.GAME);
			}
			if(menuUI.isButtonClicked("MainMenu")){
				StateManager.setState(GameState.MAINMENU);
			}
			if(menuUI.isButtonClicked("Restart")){
				StateManager.setState(GameState.RESTART);
			}
		}
		this.leftMouseButtonDown = false;
	}
	
	public void update(){
		drawQuadTexTrans(background, 0.8f, 0, 0, 2048, 1024);
		menuUI.draw();
		updateButtons();
	}
}
