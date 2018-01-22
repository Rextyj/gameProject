package helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import data.Tile;
import data.TileGrid;
import data.TileType;

import static helpers.Artist.TILE_SIZE;

public class LevelDesign {
	
	public static void saveMap(String mapName, TileGrid grid){
		String mapData = "", startEndInfo = "";
		for(int i = 0; i < grid.getTilesWide(); i++){
			for(int j = 0; j < grid.getTilesHigh(); j++){
				mapData += getTileID(grid.getTile(i, j));
			}
		}
		
		//writing the start and end info into a separate file
		Tile[] startEndTiles = new Tile[2];
		startEndTiles[0] = grid.getTile(grid.getTilesWide() - grid.getInvalidWidth(), 0);
		startEndTiles[1] = grid.getTile(grid.getTilesWide() - grid.getInvalidWidth() + 1, 0);
		
		for(int i = 0; i < 2; i++) {
			startEndInfo += startEndTiles[i].getXPlace() + "-" + startEndTiles[i].getYPlace() + "\n";
		}
		
		try {
			File file = new File(mapName);
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(mapData);
			bw.close();
			
			File startEndLocation = new File(mapName + "startEndLoc");
			BufferedWriter bw2 = new BufferedWriter(new FileWriter(startEndLocation));
			bw2.write(startEndInfo);
			bw2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static TileGrid loadMap(String mapName){
		TileGrid grid = new TileGrid();
		//--------------------------------------------------
		String[][] locInfo = new String[2][2];
		TileType[] tileTypes = new TileType[2];
		tileTypes[0] = TileType.Start;
		tileTypes[1] = TileType.End;
		//---------------------------------------------------
		try{
			BufferedReader br = new BufferedReader(new FileReader(mapName));
			String data = br.readLine();
			for(int i = 0; i < grid.getTilesWide(); i++){
				for(int j = 0; j < grid.getTilesHigh(); j++){
					//note, setTile will do nothing for the tiles in the invalid region!!!!!
					grid.setTile(i, j, getTileType(data.substring(i * grid.getTilesHigh() + j,i * grid.getTilesHigh() + j + 1)));
				}
			}
			br.close();
			
			BufferedReader br2 = new BufferedReader(new FileReader(mapName + "startEndLoc"));
			
			for(int i = 0; i < 2; i++) {
				 locInfo[i] = br2.readLine().split("-");
			}
			br2.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		for(int i = 0; i < 2; i++) {
			Tile currentTile = new Tile(Integer.parseInt(locInfo[i][0]) * TILE_SIZE, Integer.parseInt(locInfo[i][1]) * TILE_SIZE, TILE_SIZE, TILE_SIZE, tileTypes[i]); 
			System.out.println("the tile type is" + currentTile.getType().toString());
			//this is a different setTile method
			grid.setTile(grid.getTilesWide() - grid.getInvalidWidth() + i, 0, currentTile);
		}
		return grid;
	}
	
	public static TileType getTileType(String ID){
		TileType type = TileType.Null;
		
		switch(ID){
		case "0":
			type = TileType.Grass;
			break;
		case "1":
			type = TileType.Dirt;
			break;
		case "2":
			type = TileType.Water;
			break;
		case "3":
			type = TileType.Null;
			break;
		case "4":
			type = TileType.Start;
			break;
		case "5":
			type = TileType.End;
			break;
		}
		
		
		return type;
	}
	
	public static String getTileID(Tile t){
		String ID = "E";//E stands for error
		switch(t.getType()){
		case Grass:
			ID = "0";
			break;
		case Dirt:
			ID = "1";
			break;
		case Water:
			ID = "2";
			break;
		case Null:
			ID = "3";
			break;
		case Start:
			ID = "4";
			break;
		case End:
			ID = "5";
			break;
		}
		return ID;
	}
}
