package data;

import static helpers.Artist.drawQuadTex;

import java.util.concurrent.CopyOnWriteArrayList;

public class TowerIce extends Tower{

	public TowerIce(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		super(type, startTile, enemies);
	}


	@Override
	public void draw(){
		for(int i = 0; i < super.getTextures().length; i++){
			drawQuadTex(super.getTextures()[i], super.getX(), super.getY(), super.getWidth(), super.getHeight());
		}
	}

	@Override
	public void shoot(Enemy target) {
		//16 is the offset so the project tile (which is 32 x 32) is centered
		super.projectiles.add(new ProjectileIce(super.type.projectileType, super.target, super.getX() + 16, super.getY() + 16, 32, 32));
		//reduce hidden health as soon as the projectile is added
		super.target.reduceHiddenHealth(super.type.projectileType.damage);
	}
}
