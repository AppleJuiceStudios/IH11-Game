package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import staging.StageManager;

public class GamePanel extends JPanel implements KeyListener{
	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	private Graphics2D graphics;
	private StageManager stageManager;
	
	public static final int FPS_MAX = 60;
	
	public GamePanel(){
		requestFocus();
		
		image = new BufferedImage(400, 300, BufferedImage.TYPE_INT_RGB);
		graphics = (Graphics2D) image.getGraphics();
		stageManager = new StageManager(StageManager.STAGE_MENUE);
		new Thread(new Runnable() {
			public void run() {
				long startTime = 0;
				long delay = 0;
				long waitTime = 1000 / FPS_MAX;
				while(true){
					startTime = System.currentTimeMillis();
					draw(graphics);
					delay = waitTime - (System.currentTimeMillis() - startTime);
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	public void draw(Graphics2D g2) {
		stageManager.draw(g2);
		
		Graphics g = this.getGraphics();
		g.drawImage(image, 0, 0, 800, 600, null);
		g.dispose();
	}

	public void keyPressed(KeyEvent e) {
		keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		keyReleased(e);
	}

	public void keyTyped(KeyEvent e) {
		keyTyped(e);
	}
	
}
