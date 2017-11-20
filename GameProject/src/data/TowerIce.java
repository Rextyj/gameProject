package data;

import java.util.ArrayList;
import static helpers.Artist.drawQuadTex;

public class TowerIce extends Tower{

	public TowerIce(TowerType type, Tile startTile, ArrayList<Enemy> enemies) {
		super(type, startTile, enemies);
	}
	
	
	@Override
	public void draw(){
		for(int i = 0; i < super.getTextures().length; i++){
			drawQuadTex(super.getTextures()[i], super.getX(), super.getY(), super.getWidth(), super.getHeight());
		}
	}
	
}
