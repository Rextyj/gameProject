package data;

import org.newdawn.slick.opengl.Texture;

public class ProjectileCannon extends Projectile{

	public ProjectileCannon(Texture texture, Enemy target, float x, float y, int width, int height, float speed,
			int damage) {
		super(texture, target, x, y, width, height, speed, damage);
	}
	
}
