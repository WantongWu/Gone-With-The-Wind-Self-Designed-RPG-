/**
 *  Class that implements NPC
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022 
 */
public class NPC extends Figure {
  
	/** Action counter that calculates the time of each action move */
	public int actionCounter = 0;
  
  /** If entity is moving towards a specific location */
	public boolean onPath = false;

  /** Variation of the damage */
	public int strengthVariation = 0;

  /** Initial dialogue set number */
	public int dialogueSet = -1;

  /** If the npc can fight with the player */
	public boolean canFight = false;

  /** if the npc wins */
  public boolean ifWin = true;

  /** The dialogue set number after option happens */
  public int afterOpDialSet;

  /** Figure number if any */
  public int figNum = 0;
  
  /** Constructor */
	public NPC(GamePanel gp) {
		super(gp);
	}

  /** Battle action */
	public void battle() {
  	batDialIndex = (int) (Math.random()*batDials.size());
  	int damage = (int) (Math.random()*(strengthVariation*2 + 1) + strength - strengthVariation);
  	gp.ui.battleInfo.add(batDials.get(batDialIndex) + "\nDealt "+ damage + " damage.");
  	gp.player.health -= damage;
  }

  /** Disappear in the map */
  public void disappear() {
    for (int i = 0; i < gp.npc.length; i++){
      if (gp.npc[i] == this){
        gp.npc[i] = null;
      }
    }
  }

  /** Search the path if needed
   *  @param goalCol the goal column location
   *  @param goalRow the goal row location
   */
	public void searchPath(int goalCol, int goalRow) {
		int startCol = (worldX + solidArea.x)/gp.tileSize;
		int startRow = (worldY + solidArea.y)/gp.tileSize;
		
		gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow, this);
		
		if(gp.pFinder.search()) {
			
			// Next worldX & worldY
			int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
			int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
			
			// SolidArea position
			int leftX = worldX + solidArea.x;
			int rightX = worldX + solidArea.x + solidArea.width;
			int topY = worldY + solidArea.y;
			int bottomY = worldY + solidArea.y + solidArea.height;
			
			if(topY > nextY && leftX >= nextX && rightX < nextX + gp.tileSize) {
				direction = "up";
			} else if (topY < nextY && leftX >= nextX && rightX < nextX + gp.tileSize) {
				direction = "down";
			} else if (topY >= nextY && bottomY < nextY + gp.tileSize) {
				if(leftX > nextX) {
					direction = "left";
				}
				if(leftX < nextX) {
					direction = "right";
				}
			} else if (topY > nextY && leftX > nextX) {
				direction = "up";
				checkCollision();
				if(collisionOn) { direction = "left";}
			} else if (topY > nextY && leftX < nextX) {
				direction = "up";
				checkCollision();
				if(collisionOn) {direction = "right";}
			} else if (topY < nextY && leftX > nextX) {
				direction = "down";
				checkCollision();
				if(collisionOn) {direction = "left";}
			} else if (topY < nextY && leftX < nextX) {
				direction = "down";
				checkCollision();
				if(collisionOn) {direction = "right";}
			}
			
//			int nextCol = gp.pFinder.pathList.get(0).col;
//			int nextRow = gp.pFinder.pathList.get(0).row;
//			if(nextCol == goalCol && nextRow == goalRow) {
//				onPath = false;
//			}
		}
	}

  /** Happens after choose dialogue option 1
   *  @return boolean if the dialogue continues
   */
	public boolean dialogueOption1() {return false;}

  /** Happens after choose dialogue option 2
   *  @return boolean if the dialogue continues
   */
  public boolean dialogueOption2() {return false;}
	
}
