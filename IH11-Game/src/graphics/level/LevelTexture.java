package graphics.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LevelTexture {

	private String path;
	private String name;
	private BufferedImage[] tileArray;
	private BufferedImage image;

	public final int NORTHWEST = 0;
	public final int NORTH = 1;
	public final int NORTHEAST = 2;
	public final int WEST = 3;
	public final int CENTER = 4;
	public final int EAST = 5;
	public final int SOUTHWEST = 6;
	public final int SOUTH = 7;
	public final int SOUTHEAST = 8;

	LevelTexture(String path, String name) {
		this.path = path;
		this.name = name;
		tileArray = new BufferedImage[9];
		try {
			image = ImageIO.read(new URL(path));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				tileArray[x * 3 + y] = image.getSubimage(x * 32, y * 32, 32, 32);
			}
		}
	}

	public BufferedImage getTile(int tileNumber) {
		return tileArray[tileNumber];
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
