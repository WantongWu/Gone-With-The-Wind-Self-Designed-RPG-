import java.awt.Rectangle;
import java.util.Random;

/**
 *  Class for the cat that follows the player
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class NPC_Cat extends NPC {

  /** Constructor */
	public NPC_Cat(GamePanel gp) {
		super(gp);
		name = "Cat";
		direction = gp.player.direction;
		speed = 2;
		onPath = false;
		collision = false;
    
		solidArea = new Rectangle(3, 6, gp.tileSize-6, gp.tileSize-6);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
		setDialogue();
	}
	
	/** Get the invisible image of the pet cat */
  public void getImage() {
    
  	up1 = up2 = left1 = left2 = down1 = down2 = right1 = right2 = setupImage("NPCs/cat/InvisibleCat", gp.tileSize, gp.tileSize);
    
  }

  /**  Get real/moving image of the pet cat */
  public void getRealImage() {
    up1 = setupImage("NPCs/cat/cat_back_down", gp.tileSize, gp.tileSize);
  	up2 = setupImage("NPCs/cat/cat_back_up", gp.tileSize, gp.tileSize);
  	left1 = setupImage("NPCs/cat/cat_left_down", gp.tileSize, gp.tileSize);
  	left2 = setupImage("NPCs/cat/cat_left_up", gp.tileSize, gp.tileSize);
    down1 = setupImage("NPCs/cat/cat_forward_down", gp.tileSize, gp.tileSize);
    down2 = setupImage("NPCs/cat/cat_forward_up", gp.tileSize, gp.tileSize);
    right1 = setupImage("NPCs/cat/cat_right_down", gp.tileSize, gp.tileSize);
    right2 = setupImage("NPCs/cat/cat_right_up", gp.tileSize, gp.tileSize);
  }

  /**  Dialogues between the player and the pet cat */
  public void setDialogue() {
  	dialogues[0][0] = "[Cat] \nGood morning, master of the \nforest.";
    dialogues[1][0] = "[Cat] \nMeowww :3";
    dialogues[2][0] = "[Cat] \nI’m not blocking you \nintentionally. This is just the \nway of cats.";
    dialogues[3][0] = "[Cat] \nSometimes I wish I were a dog.";
  }
  
  /**  Talk to the player */
  public void speak() {
  	int random_num = (int) (Math.random() * 4);
  	startDialogue(this, random_num);
  }

  /**  Set the action of the pet cat to make it follow the player */
  public void setAction() {
  	if(onPath) {
  		int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
  		int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
  		
  		searchPath(goalCol, goalRow);
  	} else {
  		actionCounter++;
  	
	  	if(actionCounter == 120) {
		  	Random random = new Random();
		  	int i = random.nextInt(100) + 1; // random number - [1, 100]
		  	
		  	if(i <= 25) {
		  		direction = "up";
		  	} else if (i <= 50) {
		  		direction = "down";
		  	} else if (i <= 75) {
		  		direction = "left";
		  	} else if (i <= 100) {
		  		direction = "right";
		  	}
		  	
		  	actionCounter = 0;
	  	}
  	}
  }
}
