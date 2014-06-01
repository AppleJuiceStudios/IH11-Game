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
import data.PlayerData;

public class StageLevelEditor extends Stage {

	// Movement
	private double movementSpeed = 0.2;
	private double xMovement;
	private double yMovement;

	private LevelEditable level;
	private BufferedImage background;

	private int selectedX;
	private int selectedY;
	
	//LoadSave
	private boolean isLoadSaveScreen;
	private String loadedLevel;
	private StringBuilder enteredLevel;

	public StageLevelEditor(StageManager stageManager) {
		super(stageManager);
		level = new LevelEditable();
		byte[][] map = level.getTileSet();
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length - 1; y++) {
				map[x][y] = LevelTexture.AIR;
			}
			map[x][map[x].length - 1] = LevelTexture.NORTH;
		}
		loadedLevel = "Level";
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
		level = null;
	}

	public void draw(Graphics2D g2) {
		if(isLoadSaveScreen){
			g2.setColor(Color.BLUE);
			g2.fillRoundRect(80, 120, 240, 60, 10, 10);
			g2.setColor(Color.BLACK);
			g2.drawRoundRect(80, 120, 240, 60, 10, 10);
			g2.setColor(Color.WHITE);
			g2.drawString(enteredLevel.toString(), 100, 155);
		} else {
			xMovement -= (xMovement - (selectedX * level.getTileSize() - (GamePanel.WIDTH / 2) + level.getTileSize() / 2)) * movementSpeed;
			yMovement -= (yMovement - (selectedY * level.getTileSize() - (GamePanel.HEIGHT / 2) + level.getTileSize() / 2)) * movementSpeed;
			g2.drawImage(background, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
			AffineTransform tx = new AffineTransform();
			tx.translate(-xMovement, -yMovement);
			g2.setTransform(tx);
			level.draw(g2);
			int boxSize = level.getTileSize();
			g2.setColor(Color.ORANGE);
			g2.drawRect(-1, -1, level.getWidth() * boxSize + 1, level.getHeight() * boxSize + 1);
			g2.drawRect(-2, -2, level.getWidth() * boxSize + 3, level.getHeight() * boxSize + 3);
			g2.setColor(Color.BLUE);
			g2.drawRect((int) level.getStartPositionX(), (int) level.getStartPositionY(), boxSize - 1, boxSize - 1);
			g2.drawRect((int) level.getStartPositionX() + 1, (int) level.getStartPositionY() + 1, boxSize - 3, boxSize - 3);
			g2.setColor(Color.RED);
			g2.drawRect(selectedX * boxSize - 1, selectedY * boxSize - 1, boxSize + 1, boxSize + 1);
			g2.drawRect(selectedX * boxSize - 2, selectedY * boxSize - 2, boxSize + 3, boxSize + 3);
			g2.setTransform(new AffineTransform());
		}
		
	}
	
	public void setTile(byte id, boolean update){
		level.setTileID(selectedX, selectedY, id);
		if(selectedX < 0){
			xMovement -= selectedX * level.getTileSize();
			selectedX = 0;
		}
		if(selectedY < 0){
			yMovement -= selectedY * level.getTileSize();
			selectedY = 0;
		}
		if(update){
			level.calculateTileSet(selectedX, selectedY, true);
		}
	}
	
	public void loadSave(){
		
	}
	
	public void save(){
		
	}

	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {
		if(isLoadSaveScreen){
			if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
				enteredLevel.delete(enteredLevel.length() - 2, enteredLevel.length());
			}
		}
	}

	public void keyTyped(KeyEvent e) {
		if(isLoadSaveScreen){
			if(e.getKeyChar() == '\n'){
				isLoadSaveScreen = false;
				loadSave();
			} else {
				enteredLevel.append(e.getKeyChar());
			}
		} else {
			if (e.getKeyChar() == 'w') {
				selectedY--;
			} else if (e.getKeyChar() == 'a') {
				selectedX--;
			} else if (e.getKeyChar() == 's') {
				selectedY++;
			} else if (e.getKeyChar() == 'd') {
				selectedX++;
			}

			if (e.getKeyChar() == ' ') {
				if (level.getTileID(selectedX, selectedY) == LevelTexture.AIR | !level.isInTileSet(selectedX, selectedY)) {
					setTile(LevelTexture.CENTER, true);
				} else {
					setTile(LevelTexture.AIR, true);
				}
			}

			if (e.getKeyChar() == 'p') {
				level.setStartPositionX(selectedX * 32);
				level.setStartPositionY(selectedY * 32);
			}

			if (e.getKeyChar() == '+') {
				if (level.getTileSize() < 64) {
					level.setTileDrawSize(level.getTileSize() * 2);
					xMovement = selectedX * level.getTileSize() - (GamePanel.WIDTH / 2);
					yMovement = selectedY * level.getTileSize() - (GamePanel.HEIGHT / 2);
				}
			} else if (e.getKeyChar() == '-') {
				if (level.getTileSize() > 4) {
					level.setTileDrawSize(level.getTileSize() / 2);
					xMovement = selectedX * level.getTileSize() - (GamePanel.WIDTH / 2);
					yMovement = selectedY * level.getTileSize() - (GamePanel.HEIGHT / 2);
				}
			}
			
			if(e.getKeyChar() == '\n'){
				isLoadSaveScreen = true;
				enteredLevel = new StringBuilder(loadedLevel);
			}
		}
		
	}

}
