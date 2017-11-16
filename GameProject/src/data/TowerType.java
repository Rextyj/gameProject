package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;
public enum TowerType {
	
	RedCannon(new Texture[]{quickLoad("cannonBase"), quickLoad("cannonGun")}, 10, 500, 3),
	BlueCannon(new Texture[]{quickLoad("cannonBaseBlue"), quickLoad("cannonGunBlue")}, 20, 500, 2);
	
	
	Texture[] textures;
	int damage, range;
	float firingSpeed;
	
	TowerType(Texture[] textures, int damage, int range, float firingSpeed){
		this.textures = textures;
		this.damage = damage;
		this.range = range;
		this.firingSpeed = firingSpeed;
	}
	
}
