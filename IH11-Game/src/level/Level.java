package level;

import java.awt.Graphics2D;

import javax.xml.bind.annotation.XmlRootElement;

import level.graphics.LevelTexture;

@XmlRootElement
public class Level {

	private byte[][] tileSet;
	private LevelTexture levelTexture;
	private int tileSize = 32;
	
	public Level(){
		tileSet = new byte[1][1];
		levelTexture = new LevelTexture("/graphics/level/DummySetGreen001.png", "Test");
	}
	
	public void draw(Graphics2D g2){
		for(int x = 0; x < tileSet.length; x++){
			for(int y = 0; y < tileSet[x].length; y++){
				g2.drawImage(levelTexture.getTile(tileSet[x][y]), x * tileSize, y * tileSize, tileSize, tileSize, null);
			}
		}
	}
	
	public int getWidth(){
		return tileSet.length;
	}
	
	public int getHeight(){
		return tileSet[0].length;
	}
	
	public byte getTileID(int x, int y){
		return tileSet[x][y];
	}

	public byte[][] getTileSet() {
		return tileSet;
	}

	public void setTileSet(byte[][] tileSet) {
		this.tileSet = tileSet;
	}
	
}
