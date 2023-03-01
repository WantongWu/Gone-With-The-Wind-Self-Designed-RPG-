/**
 *  Class for soldiers
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class NPC_Soldier extends NPC {
  /** Constructor */
  public NPC_Soldier(GamePanel gp, int figNum) {
    super(gp);
    name = "Soldier";
    direction = "down";
    speed = 0;
    canFight = true;
    collision = true;

    maxHealth = 30;
    health = maxHealth;
    strength = 10;
    strengthVariation = 6;
    this.figNum = figNum; // 1/2/3

    getImage();
    setDialogue();
    setBattleDialogue();
  }

  /** Get image of soldiers */
  public void getImage() {

    if (figNum == 1 || figNum == 2) {
      up1 = up2 = left1 = left2 = down1 = down2 = right1 = right2 = setupImage("NPCs/soldier", gp.tileSize,
          gp.tileSize);
    } else {
      up1 = up2 = left1 = left2 = down1 = down2 = right1 = right2 = setupImage("NPCs/soldier2", gp.tileSize,
          gp.tileSize);
    }

  }

  /** Dialogues between the player and soldiers */
  public void setDialogue() {
    if (this.figNum == 1) {
      dialogues[0][0] = "[Soldier] /n......";

      dialogues[1][0] = "[Me] \nExcuse me, I have a quick \nquestion. Do you know who’s in \ncharge of the harvest of the \nforest?";
      dialogues[1][1] = "[Soldier] \nI’m busy now. ";
      dialogues[1][2] = "[Soldier] \nYou don’t seem like a human. \nYou’d better not stay here too \nlong. For your own safety.";
      dialogues[1][3] = "[Me] \nBut I must get the answer first.";
      dialogues[1][4] = "FIGHT";

      dialogues[2][0] = "[Me] \nCan you answer my question now?";
      dialogues[2][1] = "[Soldier] \n.......";
      dialogues[2][2] = "[Me] \nWhat’s going on in this forest? ";
      dialogues[2][3] = "[Soldier] \nWe are collecting lumber for \nChristmas. You know, we need to \ncook, fuel the fireplace, \ndecorating our castle…";
      dialogues[2][4] = "[Me] \n? Decorating your castle? ";
      dialogues[2][5] = "[Soldier] \nYeah. We need a lot of lumber. \nThey are definitely necessary.";
      dialogues[2][6] = "[Me] \nAnd it’s only July!";
      dialogues[2][7] = "[Soldier] \nThat is the General’s command. \nWe all follow him.";

      dialogues[3][0] = "[Soldier] \nYou’d better be careful. Sir.";
    }

    if (this.figNum == 2) {
      dialogues[0][0] = "[Soldier] \n......";

      dialogues[1][0] = "[Me] \nWhy are you here? ";
      dialogues[1][1] = "[Soldier] \nWhy are YOU here?";
      dialogues[1][2] = "[Me] \nThis is the forest. \nAren’t you supposed to stay in \nyour own territory?";
      dialogues[1][3] = "[Soldier] \nDon’t be so silly. The forest \nbelongs to no one. Look around, \nGeneral Aaron owns all this now.";
      dialogues[1][4] = "FIGHT";

      dialogues[2][0] = "[Me] \nDon’t you feel guilty for taking \naway the homeland of animals? \nWhat if you were the one who \nlost their home?";
      dialogues[2][1] = "[Soldier] \nBut we were stronger. \nSurvival of the Fittest, you know.";
      dialogues[2][2] = "[Me] \nThink about it again after \nyour failure.";

      dialogues[3][0] = "[Soldier] \nYou can’t stop us.";
    }

    if (this.figNum == 3) {
      dialogues[0][0] = "[Soldier] \n......";

      dialogues[1][0] = "[Me] \nAre you also a soldier in General \nAaron’s army? I need to s…";
      dialogues[1][1] = "[Soldier] \nI’m a knight, sir.";
      dialogues[1][2] = "[Me] \n?";
      dialogues[1][3] = "[Soldier] \nI’m a knight. I follow the Code \nof Chivalry. I love the country \nin which I was born. I never lie. ";
      dialogues[1][4] = "[Soldier] \nAnd I don’t speak to, evil. \nI am not talking to you.";
      dialogues[1][5] = "[Me] \nI have to see General Aaron.";
      dialogues[1][6] = "[Soldier] \n“Thou shalt not recoil in fear \nbefore thine enemy.";
      dialogues[1][7] = "FIGHT";

      dialogues[2][0] = "[Soldier] \nYou won. But I am not lost. \nI will fight for the General \nuntil the last minute of my life.";
      dialogues[2][1] = "[Soldier] \nBut there is something you \nshould know. Someone, or, \nsomething, is killing people \nin the forest. ";
      dialogues[2][2] = "[Soldier] \nIf you really care about your \nhomeland, you shall notice that.";

      dialogues[3][0] = "[Soldier] \nSorry sir, no further words.";
    }
  }

  /** Dialogue of soldiers in battles*/
  public void setBattleDialogue() {
    batDials.add("Soldier swings their \nsword!");
    batDials.add("Soldier stabbed you with \ntheir sword!");
    batDials.add("Soldier gives you a punch!");
    batDials.add("Soldier rolls around, and \nscared you from the back!");
  }

  /** Chose the dialogue sets based on the result of the battle. */
  public void afterBattle() {
    if (!this.ifWin) {
      startDialogue(this, 2);
    } else {
      startDialogue(this, 3);
    }
  }

  /** Talk to the player */
  public void speak() {
    if (!this.ifWin) {
      startDialogue(this, 0);
    } else {
      if (dialogueSet == -1) {
        dialogueSet = 1;
      }
      if (dialogueSet == 1) {
        startDialogue(this, 1);
      }
    }
  }
}