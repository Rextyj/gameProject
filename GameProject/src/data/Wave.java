package data;

import static helpers.Artist.TILE_SIZE;
import static helpers.Clock.delta;

import java.util.concurrent.CopyOnWriteArrayList;

public class Wave {

	private float timeSinceLastSpawn, spawnTime;
	private Enemy enemyType;
	private CopyOnWriteArrayList<Enemy> enemyList;
	private int enemiesPerWave, enemiesSpawned;
	private boolean waveCompleted;
	
	public Wave(Enemy enemyType, float spawnTime, int enemiesPerWave) {
		this.enemyType = enemyType;
		this.spawnTime = spawnTime;
		this.enemiesPerWave = enemiesPerWave;
		this.enemiesSpawned = 0;
		this.timeSinceLastSpawn = 0;
		this.enemyList = new CopyOnWriteArrayList<Enemy>();
		this.waveCompleted = false;
		
		spawn();//spawn the first enemy when program started
	}

	public void update() {
		//continue spawning until reaching the number required
		if(enemiesSpawned < enemiesPerWave){
			timeSinceLastSpawn += delta();
			if (timeSinceLastSpawn > spawnTime) {
				spawn();
				timeSinceLastSpawn = 0;
			}
		}
		
		for (Enemy e : enemyList) {
			if (e.isAlive()) {
				e.update();
				e.draw();
			} else {
				//remove dead enemies
				enemyList.remove(e);
				//but, if we use arraylist, there will be concurrent writing error
				//so we use copyonwritearraylist
			}
		}
		
		if(enemyList.isEmpty()){
			waveCompleted = true;
		}
	}

	private void spawn() {
		enemyList.add(new Enemy(enemyType.getTexture(), enemyType.getStartTile(), enemyType.getGrid(), TILE_SIZE, TILE_SIZE,
				enemyType.getSpeed(), enemyType.getHealth()));
		enemiesSpawned++;
	}
	
	public boolean isCompleted(){
		return waveCompleted;
	}

	public CopyOnWriteArrayList<Enemy> getEnemyList() {
		return enemyList;
	}
	
	
}
