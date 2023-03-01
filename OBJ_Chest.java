import java.awt.image.BufferedImage;

/**
 *  Class for chests
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class OBJ_Chest extends OBJ {

  /** if the chest is open */
  public boolean opened = false;

  /** store the loot cards */
  OBJ_Card[] loot = new OBJ_Card[3];

  /** store the image for open chest */
  public BufferedImage imageOp;

  /** Constructor */
  public OBJ_Chest(GamePanel gp) {
    super(gp);

    this.gp = gp;

    type = EntityType.OBJ_OBSTACLE;
    name = "Wooden Chest";

    this.value = 1; // 1 - wooden; 2 - metal; 3 - golden
    setLoot(1);

    image = setupImage("objects/chests/wooden_chest", gp.tileSize, gp.tileSize);
    imageOp = setupImage("objects/chests/wooden_chest_open", gp.tileSize, gp.tileSize);
    collision = true;

    // Set Solid Area
    solidArea.setBounds(0, 24, 48, 24);
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
  }

  /** Constructor */
  public OBJ_Chest(GamePanel gp, int typeNum) {
    super(gp);

    this.gp = gp;

    type = EntityType.OBJ_OBSTACLE;

    this.value = typeNum;
    setLoot(typeNum);

    switch (typeNum) {
      case 1:
        name = "Wooden Chest";
        image = setupImage("objects/chests/wooden_chest", gp.tileSize, gp.tileSize);
        imageOp = setupImage("objects/chests/wooden_chest_open", gp.tileSize, gp.tileSize);
        break;
      case 2:
        name = "Metal Chest";
        image = setupImage("objects/chests/metal_chest", gp.tileSize, gp.tileSize);
        imageOp = setupImage("objects/chests/metal_chest_open", gp.tileSize, gp.tileSize);
        break;
      case 3:
        name = "Golden Chest";
        image = setupImage("objects/chests/golden_chest", gp.tileSize, gp.tileSize);
        imageOp = setupImage("objects/chests/golden_chest_open", gp.tileSize, gp.tileSize);
        break;
    }

    collision = true;

    // Set Solid Area
    solidArea.setBounds(0, 24, 48, 24);
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
  }

  /** Give the player random card(s) */
  public void setLoot(int lootType) {
    for (int i = 0; i < lootType; i++) {
      int rank = (int) (Math.random() * 32 + 1); // 1-32
      this.loot[i] = new OBJ_Card(gp, rank);
    }
    setDialogue();
  }

  /** Prompt after the player open the chest */
  public void setDialogue() {
    dialogues[0][0] = "Opened a chest and find a \ncard!\n...But you cannot carry more \nitems!";
    dialogues[1][0] = "Opened a chest and find \n" + value + " cards!\n...But you cannot carry more \nitems!";
    dialogues[2][0] = "Opened a chest and find a \ncard!\nYou obtain a card!";
    dialogues[3][0] = "Opened a chest and find \n" + value + " cards!\nYou obtain " + value + " cards!";
    dialogues[4][0] = "It is empty.";
  }

  /** interact with chest */
  public void interact() {
    gp.state = GameState.DIALOGUE;

    if (!opened) {

      if (gp.player.inventory.size() == gp.player.maxInventorySize && gp.player.getItemIndex("Card") != -1) {
        if (value == 1) {
          startDialogue(this, 0);
        } else {
          startDialogue(this, 1);
        }
      } else {
        if (value == 1) {
          startDialogue(this, 2);
        } else {
          startDialogue(this, 3);
        }
        for (OBJ_Card c : loot) {
          if (c != null) {
            gp.player.cardPile.add(c);
          }
        }
        if (gp.player.getItemIndex("Card") != -1) {
          gp.player.inventory.set(gp.player.getItemIndex("Card"), gp.player.cardPile.peekLast());
        } else {
          gp.player.inventory.add(gp.player.cardPile.peekLast());
        }        
        image = imageOp;
        opened = true;
      }
    } else {
      startDialogue(this, 4);
    }
  }
}
