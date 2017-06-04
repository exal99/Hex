package hexGrid;

import java.lang.reflect.Array;

public class HexGrid <E>{
	
	protected E[][] hexGrid;
	protected int width;
	protected int height;
	private Class<E> clazz;
	
	@SuppressWarnings("unchecked")
	public HexGrid(int width, int height, Class<E> clazz) {
		hexGrid = (E[][]) Array.newInstance(clazz, height, width);
		
		this.width = width;
		this.height = height;
		this.clazz = clazz;
	}
	
	public void set(int row, int col, E val) {
		hexGrid[row][col] = val;
	}
	
	public E[] getNeighbor(int row, int col) {
		return getNeighbours(row, col, hexGrid, clazz);
	}
		
	
	public int getNumNeighbor(int row, int col) {
		return getNumNeighbors(row, col, hexGrid);
	}
	
	protected <T> int getNumNeighbors(int row, int col, T[][] hexGrid) {
		int numNeighbors = 0;
		//int xDiff = (row % 2 == 0) ? -1 : 0;
		for (int y = row - 1; y <= row + 1; y++) {
			if (y == row) {
				numNeighbors += (col - 1 >= 0 && hexGrid[y][col - 1] != null) ? 1 : 0;
				numNeighbors += (col + 1 < hexGrid[0].length && hexGrid[y][col + 1] != null) ? 1 : 0;
			} else if (y == row - 1){
				numNeighbors += (y >= 0 && y < hexGrid.length && col >= 0 && hexGrid[y][col] != null) ? 1 : 0;
				numNeighbors += (y >= 0 && y < hexGrid.length && col + 1 <  hexGrid[0].length && hexGrid[y][col + 1] != null) ? 1 : 0;
			} else {
				numNeighbors += (y >= 0 && y < hexGrid.length && col >= 0 && hexGrid[y][col] != null) ? 1 : 0;
				numNeighbors += (y >= 0 && y < hexGrid.length && col - 1 >= 0 && hexGrid[y][col - 1] != null) ? 1 : 0;
			}
		}
		return numNeighbors;
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T[] getNeighbours(int row, int col, T[][] hexGrid, Class<T> clazz) {
		// (-y,x), (-y,+x), (y,-x), (y,+x), (+y, -x), (+y,x)
		T[] tempNeighbors = (T[]) Array.newInstance(clazz, 6);
		int index = 0;
		//int xDiff = (row % 2 == 0) ? -1 : 0;
		for (int y = row - 1; y <= row + 1; y++) {
			if (y == row) {
				tempNeighbors[index] = (col - 1 >= 0) ? hexGrid[y][col - 1] : null;
				index++;
				tempNeighbors[index] = (col + 1 < hexGrid[0].length) ? hexGrid[y][col + 1] : null;
				index++;
			} else if (y == row -1) {
				tempNeighbors[index] = (y >= 0 && y < hexGrid.length && col >= 0) ? hexGrid[y][col] : null;
				index++;
				tempNeighbors[index] = (y >= 0 && y < hexGrid.length && col + 1 < hexGrid[0].length) ? hexGrid[y][col + 1] : null;
				index++;
			} else {
				tempNeighbors[index] = (y >= 0 && y < hexGrid.length && col >= 0) ? hexGrid[y][col] : null;
				index++;
				tempNeighbors[index] = (y >= 0 && y < hexGrid.length && col - 1 >= 0) ? hexGrid[y][col - 1] : null;
				index++;
			}
		}
		T[] neighbors = (T[]) Array.newInstance(clazz, getNumNeighbors(row, col, hexGrid));
		index = 0;
		for (T neighbor : tempNeighbors) {
			if (neighbor != null) {
				neighbors[index] = neighbor;
				index++;
			}
		}
		return neighbors;
	}
	
	public E get(int row, int col) {
		return hexGrid[row][col];
	}


}
