package staging;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class StageWelcome extends Stage {

	private BufferedImage welcome;

	public StageWelcome(StageManager stageManager) {
		super(stageManager);
		try {
			welcome = ImageIO.read(getClass().getResourceAsStream("/graphics/loading/Welcome.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(welcome, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		getStageManager().setStatge(StageManager.STAGE_MENUE);
	}
}
