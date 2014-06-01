package main;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private GamePanel gamePanel;

	public GameFrame() {
		init();
	}

	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gamePanel = new GamePanel();
		setContentPane(gamePanel);
		setResizable(false);
		setTitle("Spiel");
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}
}
