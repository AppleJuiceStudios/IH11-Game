package staging;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import sound.AudioPlayer;
import main.GamePanel;
import data.PlayerData;

public class StageShop extends Stage {

	private BufferedImage background;
	private BufferedImage coin;
	private int items = 6;
	private int selected = 0;
	private int klicked = -1;
	private BufferedImage[][] buttons = new BufferedImage[items][3];
	private Rectangle[] recs = new Rectangle[items];
	private AudioPlayer audio;

	public StageShop(StageManager stageManager) {
		super(stageManager);
		audio = new AudioPlayer();
		try {
			background = ImageIO.read(getClass().getResourceAsStream("/graphics/level/background/BlueBackgroundPixel.png"));
			coin = ImageIO.read(getClass().getResourceAsStream("/graphics/entity/coin/coin.png"));

			buttons[0][0] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/PlayButtonBreitSelected.png"));
			buttons[0][1] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/PlayButtonBreitUnpressed.png"));
			buttons[0][2] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/PlayButtonBreitPressed.png"));

			buttons[1][0] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/BackButtonBreitSelected.png"));
			buttons[1][1] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/BackButtonBreitUnpressed.png"));
			buttons[1][2] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/BackButtonBreitPressed.png"));

			buttons[2][0] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/QuitButtonSelected.png"));
			buttons[2][1] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/QuitButtonUnpressed.png"));
			buttons[2][2] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/QuitButtonPressed.png"));

			buttons[3][0] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/QuitButtonSelected.png"));
			buttons[3][1] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/QuitButtonUnpressed.png"));
			buttons[3][2] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/QuitButtonPressed.png"));

			buttons[4][0] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/QuitButtonSelected.png"));
			buttons[4][1] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/QuitButtonUnpressed.png"));
			buttons[4][2] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/QuitButtonPressed.png"));

			buttons[5][0] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/QuitButtonSelected.png"));
			buttons[5][1] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/QuitButtonUnpressed.png"));
			buttons[5][2] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/QuitButtonPressed.png"));

			recs[0] = new Rectangle(20, GamePanel.HEIGHT - 50, buttons[0][0].getWidth(), buttons[0][0].getHeight());
			recs[1] = new Rectangle(220, GamePanel.HEIGHT - 50, buttons[0][0].getWidth(), buttons[0][0].getHeight());
			recs[2] = new Rectangle(20, GamePanel.HEIGHT - 150, 164, buttons[2][0].getHeight() + 25);
			recs[3] = new Rectangle(220, GamePanel.HEIGHT - 150, 164, buttons[2][0].getHeight() + 25);
			recs[4] = new Rectangle(20, GamePanel.HEIGHT - 240, 164, buttons[2][0].getHeight() + 25);
			recs[5] = new Rectangle(220, GamePanel.HEIGHT - 240, 164, buttons[2][0].getHeight() + 25);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		audio.close();
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(background, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		g2.drawImage(coin, GamePanel.WIDTH - coin.getWidth() - 90, 18, coin.getWidth(), coin.getHeight(), null);
		g2.setFont(new Font("LCD", 2, 50));
		g2.drawString(PlayerData.playerData.getName(), 80, 49);
		g2.drawString(String.valueOf(PlayerData.playerData.getCoins()), GamePanel.WIDTH - 90, 49);
		g2.setFont(new Font("Dialog", Font.PLAIN, 12));

		for (int i = 0; i < items; i++) {
			if (i == klicked) {
				g2.drawImage(buttons[i][2], recs[i].x, recs[i].y, recs[i].width, recs[i].height, null);
			} else if (i == selected) {
				g2.drawImage(buttons[i][0], recs[i].x, recs[i].y, recs[i].width, recs[i].height, null);
			} else {
				g2.drawImage(buttons[i][1], recs[i].x, recs[i].y, recs[i].width, recs[i].height, null);
			}

		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == 'w') {
			selected = (selected + 2) % items;
		} else if (e.getKeyChar() == 'a') {
			if (selected > 0) {
				selected--;
			} else {
				selected = items - 1;
			}
		} else if (e.getKeyChar() == 's') {
			if (selected > 1) {
				selected -= 2;
			} else {
				selected = items - 1;
			}
		} else if (e.getKeyChar() == 'd') {
			selected = (selected + 1) % items;
		} else if (e.getKeyChar() == ' ') {
			klicked = selected;

			try {
				Thread.sleep(150);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			audio.play("Hit");
			try {
				Thread.sleep(250);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			switch (selected) {
			case 0:
				getStageManager().setStatge(StageManager.STAGE_LEVEL);
				break;
			case 1:
				getStageManager().setStatge(StageManager.STAGE_MENUE);
				break;
			case 2:

				break;
			case 3:

				break;
			case 4:

				break;
			case 5:

				break;
			}
		}
	}
}
