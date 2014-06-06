package entity;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.xml.bind.JAXB;

import resource.SoundManager;
import level.Level;
import data.PlayerData;

public class EntityPlayer extends Entity {

	//Animation
	private AnimationPlayer animation;
	public boolean lookingRight;
	private int action;
	public static final int ACTION_IDLE = 0;
	public static final int ACTION_MOVE = 1;
	public static final int ACTION_JUMP = 2;
	public static final int ACTION_FALL = 3;
	public static final int ACTION_WINN = 4;

	private boolean keyRight;
	private boolean keyLeft;
	private boolean keyUp;
	//	private boolean keyDown;
	private boolean hasWinn;

	public EntityPlayer(Level level, double x, double y) {
		lookingRight = true;
		this.level = level;
		xPos = x;
		yPos = y;
		speed = 2.0;
		jumpSpeed = -5.0;
		falingSpeed = 0.15;
		maxfalingSpeed = 10;
		width = 32;
		height = 32;
		animation = JAXB.unmarshal(getClass().getResourceAsStream(choosecharacter()), AnimationPlayer.class);
		animation.load();
	}

	public void update() {
		boolean startJump = false;
		if (keyUp & onGround) {
			startJump = true;
			yMoveMent = jumpSpeed;
		} else {
			yMoveMent += falingSpeed;
			if(yMoveMent > maxfalingSpeed){
				yMoveMent = maxfalingSpeed;
			}
		}
		if (keyRight) {
			xMoveMent = speed;
			lookingRight = true;
			action = ACTION_MOVE;
		} else if (keyLeft) {
			xMoveMent = -speed;
			lookingRight = false;
			action = ACTION_MOVE;
		} else {
			xMoveMent = 0;
			action = ACTION_IDLE;
		}
		tryMove();
		if (yMoveMent < 0) {
			action = ACTION_JUMP;
			if (startJump) {
				SoundManager.play("jump");
			}
		} else if (yMoveMent > 0) {
			action = ACTION_FALL;
		}
		if (hasWinn) {
			action = ACTION_WINN;
		}
	}

	private String choosecharacter() {
		List<String> character = PlayerData.playerData.getCharacter();
		return character.get((int) (Math.random() * character.size()));
	}

	public void draw(Graphics2D g2) {
		BufferedImage image = animation.getImage(action);
		if (lookingRight) {
			g2.drawImage(image, (int) xPos, (int) yPos, width, height, null);
		} else {
			g2.drawImage(image, (int) xPos + width, (int) yPos, -width, height, null);
		}

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			keyUp = true;
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			keyLeft = true;
			//		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			//			keyDown = true;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			keyRight = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			keyUp = false;
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			keyLeft = false;
			//		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			//			keyDown = false;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			keyRight = false;
		}
	}

	public void keyTyped(KeyEvent e) {

	}

	public void setWinn(boolean hasWinn) {
		this.hasWinn = hasWinn;
	}

	public void close() {
		level = null;
	}

}
