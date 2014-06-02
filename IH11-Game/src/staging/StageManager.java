package staging;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class StageManager {

	private Stage stage;
	private BufferedImage loadingScreen;
	public static final int STAGE_WELCOME = 0;
	public static final int STAGE_MENUE = 1;
	public static final int STAGE_LEVEL = 2;
	public static final int STAGE_SHOP = 4;
	public static final int STAGE_SHOP_BACKGROUNDS = 5;
	public static final int STAGE_SHOP_PLAYER = 6;
	public static final int STAGE_LEVELEDITOR = 7;
	private long startedLoadingTime;

	public StageManager(int startStage) {
		setStatge(startStage);
		try {
			loadingScreen = ImageIO.read(getClass().getResourceAsStream("/graphics/loading/DummyLoading.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setStatge(int stageID) {
		startedLoadingTime = System.nanoTime();
		if (stage != null) {
			Stage s = stage;
			stage = null;
			s.close();
		}
		if (stageID == STAGE_WELCOME) {
			stage = new StageWelcome(this);
		} else if (stageID == STAGE_MENUE) {
			stage = new StageMenue(this);
		} else if (stageID == STAGE_LEVEL) {
			stage = new StageLevel(this);
		} else if (stageID == STAGE_SHOP) {
			stage = new StageShop(this);
		} else if (stageID == STAGE_SHOP_BACKGROUNDS) {
			stage = new StagShopBackgrounds(this);
		} else if (stageID == STAGE_SHOP_PLAYER) {
			stage = new StagShopPlayer(this);
		} else if (stageID == STAGE_LEVELEDITOR) {
			stage = new StageLevelEditor(this);
		}
		System.out.println("[StageManager] Took " + ((double) (System.nanoTime() - startedLoadingTime) / 1000000000) + " Seconds to load!");
	}

	public void draw(Graphics2D g2) {
		if (stage != null) {
			stage.draw(g2);
		} else {
			g2.drawImage(loadingScreen, 0, 0, 400, 300, null);
		}
	}

	public void close() {
		stage.close();
		System.exit(0);
	}

	public void keyPressed(KeyEvent e) {
		if (stage != null) {
			stage.keyPressed(e);
		}
	}

	public void keyReleased(KeyEvent e) {
		if (stage != null) {
			stage.keyReleased(e);
		}
	}

	public void keyTyped(KeyEvent e) {
		if (stage != null) {
			stage.keyTyped(e);
		}
	}
}
