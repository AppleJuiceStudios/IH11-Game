package staging;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXB;

import entity.EntityPlayer;
import level.Level;
import main.GamePanel;

public class StageLevel extends Stage {

	private Level level;
	private BufferedImage background;
	private Thread updateThread;

	private EntityPlayer player;

	public StageLevel(StageManager stageManager) {
		super(stageManager);
		level = JAXB.unmarshal(new File("ExampleLevel.xml"), Level.class);
		player = new EntityPlayer(level, level.getStartPositionX(), level.getStartPositionY());
		try {
			background = ImageIO.read(getClass().getResourceAsStream(
					"/graphics/level/DummyBackgroundPixel.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		updateThread = new Thread(new Runnable() {
			public void run() {
				long startTime = 0;
				long delay = 0;
				long waitTime = 1000 / 60;
				while (true) {
					startTime = System.currentTimeMillis();
					update();
					delay = waitTime - (System.currentTimeMillis() - startTime);
					if (delay > 0) {
						try {
							Thread.sleep(delay);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

				}
			}
		});
		updateThread.start();
	}

	public void update() {
		player.update();
	}

	public void close() {

	}

	public void draw(Graphics2D g2) {
		g2.drawImage(background, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		level.draw(g2);
		player.draw(g2);
	}

	public void keyPressed(KeyEvent e) {
		player.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);
	}

	public void keyTyped(KeyEvent e) {
		player.keyTyped(e);
	}

}
