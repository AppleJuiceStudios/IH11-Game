package staging;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import level.Level;

public class StageLevel extends Stage{

	private Level level;
	
	public StageLevel(){
		level = new Level();
		level.setTileSet(new byte[][]{
				new byte[]{},
				new byte[]{},
				new byte[]{},
				new byte[]{},
				new byte[]{}
		});
	}

	public void close() {
		
	}

	public void draw(Graphics2D g2) {
		level.draw(g2);
	}

	public void keyPressed(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void keyTyped(KeyEvent e) {
		
	}
	
}
