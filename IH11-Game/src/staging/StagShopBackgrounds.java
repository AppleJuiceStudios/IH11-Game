package staging;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import main.GamePanel;
import data.PlayerData;

public class StagShopBackgrounds extends Stage {

	private BufferedImage background;
	private BufferedImage coin;
	List<Background> backgrounds;
	private BufferedImage[][] buttons;
	private Rectangle[] recs = new Rectangle[2];
	private int selectedButton = 2;
	private int selectedBackground;

	// Scrlling
	private double scrollingPosition;
	private double scrollingSpeed = 0.05;

	public StagShopBackgrounds(StageManager stageManager) {
		super(stageManager);
		PlayerData.load("res/data/Player.xml");
		backgrounds = new ArrayList<>();
		buttons = new BufferedImage[2][2];
		addBackground("/graphics/level/background/BlueBackgroundPixel.png");
		addBackground("/graphics/level/background/CaribbeanBackgroundPixel.png");
		addBackground("/graphics/level/background/DummyBackgroundPixel.png");
		addBackground("/graphics/level/background/LandBackgroundPixel.png");
		addBackground("/graphics/level/background/RainbowBackgroundPixel.png");
		addBackground("/graphics/level/background/SkyBackgroundPixel.png");
		try {
			background = ImageIO.read(getClass().getResourceAsStream(
					"/graphics/level/background/BlueBackgroundPixel.png"));
			coin = ImageIO.read(getClass().getResourceAsStream(
					"/graphics/entity/coin/coin.png"));

			buttons[0][0] = ImageIO.read(getClass().getResourceAsStream(
					"/graphics/menue/PlayButtonBreitSelected.png"));
			buttons[0][1] = ImageIO.read(getClass().getResourceAsStream(
					"/graphics/menue/PlayButtonBreitUnpressed.png"));

			buttons[1][0] = ImageIO.read(getClass().getResourceAsStream(
					"/graphics/menue/BackButtonBreitSelected.png"));
			buttons[1][1] = ImageIO.read(getClass().getResourceAsStream(
					"/graphics/menue/BackButtonBreitUnpressed.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		recs[0] = new Rectangle(20, GamePanel.HEIGHT - 50,
				buttons[0][0].getWidth(), buttons[0][0].getHeight());
		recs[1] = new Rectangle(220, GamePanel.HEIGHT - 50,
				buttons[0][0].getWidth(), buttons[0][0].getHeight());

	}

	private void addBackground(String path) {
		try {
			backgrounds.add(new Background(ImageIO.read(getClass()
					.getResourceAsStream(path)), PlayerData.playerData
					.getBackground().contains(path), path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {
		g2.drawImage(background, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		g2.drawImage(coin, GamePanel.WIDTH - coin.getWidth() - 90, 18,
				coin.getWidth(), coin.getHeight(), null);
		g2.setFont(new Font("LCD", 2, 50));
		g2.drawString(PlayerData.playerData.getName(), 80, 49);
		g2.drawString(String.valueOf(PlayerData.playerData.getCoins()),
				GamePanel.WIDTH - 90, 49);
		g2.setFont(new Font("Dialog", Font.PLAIN, 12));
		for (int i = 0; i < 2; i++) {
			if (i == selectedButton) {
				g2.drawImage(buttons[i][0], recs[i].x, recs[i].y,
						recs[i].width, recs[i].height, null);
			} else {
				g2.drawImage(buttons[i][1], recs[i].x, recs[i].y,
						recs[i].width, recs[i].height, null);
			}

		}

		// Scrolling
		double targetPosition = selectedBackground * 50;
		scrollingPosition += (targetPosition - scrollingPosition)
				* scrollingSpeed;
		AffineTransform at = new AffineTransform();
		at.translate(0, -scrollingPosition);
		BufferedImage scrollArea = new BufferedImage(300, 160,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D scrollG = (Graphics2D) scrollArea.getGraphics();
		scrollG.setTransform(at);
		for (int i = 0; i < backgrounds.size(); i++) {
			Background b = backgrounds.get(i);
			scrollG.drawImage(b.getImage(), 20, i * 50 + 5, 60, 40, null);
			if (b.isOwend()) {
				scrollG.drawString("Yours", 100, i * 50 + 30);
			} else {
				scrollG.drawString("Buy        75 $", 100, i * 50 + 30);
			}
		}
		g2.drawImage(scrollArea, 50, 70, 300, 160, null);
		if (selectedButton == 2) {
			g2.setColor(Color.LIGHT_GRAY);
			g2.drawRect(52, 72, 296, 46);
			g2.drawRect(53, 73, 294, 44);
			g2.drawRect(54, 74, 292, 42);
		}

	}

	@Override
	public void close() {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == 's') {
			selectedBackground = (selectedBackground + 1) % backgrounds.size();
		} else if (e.getKeyChar() == 'w') {
			selectedBackground--;
			if (selectedBackground < 0) {
				selectedBackground = backgrounds.size() - 1;
			}
		} else if (e.getKeyChar() == 'd') {
			selectedButton = (selectedButton + 1) % 3;
		} else if (e.getKeyChar() == 'a') {
			selectedButton--;
			if (selectedButton < 0) {
				selectedButton = 2;
			}
		}
		if (e.getKeyChar() == ' ') {
			if (selectedButton == 0) {
				getStageManager().setStatge(StageManager.STAGE_LEVEL);
			} else if (selectedButton == 1) {
				getStageManager().setStatge(StageManager.STAGE_SHOP);
			} else {
				Background bg = backgrounds.get(selectedBackground);
				if (!bg.isOwend()) {
					int coins = PlayerData.playerData.getCoins();
					if (coins >= 75) {
						coins -= 75;
						PlayerData.playerData.setCoins(coins);
						PlayerData.playerData.getBackground().add(bg.getPath());
					}
				}
			}
		}
	}

	private class Background {
		BufferedImage image;
		boolean owend;
		String path;

		private Background(BufferedImage image, boolean owend, String path) {
			this.image = image;
			this.owend = owend;
			this.path = path;
		}

		public BufferedImage getImage() {
			return image;
		}

		public boolean isOwend() {
			return owend;
		}

		public String getPath() {
			return path;
		}

	}

}
