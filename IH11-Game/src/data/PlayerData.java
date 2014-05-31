package data;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PlayerData {

	public static PlayerData playerData;

	private int coins;
	private String name;
	private List<String> level;
	private List<String> background;
	private List<String> tileSet;
	private List<String> character;

	public static PlayerData getPlayerData() {
		return playerData;
	}

	public static void setPlayerData(PlayerData playerData) {
		PlayerData.playerData = playerData;
	}

	public static void load() {
		playerData = JAXB.unmarshal(new File("./Player.xml"), PlayerData.class);
	}

	public static void save() {
		JAXB.marshal(playerData, new File("./Player.xml"));
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getLevel() {
		return level;
	}

	public void setLevel(List<String> level) {
		this.level = level;
	}

	public List<String> getBackground() {
		return background;
	}

	public void setBackground(List<String> background) {
		this.background = background;
	}

	public List<String> getTileSet() {
		return tileSet;
	}

	public void setTileSet(List<String> tileSet) {
		this.tileSet = tileSet;
	}

	public List<String> getCharacter() {
		return character;
	}

	public void setCharacter(List<String> character) {
		this.character = character;
	}

}
