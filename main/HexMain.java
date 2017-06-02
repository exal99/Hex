package main;

import hexGrid.DrawableHexGrid;
import hexGrid.HexGrid;
import hexagon.Hexagon;
import processing.core.PApplet;

public class HexMain extends PApplet {
	
	public static int HEX_SIZE = 50;
	private DrawableHexGrid<Hexagon> grid;
	
	private int row,  col;
	private int rows, cols;
	@Override
	public void settings() {
		// width:  n * sqrt(3) + sqrt(3)/2
		// height: 2n - (n-1)/2)
		size((int) Math.round(HEX_SIZE * (8 * sqrt(3) + sqrt(3)/2)), (int) Math.round(HEX_SIZE * (14 - 3)));
	}
	
	@Override
	public void setup() {
		rows = 7;
		cols = 8;
		grid = new DrawableHexGrid<Hexagon>(this, cols, rows, sqrt(3)/2 * HEX_SIZE, HEX_SIZE, Hexagon.class);
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				float x = grid.getX() + (((row) * PApplet.sqrt(3)/2) + col * PApplet.sqrt(3)) * HexMain.HEX_SIZE;
				float y = grid.getY() + 1.5f * row * HexMain.HEX_SIZE;
				grid.set(row, col, new Hexagon(this, x, y));
			} 
		}
		row = 0;
		col = 0;
	}
	
	@Override
	public void draw() {
		// start: sqrt(3)/2, 1
		background(51);
		grid.draw();
		if (frameCount % 10 == 0) {
			grid.get(row, col).setColor(color(200,0,0));
			for (Hexagon hex : grid.getNeighbor(row, col)) {
				hex.setColor(color(200,0,0));
			}
			col++;
			row += (col == cols) ? 1 : 0;
			col %= cols;
			row %= rows;
			grid.get(row, col).setColor(color(0,0,200));
			for (Hexagon hex : grid.getNeighbor(row, col)) {
				hex.setColor(color(0,200,0));
			}
		}
	}
	
	@Override
	public void mousePressed() {
		grid.mousePress(mouseX, mouseY);
	}

	public static void main(String[] args) {
		PApplet.main("main.HexMain");
	}

}
