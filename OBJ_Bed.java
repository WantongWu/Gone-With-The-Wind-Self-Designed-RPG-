/**
 *  Class for bed
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class OBJ_Bed extends OBJ{
	/** Constructor */
	public OBJ_Bed(GamePanel gp) {
		super(gp);
		name = "Bed";
		type = EntityType.OBJ_OBSTACLE;
		
		image = setupImage("objects/bed", gp.tileSize, gp.tileSize);
		
		collision = true;
		setDialogue();
	}

  /**  Prompt after the player save the progress */
	public void setDialogue() {
		dialogues[0][0] ="Your progress has been saved.";
	}

  /**  Save the game progress after the player interact with the bed */
	public void interact() {
		startDialogue(this, 0);
		gp.saveLoad.save();
	}
}
