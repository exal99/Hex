package objects;

public interface GameObject{
	
	public void mouseMove(int newX, int newY);
	public boolean mousePress(int x, int y);
	public void mouseReleased(int x, int y);
	public void mouseDragged(int dX, int dY);
	public void mouseWheel(int dir);
}
