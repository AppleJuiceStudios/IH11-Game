package level.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LevelTexture {

	private String path;
	private String name;
	private BufferedImage[] tileArray;
	private BufferedImage image;

	public static final int NORTHWEST = 0;
	public static final int NORTH = 1;
	public static final int NORTHEAST = 2;
	public static final int WEST = 3;
	public static final int CENTER = 4;
	public static final int EAST = 5;
	public static final int SOUTHWEST = 6;
	public static final int SOUTH = 7;
	public static final int SOUTHEAST = 8;
	public static final int AIR = 9;

	public LevelTexture() {

	}

	public LevelTexture(String path, String name) {
		this.path = path;
		this.name = name;
		tileArray = new BufferedImage[9];
		try {
			image = ImageIO.read(getClass().getResourceAsStream(path));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				tileArray[x * 3 + y] = image.getSubimage(x * 32, y * 32, 32, 32);
			}
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BufferedImage getTile(int tileNumber) {
		return tileArray[tileNumber];
	}
}
