package hexGrid;

import java.util.PriorityQueue;

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
				float x = HexMain.SQRT_3_2 * HexMain.getHexGridSize() + (((row) * HexMain.SQRT_3_2) + col * HexMain.SQRT_3) * HexMain.getHexGridSize();
				float y =  HexMain.getHexGridSize() + 1.5f * row * HexMain.getHexGridSize();
				hexGrid[row][col] = new Hexagon(applet, x, y);
			} 
		}
	}
	
	private void updatePos() {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				float x = HexMain.SQRT_3_2 * HexMain.getHexGridSize() + (((row) * HexMain.SQRT_3_2) + col * HexMain.SQRT_3) * HexMain.getHexGridSize();
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
		applet.fill(HexMain.PLAYER_COLOR.get(0));
		applet.beginShape();
		float yPadding = 5;
		float targetY = -15;
		float hexCordX = HexMain.SQRT_3_2 * HexMain.getHexGridSize();
		float hexCordY  =  HexMain.getHexGridSize();
		applet.vertex((targetY - hexCordY) * HexMain.SQRT_3 + hexCordX + dx+ yPadding, targetY + dy);
		for (int col = 0; col < width; col++) {
			applet.vertex(col * HexMain.SQRT_3 * HexMain.getHexGridSize() + dx,  HexMain.getHexGridSize()/2 + dy - yPadding);
			applet.vertex((HexMain.SQRT_3_2 + col * HexMain.SQRT_3) * HexMain.getHexGridSize() + dx, 0 + dy - yPadding);
		}
		applet.vertex((0.75f * HexMain.SQRT_3 + (width-1) * HexMain.SQRT_3 ) * HexMain.getHexGridSize() + dx, HexMain.getHexGridSize()/4 + dy - yPadding);
		
		float endX = (0.75f * HexMain.SQRT_3 + (width-1) * HexMain.SQRT_3 ) * HexMain.getHexGridSize();
		float endY =  HexMain.getHexGridSize()/4;
		applet.vertex(1.0f/HexMain.SQRT_3 * (endY - targetY) + endX + dx, targetY + dy);
		applet.endShape(PApplet.CLOSE);
	}
	
	private void drawLeftLine() {
		applet.noStroke();
		applet.fill(HexMain.PLAYER_COLOR.get(1));
		applet.beginShape();
		float xPadding = 2.5f;
		float startY = -15;
		float hexCordX = HexMain.SQRT_3_2 * HexMain.getHexGridSize();
		float hexCordY  =  HexMain.getHexGridSize();
		applet.vertex((startY - hexCordY) * HexMain.SQRT_3 + hexCordX + dx, startY + dy + xPadding);
		for (int row = 0; row < height; row++) {
			float x = HexMain.SQRT_3_2 * HexMain.getHexGridSize() + (((row) * HexMain.SQRT_3_2)) * HexMain.getHexGridSize();
			float y =  HexMain.getHexGridSize() + 1.5f * row * HexMain.getHexGridSize();
			applet.vertex(x - HexMain.SQRT_3_2 * HexMain.getHexGridSize()  - xPadding + dx, y-HexMain.getHexGridSize()/2 + dy + xPadding);
			applet.vertex(x - HexMain.SQRT_3_2 * HexMain.getHexGridSize()  - xPadding + dx, y+HexMain.getHexGridSize()/2 + dy + xPadding);
		}
		float x = HexMain.SQRT_3_2 * HexMain.getHexGridSize() + (((height - 1) * HexMain.SQRT_3_2)) * HexMain.getHexGridSize();
		float y =  HexMain.getHexGridSize() + 1.5f * (height - 1) * HexMain.getHexGridSize();
		applet.vertex(x - HexMain.SQRT_3 / 4 * HexMain.getHexGridSize() - xPadding + dx, y + 0.75f * HexMain.getHexGridSize() + dy + xPadding);
		
		PVector l1 = new PVector((startY - hexCordY) * HexMain.SQRT_3 + hexCordX , startY );
		PVector l2 = new PVector(x - HexMain.SQRT_3 / 4 * HexMain.getHexGridSize() - xPadding, y + 0.75f * HexMain.getHexGridSize());
		PVector pos = new PVector(1, HexMain.SQRT_3).mult((-3*l1.x - HexMain.SQRT_3 * l1.y + 3 * l2.x + HexMain.SQRT_3 * l2.y)/6);
		pos.add(l1);
		pos.add(dx, dy);
		applet.vertex(pos.x, pos.y);
		
		applet.endShape(PApplet.CLOSE);
	}
	
	private void drawRightLine() {
		applet.noStroke();
		applet.fill(HexMain.PLAYER_COLOR.get(1));
		applet.beginShape();
		float xPadding = 2.5f;
		float startY = -15;
		
		float endX = (0.75f * HexMain.SQRT_3 + (width-1) * HexMain.SQRT_3 ) * HexMain.getHexGridSize();
		float endY =  HexMain.getHexGridSize()/4;
		float targetY = -15;
		applet.vertex(1.0f/HexMain.SQRT_3 * (endY - targetY) + endX + dx, targetY + dy);
		applet.vertex((0.75f * HexMain.SQRT_3 + (width-1) * HexMain.SQRT_3 ) * HexMain.getHexGridSize() + dx, HexMain.getHexGridSize()/4 + dy - xPadding);
		
		for (int row = 0; row < height; row++) {
			float x = HexMain.SQRT_3_2 * HexMain.getHexGridSize() + (((row) * HexMain.SQRT_3_2) + (width - 1) * HexMain.SQRT_3) * HexMain.getHexGridSize();
			float y = HexMain.getHexGridSize() + 1.5f * row * HexMain.getHexGridSize();
			applet.vertex(x + HexMain.SQRT_3_2 * HexMain.getHexGridSize()  + xPadding + dx, y-HexMain.getHexGridSize()/2 + dy -xPadding);
			applet.vertex(x + HexMain.SQRT_3_2 * HexMain.getHexGridSize()  + xPadding + dx, y+HexMain.getHexGridSize()/2 + dy -xPadding) ;
		}
		
		float hexCordX = HexMain.SQRT_3_2 * HexMain.getHexGridSize();
		float hexCordY  =  HexMain.getHexGridSize();
		
		float x = HexMain.SQRT_3_2 * HexMain.getHexGridSize() + (((height - 1) * HexMain.SQRT_3_2)) * HexMain.getHexGridSize();
		float y =  HexMain.getHexGridSize() + 1.5f * (height - 1) * HexMain.getHexGridSize();
		PVector l1 = new PVector((startY - hexCordY) * HexMain.SQRT_3 + hexCordX , startY );
		PVector l2 = new PVector(x - HexMain.SQRT_3 / 4 * HexMain.getHexGridSize() - xPadding, y + 0.75f * HexMain.getHexGridSize());
		PVector pos = new PVector(1, HexMain.SQRT_3).mult((-3*l1.x - HexMain.SQRT_3 * l1.y + 3 * l2.x + HexMain.SQRT_3 * l2.y)/6);
		pos.add(l1);
		targetY = pos.y;
		applet.vertex((targetY - hexCordY) * HexMain.SQRT_3 + hexCordX + dx + xPadding, targetY + dy - xPadding);
		applet.endShape(PApplet.CLOSE);
	}
	
	private void drawBottomLine() {
		applet.noStroke();
		applet.fill(HexMain.PLAYER_COLOR.get(0));
		applet.beginShape();
		float yPadding = 5;
		float startY = -15;
		float hexCordX = HexMain.SQRT_3_2 * HexMain.getHexGridSize();
		float hexCordY  =  HexMain.getHexGridSize();
		float sx = HexMain.SQRT_3_2 * HexMain.getHexGridSize() + (((height - 1) * HexMain.SQRT_3_2)) * HexMain.getHexGridSize();
		float sy =  HexMain.getHexGridSize() + 1.5f * (height - 1) * HexMain.getHexGridSize();
		PVector l1 = new PVector((startY - hexCordY) * HexMain.SQRT_3 + hexCordX , startY );
		PVector l2 = new PVector(sx - HexMain.SQRT_3 / 4 * HexMain.getHexGridSize() - yPadding/2, sy + 0.75f * HexMain.getHexGridSize() + yPadding/2);
		PVector pos = new PVector(1, HexMain.SQRT_3).mult((-3*l1.x - HexMain.SQRT_3 * l1.y + 3 * l2.x + HexMain.SQRT_3 * l2.y)/6);
		pos.add(l1);
		pos.add(dx, dy);
		applet.vertex(pos.x, pos.y);
		l2.add(dx, dy);
		applet.vertex(l2.array());
		for (int col = 0; col < width; col++) {
			float x = HexMain.SQRT_3_2 * HexMain.getHexGridSize() + (((height - 1) * HexMain.SQRT_3_2) + col * HexMain.SQRT_3) * HexMain.getHexGridSize();
			float y =  HexMain.getHexGridSize() + 1.5f * (height - 1) * HexMain.getHexGridSize();
			applet.vertex(x + dx, y + HexMain.getHexGridSize() + yPadding + dy);
			applet.vertex(x + HexMain.SQRT_3_2 * HexMain.getHexGridSize() + dx, y + HexMain.getHexGridSize()/2 + yPadding + dy);
		}
		pos.sub(dx, dy);
		applet.vertex((pos.y - hexCordY) * HexMain.SQRT_3 + hexCordX + dx - yPadding, pos.y + dy);
		applet.endShape(PApplet.CLOSE);
		
	}
	
	@SuppressWarnings("unchecked")
	public Node<Hexagon> isPath(int fromRow, int fromCol, int toRow, int toCol, int playerColor) {
		if (!HexMain.PLAYER_COLOR.containsValue(playerColor) || hexGrid[fromRow][fromCol].getColor() != playerColor) {
			return null;
		}
		PriorityQueue<Node<Hexagon>> toLookAt = new PriorityQueue<Node<Hexagon>>();
		Node<Hexagon>[][] hexGrid = new Node[height][width];
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				Node<Hexagon> node= new Node<Hexagon>(this.hexGrid[row][col], row, col);
				if (row == fromRow && col == fromCol) {
					node.dist = 0;
				}
				if (this.hexGrid[row][col].getColor() == playerColor){
					toLookAt.add(node);
					hexGrid[row][col] = node;
				}
			}
		}
		while (!toLookAt.isEmpty()) {
			Node<Hexagon> current = toLookAt.poll();
			if (current.dist != Integer.MAX_VALUE) {
				if (current.row == toRow || current.col == toCol) {
					return current;
				}
				
				for (Node<Hexagon> neighbour : getNeighbours(current.row, current.col, hexGrid, GameHexGrid.Node.class)) {
					int newDist = current.dist + 1;
					if (newDist < neighbour.dist) {
						toLookAt.remove(neighbour);
						neighbour.dist = newDist;
						neighbour.prev = current;
						toLookAt.add(neighbour);
					}
				}
			}
		}

		
		return null;
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
		for (Hexagon[] row : hexGrid) {
			for (Hexagon cell : row) {
				if (cell.mouseOver(Math.round(newX -dx), Math.round(newY - dy))) {
					cell.setMouseOver(true);
				} else {
					cell.setMouseOver(false);
				}
			}
		}
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
	
	public class Node<E> implements Comparable<Node<E>>{
		int dist;
		public Node<E> prev;
		public E val;
		int row,col;
		
		public Node(E value,int row, int col) {
			val = value;
			dist = Integer.MAX_VALUE;
			prev = null;
			this.row = row;
			this.col = col;
		}

		@Override
		public int compareTo(Node<E> o) {
			return Integer.compare(dist, o.dist);
		}
	}

}
