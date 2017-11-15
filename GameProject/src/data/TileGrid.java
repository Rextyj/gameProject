package data;

import static helpers.Artist.TILE_SIZE;

public class TileGrid {
	
	public Tile[][] map;
	private int tilesWide, tilesHigh;
	
	public TileGrid(){
		this.tilesWide = 20;
		this.tilesHigh = 15;
		map = new Tile[20][15];
		for(int i =0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){
				map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Grass);//the coordinate system in opengl
				//uses column number for x instead of y
				//in Java, [10][5] means 10 rows and 5 columns
				//but the glTranslatef sees x as the column number, thus would need to be
				//glTranslatef(5, 10, 0);
			}
		}
	}
	
	public TileGrid(int[][] newMap){
		this.tilesWide = newMap[0].length;
		this.tilesHigh = newMap.length;
		map = new Tile[tilesWide][tilesHigh];
		for(int i =0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){
				
				switch(newMap[j][i]) {//the Java array x and y are swapped comparing to the coordinate system we use
				case 0:
					map[i][j] = new Tile(i*TILE_SIZE, j*TILE_SIZE, TILE_SIZE,TILE_SIZE, TileType.Grass);
					break;
				case 1:
					map[i][j] = new Tile(i*TILE_SIZE, j*TILE_SIZE, TILE_SIZE,TILE_SIZE, TileType.Dirt);
					break;
				case 2:
					map[i][j] = new Tile(i*TILE_SIZE, j*TILE_SIZE, TILE_SIZE,TILE_SIZE, TileType.Water);
					break;
				}
			}
		}
	}
	
	public void setTile(int xCoord, int yCoord, TileType type){
		map[xCoord][yCoord] = new Tile(xCoord*TILE_SIZE, yCoord*TILE_SIZE, TILE_SIZE, TILE_SIZE, type);//remember the input argument does not match the coordinate system of Java
	}
	
	public Tile getTile(int xPlace, int yPlace){//input the coor according to our coordinate sys
		if(xPlace < tilesWide && xPlace > -1 && yPlace < tilesHigh && yPlace > -1){
			return map[xPlace][yPlace];
		} else {
			return new Tile(0, 0, 0, 0, TileType.Null);
		}
		
	}
	
	public void draw(){
		for(int i =0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){
//				Tile t = map[i][j];
//				drawQuadTex(t.getTexture(), t.getX(), t.getY(), t.getWidth(), t.getHeight());
				map[i][j].draw();
			}
		}
	}

	public int getTilesWide() {
		return tilesWide;
	}

	public void setTilesWide(int tilesWide) {
		this.tilesWide = tilesWide;
	}

	public int getTilesHigh() {
		return tilesHigh;
	}

	public void setTilesHigh(int tilesHigh) {
		this.tilesHigh = tilesHigh;
	}
	
	
}
