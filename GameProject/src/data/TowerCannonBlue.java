package data;

import java.util.concurrent.CopyOnWriteArrayList;

public class TowerCannonBlue extends Tower{
	
	public TowerCannonBlue(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		super(type, startTile, enemies);
	}

	@Override
	public void shoot(Enemy target) {
		//+16 so that projectile is centered
		super.projectiles.add(new ProjectileCannon(super.type.projectileType, super.target, super.getX() + 16, super.getY() + 16, 32, 32));
		//reduce hidden health as soon as the projectile is added
		super.target.reduceHiddenHealth(super.type.projectileType.damage);
	}
			
}