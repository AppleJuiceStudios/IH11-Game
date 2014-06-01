package staging;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import level.LevelEditable;
import level.graphics.LevelTexture;
import main.GamePanel;
import sound.AudioPlayer;
import data.PlayerData;

public class StageLevelEditor extends Stage {

	// Movement
	private double movementSpeed = 0.2;
	private double xMovement;
	private double yMovement;

	private LevelEditable level;
	private AudioPlayer audio;
	private BufferedImage background;

	private int selectedX;
	private int selectedY;

	public StageLevelEditor(StageManager stageManager) {
		super(stageManager);
		audio = new AudioPlayer();
		level = new LevelEditable();
		byte[][] map = level.getTileSet();
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length - 1; y++) {
				map[x][y] = LevelTexture.AIR;
			}
			map[x][map[x].length - 1] = LevelTexture.NORTH;
		}
		selectedX = map.length / 2 - 1;
		selectedY = map[selectedX].length - 1;
		// Movement
		xMovement = selectedX * level.getTileSize() - (GamePanel.WIDTH / 2);
		yMovement = selectedY * level.getTileSize() - (GamePanel.HEIGHT / 2);
		try {
			background = ImageIO.read(getClass().getResourceAsStream(chooseBackGround()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String chooseBackGround() {
		List<String> background = PlayerData.playerData.getBackground();
		return background.get((int) (Math.random() * background.size()));
	}

	public void close() {
		audio.close();
		level = null;
		audio = null;
	}

	public void draw(Graphics2D g2) {
		xMovement -= (xMovement - (selectedX * level.getTileSize() - (GamePanel.WIDTH / 2) + level.getTileSize() / 2)) * movementSpeed;
		yMovement -= (yMovement - (selectedY * level.getTileSize() - (GamePanel.HEIGHT / 2) + level.getTileSize() / 2)) * movementSpeed;
		g2.drawImage(background, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		AffineTransform tx = new AffineTransform();
		tx.translate(-xMovement, -yMovement);
		g2.setTransform(tx);
		level.draw(g2);
		g2.setColor(Color.RED);
		int boxSize = level.getTileSize();
		int selectedXpos = selectedX * boxSize;
		int selectedYpos = selectedY * boxSize;
		g2.drawRect(selectedXpos - 1, selectedYpos - 1, boxSize + 1, boxSize + 1);
		g2.drawRect(selectedXpos - 2, selectedYpos - 2, boxSize + 3, boxSize + 3);
		g2.setTransform(new AffineTransform());
	}

	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == 'w') {
			selectedY--;
		} else if (e.getKeyChar() == 'a') {
			selectedX--;
		} else if (e.getKeyChar() == 's') {
			selectedY++;
		} else if (e.getKeyChar() == 'd') {
			selectedX++;
		}
		
		if (e.getKeyChar() == ' '){
			if(level.getTileID(selectedX, selectedY) == LevelTexture.AIR | !level.isInTileSet(selectedX, selectedY)){
				level.setTileID(selectedX, selectedY, LevelTexture.CENTER);
			} else {
				level.setTileID(selectedX, selectedY, LevelTexture.AIR);
			}
			if(selectedX < 0){
				xMovement -= selectedX * level.getTileSize();
				selectedX = 0;
			}
			if(selectedY < 0){
				yMovement -= selectedY * level.getTileSize();
				selectedY = 0;
			}
			level.calculateTileSet(selectedX, selectedY, true);
		}
	}

}
