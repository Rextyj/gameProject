package helpers;

import org.lwjgl.Sys;

public class Clock {
	
	private static boolean paused = false;
	private static long lastFrame;
	private static float d = 0, multiplier = 1,totalTime;
	
	public static long getTime(){
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}
	
	public static float getDelta(){//change in time
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = getTime();
		//stops enemy from jumping when moving the window
		if(delta * 0.001f > 0.05f){
			return 0.05f;
		}
		return delta * 0.001f;
	}
	
	public static float delta(){
		if(paused){
			return 0;
		} else {
			return d * multiplier;
		}
	}
	
	public static float getTotalTime(){
		return totalTime;
	}
	
	public static float multiplier(){
		return multiplier;
	}
	
	public static void update(){
		d = getDelta();
		if(!paused) {
			totalTime += d;
		} else {
			return;
		}
		
	}
	
	public static void togglePause() {
		paused = !paused;
	}
	
	public static void changeMultiplier(float change){//speed up or slow down
		//The Clock multiplier cannot go under 0.2 or go beyound 3
		if(multiplier + change < 0.2 || multiplier + change > 3){
			return;
		} else {
			multiplier += change;
		}
	}
}
