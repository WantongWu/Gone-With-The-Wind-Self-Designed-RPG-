import java.awt.Rectangle;
import java.util.Random;

/**
 *  Class for the fog
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class NPC_Fog extends NPC {
  /** Constructor */
  public NPC_Fog(GamePanel gp) {
    super(gp);
    name = "Fog";
    direction = "down";
    speed = 1;
    canFight = true;
    solidArea = new Rectangle(3, 18, gp.tileSize * 3 - 6, gp.tileSize * 3 - 18);
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    collision = true;

    maxHealth = 100;
    health = maxHealth;
    strength = 20;
    strengthVariation = 15;

    getImage();
    setDialogue();
    setBattleDialogue();
  }

  /** Get image of the fog */
  public void getImage() {

    up1 = up2 = left1 = left2 = setupImage("NPCs/fog_dark_left", gp.tileSize * 3, gp.tileSize * 3);
    down1 = down2 = right1 = right2 = setupImage("NPCs/fog_dark_right", gp.tileSize * 3, gp.tileSize * 3);

  }

  /**  Dialogues between the player and the fog */
  public void setDialogue() {
    dialogues[0][0] = "[Fog] \n……";

    dialogues[1][0] = "[Me] \nYou are the one who was killing \npeople who invaded the forest, \nright?";
    dialogues[1][1] = "[Fog] \n……";
    dialogues[1][2] = "[Me] \nI know it’s you. Don’t play dumb.";
    dialogues[1][3] = "[Fog] \nI am not here. I am everywhere.";
    dialogues[1][4] = "[Me] \nI know you are doing all \nthose things for the forest.";
    dialogues[1][5] = "[Fog] \n……";
    dialogues[1][6] = "[Me] \nViolence is not the only \nsolution. It’s not even a \nsolution.";
    dialogues[1][7] = "[Fog] \nI know what you are trying to \nplay here. Hero, protector, \nguardian of everyone, call it \n“GOOD MAN”.";
    dialogues[1][8] = "[Fog] \nCentrism is the funniest. \nIt always is. ";
    dialogues[1][9] = "[Fog] \nYou have been asleep for \nhundreds of years, “guardian”. ";
    dialogues[1][10] = "[Fog] \nIt’s ME. I AM protecting animals \nfrom being slaughtered. I AM \nprotecting trees and rivers.";
    dialogues[1][11] = "[Me] \nDid you stop them then?";
    dialogues[1][12] = "[Fog] \n……";
    dialogues[1][13] = "[Fog] \nI do things the way I want. \nWhy do you have to mess up with \nme? You do whatever you want, \nand I do what I want.";
    dialogues[1][14] = "[Me] \nSome of them were innocent. \nWhat about their family? Their \nchildren?...";
    dialogues[1][15] = "[Fog] \nShut up!";
    dialogues[1][16] = "FIGHT";

    dialogues[2][0] = "[Fog] \nYou are still very naive, young \nguardian. They are not going to \nlisten to you…";
    dialogues[2][1] = "[Fog] \n[Disappeared in the air]";
    dialogues[2][2] = "[Hint] \nTalk to the General again.";
    dialogues[2][3] = "LEAVE";

    dialogues[3][0] = "[Fog] \nYou are more powerful than \nI thought. What do you want? ";
    dialogues[3][1] = "[Me] \nI need you to go with me. \nWe can talk to the General \ntogether.";
    dialogues[3][2] = "[Fog] \nI don’t wanna go. \nI belong to the forest.";
    dialogues[3][3] = "[Fog] \nGo, do whatever you want. \nYou are already strong enough \nto protect the forest.";
    dialogues[3][4] = "[Fog] \nTell them I am still here, in \nthe forest. I will always keep \nan eye on them. ";
    dialogues[3][5] = "[Me] \n…Thank you for guarding the \nforest while I was not here.";
    dialogues[3][6] = "[Fog] \nIt is not for you. \nThe forest is my home.";
    dialogues[3][7] = "LEAVE";

    dialogues[4][0] = "[Fog] \nMind your own business.";
  }

  /**  Dialogue of the fog in battles*/
  public void setBattleDialogue() {
    batDials.add("Fog blows wind toward you!");
    batDials.add("Fog goes around you and \nmakes you lost!");
    batDials.add("Fog poisons you!");
    batDials.add("Fog hurts you by telling \ntheir trauma in the past \nthree years!");
  }

  /**  Chose the dialogue sets after battle based on the player's strength. */
  public void afterBattle() {
    if (!this.ifWin && gp.player.strength >= 30) {
      gp.gamePhase = 2;
      startDialogue(this, 2);
    } else if (!this.ifWin && gp.player.strength < 30) {
      gp.gamePhase = 3;
      startDialogue(this, 3);
    } else {
      gp.player.defeatedByFog++;
      startDialogue(this, 4);
    }
  }

  /**  Talk to the player */
  public void speak() {
    if (dialogueSet == -1) {
      dialogueSet = 0;
    }

    if (gp.gamePhase == 0) {
      startDialogue(this, 0);
    } else {
      startDialogue(this, 1);
    }
  }

  /** Set the action of the fog on map */
  public void setAction() {
    actionCounter++;

    if (actionCounter == 120) {
      Random random = new Random();
      int i = random.nextInt(100) + 1; // random number - [1, 100]

      if (i <= 25) {
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
