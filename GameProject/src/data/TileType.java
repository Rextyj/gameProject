package data;

public enum TileType {
	Grass("grass64", true), Dirt("dirt64", false), Water("water64", false), Null("null",false), Start("startTile", false), End("baseTile", false);
	
	String textureName;
	boolean buildable;
	
	TileType(String textureName, boolean buildable){
		this.textureName = textureName;
		this.buildable = buildable;
	}

}
