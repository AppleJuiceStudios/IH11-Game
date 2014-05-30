package entity;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import level.Level;
import main.GamePanel;

public class EntityPlayer extends Entity{

	private BufferedImage image;
	private boolean keyRight;
	private boolean keyLeft;
	private boolean keyUp;
	private boolean keyDown;
	
	public EntityPlayer(Level level, double x, double y){
		this.level = level;
		xPos = x;
		yPos = y;
		speed = 2.5;
		jumpSpeed = -5;
		falingSpeed = 0.1;
		width = 32;
		height = 32;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/graphics/entity/TetrisPlayer.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update(){
		if(keyUp & yMoveMent == 0){
			yMoveMent = jumpSpeed;
		} else {
			yMoveMent += falingSpeed;
		}
		if(keyRight){
			xMoveMent = speed;
		} else if(keyLeft){
			xMoveMent = -speed;
		} else {
			xMoveMent = 0;
		}
		tryMove();
	}
	
	public void draw(Graphics2D g2) {
		g2.drawImage(image, (int) xPos, (int) yPos, width, height, null);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W){
			keyUp = true;
		} else if(e.getKeyCode() == KeyEvent.VK_A){
			keyLeft = true;
		} else if(e.getKeyCode() == KeyEvent.VK_S){
			keyDown = true;
		} else if(e.getKeyCode() == KeyEvent.VK_D){
			keyRight = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W){
			keyUp = false;
		} else if(e.getKeyCode() == KeyEvent.VK_A){
			keyLeft = false;
		} else if(e.getKeyCode() == KeyEvent.VK_S){
			keyDown = false;
		} else if(e.getKeyCode() == KeyEvent.VK_D){
			keyRight = false;
		}
	}

	public void keyTyped(KeyEvent e) {

	}
	
}
