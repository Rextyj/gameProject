package helpers;

import data.Editor;
import data.Game;
import data.MainMenu;
import data.TileGrid;

import static helpers.LevelDesign.loadMap;

public class StateManager {
	public static enum GameState {
		MAINMENU, GAME, EDITOR
	}
	
	public static GameState gameState = GameState.MAINMENU;
	public static MainMenu mainMenu;
	public static Game game;
	public static Editor editor;
	//fps tracking 
	public static long nextSecond = System.currentTimeMillis() + 1000;
	public static int framesInLastSecond = 0;
	public static int framesInCurrentSecond = 0;
	
	//the loadmap function will return a TileGrid constructed by the default constructor that is all grass
	static TileGrid map = loadMap("newMap1");
	
	public static void update(){
		switch(gameState) {
		case MAINMENU:
			if(mainMenu == null){
				mainMenu = new MainMenu();
			}
			mainMenu.update();
			break;
		case GAME:
			if(game == null){
				game = new Game(map);
			}
			game.update();
			break;
		case EDITOR:
			if(editor == null){
				editor = new Editor();
			}
			editor.update();
			break;
		}
		
		/**
		 * FPS Tracking
		 */
		long currentTime = System.currentTimeMillis();
		if(currentTime > nextSecond) {
			nextSecond += 1000;
			framesInLastSecond = framesInCurrentSecond;
			framesInCurrentSecond = 0;
		}
		framesInCurrentSecond++;
		
	}
	
	public static void setState(GameState newState){
		gameState = newState;
	}
}
