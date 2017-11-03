package data;

import static helpers.Clock.delta;

import java.util.ArrayList;

public class Wave {

	private float timeSinceLastSpawn, spawnTime;
	private Enemy enemyType;
	private ArrayList<Enemy> enemyList;

	public Wave(float spawnTime, Enemy enemyType) {
		this.enemyType = enemyType;
		this.spawnTime = spawnTime;
		enemyList = new ArrayList<Enemy>();
	}

	public void update() {
		timeSinceLastSpawn += delta();
		if (timeSinceLastSpawn > spawnTime) {
			spawn();
			timeSinceLastSpawn = 0;
		}

		for (Enemy e : enemyList) {
			if (e.isAlive()) {
				e.update();
				e.draw();
			}

		}
	}

	private void spawn() {
		enemyList.add(new Enemy(enemyType.getTexture(), enemyType.getStartTile(), enemyType.getGrid(), 64, 64,
				enemyType.getSpeed()));
	}
}
