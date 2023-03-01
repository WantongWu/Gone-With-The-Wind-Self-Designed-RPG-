/**
 *  Class for potions
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class OBJ_Potion extends OBJ {
	/**  Constructor */
	public OBJ_Potion(GamePanel gp) {
		super(gp);
		
		stackable = true;
		type = EntityType.OBJ_POTION;
		name = "Red Potion";
		image = setupImage("objects/potion", gp.tileSize, gp.tileSize);
		description = "[Red Potion]@Cures your ache.";
    this.value = 1;
		
		setDialogue();
	}

  /**  Constructor */
	public OBJ_Potion(GamePanel gp, int pType) {
		super(gp);
		
		stackable = true;
		type = EntityType.OBJ_POTION;
		this.value = pType;
		
		if(pType == 1) {
			name = "Red Potion";
			image = setupImage("objects/potion", gp.tileSize, gp.tileSize);
			description = "[Red Potion]@Cures your ache.";
		} else {
			name = "Blue Potion";
			image = setupImage("objects/potionBlue", gp.tileSize, gp.tileSize);
			description = "[Blue Potion]@Home Sweet Home. @Very useful when you get @stuck (by the cat).";
		}
		setDialogue();
	}

  /**  Prompt to display to the player */
	public void setDialogue() {
		dialogues[0][0] = "You drink the " + name + "!\nYour life has been recovered \nby 1.";
		
		dialogues[1][0] = "You drink the " + name + "!\nTeleported home!";
	}

  /**  Use potion to restore hearts or teleport back home 
   *  @return boolean Check if it is used successfully
   */
	public boolean use(Figure fig) {
		if(value == 1) {
			startDialogue(this, 0);
			if(fig.life < fig.maxLife) {
				fig.life += 1;
			}
			gp.player.usedRed++;
		} else if (value == 2) {
			startDialogue(this, 1);
			fig.worldX = gp.tileSize * 42;
	    fig.worldY = gp.tileSize * 19;
	    gp.player.usedBlue++;
		}
		return true;
	}
}
