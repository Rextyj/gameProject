package helpers;

import static helpers.LevelDesign.loadMap;

import data.Editor;
import data.Game;
import data.MainMenu;
import data.PauseMenu;
import data.TileGrid;

//State manager generate the instances for each major component of the game
public class StateManager {
	public static enum GameState {
		MAINMENU, GAME, EDITOR, PAUSEMENU
	}
	
	public static GameState gameState = GameState.MAINMENU;
	public static MainMenu mainMenu;
	public static PauseMenu pauseMenu;
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
			if(editor != null) {
				if(editor.isSavedDuringThisSession()) {
					//apply the new map when a new map is saved during the session
					map = loadMap("newMap1");
					game = new Game(map);
				}
				//reset it to false so it won't reload every time resume the game
				editor.setSavedDuringThisSession(false);
			}
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
		case PAUSEMENU:
			if(pauseMenu == null) {
				pauseMenu = new PauseMenu();
			}
			//game needs to be still drawing to make the it visible through the background
			game.keepDrawing();
			pauseMenu.update();
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
