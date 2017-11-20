package data;

import org.newdawn.slick.opengl.Texture;

public class ProjectileIce extends Projectile{

	public ProjectileIce(Texture texture, Enemy target, float x, float y, int width, int height, 
			float speed, int damage) {
		super(texture, target, x, y, width, height, speed, damage);
	}
	
	@Override
	public void damage(){
		super.getTarget().setSpeed(4f);
		super.damage();
	}
}
