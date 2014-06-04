package staging;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import main.GamePanel;

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
	private GamePanel gamePanel;
	private MouseListener mouseListener;

	public StageManager(GamePanel gamePanel, int startStage) {
		this.gamePanel = gamePanel;
		setStatge(startStage, null);
		try {
			loadingScreen = ImageIO.read(getClass().getResourceAsStream("/graphics/loading/DummyLoading.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setStatge(int stageID, Map<String, String> data) {
		if(this.mouseListener != null){
			gamePanel.removeMouseListener(this.mouseListener);
		}
		startedLoadingTime = System.nanoTime();
		if (stage != null) {
			Stage s = stage;
			stage = null;
			s.close();
		}
		if (stageID == STAGE_WELCOME) {
			stage = new StageWelcome(this, data);
		} else if (stageID == STAGE_MENUE) {
			stage = new StageMenue(this, data);
		} else if (stageID == STAGE_LEVEL) {
			stage = new StageLevel(this, data);
		} else if (stageID == STAGE_SHOP) {
			stage = new StageShop(this, data);
		} else if (stageID == STAGE_SHOP_BACKGROUNDS) {
			stage = new StagShopBackgrounds(this, data);
		} else if (stageID == STAGE_SHOP_PLAYER) {
			stage = new StagShopPlayer(this, data);
		} else if (stageID == STAGE_LEVELEDITOR) {
			stage = new StageLevelEditor(this, data);
		}
		System.out.println("[StageManager] Stage " + stageID + " took " + ((double) (System.nanoTime() - startedLoadingTime) / 1000000000)
				+ " Seconds to load!");
	}
	
	public void setMouseListener(MouseListener mouseListener){
		if(this.mouseListener != null){
			gamePanel.removeMouseListener(this.mouseListener);
		}
		this.mouseListener = mouseListener;
		gamePanel.addMouseListener(mouseListener);
	}

	public void draw(Graphics2D g2) {
		g2.setTransform(new AffineTransform());
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
