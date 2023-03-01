import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;
/**
 *  Class for the player
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class Player extends Figure {
  
  /** Key Handler that controls the movement of player */
  KeyHandler keyH;

  /** X value of the position of the player in the screen (camera) */
  public final int screenX;
  
  /** Y value of the position of the player in the screen (camera) */
  public final int screenY;

  /** The coin that the player possesses */
  public int coin;

  /** inventory list */
  public ArrayList<OBJ> inventory = new ArrayList<>();

  /** card pile that player holds */
  public CardPile cardPile = new CardPile(gp);

  /** Maximum number of inventory items */
  public final int maxInventorySize = 20;

  /** number of times defeated by the fog */
  public int defeatedByFog = 0;

  /** number of times defeated by the general */
  public int defeatedByGen = 0;

  /** number of times the player used card(s) */
  public int usedCard = 0;

  /** number of times the player was drown */
  public int drown = 0;

  /** number of times the player used portal(s) */
  public int usedPortal = 0;

  /** number of times the player used blue potion(s) */
  public int usedBlue = 0;

  /** number of times the player used red potion(s) */
  public int usedRed = 0;

  /** number of times the player used key(s) */
  public int usedKey = 0;

  /** Constructor */
  public Player(GamePanel gp, KeyHandler keyH) {
  	super(gp);

    this.keyH = keyH;
    
    this.screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
    this.screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
    
    solidArea = new Rectangle(8, 16, 32, 30); // x, y, width, height - 32
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    
    setDefaultValue();
  }

  /** Set the original default position and speed of player */
  public void setDefaultValue() {
    worldX = gp.tileSize * 42;
    worldY = gp.tileSize * 19;
    speed = 4;
    direction = "down";
    
    // Player Status
    maxLife = 5;
    life = maxLife;
    maxHealth = 5;
    health = maxHealth;
    strength = 2;
    coin = 0;
    
    defeatedByFog = 0;
    defeatedByGen = 0;
    usedCard = 0;
    drown = 0;
    usedPortal = 0;
    usedBlue = 0;
    usedRed = 0;
    usedKey = 0;
    
    getImage();
    setDefaultItems();
    setBattleDialogue();
  }

  /** Set the original default status of player */
  public void setDefaultStatus() {
  	worldX = gp.tileSize * 42;
    worldY = gp.tileSize * 19;
    direction = "down";
    life = maxLife;
  }

  /** Set the original default items in inventory */
  public void setDefaultItems() {
  	inventory.clear();
  	cardPile.clear();
  	OBJ_Card c = new OBJ_Card(gp, 15);
  	inventory.add(c);
  	cardPile.add(c);
  	inventory.add(new OBJ_Key(gp));
    inventory.add(new OBJ_Potion(gp, 2));
  }

  /** Set the battle dialogue */
  public void setBattleDialogue() {
    batDials.add("You blasts fire!");
    batDials.add("You runs closer to them \nand tickles them!");
    batDials.add("You slaps their face!");
    batDials.add("You punches them in their \nchest!");
    batDials.add("You hits them with your \ntail!");
    batDials.add("You dances a cute little \njig and accidentally kicks \nthem!");
    batDials.add("You stomps!");
  }
  
  /** Get player image */
  public void getImage() {
    
    up1 = setupImage("player/up_1", gp.tileSize, gp.tileSize);
    up2 = setupImage("player/up_2", gp.tileSize, gp.tileSize);
    down1 = setupImage("player/down_1", gp.tileSize, gp.tileSize);
    down2 = setupImage("player/down_2", gp.tileSize, gp.tileSize);
    left1 = setupImage("player/left_1", gp.tileSize, gp.tileSize);
    left2 = setupImage("player/left_2", gp.tileSize, gp.tileSize);
    right1 = setupImage("player/right_1", gp.tileSize, gp.tileSize);
    right2 = setupImage("player/right_2", gp.tileSize, gp.tileSize);
    
  }

	/** Update the position and direction after each move */
  public void update() {
  	
  	if (keyH.upPressed || keyH.downPressed || 
  			keyH.leftPressed || keyH.rightPressed || 
  			keyH.interactPressed) {
  		if(keyH.upPressed) {
        direction = "up";
      }
  		if(keyH.downPressed) {
        direction = "down";
      }
  		if(keyH.leftPressed) {
        direction = "left";
      }
  		if(keyH.rightPressed) {
        direction = "right";
      }
      
      // Check collision
      collisionOn = false;
      gp.cCheck.checkTile(this);
      
      // Check object collision
      int objIndex = gp.cCheck.checkObject(this, true);
      pickUpObject(objIndex);
      
      // Check NPC collision
      int npcIndex = gp.cCheck.checkEntity(this, gp.npc);
      interactNPC(npcIndex);
      
      // Check Event
      gp.eventH.checkEvent();
      
      // Move if player doesn't collide with certain tiles
      if (!collisionOn && !keyH.interactPressed) {
      	switch(direction) {
      	case "up": worldY -= speed; break;
      	case "down": worldY += speed; break;
      	case "left": worldX -= speed; break;
      	case "right": worldX += speed; break;
      	}
      }
      
      gp.keyH.interactPressed = false;
      
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
  	
  	if(life <= 0 && !gp.ui.gameEnd) {
      gp.ui.ending = gp.achiPool.getAchievement(5);
      if(!gp.achiPool.getAchievement(5).unlocked) {
        gp.achiPool.getAchievement(5).unlocked = true;
        gp.achiPool = gp.achiPool.sort();
      }
  		gp.state = GameState.GAMEOVER;
  		gp.ui.commandNum = -1;
      gp.ui.gameEnd = false;
  	} else if (life <= 0 && gp.ui.gameEnd){
      gp.state = GameState.GAMEOVER;
  		gp.ui.commandNum = -1;
      gp.ui.gameEnd = false;
    }
  	
  	if (checkAchievement()) {
  		gp.ui.addMessage("Unlocked an achievement.");
  	}

    if (checkEnding()) {
      gp.state = GameState.GAMEOVER;
      gp.ui.commandNum = -1;
      gp.ui.gameEnd = false;
    }
  }
  
  /** Regulate the reaction of picking up objects
   *  @param i index of object in the object array
   */
  public void pickUpObject(int i) {
  	if (i != -1) {
  		
  		// Pick up object
  		if(gp.obj[i].type == EntityType.OBJ_COIN) {
  			gp.obj[i].use(this);
  			gp.obj[i] = null;
  		}
  		
  		// Inventory Object
  		else if(gp.obj[i].type == EntityType.OBJ_INVENTORY ||
  				gp.obj[i].type == EntityType.OBJ_POTION) {
  			String text;
  			if (canObtainItem(gp.obj[i])) {
  				text = "Got a " + gp.obj[i].name + "!";
          gp.obj[i] = null;
  			} else {
  				text = "You cannot carry more items!";
  			}
  			gp.ui.addMessage(text);
  		}
  		
  		// Door
  		else if (gp.obj[i].type == EntityType.OBJ_OBSTACLE) {
  			if(keyH.interactPressed) {
  				gp.obj[i].interact();
  			}
  		}
  	}
  }

  /** Interact with NPC
   *  @param i index of NPC in the NPC array
   */
  public void interactNPC(int i) {
  	if (i != -1) {
  		if(gp.keyH.interactPressed) {
    		gp.npc[i].speak();
  		}
  	}
  }

  /** Select an item in inventory and use it */
  public void selectItem() {
  	int itemIndex = gp.ui.getItemIndex();
  	if (itemIndex < inventory.size()) {
  		OBJ selected = inventory.get(itemIndex);
  		if(selected.type == EntityType.OBJ_POTION || selected.type == EntityType.OBJ_INVENTORY) {
  			if(selected.use(this)) {
  				if(selected.amount>1) {
  					selected.amount--;
  				} else {
  					inventory.remove(itemIndex);
  				}
  			}
  		}
  	}
  }

  /** Check if the player can obtain an item to their inventory
   *  @param item item to be checked
   *  @return boolean if the item can be obtained
   */
  public boolean canObtainItem(OBJ item) {
  	boolean canObtain = false;
  	
  	if(item.stackable) {
  		int index = getItemIndex(item.name);
  		if(index != -1) {
  			inventory.get(index).amount++;
  			canObtain = true;
  		} else {
  			if(inventory.size() != maxInventorySize) {
  				inventory.add(item);
  				canObtain = true;
  			}
  		}
  	} else {
  		if(inventory.size() != maxInventorySize) {
				inventory.add(item);
				canObtain = true;
			}
  	}
  	return canObtain;
  }

  /** Get the index of item in the inventory
   *  @param itemName The item to be searched
   *  @return int the index of the item
   */
  public int getItemIndex(String itemName) {
  	int index = -1;
  	for(int i = 0; i < inventory.size(); i++) {
  		if(inventory.get(i).name.contains(itemName)) {
  			index = i;
  			break;
  		}
  	}
  	return index;
  }

  /** Battle actions */
  public void battle() {
  	switch(gp.ui.commandNum) {
  	case 0: 
      batDialIndex = (int) (Math.random()*batDials.size());
  		gp.ui.battleInfo.add(batDials.get(batDialIndex) + "\nDealt " + strength + " damage.");
  		((NPC)gp.ui.speaker).health -= strength;
  		break;
  	case 1:
  		if(!cardPile.isEmpty()) {
  			StringBuilder sb = new StringBuilder();
	  		sb.append("You used a card.\n");
	  		OBJ_Card c = cardPile.pollLast();
        if (cardPile.size() == 0) {
          for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.contains("Card")){
              inventory.remove(i);
            }
          }
        }
	  		if(c.instDmg != 0) {
	  			sb.append("Dealt " + c.instDmg + " damage.\n"); 
	  			((NPC)gp.ui.speaker).health -= c.instDmg;
	  		}
	  		if(c.permDmg != 0) {
	  			sb.append("Strength increased by " + c.permDmg + ".\n"); 
	  			strength += c.permDmg;
	  		}
	  		if(c.health > 0) {
	  			sb.append("Health increased by " + c.health + ".\n"); 
	  			this.health += c.health; 
	  			this.maxHealth += c.health; 
	  		}
	  		if(c.health < 0) {
	  			sb.append("Health decreased by " + Math.abs(c.health) + ".\n");
	  			
	  		}
	  		gp.ui.battleInfo.add(sb.toString());
	  		usedCard++;
  		} else {
  			gp.ui.battleInfo.add("You don't have any \ncard!");
  		}
      if (gp.player.getItemIndex("Card") != -1) {
        gp.player.inventory.set(gp.player.getItemIndex("Card"), gp.player.cardPile.peekLast());
      } else {
        gp.player.inventory.add(gp.player.cardPile.peekLast());
      }
  		break;
  		
  	case 2:
  		cardPile = cardPile.sort();
  		gp.ui.battleInfo.add("You sorted your card pile.");
      if (gp.player.getItemIndex("Card") != -1) {
        gp.player.inventory.set(gp.player.getItemIndex("Card"), gp.player.cardPile.peekLast());
      } else {
        gp.player.inventory.add(gp.player.cardPile.peekLast());
      }
  		break;
  	}
  	gp.ui.commandNum = 0;
  }

  /** Check the ending
   *  @return boolean if an ending is reached
   */
  public boolean checkEnding() {
    boolean ifEnd = false;
    if (gp.player.defeatedByFog == 5) {
      gp.ui.ending = gp.achiPool.getAchievement(2);
      if(!gp.achiPool.getAchievement(2).unlocked) {
  		gp.achiPool.getAchievement(2).unlocked = true;
  		ifEnd = true;
      }
  	}
    if (gp.player.defeatedByGen == 3) {
      gp.ui.ending = gp.achiPool.getAchievement(14);
      if(!gp.achiPool.getAchievement(14).unlocked) {
  		gp.achiPool.getAchievement(14).unlocked = true;
  		ifEnd = true;
      }
    }
    if (ifEnd) {
      gp.achiPool = gp.achiPool.sort();
    }
    return ifEnd;
  }

  /** Check the achievement
   *  @return boolean if an achievement is reached
   */
  public boolean checkAchievement() {
  	boolean ifAchi = false;
  	
  	if (gp.player.cardPile.size() >= 10 && !gp.achiPool.getAchievement(1).unlocked) {
  		gp.achiPool.getAchievement(1).unlocked = true;
  		ifAchi = true;
  	} 
  	// SKIP : Dictator
  	if (gp.player.usedCard >= 10 && !gp.achiPool.getAchievement(3).unlocked) {
  		gp.achiPool.getAchievement(3).unlocked = true;
  		ifAchi = true;
  	}
  	// SKIP : FALL INTO THE RIVER ONCE
  	// SKIP : WASTED
  	if (gp.player.usedPortal == 1 && !gp.achiPool.getAchievement(6).unlocked) {
  		gp.achiPool.getAchievement(6).unlocked = true;
  		ifAchi = true;
  	}
  	if (gp.player.usedRed == 10 && !gp.achiPool.getAchievement(7).unlocked) {
  		gp.achiPool.getAchievement(7).unlocked = true;
  		ifAchi = true;
  	}
  	if (gp.player.usedBlue == 10 && !gp.achiPool.getAchievement(8).unlocked) {
  		gp.achiPool.getAchievement(8).unlocked = true;
  		ifAchi = true;
  	}
  	if (gp.player.cardPile.size() >= 20 && !gp.achiPool.getAchievement(9).unlocked) {
  		gp.achiPool.getAchievement(9).unlocked = true;
  		ifAchi = true;
  	}
  	// SKIP : MARTYR
  	if (gp.player.usedKey == 5 && !gp.achiPool.getAchievement(11).unlocked) {
  		gp.achiPool.getAchievement(11).unlocked = true;
  		ifAchi = true;
  	}
  	if (gp.player.usedKey == 10 && !gp.achiPool.getAchievement(12).unlocked) {
  		gp.achiPool.getAchievement(12).unlocked = true;
  		ifAchi = true;
  	}
  	// SKIP : PROTECTOR
  	// SKIP : SHAME
  	if (gp.player.usedBlue == 5 && !gp.achiPool.getAchievement(15).unlocked) {
  		gp.achiPool.getAchievement(15).unlocked = true;
  		ifAchi = true;
  	}
  	if (gp.player.usedRed == 5 && !gp.achiPool.getAchievement(16).unlocked) {
  		gp.achiPool.getAchievement(16).unlocked = true;
  		ifAchi = true;
  	}
  	if (gp.player.usedPortal == 3 && !gp.achiPool.getAchievement(17).unlocked) {
  		gp.achiPool.getAchievement(17).unlocked = true;
  		ifAchi = true;
  	}
  	// SKIP : UNDERCURRENT
  	if (gp.player.drown == 15 && !gp.achiPool.getAchievement(19).unlocked) {
  		gp.achiPool.getAchievement(19).unlocked = true;
  		ifAchi = true;
  	}
  	if (gp.player.drown == 5 && !gp.achiPool.getAchievement(20).unlocked) {
  		gp.achiPool.getAchievement(20).unlocked = true;
  		ifAchi = true;
  	}
  	if (gp.player.usedCard == 20 && !gp.achiPool.getAchievement(21).unlocked) {
  		gp.achiPool.getAchievement(21).unlocked = true;
  		ifAchi = true;
  	}
  	if (ifAchi) {
  		gp.achiPool = gp.achiPool.sort();
  	}
  	return ifAchi;
  }

  /** Draw the image of player
   *  @param g2 Graphics2D where player is drawn
   */
  public void draw(Graphics2D g2) {
    BufferedImage image = null;

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