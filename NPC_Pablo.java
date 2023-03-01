/**
 *  Class for Pablo
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class NPC_Pablo extends NPC {
  /** Constructor */
  public NPC_Pablo(GamePanel gp) {
    super(gp);
    name = "Pablo";
    direction = "down";
    speed = 0;
    collision = true;

    getImage();
    setDialogue();
  }

  /** Get image of Pablo */
  public void getImage() {

    up1 = up2 = left1 = left2 = down1 = down2 = right1 = right2 = setupImage("NPCs/Pablo", gp.tileSize, gp.tileSize);
  }

  /** Dialogues between Pablo and the player */
  public void setDialogue() {
    dialogues[0][0] = "[Pablo] \nOh my dearest guardian! \nWelcome back from the \n300-years ‘dormant’. It’s such \na long time, isn’t it?";
    dialogues[0][1] = "[Me] \n……";
    dialogues[0][2] = "[Me] \nWho are you?";
    dialogues[0][3] = "[Pablo] \nI am Pablo – \na fairy called by the forest. ";
    dialogues[0][4] = "[Me] \n……A fairy? There hasn’t been \na fairy for hundreds of years.";
    dialogues[0][5] = "[Pablo] \nThe forest has called me to \nwake you up. ";
    dialogues[0][6] = "[Pablo] \nNow, the unknown threat is \napproaching our finest home. \nThe forest and its citizens \nneed your help to overcome the \ngreat danger.";
    dialogues[0][7] = "[Me] \n……what danger?";
    dialogues[0][8] = "[Pablo] \nI’m afraid you will need to \nfind out yourself. Once you \nopen your eyes, the answer \nlies there.";
    dialogues[0][9] = "[Pablo] \nSince you are not awoken \nnaturally, you have lost most \nof your natural power as a \ndragon. ";
    dialogues[0][10] = "[Pablo] \nAll of your power now breaks \ninto card pieces, and you will \nneed to collect them to \nretrieve your power. ";
    dialogues[0][11] = "[Me] \n......";
    dialogues[0][12] = "[Pablo] \nStart your journey, my dearest \nguardian! ";
    dialogues[0][13] = "[Pablo] \nThe survival of the forest is \nin your hands. I trust you, \nas all other citizens in the \nforest.";
    dialogues[0][14] = "[Me] \nI will try my best.";
    dialogues[0][15] = "[Pablo] \nA companion may help you get \nfamiliar with the forest. ";
    dialogues[0][16] = "Do you like a cat or a dog?";
    dialogues[0][17] = "OPTION";
    
    dialogues[1][0] = "";
    dialogues[1][1] ="[Pablo] \nBad taste! But I hope you \nenjoy the journey with your \ncat. This is yours now. ";
    dialogues[1][2] = "[Pablo] \nTake this cat with you.";
    dialogues[1][3] = "You received a Cat!";
    dialogues[1][4] = "LEAVE";

    dialogues[2][0] = "";
    dialogues[2][1] = "I’m sorry. But even if you are \nthe best guardian in this \nworld, you still can’t take \nany dog from me. ";
    dialogues[2][2] = "[Pablo] \nTake this cat with you.";
    dialogues[2][3] = "You received a Cat!";
    dialogues[2][4] = "LEAVE";
  }

  /** Talk to the player */
  public void speak() {
    if (dialogueSet == -1) {
      dialogueSet = 0;
    }
    if (dialogueSet == 0) {
      gp.ui.dialSubwindow = "";
      startDialogue(this, 0, "A cat.", "A dog.");
    }
  }

  /** Happens after choose dialogue option 1
   *  @return boolean if the dialogue continues
   */
  public boolean dialogueOption1() {
    observeCat();
    afterOpDialSet = 1;
    return true;
  }

  /** Happens after choose dialogue option 2
   *  @return boolean if the dialogue continues
   */
  public boolean dialogueOption2() {
    observeCat();
    afterOpDialSet = 2;
    return true;
  }

  /**  Make the pet cat visible after the conservation with the player */
  public void observeCat(){
    for(int i = 0; i < gp.npc.length; i++){
      if (gp.npc[i] != null && gp.npc[i].name.equals("Cat")){
        ((NPC_Cat)gp.npc[i]).getRealImage();
        ((NPC_Cat)gp.npc[i]).collision = true;
        ((NPC_Cat)gp.npc[i]).onPath = true;
      }
    }
  }
}