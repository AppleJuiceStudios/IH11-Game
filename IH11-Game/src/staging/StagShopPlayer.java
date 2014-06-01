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

import sound.AudioPlayer;
import main.GamePanel;
import data.PlayerData;

public class StagShopPlayer extends Stage {

	private BufferedImage background;
	private BufferedImage coin;
	private AudioPlayer audio;
	List<Player> players;
	private BufferedImage[][] buttons;
	private Rectangle[] recs = new Rectangle[2];
	private int selectedButton = 2;
	private int selectedPlayer;

	// Scrlling
	private double scrollingPosition;
	private double scrollingSpeed = 0.05;

	public StagShopPlayer(StageManager stageManager) {
		super(stageManager);
		audio = new AudioPlayer();
		audio.load(AudioPlayer.HIT);
		PlayerData.load();
		players = new ArrayList<>();
		buttons = new BufferedImage[2][2];
		try {
			addPlayer("/graphics/entity/MCPlayerANimation.xml", ImageIO.read(getClass().getResourceAsStream("/graphics/entity/MCPlayer.png")));
			addPlayer("/graphics/entity/TetrisPlayerANimation.xml", ImageIO.read(getClass().getResourceAsStream("/graphics/entity/TetrisPlayer.png")));
			background = ImageIO.read(getClass().getResourceAsStream("/graphics/level/background/BlueBackgroundPixel.png"));
			coin = ImageIO.read(getClass().getResourceAsStream("/graphics/entity/coin/coin.png"));

			buttons[0][0] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/PlayButtonBreitSelected.png"));
			buttons[0][1] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/PlayButtonBreitUnpressed.png"));

			buttons[1][0] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/BackButtonBreitSelected.png"));
			buttons[1][1] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/BackButtonBreitUnpressed.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		recs[0] = new Rectangle(20, GamePanel.HEIGHT - 50, buttons[0][0].getWidth(), buttons[0][0].getHeight());
		recs[1] = new Rectangle(220, GamePanel.HEIGHT - 50, buttons[0][0].getWidth(), buttons[0][0].getHeight());

	}

	private void addPlayer(String path, BufferedImage image) {

		players.add(new Player(image, PlayerData.playerData.getCharacter().contains(path), path));

	}

	public void draw(Graphics2D g2) {
		g2.drawImage(background, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		g2.drawImage(coin, GamePanel.WIDTH - coin.getWidth() - 90, 18, coin.getWidth(), coin.getHeight(), null);
		g2.setFont(new Font("LCD", 2, 50));
		g2.drawString(PlayerData.playerData.getName(), 80, 49);
		g2.drawString(String.valueOf(PlayerData.playerData.getCoins()), GamePanel.WIDTH - 90, 49);
		g2.setFont(new Font("Dialog", Font.PLAIN, 12));
		for (int i = 0; i < 2; i++) {
			if (i == selectedButton) {
				g2.drawImage(buttons[i][0], recs[i].x, recs[i].y, recs[i].width, recs[i].height, null);
			} else {
				g2.drawImage(buttons[i][1], recs[i].x, recs[i].y, recs[i].width, recs[i].height, null);
			}

		}

		// Scrolling
		double targetPosition = selectedPlayer * 50;
		scrollingPosition += (targetPosition - scrollingPosition) * scrollingSpeed;
		AffineTransform at = new AffineTransform();
		at.translate(0, -scrollingPosition);
		BufferedImage scrollArea = new BufferedImage(300, 160, BufferedImage.TYPE_INT_RGB);
		Graphics2D scrollG = (Graphics2D) scrollArea.getGraphics();
		scrollG.setTransform(at);
		for (int i = 0; i < players.size(); i++) {
			Player b = players.get(i);
			scrollG.drawImage(b.getImage(), 30, i * 50 + 5, 40, 40, null);
			if (b.isOwend()) {
				scrollG.drawString("Yours", 100, i * 50 + 30);
			} else {
				scrollG.drawString("Buy        150 $", 100, i * 50 + 30);
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
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			getStageManager().setStatge(StageManager.STAGE_MENUE);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == 's') {
			selectedPlayer = (selectedPlayer + 1) % players.size();
		} else if (e.getKeyChar() == 'w') {
			selectedPlayer--;
			if (selectedPlayer < 0) {
				selectedPlayer = players.size() - 1;
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
			audio.play(AudioPlayer.HIT);
			if (selectedButton == 0) {
				getStageManager().setStatge(StageManager.STAGE_LEVEL);
			} else if (selectedButton == 1) {
				getStageManager().setStatge(StageManager.STAGE_SHOP);
			} else {
				Player bg = players.get(selectedPlayer);
				if (!bg.isOwend()) {
					int coins = PlayerData.playerData.getCoins();
					if (coins >= 150) {
						coins -= 150;
						PlayerData.playerData.setCoins(coins);
						PlayerData.playerData.getCharacter().add(bg.getPath());
						PlayerData.save();
						getStageManager().setStatge(StageManager.STAGE_SHOP_PLAYER);
					}
				}
			}
		}
	}

	private class Player {
		BufferedImage image;
		boolean owend;
		String path;

		private Player(BufferedImage image, boolean owend, String path) {
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
