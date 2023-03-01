import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.*;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *  Class to represent entities and store variables that will be 
 *  used in player, monster, and NPC classes.
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class Entity {
	
	/** Game Panel */
	public GamePanel gp;
	
	/** Position of entity in WORLD MAP */
  public int worldX, worldY;

  
  /** COLLLISION & INTERACT */
  /** Marks the area on entity where collision happens */
  public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
  
  /** Default x and y values that are reset after every detection */
  public int solidAreaDefaultX = solidArea.x;
  public int solidAreaDefaultY = solidArea.y;
	
	/** Detect collision */
	public boolean collision = false;
  
  /** STATUS */
  /** Name of the entity */
	public String name;
  
  /** Type of entity */
  public EntityType type;

  /** Store dialogue of entity */
  public String dialogues[][] = new String[20][20];

  /** dialogue set (row of dialogues 2d array) */
  public int dialogueSet = 0;

  /** dialogue index (column of dialogues 2d array) */
  public int dialogueIndex = 0;

  /** Constructor */
  public Entity(GamePanel gp) {
  	this.gp = gp;
  }

  /** Get the image of the entity */
  public void getImage() {}

  /** Set up image of the entity
	 *  @param path location of the image
	 *  @param width the width of the image to be set
	 *  @param height the height of the image to be set
   *  @return BufferedImage variable to store image
	 */
  public BufferedImage setupImage(String path, int width, int height) {
  	
  	UtilityTool uTool = new UtilityTool();
  	BufferedImage image = null;
  	
  	try {
  		
      image = ImageIO.read(getClass().getResourceAsStream("/src/" + path + ".png"));
      image = uTool.scaleImage(image, width, height);
      
    } catch (IOException e) {
      e.printStackTrace();
    }
  	
  	return image;
  }

  /** draw the image of entity
   *  @param g2 the Graphics2D that draw the image
   */
  public void draw(Graphics2D g2) {}

  /** Change the transparency of the image
   *  @param g2 the Graphics2D that draw the image
   *  @param alpha the degree of transparency
   */
  public void changeAlpha(Graphics2D g2, float alpha) {
  	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
  }

  /** Update entity */
  public void update() {}

  /** Start dialogue of an entity
   *  @param speaker the entity who the protagonist starts dialogue with
   *  @param setNum the index of set of the dialogue to be started
   */
  public void startDialogue(Entity speaker, int setNum) {
  	gp.state = GameState.DIALOGUE;
  	gp.ui.speaker = speaker;
  	dialogueSet = setNum;
  }

  /** Start dialogue of an entity with options
   *  @param speaker the entity who the protagonist starts dialogue with
   *  @param setNum the index of set of the dialogue to be started
   *  @param op1 description of op1
   *  @param op2 description of op2
   */
  public void startDialogue(Entity speaker, int setNum, String op1, String op2) {
  	gp.ui.dialSubwindow = op1 + "@" + op2;
  	gp.state = GameState.DIALOGUE;
  	gp.ui.speaker = speaker;
  	dialogueSet = setNum;
  }

  /** Get the left x coordinate of the entity
   *  @param int left x coordinate
   */
  public int getLeftX() {
  	return worldX + solidArea.x;
  }

  /** Get the right x coordinate of the entity
   *  @param int right x coordinate
   */
  public int getRightX() {
  	return worldX + solidArea.x + solidArea.width;
  }

  /** Get the top y coordinate of the entity
   *  @param int top y coordinate
   */
  public int getTopY() {
  	return worldY + solidArea.y;
  }

  /** Get the bottom y coordinate of the entity
   *  @param int bottom y coordinate
   */
  public int getBottomY() {
  	return worldY + solidArea.y + solidArea.height;
  }

  /** Get the world column of the entity
   *  @param int world column
   */
  public int getCol() {
  	return (worldX + solidArea.x) / gp.tileSize;
  }

  /** Get the world row of the entity
   *  @param int world row
   */
  public int getRow() {
  	return (worldY + solidArea.y) / gp.tileSize;
  }
  
}