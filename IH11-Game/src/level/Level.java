package level;

import java.awt.Graphics2D;
import java.io.File;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import level.graphics.LevelTexture;

@XmlRootElement
public class Level {

	private byte[][] tileSet;
	private LevelTexture levelTexture;
	private int tileSize = 32;

	private double startPositionX;
	private double startPositionY;

	public Level() {
		tileSet = new byte[1][1];
		levelTexture = new LevelTexture("/graphics/level/DirtGrassTileSet.png", "Test");
	}

	public void draw(Graphics2D g2) {
		for (int x = 0; x < tileSet.length; x++) {
			for (int y = 0; y < tileSet[x].length; y++) {
				if (tileSet[x][y] != LevelTexture.AIR) {
					g2.drawImage(levelTexture.getTile(tileSet[x][y]), x * tileSize, y * tileSize, tileSize, tileSize,
							null);
				}
			}
		}
	}
	
	public void save(String name) {
		JAXB.marshal(this, new File(name + ".xml"));
	}
	
	public int getWidth() {
		return tileSet.length;
	}

	public int getHeight() {
		return tileSet[0].length;
	}

	public byte getTileID(int x, int y) {
		return tileSet[x][y];
	}

	@XmlElement(name = "TileSet")
	public byte[][] getTileSet() {
		return tileSet;
	}

	public void setTileSet(byte[][] tileSet) {
		this.tileSet = tileSet;
	}

	public double getStartPositionX() {
		return startPositionX;
	}

	public void setStartPositionX(double startPositionX) {
		this.startPositionX = startPositionX;
	}

	public double getStartPositionY() {
		return startPositionY;
	}

	public void setStartPositionY(double startPositionY) {
		this.startPositionY = startPositionY;
	}

	public double getTileSize() {
		return tileSize;
	}

}
