/**
 *  Class for coins
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class OBJ_Coin extends OBJ {
	/**  Constructor */
	public OBJ_Coin(GamePanel gp) {
		super(gp);
		
		setupDefault();
		value = 1;		
	}
  
	/**  Constructor */
	public OBJ_Coin(GamePanel gp, int value) {
		super(gp);
		
		setupDefault();
		this.value = value;
	}

  /** Set the default values of coin */
	public void setupDefault() {
		name = "Coin";
		type = EntityType.OBJ_COIN;
		image = setupImage("objects/coin", gp.tileSize, gp.tileSize);
	}

  /** Add coin number and prompt a message after the player picking up coin(s) 
   *  @return boolean Check if it is use successfully
   */
	public boolean use(Figure entity) {
		if (value == 1) {
			gp.ui.addMessage("Picked up " + this.value + " coin.");
		} else {
			gp.ui.addMessage("Picked up " + this.value + " coins.");
		}	
		gp.player.coin += value;
		return true;
	}
}
