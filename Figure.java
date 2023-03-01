import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *  Class to represent figure (extends Entity, subclasses are NPC and Player)
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class Figure extends Entity{

	/** IMAGE */
	/** Store images */
  public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
  
  /** Counter of numbers of frames the figure goes through (decide the speed of updating sprite image) */
  public int spriteCounter = 0;
  
  /** Marks the image of the same direction of an entity (if any), varies between 1 and 2 */
  public int spriteNum = 1;
  
  /** Direction of entity, towards where it is facing */
  public String direction = "down";
  
  /** A boolean that marks if entity collides with tiles */
  public boolean collisionOn = false;

  /** Max life of figure */
  public int maxLife;

  /** Current life of figure */
  public int life;

  /** If the figure is alive */
  boolean alive = true;

  /** If the figure is dying */
  boolean dying = false;

  /** Dying counter, used only in animation to calculate alpha value time interval */
  int dyingCounter = 0;
  
  /** Moving speed */
  public int speed;

  /** health, used in battle */
  public int health;
  
  /** Max health of figure, restored after each battle */
  public int maxHealth;

  /** Damage value */
  public int strength;

  /** Store battle dialogue */
  public ArrayList<String> batDials = new ArrayList<String>();

  /** Battle dialogue index */
  public int batDialIndex = 0;

  /** Constructor */
  public Figure(GamePanel gp) {
		super(gp);
	}

  /** Draw figure picture
   *  @param g2 Graphics2D where the image is drawn
   */
  public void draw(Graphics2D g2) {
  	BufferedImage image = null;
  	
  	if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
				worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			switch(direction) {
      case "up":
	    	if (spriteNum == 1) {
	    		image = up1;
	    	}
        if (spriteNum == 2) {
        	image = up2;
        }
        break;
      case "down":
      	if (spriteNum == 1) {
	    		image = down1;
	    	}
        if (spriteNum == 2) {
        	image = down2;
        }
        break;
      case "left":
      	if (spriteNum == 1) {
	    		image = left1;
	    	}
        if (spriteNum == 2) {
        	image = left2;
        }
        break;
      case "right":
      	if (spriteNum == 1) {
	    		image = right1;
	    	}
        if (spriteNum == 2) {
        	image = right2;
        }
        break;
    }
			g2.drawImage(image, screenX, screenY, null);
		}
  }

  /** Draw dying animation, not in use 
   *  @param g2 Graphics2D where the image is drawn
   */
  private void dyingAnimation(Graphics2D g2) {
		dyingCounter++;
		int i = 5;
		
		if (dyingCounter <=i) {changeAlpha(g2, 0f);} 
		else if (dyingCounter <=i*2) {changeAlpha(g2, 1f);} 
		else if (dyingCounter <=i*3) {changeAlpha(g2, 0f);} 
		else if (dyingCounter <=i*4) {changeAlpha(g2, 1f);} 
		else if (dyingCounter <=i*5) {changeAlpha(g2, 0f);} 
		else if (dyingCounter <=i*6) {changeAlpha(g2, 1f);} 
		else if (dyingCounter <=i*7) {changeAlpha(g2, 0f);} 
		else if (dyingCounter <=i*8) {changeAlpha(g2, 1f);} 
		else if (dyingCounter > i*8) {
			alive = false;
		}
	}

  /** Set action of the figure */
  public void setAction() {}

  /** Set the speak sequence of the figure */
  public void speak() {}

  /** Set the battle dialogue */
  public void setBattleDialogue() {}

  /** Set the battle action */
  public void battle() {}

  /** Start battle
   *  @param speaker the enemy who the player fights with  
   */
  public void startBattle(Entity speaker) {
  	gp.state = GameState.BATTLE;
  	gp.ui.speaker = speaker;
  	gp.ui.battleInfo.add("You start a fight with\n" + speaker.name + "!");
  }

  /** After battle dialogue (if any) */
  public void afterBattle() {}

  /** Set the entity's direction towards player */
  public void facePlayer() {
  	
  	switch(gp.player.direction) {
  	case "up":
  		direction = "down";
  		break;
  	case "down":
  		direction = "up";
  		break;
  	case "left":
  		direction = "right";
  		break;
  	case "right":
  		direction = "left";
  		break;
  	}
  }

  /** Check the collision of the figure */
  public void checkCollision() {
  	collisionOn = false;
  	gp.cCheck.checkTile(this);
  	gp.cCheck.checkObject(this, false);
  	gp.cCheck.checkEntity(this, gp.npc);
  	gp.cCheck.checkPlayer(this);
  }

  /** Update the condition of the figure */
  public void update() {
  	setAction();
  	checkCollision(); 
  	
  	
  	if (!collisionOn) {
    	switch(direction) {
    	case "up": worldY -= speed; break;
    	case "down": worldY += speed; break;
    	case "left": worldX -= speed; break;
    	case "right": worldX += speed; break;
    	}
    }
    
    spriteCounter++;
    if(spriteCounter > 12) { // Player image change every 12 frames -> 5 times/sec
    	if (spriteNum == 1) {
    		spriteNum = 2;
    	} else if (spriteNum == 2) {
    		spriteNum = 1;
    	}
    	spriteCounter = 0;
    }
  }
  
}
