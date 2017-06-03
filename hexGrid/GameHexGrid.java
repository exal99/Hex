package hexGrid;

import hexagon.Hexagon;
import main.HexMain;
import objects.Drawable;
import objects.GameObject;
import processing.core.PApplet;

public class GameHexGrid extends HexGrid<Hexagon> implements GameObject, Drawable{

	private HexMain applet;
	private float dx, dy;
	
	public GameHexGrid(HexMain applet, int width, int height) {
		super(width, height, Hexagon.class);
		this.applet = applet;
		init();
	}
	
	private void init() {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				float x = PApplet.sqrt(3)/2 * HexMain.getHexGridSize() + (((row) * PApplet.sqrt(3)/2) + col * PApplet.sqrt(3)) * HexMain.getHexGridSize();
				float y =  HexMain.getHexGridSize() + 1.5f * row * HexMain.getHexGridSize();
				hexGrid[row][col] = new Hexagon(applet, x, y);
			} 
		}
	}
	
	private void updatePos() {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				float x = PApplet.sqrt(3)/2 * HexMain.getHexGridSize() + (((row) * PApplet.sqrt(3)/2) + col * PApplet.sqrt(3)) * HexMain.getHexGridSize();
				float y =  HexMain.getHexGridSize() + 1.5f * row * HexMain.getHexGridSize();
				hexGrid[row][col].updatePos(x, y);
			}
		}
	}

	@Override
	public void draw() {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				hexGrid[row][col].draw(dx, dy);
			}
		} 
	}
	
	@Override
	public void draw(float dx, float dy) {
		float tempDX = this.dx;
		float tempDY = this.dy;
		this.dx += dx;
		this.dy += dy;
		draw();
		this.dx = tempDX;
		this.dy = tempDY;
	}

	@Override
	public boolean mousePress(int x, int y) {
		for (Hexagon[] row : hexGrid) {
			for (Hexagon cell : row) {
				if (cell.mousePress(Math.round(x - dx), Math.round(y - dy))) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void mouseMove(int newX, int newY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(int dX, int dY) {
		this.dx += dX;
		this.dy += dY;
	}
	
	@Override
	public void mouseWheel(int dir) {
		float scale = 0.1f;
		scaleTransformate(1 - dir*scale, applet.width / 2 - dx, applet.height/ 2 - dy);
		if (dir > 0 ) {
			//scaleTransformate(1 - scale);
			HexMain.setHexSize( (int) Math.round(HexMain.getHexGridSize() * (1-scale)));
			updatePos();
			
		}
		else if (dir < 0 ) {
			//scaleTransformate(1 + scale);
			HexMain.setHexSize( (int) Math.round(HexMain.getHexGridSize() * (1+scale)));
			updatePos();
		}
		
	}
	
	private void scaleTransformate(float scale, float startX, float startY) {
		//float startX = applet.width / 2 - dx;
		//float startY = applet.height/ 2 - dy;
		
		float transformX = scale*startX;
		float transformY = scale*startY;
		
		dx -= transformX - startX;
		dy -= transformY - startY;
	}

}
