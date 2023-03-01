import java.awt.Rectangle;

/**
 *  Class for the nun
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class NPC_Nun extends NPC {
	/** Constructor */
	public NPC_Nun(GamePanel gp) {
		super(gp);
		name = "Nun";
		direction = "down";
		speed = 0;
    collision = true;
		
		getImage();
		setDialogue();
	}
	
	/** Get image of the nun*/
  public void getImage() {
  	up1 = up2 = left1 = left2 = down1 = down2 = right1 = right2 = setupImage("NPCs/nun", gp.tileSize, gp.tileSize);  
  }

  /**  Dialogues between the nun and the player */
  public void setDialogue() {
  	dialogues[0][0] = "[Me] \nDo you know what’s going on \nin the forest?";
  	dialogues[0][1] = "[Sister] \nIt started from the winter \nthree years ago... ";
  	dialogues[0][2] = "[Sister] \nIt was a really cold winter, \nwe couldn't even fall asleep at \nnight. ";
  	dialogues[0][3] = "[Sister] \nThe children in our convent never \ncomplained, but one night I \nsaw a child shaking and sobbing \nin her bed. ";
  	dialogues[0][4] = "[Sister] \nWe had a meeting with the General \nto decide if we should harvest \nmore trees to provide heating.";
    dialogues[0][5] = "[Sister] \nMost of us agreed at that time, \nand we thought it’s just \ntemporary. But the \nGeneral never satisfies…";
    dialogues[0][6] = "[Sister] \nI wish all the best for your \njourney, and you can always \nfind me here.";
  	
  	dialogues[1][0] = "[Sister] \nPlease let me heal you first.";
    dialogues[1][1] = "[Sister] \nI wish all the best for your \njourney, and you can always \nfind me here.";
  	
  	dialogues[2][0] = "[Sister] \nGood morning, guardian \nof the forest.";
  }

  /**  Talk to the player */
  public void speak() {
  	if (dialogueSet == -1){
  		dialogueSet = 0;
  	}
  	
  	if (dialogueSet == 0) {
  		startDialogue(this, 0);
  		dialogueSet++;
  	} else {
      // If the player doen't have full 5 hearts, restore the player's health
  		if(gp.player.life < gp.player.maxLife) {
  			startDialogue(this, 1);
  			gp.player.life++;
  		} else {
  			startDialogue(this, 2);
  		}
  	}

  }
}
