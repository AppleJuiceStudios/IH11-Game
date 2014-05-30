package staging;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class StageManager {

	private Stage stage;
	
	public static final int STAGE_MENUE = 1;
	public static final int STAGE_LEVEL = 2;
	
	public StageManager(int startStage){
		setStatge(startStage);
	}
	
	public void setStatge(int stageID){
		if(stage != null){
			stage.close();
			stage = null;
		}
		if(stageID == STAGE_MENUE){
			//stage = new StageMenue();
		}
	}
	
	public void draw(Graphics2D g2){
		stage.draw(g2);
	}
	
	public void keyPressed(KeyEvent e) {
		stage.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		stage.keyReleased(e);
	}

	public void keyTyped(KeyEvent e) {
		stage.keyTyped(e);
	}
	
}
