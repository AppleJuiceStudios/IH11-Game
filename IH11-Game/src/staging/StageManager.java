package staging;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.omg.CORBA.Environment;
import org.omg.CORBA.portable.ApplicationException;

public class StageManager {

	private Stage stage;
	private BufferedImage loadingScreen;
	public static final int STAGE_MENUE = 1;
	public static final int STAGE_LEVEL = 2;
	public static final int STAGE_SHOP = 4;

	public StageManager(int startStage) {
		setStatge(startStage);
		try {
			loadingScreen = ImageIO.read(getClass().getResourceAsStream("/graphics/loading/DummyLoading.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setStatge(int stageID) {
		if (stage != null) {
			Stage s = stage;
			stage = null;
			s.close();
		}
		if (stageID == STAGE_MENUE) {
			stage = new StageMenue(this);
		} else if (stageID == STAGE_LEVEL) {
			stage = new StageLevel(this);
		} else if (stageID == STAGE_SHOP) {
			stage = new StageShop(this);
		}
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
