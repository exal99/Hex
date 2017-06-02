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
	
	@SuppressWarnings("unchecked")
	public E[] getNeighbor(int row, int col) {
		
		// (-y,x), (-y,+x), (y,-x), (y,+x), (+y, -x), (+y,x)
		E[] tempNeighbors = (E[]) Array.newInstance(clazz, 6);
		int index = 0;
		//int xDiff = (row % 2 == 0) ? -1 : 0;
		for (int y = row - 1; y <= row + 1; y++) {
			if (y == row) {
				tempNeighbors[index] = (col - 1 >= 0) ? hexGrid[y][col - 1] : null;
				index++;
				tempNeighbors[index] = (col + 1 < width) ? hexGrid[y][col + 1] : null;
				index++;
			} else if (y == row -1) {
				tempNeighbors[index] = (y >= 0 && y < height && col >= 0) ? hexGrid[y][col] : null;
				index++;
				tempNeighbors[index] = (y >= 0 && y < height && col + 1 < width) ? hexGrid[y][col + 1] : null;
				index++;
			} else {
				tempNeighbors[index] = (y >= 0 && y < height && col >= 0) ? hexGrid[y][col] : null;
				index++;
				tempNeighbors[index] = (y >= 0 && y < height && col - 1 >= 0) ? hexGrid[y][col - 1] : null;
				index++;
			}
		}
		E[] neighbors = (E[]) Array.newInstance(clazz, getNumNeighbor(row, col));
		index = 0;
		for (E neighbor : tempNeighbors) {
			if (neighbor != null) {
				neighbors[index] = neighbor;
				index++;
			}
		}
		return neighbors;
	}
		
	
	public int getNumNeighbor(int row, int col) {
		int numNeighbors = 0;
		//int xDiff = (row % 2 == 0) ? -1 : 0;
		for (int y = row - 1; y <= row + 1; y++) {
			if (y == row) {
				numNeighbors += (col - 1 >= 0) ? 1 : 0;
				numNeighbors += (col + 1 < width) ? 1 : 0;
			} else if (y == row - 1){
				numNeighbors += (y >= 0 && y < height && col >= 0) ? 1 : 0;
				numNeighbors += (y >= 0 && y < height && col + 1 < width) ? 1 : 0;
			} else {
				numNeighbors += (y >= 0 && y < height && col >= 0) ? 1 : 0;
				numNeighbors += (y >= 0 && y < height && col - 1 >= 0) ? 1 : 0;
			}
		}
		return numNeighbors;
	}
	
	public E get(int row, int col) {
		return hexGrid[row][col];
	}


}
