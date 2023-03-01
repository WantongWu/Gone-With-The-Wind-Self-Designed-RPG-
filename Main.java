import javax.swing.JFrame;

/**
 *  Final Project: We did a RPG that takes a dragon as the protagonist,
 *  and she needs to find out what is going on in the forest throughout the process of
 *  exploring the forest. Main class is to control the start of the game and set the
 *  original JFrame settings.
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 *  @Reference:
 *  -  https://www.youtube.com/watch?v=om59cwR7psI&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq&index=1
 */
class Main {
	
	/** Run the game */
  public static void main(String[] args) {
    JFrame window = new JFrame();
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.setTitle("GONE WITH THE WIND");
    
    GamePanel gamePanel = new GamePanel();
    window.add(gamePanel);

    window.pack();
    
    window.setLocationRelativeTo(null);
    window.setVisible(true);

    gamePanel.setupGame();
    gamePanel.startGameThread();
  }
}