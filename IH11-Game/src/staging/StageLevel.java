package staging;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import level.Level;

public class StageLevel extends Stage {

	private Level level;

	public StageLevel(StageManager stageManager) {
		super(stageManager);
		level = new Level();
		level.setTileSet(new byte[][] { 
				new byte[] { 5, 5, 5, 5, 5, 5, 5, 5, 5, 4 },
				new byte[] { 9, 9, 9, 9, 9, 9, 9, 9, 9, 1 },
				new byte[] { 9, 9, 9, 9, 9, 9, 9, 9, 9, 1 },
				new byte[] { 9, 9, 9, 9, 9, 9, 9, 9, 9, 1 },
				new byte[] { 9, 9, 9, 9, 9, 9, 9, 9, 9, 1 },
				new byte[] { 9, 9, 9, 9, 9, 9, 9, 9, 9, 1 },
				new byte[] { 9, 9, 9, 9, 9, 9, 9, 9, 9, 1 },
				new byte[] { 9, 9, 9, 9, 9, 9, 9, 9, 9, 1 },
				new byte[] { 9, 9, 9, 9, 9, 9, 9, 9, 9, 1 },
				new byte[] { 9, 9, 9, 9, 9, 9, 9, 9, 0, 4 },
				new byte[] { 9, 9, 9, 9, 9, 9, 9, 0, 4, 4 },
				new byte[] { 9, 9, 9, 9, 9, 9, 9, 1, 4, 4 },
				new byte[] { 9, 9, 9, 9, 9, 9, 9, 1, 4, 4 },
				new byte[] { 9, 9, 9, 9, 9, 9, 9, 1, 4, 4 },
				new byte[] { 3, 3, 3, 3, 3, 3, 3, 3, 4, 4 }
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
