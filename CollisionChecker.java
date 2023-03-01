/**
 *  Class to check collision of player, entity, objects
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class CollisionChecker {
	
	/** Game Panel */
	GamePanel gp;

	/** Constructor */
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	/** Check Collision with tiles
	 *  @param entity entity that is checked
	 */
	public void checkTile(Figure entity) {
		
		int leftWorldX = entity.worldX + entity.solidArea.x;
		int rightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int topWorldY = entity.worldY + entity.solidArea.y;
		int bottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int leftCol = leftWorldX / gp.tileSize;
		int rightCol = rightWorldX / gp.tileSize;
		int topRow = topWorldY / gp.tileSize;
		int bottomRow = bottomWorldY / gp.tileSize;
		
		int tile1, tile2;
		
		switch(entity.direction) {
		
		case "up":
			topRow = (topWorldY - entity.speed) / gp.tileSize;
			tile1 = gp.tileM.mapTileNum[leftCol][topRow];
			tile2 = gp.tileM.mapTileNum[rightCol][topRow];
			if (gp.tileM.tile[tile1].collision || 
					gp.tileM.tile[tile2].collision) {
				entity.collisionOn = true;
			}
			break;
		case "down":
			bottomRow = (bottomWorldY + entity.speed) / gp.tileSize;
			tile1 = gp.tileM.mapTileNum[leftCol][bottomRow];
			tile2 = gp.tileM.mapTileNum[rightCol][bottomRow];
			if (gp.tileM.tile[tile1].collision || 
					gp.tileM.tile[tile2].collision) {
				entity.collisionOn = true;
			}
			break;
		case "left":
			leftCol = (leftWorldX - entity.speed) / gp.tileSize;
			tile1 = gp.tileM.mapTileNum[leftCol][topRow];
			tile2 = gp.tileM.mapTileNum[leftCol][bottomRow];
			if (gp.tileM.tile[tile1].collision || 
					gp.tileM.tile[tile2].collision) {
				entity.collisionOn = true;
			}
			break;
		case "right":
			rightCol = (rightWorldX + entity.speed) / gp.tileSize;
			tile1 = gp.tileM.mapTileNum[rightCol][topRow];
			tile2 = gp.tileM.mapTileNum[rightCol][bottomRow];
			if (gp.tileM.tile[tile1].collision || 
					gp.tileM.tile[tile2].collision) {
				entity.collisionOn = true;
			}
			break;
		}
	}
	
	/** Check Collision with objects
	 *  @param entity entity that is checked
	 *  @param isPlayer if the entity is player and can pick up objects
   *  @return int index of object in object array
	 */
	public int checkObject(Figure entity, boolean isPlayer) {
		
		int index = -1;
		
		for (int i = 0; i < gp.obj.length; i++) {
			if(gp.obj[i] != null) {
				
				// Get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				
				// get object's solid area position
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
			
				switch(entity.direction) {
				case "up": entity.solidArea.y -= entity.speed; break;
				case "down": entity.solidArea.y += entity.speed; break;
				case "left": entity.solidArea.x -= entity.speed; break;
				case "right": entity.solidArea.x += entity.speed; break;
				}
				
				if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
					if(gp.obj[i].collision) {
						entity.collisionOn = true;
					}
					if(isPlayer) {
						index = i;
					}
				}
				
				// Reset solidArea values
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
			}
		}
		
		return index;
		
	}

  /** Check Collision with entity
	 *  @param entity entity that is checked
	 *  @param target the target entity list
   *  @return int index of entity in npc array
	 */
	public int checkEntity(Figure entity, Entity[] target) {
		int index = -1;
		
		for (int i = 0; i < target.length; i++) {
			if(target[i] != null && target[i].collision) {
				
				// Get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				
				// get object's solid area position
				target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
				target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;
			
				switch(entity.direction) {
				case "up": entity.solidArea.y -= entity.speed; break;
				case "down": entity.solidArea.y += entity.speed; break;
				case "left": entity.solidArea.x -= entity.speed; break;
				case "right": entity.solidArea.x += entity.speed; break;
				}
				
				if(entity.solidArea.intersects(target[i].solidArea) && target[i].collision) {
					if(target[i] != entity) {
						entity.collisionOn = true;
						index = i;
					}
				}
				
				// Reset solidArea values
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[i].solidArea.x = target[i].solidAreaDefaultX;
				target[i].solidArea.y = target[i].solidAreaDefaultY;
			}
		}
		
		return index;
	}

  /** Check Collision of player with figure
	 *  @param entity entity that is checked
	 */
	public void checkPlayer(Figure entity) {
		// Get entity's solid area position
		entity.solidArea.x = entity.worldX + entity.solidArea.x;
		entity.solidArea.y = entity.worldY + entity.solidArea.y;
		
		// get object's solid area position
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
	
		switch(entity.direction) {
		case "up":
			entity.solidArea.y -= entity.speed;
			break;
		case "down":
			entity.solidArea.y += entity.speed;
			break;
		case "left":
			entity.solidArea.x -= entity.speed;
			break;
		case "right":
			entity.solidArea.x += entity.speed;
			break;
		}
		
		if(entity.solidArea.intersects(gp.player.solidArea)) {
			entity.collisionOn = true;
		}
		
		// Reset solidArea values
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		
	}
}
