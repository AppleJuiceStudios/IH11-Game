package staging;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class StageMenue extends Stage {

	private int selectedItem = 0;
	private int items = 2;
	private BufferedImage[][] buttons = new BufferedImage[items][2];
	private BufferedImage background;

	public StageMenue() {
		try {
			background = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/MenueBackground.png"));
			buttons[0][0] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/PlayButtonPressed.png"));
			buttons[0][1] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/PlayButtonUnpressed.png"));
			buttons[1][0] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/QuitButtonPressed.png"));
			buttons[1][1] = ImageIO.read(getClass().getResourceAsStream("/graphics/menue/QuitButtonUnpressed.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(background, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);

		for (int i = 0; i < items; i++) {
			Rectangle rect = new Rectangle((GamePanel.WIDTH / 2) - (90 / 2), GamePanel.HEIGHT + 20 + i * 50, 90, 46);

			if (i == selectedItem) {
				g2.drawImage(buttons[i][0], rect.x, rect.y, rect.width, rect.height, null);
			} else {
				g2.drawImage(buttons[i][1], rect.x, rect.y, rect.width, rect.height, null);
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
		if (e.getKeyChar() == KeyEvent.VK_UP || e.getKeyChar() == KeyEvent.VK_W) {
			if (selectedItem < items - 1) {
				selectedItem++;
			} else {
				selectedItem = 0;
			}
		}
		if (e.getKeyChar() == KeyEvent.VK_DOWN || e.getKeyChar() == KeyEvent.VK_S) {
			if (selectedItem < 0) {
				selectedItem--;
			} else {
				selectedItem = items;
			}
		}
		if (e.getKeyChar() == KeyEvent.VK_ENTER || e.getKeyChar() == KeyEvent.VK_SPACE) {
			switch (selectedItem) {
			case 0:

				break;
			case 1:

				break;
			}
		}
	}
}
