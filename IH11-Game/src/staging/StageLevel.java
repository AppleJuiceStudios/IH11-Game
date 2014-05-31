package staging;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXB;

import data.PlayerData;
import sound.AudioPlayer;
import level.Item;
import level.Level;
import level.graphics.LevelTexture;
import main.GamePanel;
import entity.EntityPlayer;

public class StageLevel extends Stage {

	//Movement
	private double movementSpeed = 0.7;
	private double maxXMovement;
	private double maxYMovement;
	private double xMovement;
	private double yMovement;

	private Level level;
	private AudioPlayer audio;
	private BufferedImage background;
	private BufferedImage clock;
	private Thread updateThread;
	private List<Item> items;
	private BufferedImage itemImage;
	private int collectedItems;
	private int itemCount = 20;
	private boolean hasWinn;

	private EntityPlayer player;

	public StageLevel(StageManager stageManager) {
		super(stageManager);
		audio = new AudioPlayer();
		level = JAXB.unmarshal(new File(chooseLevel()), Level.class);
		player = new EntityPlayer(level, level.getStartPositionX(), level.getStartPositionY());
		initItems(itemCount);
		//Movement
		maxXMovement = level.getWidth() * level.getTileSize() - GamePanel.WIDTH;
		maxYMovement = level.getHeight() * level.getTileSize() - GamePanel.HEIGHT;
		xMovement = player.getxPos() - (GamePanel.WIDTH / 2);
		yMovement = player.getxPos() - (GamePanel.HEIGHT / 2);
		if (xMovement < 0) {
			xMovement = 0;
		} else if (xMovement > maxXMovement) {
			xMovement = maxXMovement;
		}
		if (yMovement < 0) {
			yMovement = 0;
		} else if (yMovement > maxYMovement) {
			yMovement = maxYMovement;
		}
		try {
			background = ImageIO.read(getClass().getResourceAsStream(chooseBackGround()));
			clock = ImageIO.read(getClass().getResourceAsStream("/graphics/entity/Clock.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		updateThread = new Thread(new Runnable() {
			public void run() {
				long startTime = 0;
				long delay = 0;
				long waitTime = 1000 / 60;
				while (!Thread.interrupted()) {
					startTime = System.currentTimeMillis();
					update();
					delay = waitTime - (System.currentTimeMillis() - startTime);
					if (delay > 0) {
						try {
							Thread.sleep(delay);
						} catch (InterruptedException e) {
							updateThread.interrupt();
						}
					}

				}
			}
		});
		updateThread.start();
	}

	private void initItems(int count) {
		items = new ArrayList<>();
		BufferedImage image = getItemImage();
		itemImage = image;
		for (int i = 0; i < count; i++) {
			int xPos = (int) ((Math.random() * (level.getWidth() - 4)) + 2);
			List<Integer> posibalYpos = new ArrayList<>();
			boolean wasLastAir = false;
			for (int j = 0; j < level.getHeight(); j++) {
				boolean isAir = level.getTileID(xPos, j) == LevelTexture.AIR;
				if (wasLastAir && !isAir) {
					posibalYpos.add(j - 1);
				}
				wasLastAir = isAir;
			}
			int yPos = posibalYpos.get((int) (Math.random() * posibalYpos.size()));
			items.add(new Item(image, xPos, yPos));
		}
	}

	private String chooseBackGround() {
		List<String> background = PlayerData.playerData.getBackground();
		return background.get((int) (Math.random() * background.size()));
	}

	private String chooseLevel() {
		String mainPath = new File("").getAbsolutePath();
		File file = new File(mainPath + "/bin/data/levels/");
		File[] fileArray = file.listFiles();
		String str = fileArray[(int) (Math.random() * 10) % fileArray.length].getPath();
		str = str.substring(mainPath.length() + 1);
		str = str.replace('\\', '/');
		System.out.println("[StageLevel] Loading Level: " + str.substring(str.lastIndexOf("/") + 1));
		return str;
	}

	private BufferedImage getItemImage() {
		String mainPath = new File("").getAbsolutePath();
		File file = new File(mainPath + "/bin/graphics/entity/coin/");
		File[] fileArray = file.listFiles();
		String str = fileArray[(int) (Math.random() * 10) % fileArray.length].getPath();
		str = str.substring(mainPath.length() + 4);
		str = str.replace('\\', '/');
		System.out.println("[StageLevel] Loading Backgrund: " + str.substring(str.lastIndexOf("/") + 1));
		try {
			return ImageIO.read(getClass().getResourceAsStream(str));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void update() {
		player.update();
		xMovement += (player.getxPos() - (GamePanel.WIDTH / 2) - xMovement) * movementSpeed;
		yMovement += (player.getyPos() - (GamePanel.HEIGHT / 3) - yMovement) * movementSpeed;
		if (xMovement < 0) {
			xMovement = 0;
		} else if (xMovement > maxXMovement) {
			xMovement = maxXMovement;
		}
		if (yMovement < 0) {
			yMovement = 0;
		} else if (yMovement > maxYMovement) {
			yMovement = maxYMovement;
		}
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).canCollectCoin(player)) {
				collectedItems++;
				audio.play("Orb");
				items.remove(i);
				if (collectedItems == itemCount) {
					audio.play("Win");
					hasWinn = true;
					player.setWinn(true);
				}
			}
		}
	}

	public void close() {
		updateThread.interrupt();
	}

	public void draw(Graphics2D g2) {
		AffineTransform tx = new AffineTransform();
		tx.translate(-(xMovement / maxXMovement * GamePanel.WIDTH), 0);
		g2.setTransform(tx);
		g2.drawImage(background, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		g2.drawImage(background, GamePanel.WIDTH * 2, 0, -GamePanel.WIDTH, GamePanel.HEIGHT, null);
		tx = new AffineTransform();
		tx.translate(-xMovement, -yMovement);
		g2.setTransform(tx);
		level.draw(g2);
		for (int i = 0; i < items.size(); i++) {
			items.get(i).draw(g2);
		}
		player.draw(g2);
		g2.setTransform(new AffineTransform());

		g2.drawImage(itemImage, 5, 5, clock.getWidth(), clock.getHeight(), null);
		g2.drawString(collectedItems + " / " + itemCount, itemImage.getWidth() + 10, itemImage.getHeight() / 2 + 10);
		g2.drawImage(clock, 5, itemImage.getHeight() + 10, clock.getWidth(), clock.getHeight(), null);
		g2.drawString("00:00", clock.getWidth() + 10, clock.getHeight() / 2 + clock.getHeight() + 15);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			getStageManager().setStatge(StageManager.STAGE_MENUE);
		} else if (e.getKeyCode() == KeyEvent.VK_R) {
			getStageManager().setStatge(StageManager.STAGE_LEVEL);

		} else {
			player.keyPressed(e);
		}
	}

	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);
	}

	public void keyTyped(KeyEvent e) {
		player.keyTyped(e);
	}

}
