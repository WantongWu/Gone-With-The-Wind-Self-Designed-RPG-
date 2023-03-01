/**
 *  Class for doors
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class OBJ_Door extends OBJ {
	/** Constructor */
	public OBJ_Door(GamePanel gp) {
		super(gp);
		name = "Door";
		type = EntityType.OBJ_OBSTACLE;
    this.value = 1; // 1 / 2
		
		getImage();		
		collision = true;
		setDialogue();
	}

  /** Constructor */
	public OBJ_Door(GamePanel gp, int objNum) {
		super(gp);
		name = "Door";
		type = EntityType.OBJ_OBSTACLE;
    this.value = objNum;
		
		getImage();
		
		collision = true;
		setDialogue();
	}

  /**  Prompt to display to the player */
	public void setDialogue() {
		dialogues[0][0] ="You need a key. \nUse a key in your inventory.";
	}

  /**  Get image of doors */
  public void getImage() {
    if (value == 1) {
      image = setupImage("objects/door_grey", gp.tileSize, gp.tileSize);
    } else {
      image = setupImage("objects/door_brown", gp.tileSize, gp.tileSize);
    } 
  }

  /** Prompt a message if the player try to interact with the door */
	public void interact() {
		startDialogue(this, 0);
	}
	
}
