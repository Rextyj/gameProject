public void pathFinding(TileGrid grid, Tile start, Tile target){
	Set<Integer[]> visited = new HashSet<>();
	List<Integer[]> path = new ArrayList<>();
	DFS(grid, start.getX(), start.getY(), target.getX(), target.getY(), visited);
}

public void DFS(TileGrid grid, int x, int y, int targetX, int targetY, Set visited){
	if(x < 0 || x >= grid.map.length || y < 0 || y >= grid.map[0].length){
		return;
	}

	if(visited.contains(new int[]{x, y}) || grid.getTile(x, y).type != TileType.Dirt) {//can't go through other types of tiles
		return;
	}

	path.add(new int[]{x, y});
	visited.add(new int[]{x, y});//add the current tile to the visited list

	DFS(grid, x + 1, y, targetX, targetY, visited);
	DFS(grid, x, y + 1, targetX, targetY, visited);
	DFS(grid, x - 1, y, targetX, targetY, visited);
	DFS(grid, x, y - 1, targetX, targetY, visited);

}
