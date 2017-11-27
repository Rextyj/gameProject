package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;
public enum TowerType {
	
	RedCannon(new Texture[]{quickLoad("cannonBase"), quickLoad("cannonGun")}, ProjectileType.CannonBall, 10, 500, 3),
	BlueCannon(new Texture[]{quickLoad("cannonBaseBlue"), quickLoad("cannonGunBlue")}, ProjectileType.CannonBall, 20, 500, 2),
	IceCannon(new Texture[]{quickLoad("iceTowerBase"), quickLoad("iceTowerCannon")}, ProjectileType.IceBall, 20, 500 , 2);
	
	
	Texture[] textures;
	ProjectileType projectileType;
	int damage, range;
	float firingSpeed;
	
	TowerType(Texture[] textures, ProjectileType projectileType, int damage, int range, float firingSpeed){
		this.textures = textures;
		this.projectileType = projectileType;
		this.damage = damage;
		this.range = range;
		this.firingSpeed = firingSpeed;
	}
	
}
