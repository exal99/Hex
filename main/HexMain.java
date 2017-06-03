package main;

import hexGrid.GameHexGrid;
import hexagon.Hexagon;
import processing.core.PApplet;
import processing.event.MouseEvent;

public class HexMain extends PApplet {
	
	private static int HEX_SIZE = 48;
	private static int HEX_GRID_SIZE = 50;
	
	private GameHexGrid grid;
	
	private int row,  col;
	private int rows, cols;
	
	private int clickTime;
	private int pressX, pressY;
	
	private boolean mouseDown;
	
	public static void setHexSize(int newSize) {
		HEX_GRID_SIZE = newSize;
		HEX_SIZE = (int) Math.round(0.96 * HEX_GRID_SIZE);
	}
	
	public static int getHexSize() {
		return HEX_SIZE;
	}
	
	public static int getHexGridSize() {
		return HEX_GRID_SIZE;
	}
	
	@Override
	public void settings() {
		// width:  n * sqrt(3) + sqrt(3)/2
		// height: 2n - (n-1)/2)
		size(1200,800);
	}
	
	@Override
	public void setup() {
		rows = 11;
		cols = 11;
		grid = new GameHexGrid(this, cols, rows);
		/**
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				float x = sqrt(3)/2 * HEX_SIZE + (((row) * PApplet.sqrt(3)/2) + col * PApplet.sqrt(3)) * HexMain.HEX_SIZE;
				float y =  HEX_SIZE + 1.5f * row * HexMain.HEX_SIZE;
				grid.set(row, col, new Hexagon(this, x, y));
			} 
		}**/
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
		if (clickTime != -1 && millis() - clickTime > 150) {
			mouseDown = true;;
		}

	}
	
	@Override
	public void mousePressed() {
		clickTime = millis();
		pressX = mouseX;
		pressY = mouseY;
		
	}
	
	@Override
	public void mouseReleased() {
		if (millis() - clickTime <= 150) {
			grid.mousePress(pressX, pressY);
			clickTime = -1;
		}
		mouseDown = false;
	}
	
	@Override
	public void mouseDragged() {
		if (mouseDown) {
			grid.mouseDragged(mouseX - pressX, mouseY - pressY);
			pressX = mouseX;
			pressY = mouseY;
		}
	}
	
	@Override
	public void mouseMoved() {
		grid.mouseMove(mouseX, mouseX);
	}
	
	@Override
	public void mouseWheel(MouseEvent event) {
		int dir = event.getCount();
		grid.mouseWheel(dir);
	}

	public static void main(String[] args) {
		PApplet.main("main.HexMain");
	}
	

}
