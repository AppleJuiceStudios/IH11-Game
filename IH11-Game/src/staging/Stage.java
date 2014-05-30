package staging;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public abstract class Stage {

	public abstract void close();
	
	public abstract void draw(Graphics2D g2);
	
	public abstract void keyPressed(KeyEvent e);

	public abstract void keyReleased(KeyEvent e);

	public abstract void keyTyped(KeyEvent e);
}
