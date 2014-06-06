package level;

import java.awt.Graphics2D;
import java.io.File;
import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import data.PlayerData;
import level.graphics.LevelTexture;

@XmlRootElement
public class Level {

	private byte[][] tileSet;
	private LevelTexture levelTexture;
	private int tileSize = 32;

	private double startPositionX;
	private double startPositionY;

	public Level() {
		tileSet =  new byte[10][10];
		levelTexture = new LevelTexture(chooseTileSet(), "TileSet");
	}

	private String chooseTileSet() {
		List<String> tileSet = PlayerData.playerData.getTileSet();
		return tileSet.get((int) (Math.random() * tileSet.size()));
	}

	public void draw(Graphics2D g2, int xStart, int yStart, int xEnd, int yEnd) {
		for (int x = xStart; x <= xEnd; x++) {
			for (int y = yStart; y <= yEnd; y++) {
				if (isInTileSet(x, y) && (getTileID(x, y) != LevelTexture.AIR)) {
					g2.drawImage(levelTexture.getTile(getTileID(x, y)), x * tileSize, y * tileSize, tileSize, tileSize,
							null);
				}
			}
		}
	}
	
	public boolean isInTileSet(int x, int y){
		return x >= 0 & x < getWidth() & y >= 0 & y < getHeight();
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
		if(x < 0 | x >= getWidth()){
			return LevelTexture.CENTER;
		} else if(y < 0){
			return LevelTexture.AIR;
		} else if (y >= getHeight()){
			if(tileSet[x][getHeight() - 1] == LevelTexture.AIR){
				return tileSet[x][0];
			} else {
				return LevelTexture.CENTER;
			}
			
		} else {
			return tileSet[x][y];
		}
	}
	
	@XmlElement(name = "TileSet")
	public byte[][] getTileSet() {
		return this.tileSet;
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

	public int getTileSize() {
		return tileSize;
	}
	
	public void setTileDrawSize(int i) {
		tileSize = i;
	}

}
