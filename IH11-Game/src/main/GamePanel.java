package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener{
	private static final long serialVersionUID = 1L;
	
	BufferedImage image;
	Graphics2D graphics;
	
	public GamePanel(){
		setBackground(Color.BLACK);
		requestFocus();
		
		image = new BufferedImage(400, 300, BufferedImage.TYPE_INT_RGB);
		graphics = (Graphics2D) image.getGraphics();
	}
	
	public void draw(Graphics2D g2) {
		//Draw
		
		Graphics g = this.getGraphics();
		g.drawImage(image, 0, 0, 800, 600, null);
		g.dispose();
	}

	public void keyPressed(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void keyTyped(KeyEvent e) {
		
	}
	
}
