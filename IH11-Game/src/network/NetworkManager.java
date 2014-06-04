package network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class NetworkManager {

	private final String url = "jdbc:mysql://db4free.net:3306/";
	private final String dbName = "testdbauch";
	private final String driver = "com.mysql.jdbc.Driver";
	private final String dbUser = "philippauch";
	private final String dbPassword = "Baumhaus";
	private Connection conn;
	private Statement st;

	private String user;
	private int coins;
	private String unlocked;
	private int playtime;

	private void openConnection() {
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + dbName, dbUser, dbPassword);
			st = conn.createStatement();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void closeConnection() {
		try {
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void updateDB() {
		try {
			openConnection();
			st.execute("UPDATE Player SET Coins='" + coins + "' WHERE Name='" + user + "'");
			st.execute("UPDATE Player SET Unlocked='" + unlocked + "' WHERE Name='" + user + "'");
			st.execute("UPDATE Player SET Playtime='" + playtime + "' WHERE Name='" + user + "'");
			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean login(String user, String password) {
		this.user = user;
		Map<String, String> logins = new HashMap<String, String>();
		try {
			openConnection();
			ResultSet res = st.executeQuery("SELECT Name,Password FROM Player");

			while (res.next()) {
				logins.put(res.getString("Name"), res.getString("Password"));
			}

			if (!password.equals(logins.get(user))) {
				return false;
			}

			res = st.executeQuery("SELECT * FROM Player WHERE name='" + user + "'");
			res.first();
			coins = res.getInt("Coins");
			unlocked = res.getString("Unlocked");
			playtime = res.getInt("Playtime");

			closeConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public String getUnlocked() {
		return unlocked;
	}

	public void setUnlocked(String unlocked) {
		this.unlocked = unlocked;
	}

	public int getPlaytime() {
		return playtime;
	}

	public void setPlaytime(int playtime) {
		this.playtime = playtime;
	}

}
