package main;

import javax.swing.JFrame;

public class GameFrame extends JFrame{

	private GamePanel gamePanel;
	
	public GameFrame(){
		init();
	}
	
	public void init(){
		setSize(800, 600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gamePanel = new GamePanel();
		setContentPane(gamePanel);
		setVisible(true);
	}
	
}
