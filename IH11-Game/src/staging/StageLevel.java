package staging;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXB;

import level.Item;
import level.Level;
import level.graphics.LevelTexture;
import main.GamePanel;
import resource.SoundManager;
import data.PlayerData;
import entity.EntityPlayer;

public class StageLevel extends Stage {

	// Movement
	private double movementSpeed = 0.3;
	private double maxXMovement;
	private double maxYMovement;
	private double xMovement;
	private double yMovement;

	private Level level;
	private BufferedImage background;
	private BufferedImage clock;
	private Thread updateThread;
	private List<Item> items;
	private BufferedImage itemImage;
	private int collectedItems;
	private int itemCount;
	private boolean hasWinn;
	private long startTime;
	private int zehner;
	private int einer;

	private EntityPlayer player;

	public StageLevel(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
		level = JAXB.unmarshal(getClass().getResourceAsStream(chooseLevel()), Level.class);
		player = new EntityPlayer(level, level.getStartPositionX(), level.getStartPositionY());
		initItems();
		startTime = System.currentTimeMillis();
		// Movement
		initMovementAndBackground();
		initUpdateThread();
	}

	public StageLevel(StageManager stageManager, Level level) {
		super(stageManager, null);
		this.level = level;
		player = new EntityPlayer(level, level.getStartPositionX(), level.getStartPositionY());
		initItems();
		startTime = System.currentTimeMillis();
		// Movement
		initMovementAndBackground();
		initUpdateThread();
	}

	private void initItems() {
		items = new ArrayList<>();
		BufferedImage image = getItemImage();
		itemImage = image;
		List<Integer> xPos = new ArrayList<>();
		List<Integer> yPos = new ArrayList<>();
		for (int x = 2; x < level.getWidth() - 2; x++) {
			boolean wasLastAir = false;
			for (int y = 0; y < level.getHeight(); y++) {
				boolean isAir = level.getTileID(x, y) == LevelTexture.AIR;
				if (wasLastAir && !isAir) {
					xPos.add(x);
					yPos.add(y - 1);
				}
				wasLastAir = isAir;
			}
		}
		int count = xPos.size() / 5;
		itemCount = count;
		for (int i = 0; i < xPos.size(); i++) {
			if (Math.random() < (double) count / (xPos.size() - i)) {
				items.add(new Item(image, xPos.get(i), yPos.get(i)));
				count--;
			}
		}
	}

	private void initMovementAndBackground() {
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
	}

	private void initUpdateThread() {
		updateThread = new Thread(new Runnable() {
			public void run() {
				long startTime = 0;
				long delay = 0;
				long waitTime = 1000 / 60;
				while (updateThread != null) {
					startTime = System.currentTimeMillis();
					update();
					delay = waitTime - (System.currentTimeMillis() - startTime);
					if (delay > 0) {
						try {
							Thread.sleep(delay);
						} catch (InterruptedException e) {
							updateThread = null;
						}
					}
				}
			}
		});
		updateThread.start();
	}

	private String chooseBackGround() {
		List<String> background = PlayerData.playerData.getBackground();
		return background.get((int) (Math.random() * background.size()));
	}

	private String chooseLevel() {
		List<String> level = PlayerData.playerData.getLevel();
		String strLvl = level.get((int) (Math.random() * level.size()));
		System.out.println("[StageLevel] Loaded Level: " + strLvl.substring(13, strLvl.length() - 4));
		return strLvl;
	}

	private BufferedImage getItemImage() {
		// String mainPath = new File("").getAbsolutePath();
		// File file = new File(mainPath + "/bin/graphics/entity/coin/");
		// File[] fileArray = file.listFiles();
		// String str = fileArray[(int) (Math.random() * 10) % fileArray.length]
		// .getPath();
		// str = str.substring(mainPath.length() + 4);
		// str = str.replace('\\', '/');
		// try {
		// return ImageIO.read(getClass().getResourceAsStream(str));
		// } catch (IOException e) {
		// e.printStackTrace();
		// return null;
		// }
		BufferedImage img = null;
		try {
			img = ImageIO.read(getClass().getResourceAsStream("/graphics/entity/coin/coin.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public void update() {
		player.update();
		if (player.getyPos() > level.getHeight() * level.getTileSize()) {
			player.setyPos(0);
		}
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
				SoundManager.play("orb");
				items.remove(i);
				if (collectedItems == itemCount) {
					winn();
				}
			}
		}
	}

	public void close() {
		updateThread.interrupt();
		player.close();
		player = null;
		level = null;
		updateThread = null;
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
		int xStart = (int) (xMovement / level.getTileSize());
		int yStart = (int) (yMovement / level.getTileSize());
		int xEnd = xStart + GamePanel.WIDTH / level.getTileSize() + 1;
		int yEnd = yStart + GamePanel.HEIGHT / level.getTileSize() + 1;
		level.draw(g2, xStart, yStart, xEnd, yEnd);
		for (int i = 0; i < items.size(); i++) {
			items.get(i).draw(g2);
		}
		player.draw(g2);
		g2.setTransform(new AffineTransform());

		g2.setColor(Color.WHITE);
		g2.drawImage(itemImage, 5, 5, clock.getWidth(), clock.getHeight(), null);
		g2.drawString(collectedItems + " / " + itemCount, itemImage.getWidth() + 10, itemImage.getHeight() / 2 + 10);
		g2.drawImage(clock, 5, itemImage.getHeight() + 10, clock.getWidth(), clock.getHeight(), null);
		long currentTime = System.currentTimeMillis() - startTime;
		if (!hasWinn) {
			zehner = (int) (currentTime / 1000) / 60;
			einer = (int) (currentTime / 1000) % 60;
		}
		String strEiner = einer + "";
		if (einer < 10) {
			strEiner = "0" + einer;
		}
		g2.drawString(zehner + ":" + strEiner, clock.getWidth() + 10, clock.getHeight() / 2 + clock.getHeight() + 15);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			getStageManager().setStatge(StageManager.STAGE_MENUE, null);
		} else if (e.getKeyCode() == KeyEvent.VK_R) {
			getStageManager().setStatge(StageManager.STAGE_LEVEL, null);
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

	public void winn() {
		SoundManager.play("win");
		hasWinn = true;
		player.setWinn(true);
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				getStageManager().setStatge(StageManager.STAGE_SHOP, null);
			}
		}).start();
		PlayerData.playerData.setCoins(PlayerData.playerData.getCoins() + 20);
		PlayerData.save();
	}

}
