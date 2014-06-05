package network;

public class NetworkTest {

	public static void main(String[] args) {
		NetworkManager conn = new NetworkManager();

		conn.login("Philipp", "baum");
		System.out.println(conn.getCoins());
		System.out.println(conn.getUnlocked());
		System.out.println(conn.getPlaytime());

		conn.setCoins(conn.getCoins() + 30);
		conn.setUnlocked(conn.getUnlocked() + "B");
		conn.setPlaytime(conn.getPlaytime() + 30);

		conn.updateDB();
	}

}
