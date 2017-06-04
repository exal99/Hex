package hexagon;

import main.HexMain;
import objects.Drawable;
import objects.GameObject;
import processing.core.PApplet;
import processing.core.PVector;

public class Hexagon implements Drawable, GameObject {
	
	private HexMain applet;
	private int color;
	private float x, y;
	private boolean mouseOver;
	private boolean taken;
	
	public Hexagon(HexMain applet, float x, float y) {
		this.applet = applet;
		color = applet.color(200,0,0);
		this.x = x;
		this.y = y;
		mouseOver = false;
	}

	@Override
	public void draw() {
		drawAt(x, y);
	}
	
	public void draw(float dx, float dy) {
		drawAt(x + dx, y + dy);
	}
	
	public void updatePos(float newX, float newY) {
		x = newX;
		y = newY;
	}
	
	public void setMouseOver(boolean val) {
		mouseOver = val;
	}
	
	public int getColor() {
		return color;
	}
	
	private void drawAt(float x, float y) {
		if (mouseOver && !taken) {
			applet.fill(HexMain.PLAYER_COLOR.get(applet.currentPlayer()));
		} else {
			applet.fill(color);
		}
		applet.noStroke();
		applet.beginShape();
		for (float angle = PApplet.HALF_PI + PApplet.PI / 3; angle > - (PApplet.PI + PApplet.PI/6); angle -= PApplet.PI / 3) {
			applet.vertex(x + PApplet.cos(angle) * HexMain.getHexSize(), y - PApplet.sin(angle) * HexMain.getHexSize());
		}
		applet.endShape(PApplet.CLOSE);
	}
	
	public void setColor(int newColor) {
		color = newColor;
	}
	

	@Override
	public boolean mousePress(int x, int y) {
		boolean res = mouseOver(x, y);
		if (res && !taken) {
			setColor(HexMain.PLAYER_COLOR.get(applet.currentPlayer()));
			taken = true;
			applet.nextPlayer();
		}
		return res;
	}
	
	public boolean mouseOver(int x, int y) {
		if (PApplet.dist(x, y, this.x, this.y) > HexMain.getHexSize()) {
			return false;
		}
		for (float angle = PApplet.HALF_PI + PApplet.PI / 3; angle > - (PApplet.PI + PApplet.PI/6); angle -= PApplet.PI / 3) {
			PVector p1 = new PVector(this.x + PApplet.cos(angle) * HexMain.getHexSize(), this.y - PApplet.sin(angle) * HexMain.getHexSize());
			PVector p2 = new PVector(this.x + PApplet.cos(angle - PApplet.PI / 3) * HexMain.getHexSize(), this.y - PApplet.sin(angle - PApplet.PI / 3) * HexMain.getHexSize());
			PVector p3 = new PVector(this.x,this.y);
			PVector pt = new PVector(x, y);
			if (pointInTriangle(pt, p1, p2, p3)) {
				return true;
			}
		}
		return false;
	}
	
	private float area(PVector p1, PVector p2, PVector p3) {
		/**
		 * https://stackoverflow.com/questions/2049582/how-to-determine-if-a-point-is-in-a-2d-triangle
		 */
		return (p1.x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (p1.y - p3.y);
	}
	
	private boolean pointInTriangle(PVector pt, PVector v1, PVector v2, PVector v3) {
		/**
		 * https://stackoverflow.com/questions/2049582/how-to-determine-if-a-point-is-in-a-2d-triangle
		 */
		
	    boolean b1 = area(pt, v1, v2) < 0.0f;
	    boolean b2 = area(pt, v2, v3) < 0.0f;
	    boolean b3 = area(pt, v3, v1) < 0.0f;

	    return ((b1 == b2) && (b2 == b3));
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
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseWheel(int dir) {
		
	}

}
