package level;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.EntityPlayer;

public class Item {

	private int posX;
	private int posY;
	private BufferedImage image;
	
	public Item(BufferedImage image, int posX, int posY){
		this.image = image;
		this.posX = posX;
		this.posY = posY;
	}
	
	public void draw(Graphics2D g2){
		g2.drawImage(image, posX * 32, posY * 32, 32, 32, null);
	}
	
	public boolean canCollectCoin(EntityPlayer player){
		return posX == player.getxPos() / 32 && posY == player.getyPos() / 32;
	}
	
}
