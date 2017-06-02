package hexGrid;

import main.HexMain;
import objects.Drawable;
import objects.Pressable;
import processing.core.PApplet;

public class DrawableHexGrid<E extends Drawable & Pressable> extends HexGrid<E> implements Drawable, Pressable{

	private HexMain applet;
	private float x, y;
	
	public DrawableHexGrid(HexMain applet, int width, int height, float x, float y, Class<E> clazz) {
		super(width, height, clazz);
		this.applet = applet;
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}

	@Override
	public void draw() {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				hexGrid[row][col].draw();
			}
		} 
	}

	@Override
	public boolean mousePress(int x, int y) {
		for (E[] row : hexGrid) {
			for (E cell : row) {
				if (cell.mousePress(x, y)) {
					return true;
				}
			}
		}
		return false;
	}

}
