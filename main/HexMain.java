package main;

import java.util.HashMap;

import hexGrid.GameHexGrid;
import hexGrid.GameHexGrid.Node;
import hexagon.Hexagon;
import processing.core.PApplet;
import processing.event.MouseEvent;

public class HexMain extends PApplet {
	
	private static int HEX_SIZE = 48;
	private static int HEX_GRID_SIZE = 50;
	public static final float SQRT_3 = PApplet.sqrt(3);
	public static final float SQRT_3_2 = PApplet.sqrt(3) / 2;
	
	public static final HashMap<Integer, Integer> PLAYER_COLOR;
	static {
		PLAYER_COLOR = new HashMap<Integer, Integer>();
		PLAYER_COLOR.put(0, -256); // 255,255,0
		PLAYER_COLOR.put(1, -16776961); // 0,0,255
	}
	
	private GameHexGrid grid;
	
	private int rows, cols;
	
	private int clickTime;
	private int pressX, pressY;
	
	private int currentPlayer;
	
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
		size(1200,800);
	}
	
	@Override
	public void setup() {
		rows = 11;
		cols = 11;
		grid = new GameHexGrid(this, cols, rows);
		grid.setOffset(80, 30);
		currentPlayer = 0;
	}
	
	public void nextPlayer() {
		currentPlayer++;
		currentPlayer%=2;
		for (int col = 0; col < cols; col++) {
			Node<Hexagon> path = grid.isPath(0, col, rows - 1, -1, PLAYER_COLOR.get(0));
			if (path != null) {
				retracePath(path);
				return;
			}
		}
		
		for (int row = 0; row < rows; row++) {
			Node<Hexagon> path = grid.isPath(row, 0, -1, cols - 1, PLAYER_COLOR.get(1));
			if (path != null) {
				retracePath(path);
				return;
			}
		}
	}
	
	private void retracePath(Node<Hexagon> origin) {
		Node<Hexagon> current = origin;
		while (current != null) {
			current.val.setColor(color(0,255,0));
			current = current.prev;
		}
	}
	
	public int currentPlayer() {
		return currentPlayer;
	}
	
	@Override
	public void draw() {
		background(51);
		grid.draw();
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
		grid.mouseMove(mouseX, mouseY);
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
