package data;

import static helpers.Artist.beginSession;

import org.lwjgl.opengl.Display;

import helpers.Clock;
import helpers.StateManager;
//main class for the game
public class Boot {
	
	public Boot() {
		
		//call artist class method to initialize OpenGL calls
		beginSession();
		
		//Main game loop
		while(!Display.isCloseRequested()){//keep running until hitting x
			Clock.update();
			StateManager.update();
		    Display.update();
			Display.sync(60);
		}
		
		//end the game
		Display.destroy();
	}
	public static void main(String[] args) {
		new Boot();
	}
}
