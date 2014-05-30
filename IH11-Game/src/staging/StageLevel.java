package staging;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXB;

import level.Level;
import main.GamePanel;

public class StageLevel extends Stage {

	private Level level;
	private BufferedImage background;

	public StageLevel(StageManager stageManager) {
		super(stageManager);
		level = JAXB.unmarshal(new File("ExampleLevel.xml"), Level.class);
		
		try {
			background = ImageIO.read(getClass().getResourceAsStream("/graphics/level/DummyBackgroundPixel.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {

	}

	public void draw(Graphics2D g2) {
		g2.drawImage(background, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		level.draw(g2);
	}

	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}

}
