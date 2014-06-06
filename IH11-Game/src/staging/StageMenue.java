package staging;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.Main;
import resource.SoundManager;
import data.PlayerData;

public class StageMenue extends Stage {

	private int selectedItem = 0;
	private int pressedItem = -1;
	private int items = 2;
	private BufferedImage[][] buttons = new BufferedImage[items][3];
	private BufferedImage background;

	public StageMenue(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
		SoundManager.loadClipInCache("chiptune", "chiptune.wav");
		SoundManager.play("chiptune", true);
		try {
			background = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/MenueBackground.png"));
			buttons[0][0] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/PlayButtonSelected.png"));
			buttons[0][1] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/PlayButtonUnpressed.png"));
			buttons[0][2] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/PlayButtonPressed.png"));
			buttons[1][0] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/QuitButtonSelected.png"));
			buttons[1][1] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/QuitButtonUnpressed.png"));
			buttons[1][2] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/QuitButtonPressed.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		PlayerData.load();
	}

	@Override
	public void close() {
		PlayerData.save();
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(background, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);

		for (int i = 0; i < items; i++) {
			Rectangle rect = new Rectangle((GamePanel.WIDTH / 2) - (90 / 2), (GamePanel.HEIGHT / 2) + (20 + i * 50), 90, 46);
			if (i == selectedItem) {
				g2.drawImage(buttons[i][0], rect.x, rect.y, rect.width, rect.height, null);
			} else {
				g2.drawImage(buttons[i][1], rect.x, rect.y, rect.width, rect.height, null);
			}
			if (i == pressedItem) {
				g2.drawImage(buttons[i][2], rect.x, rect.y, rect.width, rect.height, null);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
			getStageManager().close();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == 'w') {
			selectedItem = (selectedItem + 1) % items;
		}

		if (e.getKeyChar() == 's') {
			if (selectedItem > 0) {
				selectedItem--;
			} else {
				selectedItem = items - 1;
			}
		}

		if (e.getKeyChar() == ' ') {
			SoundManager.stop("chiptune");
			try {
				Thread.sleep(250);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			SoundManager.play("hit");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			pressedItem = selectedItem;
			switch (selectedItem) {
			case 0:
				getStageManager().setStatge(StageManager.STAGE_SHOP, null);
				break;
			case 1:
				Main.frame.setVisible(false);
				getStageManager().close();
				break;
			}
		}

		if (e.getKeyChar() == 'e') {
			getStageManager().setStatge(StageManager.STAGE_LEVELEDITOR, null);
		}
	}
}
