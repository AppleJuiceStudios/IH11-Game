package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import staging.StageManager;

public class GamePanel extends JPanel implements KeyListener {
	private static final long serialVersionUID = 1L;

	private BufferedImage image;
	private Graphics2D graphics;
	private StageManager stageManager;

	public final static int WIDTH = 400;
	public final static int HEIGHT = 300;

	public static int fps = 0;
	public static final int FPS_MAX = 60;

	public GamePanel() {
		setBorder(null);
		Dimension size = new Dimension(WIDTH * 2, HEIGHT * 2);
		setPreferredSize(size);
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		graphics = (Graphics2D) image.getGraphics();
		stageManager = new StageManager(StageManager.STAGE_MENUE);
		new Thread(new Runnable() {
			public void run() {
				long startTime = 0;
				long delay = 0;
				long waitTime = 1000 / FPS_MAX;

				long lastTime = 0;
				int frames = 0;
				long time = 0;
				long fps = 0;

				while (true) {
					frames++;
					time = System.nanoTime();
					if (time > lastTime) {
						fps = (time - lastTime) / 100000 * frames;
						lastTime = time;
						frames = 0;
					}
					System.out.println("FPS: " + fps);
					draw(graphics);
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
		}).start();
	}

	public void draw(Graphics2D g2) {
		stageManager.draw(g2);
		Graphics g = this.getGraphics();
		if (g != null) {
			g.drawImage(image, 0, 0, WIDTH * 2, HEIGHT * 2, null);
			g.dispose();
		}
	}

	public void keyPressed(KeyEvent e) {
		stageManager.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		stageManager.keyReleased(e);
	}

	public void keyTyped(KeyEvent e) {
		stageManager.keyTyped(e);
	}

}
