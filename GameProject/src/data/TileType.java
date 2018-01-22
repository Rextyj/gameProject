package data;

public enum TileType {
	Grass("grassTile", true), Dirt("dirtTile", false), Water("waterTile", false), Null("null",false), Start("startTile", false), End("baseTile", false);
	
	String textureName;
	boolean buildable;
	
	TileType(String textureName, boolean buildable){
		this.textureName = textureName;
		this.buildable = buildable;
	}

}
