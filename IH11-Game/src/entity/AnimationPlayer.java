package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AnimationPlayer {

	//XML
	private String path;
	private int[] delay;
	private int[] length;
	
	private BufferedImage[][] images;
	private long lastChange;
	private int index;
	private int animation;
	
	public AnimationPlayer(){
		animation = -1;
	}

	public void load(){
		try {
			images = new BufferedImage[5][];
			BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path));
			for(int i = 0; i < images.length; i++){
				BufferedImage[] subImages = new BufferedImage[length[i]];
				for(int j = 0; j < length[i]; j++){
					subImages[j] = image.getSubimage(j * 32, i * 32, 32, 32);
				}
				images[i] = subImages;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getImage(int animation){
		if(this.animation != animation){
			this.animation = animation;
			index = 0;
			lastChange = System.currentTimeMillis();
		} else if(System.currentTimeMillis() > lastChange + delay[animation]){
			lastChange += delay[animation];
			index = (index + 1) % length[animation];
		}
		return images[animation][index];
	}
	
	//XML
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int[] getDelay() {
		return delay;
	}

	public void setDelay(int[] delay) {
		this.delay = delay;
	}

	public int[] getLength() {
		return length;
	}

	public void setLength(int[] length) {
		this.length = length;
	}
	
}
