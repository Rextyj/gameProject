package data;

import static helpers.Artist.TILE_SIZE;
import static helpers.Clock.delta;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Wave {

	private float timeSinceLastSpawn, spawnTime;
	private Enemy[] enemyTypes;
	private CopyOnWriteArrayList<Enemy> enemyList;
	private int enemiesPerWave, enemiesSpawned;
	private boolean waveCompleted;
	private int enemyTypeIndex;
	private Random r;
	
	public Wave(Enemy[] enemyTypes, float spawnTime, int enemiesPerWave) {
		this.enemyTypes = enemyTypes;
		this.spawnTime = spawnTime;
		this.enemiesPerWave = enemiesPerWave;
		this.enemiesSpawned = 0;
		this.timeSinceLastSpawn = 0;
		this.enemyList = new CopyOnWriteArrayList<Enemy>();
		this.waveCompleted = false;
		this.r = new Random();
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
//		enemyTypeIndex = r.nextInt(enemyTypes.length);
		enemyTypeIndex = 2;
		//*************************************************************************************************************************************
		//Note that with this way of adding new enemies into the list, overriding methods in subclasses is not going to work!!!!!!!
		//*************************************************************************************************************************************
		enemyList.add(new Enemy(enemyTypes[enemyTypeIndex].getTexture(), enemyTypes[enemyTypeIndex].getStartTile(), enemyTypes[enemyTypeIndex].getGrid(), TILE_SIZE, TILE_SIZE,
				enemyTypes[enemyTypeIndex].getSpeed(), enemyTypes[enemyTypeIndex].getHealth()));
		enemiesSpawned++;
	}
	
	public boolean isCompleted(){
		return waveCompleted;
	}

	public CopyOnWriteArrayList<Enemy> getEnemyList() {
		return enemyList;
	}
	
	
}
