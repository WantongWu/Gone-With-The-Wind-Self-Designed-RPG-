import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *  Class that implements object
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class OBJ extends Entity{

  /** Description of the image */
	public String description = "";
	
	/** Store images */
  public BufferedImage image;

  /** If the object can be stacked */
  public boolean stackable = false;

  /** Amount of the object (to be displayed in inventory) */
  public int amount = 1;

  /** Value of the object */
  public int value = 0;

  /** Constructor */
	public OBJ(GamePanel gp) {
		super(gp);
	}

  /** Use the object
   *  @return boolean Check if it is use successfully
   */
	public boolean use(Figure entity) {return false;}

  /** Draw object picture
   *  @param g2 Graphics2D where the image is drawn
   */
	public void draw(Graphics2D g2) {
  	
  	if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
				worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			g2.drawImage(this.image, screenX, screenY, null);
		}
	}

  /** Interact with the object */
	public void interact() {}

  /** Detect nearby object 
   *  @param user the player
   *  @param target the array where the target is stored
   *  @param name the target name
   */
	public int getDetected(Figure user, OBJ target[], String name) {
		
		int index = -1;
		
		// Check the surrounding object
		int nextWorldX = user.getLeftX();
		int nextWorldY = user.getTopY();
		
		switch(user.direction) {
		case "up": nextWorldY = user.getTopY() - gp.player.speed; break;
		case "down": nextWorldY = user.getBottomY() + gp.player.speed; break;
		case "left": nextWorldX = user.getLeftX() - gp.player.speed; break;
		case "right": nextWorldX = user.getRightX() + gp.player.speed; break;
		}
		
		int col = nextWorldX/gp.tileSize;
		int row = nextWorldY/gp.tileSize;
		
		for(int i=0; i< target.length; i++) {
			if(target[i] != null && target[i].getCol() == col &&
						target[i].getRow() == row && target[i].name.equals(name)) {
					index = i;
					break;
			}
		}
		
		return index;
	}

}
