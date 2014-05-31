package level;

public class LevelEditor {

	public static void main(String[] args) {
		Level level = new Level();
		byte[][] byteArr = new byte[][] { 
			new byte[] { 5, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
			new byte[] { 5, 9, 9, 0, 1, 2, 9, 9, 9, 9, 9, 9, 9 },
			new byte[] { 5, 9, 9, 3, 4, 5, 9, 9, 9, 9, 9, 9, 9 },
			new byte[] { 5, 9, 9, 6, 7, 8, 9, 9, 9, 9, 9, 9, 9 },
			new byte[] { 5, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
			new byte[] { 5, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
			new byte[] { 5, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0, 1, 1 },
			new byte[] { 4, 2, 9, 9, 9, 9, 9, 9, 9, 0, 4, 4, 4 },
			new byte[] { 4, 4, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4 },
			new byte[] { 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 }
		};
		byte[][] byteArrNew = new byte[byteArr[0].length][byteArr.length];

		for (int i = 0; i < byteArr.length; i++) {
			for (int j = 0; j < byteArr[0].length; j++) {
				byteArrNew[j][i] = byteArr[i][j];
			}
		}
		level.setTileSet(byteArrNew);
		level.setStartPositionX(32.0);
		level.setStartPositionY(32.0);
		level.save("res/data/levels/Level1");
	}
}
