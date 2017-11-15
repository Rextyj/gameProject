package data;

import static helpers.Artist.beginSession;

import org.lwjgl.opengl.Display;

import helpers.Clock;
import helpers.StateManager;
//main class for the game
public class Boot {
	
	public Boot() {
		
		beginSession();
		
		
		
//		Game game = new Game(map);
		//keep the display open until close is requested
		while(!Display.isCloseRequested()){//keep running until hitting x
			Clock.update();
			
//			game.update();
			StateManager.update();
		    
		    Display.update();
			Display.sync(60);
		}
		
		Display.destroy();//end the game
	}
	public static void main(String[] args) {
		new Boot();
	}
}
