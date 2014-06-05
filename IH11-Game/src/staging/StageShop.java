package staging;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

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

	public StageShop(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
		audio = new AudioPlayer();
		audio.load(AudioPlayer.HIT);
		try {
			background = ImageIO.read(getClass().getResourceAsStream("/graphics/level/background/BlueBackgroundPixel.png"));
			coin = ImageIO.read(getClass().getResourceAsStream("/graphics/entity/coin/coin.png"));

			buttons[0][0] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/PlayButtonBreitSelected.png"));
			buttons[0][1] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/PlayButtonBreitUnpressed.png"));
			buttons[0][2] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/PlayButtonBreitPressed.png"));

			buttons[1][0] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/BackButtonBreitSelected.png"));
			buttons[1][1] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/BackButtonBreitUnpressed.png"));
			buttons[1][2] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/BackButtonBreitPressed.png"));

			buttons[2][0] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/ButtonBackgroundsSelected.png"));
			buttons[2][1] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/ButtonBackgroundsUnpressed.png"));
			buttons[2][2] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/ButtonBackgroundsPressed.png"));

			buttons[3][0] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/ButtonLevelsSelected.png"));
			buttons[3][1] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/ButtonLevelsUnpressed.png"));
			buttons[3][2] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/ButtonLevelsPressed.png"));

			buttons[4][0] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/ButtonPlayersSelected.png"));
			buttons[4][1] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/ButtonPlayersUnpressed.png"));
			buttons[4][2] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/ButtonPlayersPressed.png"));

			buttons[5][0] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/ButtonTilesetsSelected.png"));
			buttons[5][1] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/ButtonTilesetsUnpressed.png"));
			buttons[5][2] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/ButtonTilesetsPressed.png"));

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
		g2.drawImage(coin, GamePanel.WIDTH - coin.getWidth() - 105, 7, coin.getWidth(), coin.getHeight(), null);
		g2.setFont(newFont(45));	//argument: font-size(float)
		g2.drawString(PlayerData.playerData.getName(), 10, 37);
		g2.drawString(String.valueOf(PlayerData.playerData.getCoins()), GamePanel.WIDTH - 100, 37);
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
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			getStageManager().setStatge(StageManager.STAGE_MENUE, null);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
	
	public Font newFont(float size){
		Font font = null;
		InputStream is = null;
		is = StageShop.class.getResourceAsStream("/fonts/Perfect DOS VGA 437 Win.ttf");
//		is = StageShop.class.getResourceAsStream("/fonts/BMgermar.ttf");
//		is = StageShop.class.getResourceAsStream("/fonts/Minecraftia.ttf");
	    try {
	    	font = Font.createFont(Font.TRUETYPE_FONT, is);
	    	font = font.deriveFont(Font.PLAIN, size);
		} catch (FontFormatException e) {
			
		} catch (IOException e) {
			
		}
	    return font;
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
		}
		if (e.getKeyChar() == ' ') {
			klicked = selected;
			audio.play(AudioPlayer.HIT);
			try {
				Thread.sleep(15);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			switch (selected) {
			case 0:
				getStageManager().setStatge(StageManager.STAGE_LEVEL, null);
				break;
			case 1:
				getStageManager().setStatge(StageManager.STAGE_MENUE, null);
				break;
			case 2:
				getStageManager().setStatge(StageManager.STAGE_SHOP_BACKGROUNDS, null);
				break;
			case 3:

				break;
			case 4:
				getStageManager().setStatge(StageManager.STAGE_SHOP_PLAYER, null);
				break;
			case 5:

				break;
			}
		}
	}
}
