/**
 *  Class for the General
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class NPC_General extends NPC {
  /** Constructor */
  public NPC_General(GamePanel gp) {
    super(gp);
    name = "General";
    direction = "down";
    speed = 0;
    canFight = true;
    collision = true;

    maxHealth = 60;
    health = maxHealth;
    strength = 15;
    strengthVariation = 9;

    getImage();
    setDialogue();
    setBattleDialogue();
  }

  /** Get image of the General */
  public void getImage() {
      up1 = up2 = left1 = left2 = down1 = down2 = right1 = right2 = setupImage("NPCs/General", gp.tileSize,
          gp.tileSize);
  }

  /**  Dialogues between the General and the player */
  public void setDialogue() {
    dialogues[0][0] = "[Me] \nYou must be General Aaron. \nI need to talk to you.";
    dialogues[0][1] = "[General] \nYes.";
    dialogues[0][2] = "[Me] \nYou and your troop are stepping \non my forest.";
    dialogues[0][3] = "[Me] \nYour territory should be enough \nfor your people. Why do you \nhave to be so greedy?";
    dialogues[0][4] = "[Me] \nCan’t you see… can’t you see \nthe degradation of the forest? \nDon’t you care? At least \na little bit?";
    dialogues[0][5] = "[General] \nYou are the guardian of the \nforest? Take it easy. Let’s talk.";
    dialogues[0][6] = "[Me] \nI have protected this forest for \nthousands of years. And I can’t \njust watch it being taken \naway by others.";
    dialogues[0][7] = "[Me] \nI request you to leave this \nforest.";
    dialogues[0][8] = "[General] \nPlease don’t be so impulsive. \nWe are just here to help this \npoorly abandoned forest. ";
    dialogues[0][9] = "[General] \nWe've brought manpower, \ntechnology, and an efficient \nsystem to help develop the \nforest. ";
    dialogues[0][10] = "[General] \nSoon, no land will be wasted, \nno trees will be useless, and \nno animals will be bothered by \npoverty or famine.";
    dialogues[0][11] = "[General] \nThe process won't take too long, \nand we will live in harmony \nwith the citizens of the forest.";
    dialogues[0][12] = "[Me] \nThat is NOT an excuse for \nplundering the resources of the \nforest and encroaching on \nother residents’ habitats.";
    dialogues[0][13] = "[General] \nIt’s just a process. Development \nneeds sacrifice. ";
    dialogues[0][14] = "[Me] \nAnd do you know about the \ncasualty that your soldiers \nmentioned?";
    dialogues[0][15] = "[General] \nLord will thank them for \ntheir sacrifice.";
    dialogues[0][16] = "FIGHT";

    dialogues[1][0] = "[General] \nDefeating me is of no use. \nYour monster has killed many men, \nand that monster has become \nthe true problem. ";
    dialogues[1][1] = "[General] \nHostility has spread between races, \nyou need to deal with your \nmonster first.";
    dialogues[1][2] = "OPTION";

    dialogues[2][0] = "";
    dialogues[2][1] = "[Me] \nI don’t trust you, you liar. \nTell your soldiers to leave \nright now.";
    dialogues[2][2] = "[General] \nAlright, alright. We will leave \nyour forest. ";
    dialogues[2][3] = "[General] \nBut I have warned you, there is \na killer in the forest, and \nthings will never be the same.";
    dialogues[2][4] = "END";

    dialogues[3][0] = "";
    dialogues[3][1] = "[Me] \nWhat monster?";
    dialogues[3][2] = "[General] \nYou don’t know it? Something \nis killing people right now. \nThe deeper we went into the \nforest, more people were killed. ";
    dialogues[3][3] = "[General] \nIf you are the guardian of \nthe forest, deal with it first.";
    dialogues[3][4] = "[Hint] \nFind \"the monster\".";

    dialogues[4][0] = "[General] \nDear guardian, it seems that \nyou are not as strong as our \nancestors recorded. ";
    dialogues[4][1] = "[General] \nThe forest has to accept its \ninevitable fate – join this \nglorious evolution.";

    dialogues[5][0] = "[Me] \nThe fog has been defeated. \nNow leave our land.";
    dialogues[5][1] = "[General] \nDid you kill the monster… the \nfog? It was the fog! That’s why \nwe couldn’t find it.";
    dialogues[5][2] = "[Me] \nNow leave.";
    dialogues[5][3] = "[General] \nLeave? No, we are not leaving. \nWe need the resources for our \ndevelopment. ";
    dialogues[5][4] = "[Me] \n?! ";
    dialogues[5][5] = "[Me] \nHands off my forest, or there \nwill be another fight right now.";
    dialogues[5][6] = "[General] \nFine. Fine. Well, we can reduce \nthe frequency of harvesting. \nGesus, take it easy.";
    dialogues[5][7] = "[Me] \nYou must meet with me every year.";
    dialogues[5][8] = "[General] \nOkay, okay, I promise. \nA general always keeps their \npromises.";
    dialogues[5][9] = "[General] \nDear guardian, you won’t be \na new serial killer, will you?";
    dialogues[5][10] = "END";

    dialogues[6][0] = "[Me] \nI am back. ";
    dialogues[6][1] = "[General] \nDid you find the monster?";
    dialogues[6][2] = "[Me] \nI did. And I talked to it.";
    dialogues[6][3] = "[General] \nDid you kill it? You know, we \ndon’t want a serial killer in \nour forest.";
    dialogues[6][4] = "[Me] \nIt’s not YOUR forest.";
    dialogues[6][5] = "[General] \nWhat is the monster? \nDid you kill it?";
    dialogues[6][6] = "[Me] \nWe need you to come up with a \nformal regulation. And we will \nboth agree on it.";
    dialogues[6][7] = "[Me] \nIf you cross the line again, \nthere will be a bigger cost \nwaiting for you.";
    dialogues[6][8] = "[Me] \n“You know”, the monster is still \nwatching you.";
    dialogues[6][9] = "[General] \n……Alright.";
    dialogues[6][10] = "END";

    dialogues[7][0] = "[General] \nHave you talked to the \nmonster yet?";

  }

  /**  Dialogue of the General in battles*/
  public void setBattleDialogue() {
    batDials.add("General stabbed you with \ntheir golden sword!");
    batDials.add("General hits you with \ntheir shield!");
    batDials.add("General sprays you, a \nfire dragon, with water!");
    batDials.add("General punches you in \nyour face!");
  }

  /**  Chose the dialogue sets based on the result of the battle. */
  public void afterBattle() {
    if (!this.ifWin) {
      startDialogue(this, 1, "I don't trust you.", "What monster?");
    } else {
      gp.player.defeatedByGen++;
      startDialogue(this, 4);
    }
  }

  /**  Talk to the player */
  public void speak() {
    if (gp.gamePhase == 0) {
      startDialogue(this, 0);
    } else if (gp.gamePhase == 1){
      startDialogue(this, 7);
    } else if (gp.gamePhase == 2) {
      gp.ui.ending = gp.achiPool.getAchievement(10);
      if(!gp.achiPool.getAchievement(10).unlocked) {
        gp.achiPool.getAchievement(10).unlocked = true;
        gp.achiPool = gp.achiPool.sort();
      }
      startDialogue(this, 5);
    } else if (gp.gamePhase == 3) {
      gp.ui.ending = gp.achiPool.getAchievement(13);
      if(!gp.achiPool.getAchievement(13).unlocked) {
        gp.achiPool.getAchievement(13).unlocked = true;
        gp.achiPool = gp.achiPool.sort();
      }
      startDialogue(this, 6);
    }  
  }

  /** Unlock the achievement and lead to the ending after choosing option 1 
   *  @return boolean if the dialogue continues
   */
  public boolean dialogueOption1() {
    afterOpDialSet = 2;
    gp.ui.ending = gp.achiPool.getAchievement(18);
    if(!gp.achiPool.getAchievement(18).unlocked) {
      gp.achiPool.getAchievement(18).unlocked = true;
      gp.achiPool = gp.achiPool.sort();
    }
    gp.ui.gameEnd = true;
    return true;
  }

  /**  Unlock new conversation between the fog and the player after choosing option 2
   *  @return boolean if the dialogue continues
   */
  public boolean dialogueOption2() {
    afterOpDialSet = 3;
    gp.gamePhase = 1;
    return true;
  }

}