package data;

import static helpers.Artist.quickLoad;

import org.newdawn.slick.opengl.Texture;

public enum ProjectileType {
	CannonBall(quickLoad("bullet"), 10, 200),
	IceBall(quickLoad("iceBullet"), 6, 150);
	
	
	Texture texture;
	int damage;
	float speed;
	
	ProjectileType(Texture texture, int damage, float speed){
		this.texture = texture;
		this.damage = damage;
		this.speed = speed;
	}
}
