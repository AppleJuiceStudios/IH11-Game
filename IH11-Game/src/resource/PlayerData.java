package resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.jdbc.Statement;

public class PlayerData {

	private static String userName;
	private static int coins;
	private static long playtime;
	private static byte[] lck_lvl;
	private static byte[] lck_bg;
	private static byte[] lck_chr;
	private static byte[] lck_msc;
	private static byte[] lck_til;
	private static byte[] lck_obj;

	private static boolean isSaved = false;
	private static boolean isDBsynced = false;

	public static boolean loadDB(ResultSet res, String userName) {
		try {
			PlayerData.userName = userName;
			coins = res.getInt("Coins");
			playtime = res.getLong("Playtime");
			lck_lvl = res.getString("lck_lvl").getBytes();
			lck_bg = res.getString("lck_bg").getBytes();
			lck_chr = res.getString("lck_chr").getBytes();
			lck_msc = res.getString("lck_msc").getBytes();
			lck_til = res.getString("lck_til").getBytes();
			lck_obj = res.getString("lck_obj").getBytes();
			isDBsynced = true;
			isSaved = false;
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean saveDB(Statement st) {
		try {
			st.execute("UPDATE Player SET Coins='" + coins + "' WHERE Name='" + userName + "'");
			st.execute("UPDATE Player SET Playtime='" + playtime + "' WHERE Name='" + userName + "'");
			st.execute("UPDATE Player SET lck_lvl='" + lck_lvl + "' WHERE Name='" + userName + "'");
			st.execute("UPDATE Player SET lck_bg='" + lck_bg + "' WHERE Name='" + userName + "'");
			st.execute("UPDATE Player SET lck_chr='" + lck_chr + "' WHERE Name='" + userName + "'");
			st.execute("UPDATE Player SET lck_msc='" + lck_msc + "' WHERE Name='" + userName + "'");
			st.execute("UPDATE Player SET lck_til='" + lck_til + "' WHERE Name='" + userName + "'");
			st.execute("UPDATE Player SET lck_obj='" + lck_obj + "' WHERE Name='" + userName + "'");
			isDBsynced = true;
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean loadLocal(File file) {
		try {
			Scanner scan = new Scanner(file);
			userName = scan.nextLine();
			coins = scan.nextInt();
			playtime = scan.nextLong();
			lck_lvl = scan.nextBigInteger().toByteArray();
			lck_bg = scan.nextBigInteger().toByteArray();
			lck_chr = scan.nextBigInteger().toByteArray();
			lck_msc = scan.nextBigInteger().toByteArray();
			lck_til = scan.nextBigInteger().toByteArray();
			lck_obj = scan.nextBigInteger().toByteArray();
			scan.close();
			isSaved = true;
			isDBsynced = false;
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean saveLocal(File file) {
		try {
			PrintStream out = new PrintStream(file);
			out.println(userName);
			out.println(coins);
			out.println(playtime);
			out.println(new BigInteger(lck_lvl));
			out.println(new BigInteger(lck_bg));
			out.println(new BigInteger(lck_chr));
			out.println(new BigInteger(lck_msc));
			out.println(new BigInteger(lck_til));
			out.println(new BigInteger(lck_obj));
			out.flush();
			out.close();
			isSaved = true;
			isDBsynced = false;
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void addCoins(int amount) {
		coins += amount;
		isSaved = false;
	}

	public static void removeCoins(int amount) {
		coins -= amount;
		isSaved = false;
	}

	public static void addPlayTime(long time) {
		playtime += time;
		isSaved = false;
	}

	public static void unlockLVL(int id) {
		lck_lvl[id / 8] += 1 << (id % 8);
	}

	public static void unlockBG(int id) {
		lck_bg[id / 8] += 1 << (id % 8);
	}

	public static void unlockCHR(int id) {
		lck_chr[id / 8] += 1 << (id % 8);
	}

	public static void unlockMSC(int id) {
		lck_msc[id / 8] += 1 << (id % 8);
	}

	public static void unlockTIL(int id) {
		lck_til[id / 8] += 1 << (id % 8);
	}

	public static void unlockOBJ(int id) {
		lck_obj[id / 8] += 1 << (id % 8);
	}

	public static int getCoins() {
		return coins;
	}

	public static byte[] getLck_lvl() {
		return lck_lvl;
	}

	public static byte[] getLck_bg() {
		return lck_bg;
	}

	public static byte[] getLck_chr() {
		return lck_chr;
	}

	public static byte[] getLck_msc() {
		return lck_msc;
	}

	public static byte[] getLck_til() {
		return lck_til;
	}

	public static byte[] getLck_obj() {
		return lck_obj;
	}

	public static boolean isSaved() {
		return isSaved;
	}

	public static boolean isDBsynced() {
		return isDBsynced;
	}

	public static String getUserName() {
		return userName;
	}

	public static long getPlaytime() {
		return playtime;
	}

	public static boolean isUnlockedLVL(int id) {
		return (lck_lvl[id / 8] & (1 << (id % 8))) > 0;
	}

	public static boolean isUnlockedBG(int id) {
		return (lck_bg[id / 8] & (1 << (id % 8))) > 0;
	}

	public static boolean isUnlockedCHR(int id) {
		return (lck_chr[id / 8] & (1 << (id % 8))) > 0;
	}

	public static boolean isUnlockedMSC(int id) {
		return (lck_msc[id / 8] & (1 << (id % 8))) > 0;
	}

	public static boolean isUnlockedTIL(int id) {
		return (lck_til[id / 8] & (1 << (id % 8))) > 0;
	}

	public static boolean isUnlockedOBJ(int id) {
		return (lck_obj[id / 8] & (1 << (id % 8))) > 0;
	}

}
