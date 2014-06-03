package staging;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXB;

import level.Level;
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
	private int selectionXstart;
	private int selectionYstart;
	private boolean isSpacePressed;
	private boolean isStrgPressed;

	private TestingStageManager testingManager;
	private boolean isTesting;

	// LoadSave
	private boolean isLoadSaveScreen;
	private String loadedLevel;
	private StringBuilder enteredLevel;

	public StageLevelEditor(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
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

	}

	public void draw(Graphics2D g2) {
		if (isTesting) {
			testingManager.draw(g2);
			return;
		}
		if (isLoadSaveScreen) {
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
			g2.drawRect((int) level.getStartPositionX() / 32 * boxSize, (int) level.getStartPositionY() / 32 * boxSize, boxSize - 1, boxSize - 1);
			g2.drawRect((int) level.getStartPositionX() / 32 * boxSize + 1, (int) level.getStartPositionY() / 32 * boxSize + 1, boxSize - 3,
					boxSize - 3);
			g2.setColor(Color.RED);
			if ((selectedX != selectionXstart | selectedY != selectionYstart) & isSpacePressed) {
				drawSelection(g2);
			}
			g2.drawRect(selectedX * boxSize - 1, selectedY * boxSize - 1, boxSize + 1, boxSize + 1);
			g2.drawRect(selectedX * boxSize - 2, selectedY * boxSize - 2, boxSize + 3, boxSize + 3);
			g2.setTransform(new AffineTransform());
			g2.setColor(Color.WHITE);
			g2.drawString(loadedLevel, 5, 15);
		}

	}

	private void drawSelection(Graphics2D g2) {
		int boxSize = level.getTileSize();
		int relXstart;
		int relX;
		int relYstart;
		int relY;
		if (selectedX > selectionXstart) {
			relXstart = selectionXstart;
			relX = selectedX - selectionXstart + 1;
		} else {
			relXstart = selectedX;
			relX = selectionXstart - selectedX + 1;
		}
		if (selectedY > selectionYstart) {
			relYstart = selectionYstart;
			relY = selectedY - selectionYstart + 1;
		} else {
			relYstart = selectedY;
			relY = selectionYstart - selectedY + 1;
		}
		g2.drawRect(relXstart * boxSize - 1, relYstart * boxSize - 1, relX * boxSize + 1, relY * boxSize + 1);
		g2.drawRect(relXstart * boxSize - 2, relYstart * boxSize - 2, relX * boxSize + 3, relY * boxSize + 3);
		for (int x = relXstart; x < relXstart + relX; x++) {
			for (int y = relYstart; y < relYstart + relY; y++) {
				if (isStrgPressed != (!level.isInTileSet(x, y) | level.getTileID(x, y) == LevelTexture.AIR)) {
					drawBlockSelection(g2, x * boxSize, y * boxSize, boxSize);
				}
			}
		}
	}

	private void drawBlockSelection(Graphics2D g2, int x, int y, int blockSize) {
		for (int i = 1; i < blockSize; i += 2) {
			g2.drawLine(x + i, y, x, y + i);
			g2.drawLine(x + blockSize - i, y + blockSize, x + blockSize, y + blockSize - i);
		}
	}

	public void setTile(int x, int y, byte id, boolean update) {
		level.setTileID(x, y, id);
		if (x < 0) {
			xMovement -= x * level.getTileSize();
			selectedX -= x;
			level.setStartPositionX(level .getStartPositionX() - x * 32);
			x = 0;
		}
		if (y < 0) {
			yMovement -= y * level.getTileSize();
			selectedY -= y;
			level.setStartPositionY(level .getStartPositionY() - y * 32);
			y = 0;
		}
		if (update) {
			level.calculateTileSet(x, y, true);
		}
	}

	public void setSelection(byte id, boolean update) {
		int relXstart;
		int relXtarget;
		int relYstart;
		int relYtarget;
		if (selectedX > selectionXstart) {
			relXstart = selectionXstart - selectedX;
			relXtarget = 0;
		} else {
			relXstart = 0;
			relXtarget = selectionXstart - selectedX;
		}
		if (selectedY > selectionYstart) {
			relYstart = selectionYstart - selectedY;
			relYtarget = 0;
		} else {
			relYstart = 0;
			relYtarget = selectionYstart - selectedY;
		}
		for (int x = relXstart; x <= relXtarget; x++) {
			for (int y = relYstart; y <= relYtarget; y++) {
				setTile(selectedX + x, selectedY + y, id, update);
			}
		}
	}

	public void loadSave() {
		if (enteredLevel.toString().equals(loadedLevel)) {
			level.save("res/data/levels/" + loadedLevel);
			System.out.println("Save");
		} else {
			File file = new File("res/data/levels/" + enteredLevel.toString() + ".xml");
			if (file.exists()) {
				level = new LevelEditable(JAXB.unmarshal(file, Level.class));
				System.out.println("Load");
			} else {
				new File("res/data/levels").mkdirs();
				level.save("res/data/levels/" + enteredLevel.toString());
				System.out.println("Save new.");
			}
			loadedLevel = enteredLevel.toString();
		}
	}

	private void finishTesting() {
		isTesting = false;
		testingManager = null;
	}

	public void keyPressed(KeyEvent e) {
		if (isTesting) {
			testingManager.keyPressed(e);
			return;
		}

		if (isLoadSaveScreen) {

		} else {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				getStageManager().setStatge(StageManager.STAGE_MENUE, null);
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				if (!isSpacePressed) {
					isSpacePressed = true;
					selectionXstart = selectedX;
					selectionYstart = selectedY;
				}
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			isStrgPressed = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (isTesting) {
			testingManager.keyReleased(e);
			return;
		}

		if (isLoadSaveScreen) {
			if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				enteredLevel.delete(enteredLevel.length() - 2, enteredLevel.length());
			}
		} else {
			if (e.getKeyCode() == KeyEvent.VK_SPACE & isSpacePressed) {
				isSpacePressed = false;
				if (selectedX == selectionXstart & selectedY == selectionYstart) {
					if (level.getTileID(selectedX, selectedY) == LevelTexture.AIR | !level.isInTileSet(selectedX, selectedY)) {
						setTile(selectedX, selectedY, LevelTexture.CENTER, true);
					} else {
						setTile(selectedX, selectedY, LevelTexture.AIR, true);
					}
				} else {
					if (isStrgPressed) {
						setSelection(LevelTexture.AIR, true);
					} else {
						setSelection(LevelTexture.CENTER, true);
					}
				}
			}
			
			if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE & isSpacePressed & isStrgPressed) {
				level.setTileSet(level.resize(Math.max(selectionXstart, selectedX) + 1, Math.max(selectionYstart, selectedY) + 1, Math.min(selectionXstart, selectedX), Math.min(selectionYstart, selectedY)));
				selectedX  = 0;
				selectedY = 0;
				isSpacePressed = false;
				isStrgPressed = false;
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			isStrgPressed = false;
		}
	}

	public void keyTyped(KeyEvent e) {
		if (isTesting) {
			testingManager.keyTyped(e);
			return;
		}

		if (isLoadSaveScreen) {
			if (e.getKeyChar() == '\n') {
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

			if (e.getKeyChar() == '7') {
				setTile(selectedX, selectedY, LevelTexture.NORTHWEST, false);
			} else if (e.getKeyChar() == '8') {
				setTile(selectedX, selectedY, LevelTexture.NORTH, false);
			} else if (e.getKeyChar() == '9') {
				setTile(selectedX, selectedY, LevelTexture.NORTHEAST, false);
			} else if (e.getKeyChar() == '4') {
				setTile(selectedX, selectedY, LevelTexture.WEST, false);
			} else if (e.getKeyChar() == '5') {
				setTile(selectedX, selectedY, LevelTexture.CENTER, false);
			} else if (e.getKeyChar() == '6') {
				setTile(selectedX, selectedY, LevelTexture.EAST, false);
			} else if (e.getKeyChar() == '1') {
				setTile(selectedX, selectedY, LevelTexture.SOUTHWEST, false);
			} else if (e.getKeyChar() == '2') {
				setTile(selectedX, selectedY, LevelTexture.SOUTH, false);
			} else if (e.getKeyChar() == '3') {
				setTile(selectedX, selectedY, LevelTexture.SOUTHEAST, false);
			} else if (e.getKeyChar() == '0') {
				setTile(selectedX, selectedY, LevelTexture.AIR, false);
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

			if (e.getKeyChar() == '\n') {
				isLoadSaveScreen = true;
				enteredLevel = new StringBuilder(loadedLevel);
			}

			if (e.getKeyChar() == 't') {
				level.setTileDrawSize(32);
				testingManager = new TestingStageManager(level);
				isTesting = true;
			}
		}
	}

	private class TestingStageManager extends StageManager {

		private Stage levelStage;

		public TestingStageManager(Level level) {
			super(-1);
			levelStage = new StageLevel(this, level);
		}

		public void setStatge(int stageID, Map<String, String> data) {
			if (stageID != -1) {
				close();
			}
		}

		public void draw(Graphics2D g2) {
			try{
				if (levelStage != null) {
				levelStage.draw(g2);
			} else {
				super.draw(g2);
			}
			} catch(NullPointerException e){
				if (isTesting){
					e.printStackTrace();
				}
			}
			
		}

		public void close() {
			finishTesting();
			levelStage.close();
			levelStage = null;
		}

		public void keyPressed(KeyEvent e) {
			if (levelStage != null) {
				levelStage.keyPressed(e);
			}
		}

		public void keyReleased(KeyEvent e) {
			if (levelStage != null) {
				levelStage.keyReleased(e);
			}
		}

		public void keyTyped(KeyEvent e) {
			if (levelStage != null) {
				levelStage.keyTyped(e);
			}
		}

	}
}
