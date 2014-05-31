package staging;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.font.TransformAttribute;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXB;

import entity.EntityPlayer;
import level.Level;
import main.GamePanel;

public class StageLevel extends Stage {

	//Movement
	private double movementSpeed = 0.7;
	private Rectangle movementArea;
	private double maxXMovement;
	private double maxYMovement;
	private double xMovement;
	private double yMovement;
	
	private Level level;
	private BufferedImage background;
	private Thread updateThread;

	private EntityPlayer player;

	public StageLevel(StageManager stageManager) {
		super(stageManager);
		level = JAXB.unmarshal(new File("ExampleLevel.xml"), Level.class);
		player = new EntityPlayer(level, level.getStartPositionX(), level.getStartPositionY());
		//Movement
		maxXMovement = level.getWidth() * level.getTileSize() - GamePanel.WIDTH;
		maxYMovement = level.getHeight() * level.getTileSize() - GamePanel.HEIGHT;
		xMovement = player.getxPos() - (GamePanel.WIDTH / 2);
		yMovement = player.getxPos() - (GamePanel.HEIGHT / 2);
		if(xMovement < 0){
			xMovement = 0;
		} else if(xMovement > maxXMovement){
			xMovement = maxXMovement;
		}
		if(yMovement < 0){
			yMovement = 0;
		} else if(yMovement > maxYMovement){
			yMovement = maxYMovement;
		}
		try {
			background = ImageIO.read(getClass().getResourceAsStream("/graphics/level/DummyBackgroundPixel.png"));
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
		xMovement += (player.getxPos() - (GamePanel.WIDTH / 2) - xMovement) * movementSpeed;
		yMovement += (player.getyPos() - (GamePanel.HEIGHT / 3) - yMovement) * movementSpeed;
		if(xMovement < 0){
			xMovement = 0;
		} else if(xMovement > maxXMovement){
			xMovement = maxXMovement;
		}
		if(yMovement < 0){
			yMovement = 0;
		} else if(yMovement > maxYMovement){
			yMovement = maxYMovement;
		}
	}

	public void close() {

	}

	public void draw(Graphics2D g2) {
		g2.drawImage(background, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		AffineTransform tx = new AffineTransform();
		tx.translate(-xMovement, -yMovement);
		g2.setTransform(tx);
		level.draw(g2);
		player.draw(g2);
		g2.setTransform(new AffineTransform());
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
