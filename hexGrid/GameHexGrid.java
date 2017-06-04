package hexGrid;

import hexagon.Hexagon;
import main.HexMain;
import objects.Drawable;
import objects.GameObject;
import processing.core.PApplet;
import processing.core.PVector;

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
	
	public void setOffset(float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
	}

	@Override
	public void draw() {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				hexGrid[row][col].draw(dx, dy);
			}
		}
		drawTopLine();
		drawLeftLine();
		drawRightLine();
		drawBottomLine();
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
	
	private void drawTopLine() {
		applet.noStroke();
		applet.fill(255,255,0);
		applet.beginShape();
		float yPadding = 5;
		float targetY = -15;
		float hexCordX = PApplet.sqrt(3)/2 * HexMain.getHexGridSize();
		float hexCordY  =  HexMain.getHexGridSize();
		applet.vertex((targetY - hexCordY) * PApplet.sqrt(3) + hexCordX + dx+ yPadding, targetY + dy);
		for (int col = 0; col < width; col++) {
			applet.vertex(col * PApplet.sqrt(3) * HexMain.getHexGridSize() + dx,  HexMain.getHexGridSize()/2 + dy - yPadding);
			applet.vertex((PApplet.sqrt(3)/2 + col * PApplet.sqrt(3)) * HexMain.getHexGridSize() + dx, 0 + dy - yPadding);
		}
		applet.vertex((0.75f * PApplet.sqrt(3) + (width-1) * PApplet.sqrt(3) ) * HexMain.getHexGridSize() + dx, HexMain.getHexGridSize()/4 + dy - yPadding);
		
		float endX = (0.75f * PApplet.sqrt(3) + (width-1) * PApplet.sqrt(3) ) * HexMain.getHexGridSize();
		float endY =  HexMain.getHexGridSize()/4;
		applet.vertex(1.0f/PApplet.sqrt(3) * (endY - targetY) + endX + dx, targetY + dy);
		applet.endShape(PApplet.CLOSE);
	}
	
	private void drawLeftLine() {
		applet.noStroke();
		applet.fill(0,0,255);
		applet.beginShape();
		float xPadding = 2.5f;
		float startY = -15;
		float hexCordX = PApplet.sqrt(3)/2 * HexMain.getHexGridSize();
		float hexCordY  =  HexMain.getHexGridSize();
		applet.vertex((startY - hexCordY) * PApplet.sqrt(3) + hexCordX + dx, startY + dy + xPadding);
		for (int row = 0; row < height; row++) {
			float x = PApplet.sqrt(3)/2 * HexMain.getHexGridSize() + (((row) * PApplet.sqrt(3)/2)) * HexMain.getHexGridSize();
			float y =  HexMain.getHexGridSize() + 1.5f * row * HexMain.getHexGridSize();
			applet.vertex(x - PApplet.sqrt(3)/2 * HexMain.getHexGridSize()  - xPadding + dx, y-HexMain.getHexGridSize()/2 + dy + xPadding);
			applet.vertex(x - PApplet.sqrt(3)/2 * HexMain.getHexGridSize()  - xPadding + dx, y+HexMain.getHexGridSize()/2 + dy + xPadding);
		}
		float x = PApplet.sqrt(3)/2 * HexMain.getHexGridSize() + (((height - 1) * PApplet.sqrt(3)/2)) * HexMain.getHexGridSize();
		float y =  HexMain.getHexGridSize() + 1.5f * (height - 1) * HexMain.getHexGridSize();
		applet.vertex(x - PApplet.sqrt(3) / 4 * HexMain.getHexGridSize() - xPadding + dx, y + 0.75f * HexMain.getHexGridSize() + dy + xPadding);
		
		PVector l1 = new PVector((startY - hexCordY) * PApplet.sqrt(3) + hexCordX , startY );
		PVector l2 = new PVector(x - PApplet.sqrt(3) / 4 * HexMain.getHexGridSize() - xPadding, y + 0.75f * HexMain.getHexGridSize());
		PVector pos = new PVector(1, PApplet.sqrt(3)).mult((-3*l1.x - PApplet.sqrt(3) * l1.y + 3 * l2.x + PApplet.sqrt(3) * l2.y)/6);
		pos.add(l1);
		pos.add(dx, dy);
		applet.vertex(pos.x, pos.y);
		
		applet.endShape(PApplet.CLOSE);
	}
	
	private void drawRightLine() {
		applet.noStroke();
		applet.fill(0,0,255);
		applet.beginShape();
		float xPadding = 2.5f;
		float startY = -15;
		
		float endX = (0.75f * PApplet.sqrt(3) + (width-1) * PApplet.sqrt(3) ) * HexMain.getHexGridSize();
		float endY =  HexMain.getHexGridSize()/4;
		float targetY = -15;
		applet.vertex(1.0f/PApplet.sqrt(3) * (endY - targetY) + endX + dx, targetY + dy);
		applet.vertex((0.75f * PApplet.sqrt(3) + (width-1) * PApplet.sqrt(3) ) * HexMain.getHexGridSize() + dx, HexMain.getHexGridSize()/4 + dy - xPadding);
		
		for (int row = 0; row < height; row++) {
			float x = PApplet.sqrt(3)/2 * HexMain.getHexGridSize() + (((row) * PApplet.sqrt(3)/2) + (width - 1) * PApplet.sqrt(3)) * HexMain.getHexGridSize();
			float y = HexMain.getHexGridSize() + 1.5f * row * HexMain.getHexGridSize();
			applet.vertex(x + PApplet.sqrt(3)/2 * HexMain.getHexGridSize()  + xPadding + dx, y-HexMain.getHexGridSize()/2 + dy -xPadding);
			applet.vertex(x + PApplet.sqrt(3)/2 * HexMain.getHexGridSize()  + xPadding + dx, y+HexMain.getHexGridSize()/2 + dy -xPadding) ;
		}
		
		float hexCordX = PApplet.sqrt(3)/2 * HexMain.getHexGridSize();
		float hexCordY  =  HexMain.getHexGridSize();
		
		float x = PApplet.sqrt(3)/2 * HexMain.getHexGridSize() + (((height - 1) * PApplet.sqrt(3)/2)) * HexMain.getHexGridSize();
		float y =  HexMain.getHexGridSize() + 1.5f * (height - 1) * HexMain.getHexGridSize();
		PVector l1 = new PVector((startY - hexCordY) * PApplet.sqrt(3) + hexCordX , startY );
		PVector l2 = new PVector(x - PApplet.sqrt(3) / 4 * HexMain.getHexGridSize() - xPadding, y + 0.75f * HexMain.getHexGridSize());
		PVector pos = new PVector(1, PApplet.sqrt(3)).mult((-3*l1.x - PApplet.sqrt(3) * l1.y + 3 * l2.x + PApplet.sqrt(3) * l2.y)/6);
		pos.add(l1);
		targetY = pos.y;
		applet.vertex((targetY - hexCordY) * PApplet.sqrt(3) + hexCordX + dx + xPadding, targetY + dy - xPadding);
		applet.endShape(PApplet.CLOSE);
	}
	
	private void drawBottomLine() {
		applet.noStroke();
		applet.fill(255,255,0);
		applet.beginShape();
		float yPadding = 5;
		float startY = -15;
		float hexCordX = PApplet.sqrt(3)/2 * HexMain.getHexGridSize();
		float hexCordY  =  HexMain.getHexGridSize();
		float sx = PApplet.sqrt(3)/2 * HexMain.getHexGridSize() + (((height - 1) * PApplet.sqrt(3)/2)) * HexMain.getHexGridSize();
		float sy =  HexMain.getHexGridSize() + 1.5f * (height - 1) * HexMain.getHexGridSize();
		PVector l1 = new PVector((startY - hexCordY) * PApplet.sqrt(3) + hexCordX , startY );
		PVector l2 = new PVector(sx - PApplet.sqrt(3) / 4 * HexMain.getHexGridSize() - yPadding/2, sy + 0.75f * HexMain.getHexGridSize() + yPadding/2);
		PVector pos = new PVector(1, PApplet.sqrt(3)).mult((-3*l1.x - PApplet.sqrt(3) * l1.y + 3 * l2.x + PApplet.sqrt(3) * l2.y)/6);
		pos.add(l1);
		pos.add(dx, dy);
		applet.vertex(pos.x, pos.y);
		l2.add(dx, dy);
		applet.vertex(l2.array());
		for (int col = 0; col < width; col++) {
			float x = PApplet.sqrt(3)/2 * HexMain.getHexGridSize() + (((height - 1) * PApplet.sqrt(3)/2) + col * PApplet.sqrt(3)) * HexMain.getHexGridSize();
			float y =  HexMain.getHexGridSize() + 1.5f * (height - 1) * HexMain.getHexGridSize();
			applet.vertex(x + dx, y + HexMain.getHexGridSize() + yPadding + dy);
			applet.vertex(x + PApplet.sqrt(3) / 2 * HexMain.getHexGridSize() + dx, y + HexMain.getHexGridSize()/2 + yPadding + dy);
		}
		pos.sub(dx, dy);
		applet.vertex((pos.y - hexCordY) * PApplet.sqrt(3) + hexCordX + dx - yPadding, pos.y + dy);
		applet.endShape(PApplet.CLOSE);
		
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
		// zoom out: dir > 0, factor = (1-scale)
		// zoom  in: dir < 0, factor = (1+scale)
		if (!(HexMain.getHexGridSize() < 45 && dir > 0) && !(HexMain.getHexGridSize() > 70 && dir < 0)) {
			float scale = 0.1f;
			scaleTransformate(1 - dir*scale, applet.width / 2 - dx, applet.height/ 2 - dy);
			HexMain.setHexSize( (int) Math.round(HexMain.getHexGridSize() * (1- dir*scale)));
			updatePos();
		}
		
	}
	
	private void scaleTransformate(float scale, float startX, float startY) {
		
		float transformX = scale*startX;
		float transformY = scale*startY;
		
		dx -= transformX - startX;
		dy -= transformY - startY;
	}

}
