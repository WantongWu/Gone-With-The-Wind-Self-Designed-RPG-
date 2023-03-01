import java.awt.Rectangle;

/**
 *  Class for animals
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class NPC_Animal extends NPC{
  
	/** Constructor */
	public NPC_Animal(GamePanel gp, String name) {
		super(gp);
		this.name = name;
		direction = "left";
		speed = 0;
    collision = true;
		
		solidArea = new Rectangle(1, 6, gp.tileSize-2, gp.tileSize-6);
		if (name.equals("Bear")) {
			solidArea.setBounds(3, 6, gp.tileSize*2-6, gp.tileSize*2-6);
		}
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

    dialogueSet = (int) (Math.random()*7); // 0-6
		
		getImage();
		setDialogue();
	}
	
	/** Get image of the animal. Set special size for the bear. */
  public void getImage() {
    if (name.equals("Bear")) {
    	up1 = up2 = left1 = left2 = down1 = down2 = right1 = right2 = setupImage("NPCs/animal/" + name, gp.tileSize*2, gp.tileSize*2);
    } else {
    	up1 = up2 = left1 = left2 = down1 = down2 = right1 = right2 = setupImage("NPCs/animal/" + name, gp.tileSize, gp.tileSize);
    }
  }

  /** Set the dialogues between the player and the animal. */
  public void setDialogue() {
  	
  	dialogues[0][0] = "[" + this.name +"]\nGood morning, dear guardian! \nHere’s a small gift for you.";

    dialogues[1][0] = "[" + this.name +"]\nIt’s so nice to see you around! \nI got you a small gift.";

    dialogues[2][0] = "[" + this.name +"]\nHow’s it going? Wanna have \nlunch together sometime?";
    dialogues[2][1] = "[Me] \nI’d love to, but I have \nsomething more urgent to do.";
    dialogues[2][2] = "[" + this.name +"]\nAlright. I wanted to give you \na surprise, but it seems like \nI have to show you now. ";
    dialogues[2][3] = "[" + this.name +"]\nI found it in the forest. \nHope it helps.";

    dialogues[3][0] = "[" + this.name +"]\nOh, you’re here. Is it your card? \nI just found it on the ground.";
    dialogues[3][1] = "[Me] \nYeah I’ll take it.";
    dialogues[3][2] = "[" + this.name +"]\nDon’t drop it again!";

    dialogues[4][0] = "[" + this.name +"]\n......";
    dialogues[4][1] = "[Me] \n??";
    dialogues[4][2] = "[" + this.name +"]\n......";

    dialogues[5][0] = "[" + this.name +"]\nHey.";
    dialogues[5][1] = "[Me] \nHey.";
    dialogues[5][2] = "[" + this.name +"]\nHey.";
    dialogues[5][3] = "[Me] \nHey?";
    dialogues[5][4] = "[" + this.name +"]\nHere.";

    dialogues[6][0] = "[" + this.name +"]\nLet’s play a game. I have \nsomething for you, which hand?";
    dialogues[6][1] = "OPTION";

    dialogues[7][0] = "";
    dialogues[7][1] = "[" + this.name +"]\nThat’s right! Here it is.";
    
    dialogues[8][0] = "[" + this.name +"]\nGood morning.";
  }

  /**  Talk to the player */
  public void speak() {
    if (dialogueSet < 6) {
      startDialogue(this, dialogueSet);
      dialogueSet = 8;
      addCard();
      gp.ui.addMessage("Got a card!");
    } else if (dialogueSet == 6){
      gp.ui.dialSubwindow = "";
      startDialogue(this, 6, "Left hand.","Right hand.");
      dialogueSet = 8;
    } else {
      startDialogue(this, 8);
    }
  }

  /**  Give the player a random card after they chose option 1 (left hand) in conversation 6 */
  public boolean dialogueOption1() {
    afterOpDialSet = 7;
    addCard();
    gp.ui.addMessage("Got a card!");
    return true;
  }

  /**  Give the player a random card after they chose option 2 (right hand) in conversation 6 */
  public boolean dialogueOption2() {
    afterOpDialSet = 7;
    addCard();
    gp.ui.addMessage("Got a card!");
    return true;
  }

  /**  Give the player a random card after the conversation */
  public void addCard() {
    int cardNum = (int) (Math.random()*32 + 1); // 1-32
    gp.player.cardPile.add(new OBJ_Card(gp, cardNum));
    if (gp.player.getItemIndex("Card") != -1) {
      gp.player.inventory.set(gp.player.getItemIndex("Card"), gp.player.cardPile.peekLast());
    } else {
      gp.player.inventory.add(gp.player.cardPile.peekLast());
    }
  }

}
