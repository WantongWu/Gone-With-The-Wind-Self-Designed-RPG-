/**
 *  Class to handle the event
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class EventHandler {

  /** Game Panel */
	GamePanel gp;

  /** 2D array that includes eventRect of every tile */
	EventRect eventRect[][];

  /** A variable that store an entity to pass data */
	Entity speaker;

  /** Previous event x and y coordinate */
	int prevEventX, prevEventY;

  /** If the eventRect can be touched */
	boolean canTouch = true;

  /** Constructor */
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		
		speaker = new Entity(gp);
		eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
		
		for(int col = 0; col < gp.maxWorldCol; col++) {
			for(int row = 0; row < gp.maxWorldRow; row++) {
				eventRect[col][row] = new EventRect(23, 23, 2, 2);
				eventRect[col][row].defaultX = eventRect[col][row].x;
				eventRect[col][row].defaultY = eventRect[col][row].y;
			}
		}
		
		setDialogue();
	}

  /** Set dialogue */
	public void setDialogue() {
		speaker.dialogues[0][0] = "Your progress has been \nsaved.";
		
		speaker.dialogues[1][0] = "Your life has been recovered.";
		
		speaker.dialogues[2][0] = "As a fire dragon, \nyou should not get close to \nwater.";
	}

  /** Check event (called in player class) */
	public void checkEvent() {
		
		int xDist = Math.abs(gp.player.worldX - prevEventX);
		int yDist = Math.abs(gp.player.worldY - prevEventY);
		if(Math.max(xDist, yDist) > gp.tileSize) {
			canTouch = true;
		}
		
		if(canTouch) {
			if (hit(104, 32, "any")||hit(21, 42, "any")||hit(44, 104, "any")||hit(8, 8, "any")) {
				teleport(42, 19, GameState.DIALOGUE);
			}
			if(hitWater(gp.player.getCol(), gp.player.getRow())) {
				gp.ui.ending = gp.achiPool.getAchievement(4);
				if(!gp.achiPool.getAchievement(4).unlocked) {
					gp.achiPool.getAchievement(4).unlocked = true;
					gp.achiPool = gp.achiPool.sort();
				}
        gp.ui.gameEnd = true;
				gp.player.drown++;
				waterPit();
			}
		}
	}

  /** Check if the player hits a place
	 *  @param col column of the eventRect
	 *  @param row row of the eventRect
	 *  @param reqDirection if the tile should be hit from a specific direction
   *  @return boolean if the player hits
	 */
	public boolean hit(int col, int row, String reqDirection) {
		EventRect rect = eventRect[col][row];
		boolean hit = false;
		
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		rect.x = col * gp.tileSize + rect.x;
		rect.y = row * gp.tileSize + rect.y;
		
		if((!rect.done) && gp.player.solidArea.intersects(rect)) {
			if(gp.player.direction.equals(reqDirection) ||
					reqDirection.equals("any")) {
				hit = true;
				
				prevEventX = gp.player.worldX;
				prevEventY = gp.player.worldY;
			}
		}
		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		rect.x = rect.defaultX;
		rect.y = rect.defaultY;
		
		return hit;
	}

  /** Check if the player hits the water
	 *  @param col column of the player's position
	 *  @param row row of the player's position
   *  @return boolean if the player hits
	 */
	public boolean hitWater(int col, int row) {
		boolean hit = false;
		Tile tile = gp.tileM.tile[gp.tileM.mapTileNum[col][row]];
		if (tile.water) {
			EventRect rect = eventRect[col][row];
  		
  		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
  		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
  		rect.x = col * gp.tileSize + rect.x;
  		rect.y = row * gp.tileSize + rect.y;
  		
  		if((!rect.done) && gp.player.solidArea.intersects(rect)) {
        hit = true;
        
        prevEventX = gp.player.worldX;
        prevEventY = gp.player.worldY;
  		}
  		
  		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
  		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
  		rect.x = rect.defaultX;
  		rect.y = rect.defaultY;
		}
		return hit;
	}

  /** Happens if the player hits the water (gameover) */
	public void waterPit() {
		if(gp.player.life > 0) {
			gp.player.life -= 5;
		}
	}

  /** healing pool event (not used now)
	 *  @param col column of the eventRect
	 *  @param row row of the eventRect
	 *  @param state game state to be passed (to be entered after the event is called)
	 */
	public void healingPool(int col, int row, GameState state) {
		if(gp.keyH.interactPressed) {
			gp.state = state;
			speaker.startDialogue(speaker, 1);
			if(gp.player.life < gp.player.maxLife) {
				gp.player.life++;
			}
//			eventRect[col][row].done = true; // Happen only once
			canTouch = false; // Happen only once each touch
		}
	}

  /** Teleport to somewhere
	 *  @param toRow row to be teleported
	 *  @param toCol column to be teleported
	 *  @param state game state to be passed (to be entered after the event is called)
	 */
	public void teleport(int toRow, int toCol, GameState state) {
		if(gp.keyH.interactPressed) {
			gp.state = state;
			speaker.startDialogue(speaker, 0);
			gp.player.worldX = gp.tileSize*toRow;
			gp.player.worldY = gp.tileSize*toCol;
			gp.player.usedPortal++;
			gp.saveLoad.save();
		}
	}

}
