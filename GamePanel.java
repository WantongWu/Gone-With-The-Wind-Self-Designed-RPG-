import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 *  Game Panel that controls every handlers, set the game, and manage the central processes
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class GamePanel extends JPanel implements Runnable {

	/** TILE SETTINGS */
	/** original tile size (16*16) */
  final int originalTileSize = 16;
  
  /** scale of the tile */
  final int scale = 3;

  /** FINAL TILE SIZE (48*48) */
  public final int tileSize = originalTileSize * scale;
  
  
  /** SCREEN SETTINGS */
  /** maximum screen column */
  public final int maxScreenCol = 16;
  
  /** maximum screen row */
  public final int maxScreenRow = 12;
  
  /** screen width */
  public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
  
  /** screen height */
  public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

  
  /** WORLD SETTINGS */
  /** maximum world column */
  public final int maxWorldCol = 116;
  
  /** maximum world row */
  public final int maxWorldRow = 116;
  
  
  /** INITIALIZE GAME & TOOLS */
  /** Frame per second */
  final int FPS = 60;
  
  /** Tile Manager that regulates the tiles */
  public TileManager tileM = new TileManager(this);
  
  /** Key Handler that handles the key input */
  public KeyHandler keyH = new KeyHandler(this);
  
  /** CollisionChecker that checks the collision */
  public CollisionChecker cCheck = new CollisionChecker(this);
  
  /** AssetSetter that handles the assets */
  public AssetSetter aSetter = new AssetSetter(this);
  
  /** Handle Events triggered by position */
  public EventHandler eventH = new EventHandler(this);
  
  /** User Interface */
  public UI ui = new UI(this);

  /** Card pool that includes every card */
  public final CardPile cardPool = new CardPile(this, "/src/settings/Card_setting.txt");

  /** Achievement pool that includes every achievement */
  public Achievements achiPool = new Achievements(this, "/src/settings/Achievement_setting.txt");

  /** Game phase */
  public int gamePhase = 0;

  /** Path finder that directs the cat */
  public PathFinder pFinder = new PathFinder(this);

  /** Save and Load tool that manages the saved progress */
  public SaveLoad saveLoad = new SaveLoad(this);
  
  /** Game Thread */
  Thread gameThread;
  
  
  /** ENTITY AND OBJECT */
  /** Initialize player */
  public Player player = new Player(this, keyH);
  
  /** Initialize an array that stores all the objects */
  public OBJ obj[] = new OBJ[200];

  /** Initialize an array that stores all the npcs */
  public NPC npc[] = new NPC[40];

  /** Initialize an array that stores all the entity (sorted to manage draw sequence) */
  ArrayList<Entity> entity = new ArrayList<>();
  
  
  /** GAME STATES */
  /** Variable that controls game state */
  public GameState state;
  
  
  /** Constructor */
  public GamePanel() {
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.white);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyH);
    this.setFocusable(true);
  }
  
  /** Set up objects in map */
  public void setupGame() {
  	aSetter.setObject();
  	aSetter.setNPC();
  	state = GameState.TITLE;
  }

  /** Reset the game
   *  @param restart if the whole game is restart
   */
  public void resetGame(boolean restart) {
  	player.setDefaultStatus();
  	aSetter.setNPC();
  	ui.message.clear();
  	
  	if(restart) {
      gamePhase = 0;
  		player.setDefaultItems();
    	player.setDefaultValue();
    	aSetter.setObject();
  	}
  }

  /** Start game thread */
  public void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
  }
  
  /** @Override */
  public void run() {

    double drawInterval = 1000000000/FPS;
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    
    // Game loop
    while(gameThread != null) {
      
      currentTime = System.nanoTime();
      delta += (currentTime - lastTime) / drawInterval;
      lastTime = currentTime;

      if (delta >= 1) {
        // Update information (such as character positions)
        update();
        
        // Draw the screen with the update information
        repaint();
        delta--;
      }
    }
  }

  /** Update the variables */
  public void update() {
  	if (state == GameState.PLAY) {
  		player.update();
  		for(int i = 0; i < npc.length; i++) {
      	if(npc[i] != null) {
      		npc[i].update();
      	}
      }
    } if (state == GameState.PAUSE) {
    	
    }
  }

  /** Update the graphics 
   *  @param g Graphics to update
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    Graphics2D g2 = (Graphics2D) g;
    
    // Main menu screen
    if(state == GameState.TITLE) {
    	ui.draw(g2);
    } else {
    	// Draw tiles
      tileM.draw(g2);
      
      // Draw entity
      entity.add(player);
      for(Entity i: npc) {
      	if(i != null) {
      		entity.add(i);
      	}
      }
      for(Entity i: obj) {
      	if(i != null) {
      		entity.add(i);
      	}
      }
      
      Collections.sort(entity, new Comparator<Entity>() {
      	public int compare(Entity e1, Entity e2) {
      		int result = Integer.compare(e1.worldY, e2.worldY);
      		return result;
      	}
      });
      
      for(Entity i: entity) {
      	i.draw(g2);
      }
      entity.clear();
      
      // Draw UI
      ui.draw(g2);
      
      g2.dispose();
    }
    
  }
  
}