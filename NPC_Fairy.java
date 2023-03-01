import java.awt.Rectangle;

/**
 *  Class for the fairy.
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class NPC_Fairy extends NPC {
  /** Constructor */
  public NPC_Fairy(GamePanel gp) {
    super(gp);
    name = "Fairy";
    direction = "down";
    speed = 0;
    collision = true;

    solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;

    getImage();
    setDialogue();
  }

  /** Get image of the fairy */
  public void getImage() {

    up1 = up2 = left1 = left2 = down1 = down2 = right1 = right2 = setupImage("NPCs/fairy", gp.tileSize, gp.tileSize);

  }

  /**  Dialogues between the player and the fairy */
  public void setDialogue() {
    dialogues[0][0] = "[Me] \nOh! A fairy!";
    dialogues[0][1] = "[Fairy] \nWhat do you mean by \n“oH A fAirY”? ";
    dialogues[0][2] = "[Me] \nYou are here to help people… \nright?";
    dialogues[0][3] = "[Fairy] \nWhy should I do that?";
    dialogues[0][4] = "[Fairy] \n???";
    dialogues[0][5] = "[Me] \nBecause… because you are a fairy?";
    dialogues[0][6] = "[Fairy] \nThat’s an UNFAIR-y stereotype, \nand I hate that people always \nwant something from me!";
    dialogues[0][7] = "[Fairy] \nI do have some cards. \nFound them around the forest. ";
    dialogues[0][8] = "[Fairy] \nAre they what you’re looking for? \nBut no no, not yet. \nWhat do you have in exchange? ";
    dialogues[0][9] = "[Me] \nSome shiny, golden, round \nlittle things? ";
    dialogues[0][10] = "[Fairy] \n3 for 1 card.";
    dialogues[0][11] = "OPTION";

    dialogues[1][0] = "";
    dialogues[1][1] = "[Fairy] \n3 for 1 card.";
    dialogues[1][2] = "OPTION";
  }

  /**  Talk to the player */
  public void speak() {
    if (dialogueSet == -1) {
      dialogueSet = 0;
    } else if (dialogueSet == 0) {
      gp.ui.dialSubwindow = "";
      dialogueSet = 1;
      startDialogue(this, 0, "Okay, I'll buy one.", "No, thanks.");
    } else if (dialogueSet == 1) {
      gp.ui.dialSubwindow = "";
      startDialogue(this, 1, "Okay, I'll buy one.", "No, thanks.");
    }
  }

  /**  Give the player a random card if the player choose option 1 to buy a card and have enough coins.
   *  @return boolean if the dialogue continues
   */
  public boolean dialogueOption1() {
    if (gp.player.coin >= 3) {
      int rank = (int) (Math.random() * 32 + 1); // 1-32
      gp.player.cardPile.add(new OBJ_Card(gp, rank));
      gp.player.coin -= 3;
      gp.player.inventory.set(gp.player.getItemIndex("Card"), gp.player.cardPile.peekLast());
    } else {
      gp.ui.addMessage("You don't have enough coin!");
    }
    return false;
  }
}
