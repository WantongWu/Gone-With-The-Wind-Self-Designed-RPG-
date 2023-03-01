/**
 *  Class for goblins
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class NPC_Goblin extends NPC {
  /** Constructor */
  public NPC_Goblin(GamePanel gp, int figNum) {
    super(gp);
    name = "Goblin";
    direction = "down";
    speed = 0;
    canFight = true;
    collision = true;

    maxHealth = 5;
    health = maxHealth;
    strength = 15;
    strengthVariation = 6;
    this.figNum = figNum; // 1/2
    
    solidArea.setBounds(3, 6, gp.tileSize*2-6, gp.tileSize*2-6);
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;

    getImage();
    setDialogue();
    setBattleDialogue();
  }

  /** Get image of goblins */
  public void getImage() {
    up1 = up2 = left1 = left2 = down1 = down2 = right1 = right2 = setupImage("NPCs/goblin", gp.tileSize*2, gp.tileSize*2);

  }

  /**  Dialogues between the player and goblins */
  public void setDialogue() {
    if (figNum == 1 || figNum == 2) {
      dialogues[0][0] = "[Goblin] \nrvZfypAVxUff";
      dialogues[0][1] = "[Me] \n......?";
      dialogues[0][2] = "[Goblin] \nvOhXLZymFLVS,vrhMMlZzYmHG!";
      dialogues[0][3] = "FIGHT";
    } 
    else if (figNum == 3 || figNum == 4) {
      dialogues[0][0] = "[Goblin] \nNuhiekngNjGu";
      dialogues[0][1] = "[Me] \n......?";
      dialogues[0][2] = "[Goblin] \nOIrytccYhIOx, LtyhfnNxgjsuSze!";
      dialogues[0][3] = "FIGHT";
    }
    
    dialogues[1][0] = "[Goblin] \nCYsBPIHnvxLd… mifthoeH!";
    dialogues[1][1] = "[Goblin] \n[Runs away]";
    dialogues[1][2] = "LEAVE";

    dialogues[2][0] = "[Goblin] \nJVnOMEnnbmEOKPtTK, GhztuPUKbqu\nRTpWmdMqLRNTYGw!!";
  }

  /**  Dialogue of goblins in battles*/
  public void setBattleDialogue() {
    batDials.add("Goblin scratches you!");
    batDials.add("Goblin jumps on your head!");
    batDials.add("Goblin sings a horrible \nsong!");
    batDials.add("Goblin makes really loud \nnoises!");
  }

  /**  Talk to the player */
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