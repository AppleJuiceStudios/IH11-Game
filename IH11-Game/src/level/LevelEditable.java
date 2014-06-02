package level;

import level.graphics.LevelTexture;

public class LevelEditable extends Level {
	
	public LevelEditable(){
		super();
		setTileSet(new byte[10][10]);
	}

	public void setTileID(int x, int y, byte id) {
		if (!(x < getWidth() & y < getHeight() & x >= 0 & y >= 0)) {
			setTileSet(resize(Math.max(x + 1, getWidth()), Math.max(y + 1, getHeight()), Math.min(x, 0), Math.min(y, 0)));
			x -= Math.min(x, 0);
			y -= Math.min(y, 0);
		}
		getTileSet()[x][y] = id;
	}

	public void resize(int width, int height) {
		resize(width, height, 0, 0);
	}

	public byte[][] resize(int width, int height, int xOffset, int yoffset) {
		System.out.println("Resize;" + width  + ";" + height  + ";" + xOffset  + ";" + yoffset);
		byte[][] newTileSet = new byte[width - xOffset][height - yoffset];
		byte[][] altTileSet = getTileSet();
		for (int x = 0; x < newTileSet.length; x++) {
			for (int y = 0; y < newTileSet[0].length; y++) {
				if ((x + xOffset < altTileSet.length & x + xOffset >= 0) && (y + yoffset < altTileSet[0].length & y + yoffset >= 0)) {
					newTileSet[x][y] = altTileSet[x + xOffset][y + yoffset];
				} else {
					newTileSet[x][y] = LevelTexture.AIR;
				}
			}

		}
		return newTileSet;
	}

	public void calculateTileSet(int x, int y, boolean neighborTiles) {
		if (isInTileSet(x, y) && getTileSet()[x][y] != LevelTexture.AIR) {
			byte tileID = 0;
			boolean uT = getTileID(x, y - 1) != LevelTexture.AIR;
			boolean dT = getTileID(x, y + 1) != LevelTexture.AIR;
			boolean lT = getTileID(x - 1, y) != LevelTexture.AIR;
			boolean rT = getTileID(x + 1, y) != LevelTexture.AIR;
			if (!uT & !dT & !lT & !rT)
				tileID = LevelTexture.NORTH;
			else if (!uT & !dT & !lT & rT)
				tileID = LevelTexture.WEST;
			else if (!uT & !dT & lT & !rT)
				tileID = LevelTexture.EAST;
			else if (!uT & !dT & lT & rT)
				tileID = LevelTexture.NORTH;
			else if (!uT & dT & !lT & !rT)
				tileID = LevelTexture.NORTH;
			else if (!uT & dT & !lT & rT)
				tileID = LevelTexture.NORTHWEST;
			else if (!uT & dT & lT & !rT)
				tileID = LevelTexture.NORTHEAST;
			else if (!uT & dT & lT & rT)
				tileID = LevelTexture.NORTH;
			else if (uT & !dT & !lT & !rT)
				tileID = LevelTexture.SOUTH;
			else if (uT & !dT & !lT & rT)
				tileID = LevelTexture.SOUTHWEST;
			else if (uT & !dT & lT & !rT)
				tileID = LevelTexture.SOUTHEAST;
			else if (uT & !dT & lT & rT)
				tileID = LevelTexture.SOUTH;
			else if (uT & dT & !lT & !rT)
				tileID = LevelTexture.WEST;
			else if (uT & dT & !lT & rT)
				tileID = LevelTexture.WEST;
			else if (uT & dT & lT & !rT)
				tileID = LevelTexture.EAST;
			else if (uT & dT & lT & rT)
				tileID = LevelTexture.CENTER;
			getTileSet()[x][y] = tileID;
		}
		if (neighborTiles) {
			calculateTileSet(x + 1, y, false);
			calculateTileSet(x - 1, y, false);
			calculateTileSet(x, y + 1, false);
			calculateTileSet(x, y - 1, false);
		}
	}

}
