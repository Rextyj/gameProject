package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;
public enum TowerType {
	
	RedCannon(new Texture[]{quickLoad("cannonBase"), quickLoad("cannonGun")}, 10),
	BlueCannon(new Texture[]{quickLoad("cannonBaseBlue"), quickLoad("cannonGunBlue")}, 20);
	
	
	Texture[] textures;
	int damage;
	
	TowerType(Texture[] textures, int damage){
		this.textures = textures;
		this.damage = damage;
	}
	
}
