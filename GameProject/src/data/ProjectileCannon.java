package data;

import org.newdawn.slick.opengl.Texture;

public class ProjectileCannon extends Projectile{

	public ProjectileCannon(ProjectileType type, Enemy target, float x, float y, int width, int height) {
		super(type, target, x, y, width, height);
	}

	@Override
	public void damage(){
		super.damage();
	}
	
}
