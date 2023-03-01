/**
 *  Class for lumbermen
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class NPC_Lumberman extends NPC {
  /** Constructor */
  public NPC_Lumberman(GamePanel gp, int num) {
    super(gp);
    name = "Lumberman";
    direction = "down";
    speed = 0;
    figNum = num; // 1/2
    canFight = true;
    collision = true;

    maxHealth = 10;
    health = maxHealth;
    strength = 5;
    strengthVariation = 4;

    getImage();
    setDialogue();
    setBattleDialogue();
  }

  /** Get image of lumbermen */
  public void getImage() {

    up1 = up2 = left1 = left2 = down1 = down2 = right1 = right2 = setupImage("NPCs/dwarf", gp.tileSize, gp.tileSize);

  }

  /** Dialogues between lumbermen and the player */
  public void setDialogue() {
    if (figNum == 1) {
      dialogues[0][0] = "[Lumberman] \n[Cutting Trees]";
      dialogues[0][1] = "[Me] \nWhat are you doing here?";
      dialogues[0][2] = "[Lumberman] \nI'm cutting trees, following \nmy boss's order. Who are you?";
      dialogues[0][3] = "[Me] \nI'm the guardian of the forest. \nDo you know you are killing \nthe forest?";
      dialogues[0][4] = "[Lumberman] \nI don't care. All I know is that \nI need the money. This is none \nof your business, Fxxk off.";
      dialogues[0][5] = "FIGHT";

      dialogues[1][0] = "[Me] \nTake your ax and leave the \nforest!";
      dialogues[1][1] = "[Lumberman] \n[Runs away]";
      dialogues[1][2] = "LEAVE";

      dialogues[2][0] = "[Lumberman] \nThat's all you got? Go back \nto where you come from.";

    } else if (figNum == 2) {
      dialogues[0][0] = "[Lumberman] \n[Cutting Trees]";
      dialogues[0][1] = "[Me] \nDo you know you are destroying \nthe forest? Look around, the \nforest is dying.";
      dialogues[0][2] = "[Lumberman] \nI have to do this. This is \nmy job.";
      dialogues[0][3] = "FIGHT";

      dialogues[1][0] = "[Lumberman] \nYou must be that monster. \nPlease don't kill me, sir, \nI love the forest.";
      dialogues[1][1] = "[Lumberman] \nI still remember the days I \nspent with my family near \nthe river- the feeling of water \nflowing between my fingers,";
      dialogues[1][2] = "[Lumberman] \nthe little flower bouquet my \ndaughter gave me, and my wife, \nher hair shining under the sun...";
      dialogues[1][3] = "[Lumberman] \nBut I have to do all this... \nmy wife and kids are waiting \nfor me to put bread on the table. \nPlease don't kill me...";
      dialogues[1][4] = "[Me] \n...wait, what monster?";
      dialogues[1][5] = "[Lumberman] \nJust a saying among us. A \nmonster in the forest. Whoever \nis caught would have no bones \nleft.";
      dialogues[1][6] = "[Lumberman] \nPlease...";
      dialogues[1][7] = "[Me] \nTake your ax and never come \nback again.";
      dialogues[1][8] = "[Lumberman] \n[Runs away]";
      dialogues[1][9] = "LEAVE";

      dialogues[2][0] = "[Lumberman] \nMind your own business, sir.";

    }
  }

  /** Dialogue of lumbermen in battles */
  public void setBattleDialogue() {
    batDials.add("Lumberman runs toward \nyou!");
    batDials.add("Lumberman yells at you, \nyou feel so hurt!");
    batDials.add("Lumberman swings his ax!");
    batDials.add("Lumberman cuts down a \ntree, the tree falls on \nyou!");
  }

  /** Talk to the player */
  public void speak() {
    if (dialogueSet == -1) {
      dialogueSet = 0;
    }
    if (dialogueSet == 0) {
      startDialogue(this, 0);
    }
  }

  /**  Chose the dialogue sets based on the result of the battle. */
  public void afterBattle() {
    if (!this.ifWin) {
      startDialogue(this, 1);
    } else {
      startDialogue(this, 2);
    }
  }
}