package hexagon;

import main.HexMain;
import objects.Drawable;
import objects.Pressable;
import processing.core.PApplet;
import processing.core.PVector;

public class Hexagon implements Drawable, Pressable {
	
	private HexMain applet;
	private int color;
	private float x, y;
	
	public Hexagon(HexMain applet, float x, float y) {
		this.applet = applet;
		color = applet.color(200,0,0);
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw() {
		applet.fill(color);
		applet.beginShape();
		for (float angle = PApplet.HALF_PI + PApplet.PI / 3; angle > - (PApplet.PI + PApplet.PI/6); angle -= PApplet.PI / 3) {
			applet.vertex(x + PApplet.cos(angle) * HexMain.HEX_SIZE, y - PApplet.sin(angle) * HexMain.HEX_SIZE);
		}
		applet.endShape(PApplet.CLOSE);
	}
	
	public void setColor(int newColor) {
		color = newColor;
	}

	@Override
	public boolean mousePress(int x, int y) {
		for (float angle = PApplet.HALF_PI + PApplet.PI / 3; angle > - (PApplet.PI + PApplet.PI/6); angle -= PApplet.PI / 3) {
			PVector p1 = new PVector(this.x + PApplet.cos(angle) * HexMain.HEX_SIZE, this.y - PApplet.sin(angle) * HexMain.HEX_SIZE);
			PVector p2 = new PVector(this.x + PApplet.cos(angle - PApplet.PI / 3) * HexMain.HEX_SIZE, this.y - PApplet.sin(angle - PApplet.PI / 3) * HexMain.HEX_SIZE);
			PVector p3 = new PVector(this.x,this.y);
			PVector pt = new PVector(x, y);
			if (pointInTriangle(pt, p1, p2, p3)) {
				setColor(applet.color(100, 100, 100));
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

}
