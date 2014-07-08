package resource;

import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.Statement;

public class ResourceManager {
	
	private static boolean isOnline;
	
	// Offline
	private static String gameDataPath;
	
	// Online
	private static final String url = "jdbc:mysql://db4free.net:3306/";
	private static final String dbName = "testdbauch";
	private static final String driver = "com.mysql.jdbc.Driver";
	private static String dbUser;
	private static String dbPassword;
	private static Connection conn;
	private static Statement st;
	
	public static void init(String gameDataPath){
		ResourceManager.gameDataPath = gameDataPath;
	}
	
	public static boolean tryConnect(String player, String password){
		return false;
	}
	
	public static BufferedImage getImage(String s){
		return null;
	}
	
	
	
	public boolean isOnline(){
		return isOnline;
	}
	
}
