package main;

import java.awt.EventQueue;

public class Main {
	public static GameFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				frame = new GameFrame();
			}
		});
	}
}
