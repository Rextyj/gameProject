package data;

import static helpers.Clock.delta;
import static helpers.Artist.TILE_SIZE;

import java.util.ArrayList;

public class Wave {

	private float timeSinceLastSpawn, spawnTime;
	private Enemy enemyType;
	private ArrayList<Enemy> enemyList;
	private int enemiesPerWave;
	private boolean waveCompleted;
	
	public Wave(Enemy enemyType, float spawnTime, int enemiesPerWave) {
		this.enemyType = enemyType;
		this.spawnTime = spawnTime;
		this.enemiesPerWave = enemiesPerWave;
		this.timeSinceLastSpawn = 0;
		this.enemyList = new ArrayList<Enemy>();
		this.waveCompleted = false;
		
		spawn();//spawn the first enemy when program started
	}

	public void update() {
		boolean allEnemiesDead = true;
		//continue spawning until reaching the number required
		if(enemyList.size() < enemiesPerWave){
			timeSinceLastSpawn += delta();
			if (timeSinceLastSpawn > spawnTime) {
				spawn();
				timeSinceLastSpawn = 0;
			}
		}
		
		for (Enemy e : enemyList) {
			if (e.isAlive()) {
				allEnemiesDead = false;
				e.update();
				e.draw();
			}
		}
		
		if(allEnemiesDead){
			waveCompleted = true;
		}
	}

	private void spawn() {
		enemyList.add(new Enemy(enemyType.getTexture(), enemyType.getStartTile(), enemyType.getGrid(), TILE_SIZE, TILE_SIZE,
				enemyType.getSpeed(), enemyType.getHealth()));
	}
	
	public boolean isCompleted(){
		return waveCompleted;
	}

	public ArrayList<Enemy> getEnemyList() {
		return enemyList;
	}
	
	
}
