package staging;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Map;

public abstract class Stage {

	private StageManager stageManager;

	public Stage(StageManager stageManager, Map<String,String> data) {
		this.stageManager = stageManager;
	}

	public abstract void close();

	public abstract void draw(Graphics2D g2);

	public abstract void keyPressed(KeyEvent e);

	public abstract void keyReleased(KeyEvent e);

	public abstract void keyTyped(KeyEvent e);

	public StageManager getStageManager() {
		return stageManager;
	}

	public void setStageManager(StageManager stageManager) {
		this.stageManager = stageManager;
	}

}
