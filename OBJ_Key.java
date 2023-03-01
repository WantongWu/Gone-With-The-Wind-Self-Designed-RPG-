/**
 *  Class for keys
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class OBJ_Key extends OBJ {
	
	/** Constructor */
	public OBJ_Key(GamePanel gp) {
		super(gp);
		
		name = "Key";
		type = EntityType.OBJ_INVENTORY;
		image = setupImage("objects/gold_key", gp.tileSize, gp.tileSize);
		description = "[" + name + "]@It opens any door.";
		
		setDialogue();
	}

  /**  Prompt to display to the player */
	public void setDialogue() {
		dialogues[0][0] = "You use the " + name + " and open \nthe door.";
		
		dialogues[1][0] = "Why are you swinging the \nkey?";
	}

  /** Use keys to open doors
   *  @return boolean Check if it is use successfully
   */
	public boolean use(Figure entity) {
		 int index = getDetected(entity, gp.obj, "Door");
		 
		 if(index != -1) {
			 startDialogue(this, 0);
			 gp.obj[index] = null;
			 gp.player.usedKey++;
			 return true;
		 } else {
			 startDialogue(this, 1);
			 return false;
		 }
	}
}
