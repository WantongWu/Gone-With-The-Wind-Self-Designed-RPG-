import java.io.*;

/**
 *  Class to save and load the game 
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class SaveLoad {

  /** Game Panel */
	GamePanel gp;

  /** Constructor */
	public SaveLoad(GamePanel gp) {
		this.gp = gp;
	}

  /** Save the progress */
	public void save() {
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
			
			Storage ds = new Storage();
			ds.life = gp.player.life;
			ds.maxLife = gp.player.maxLife;
			ds.coin = gp.player.coin;
		  ds.worldX = gp.player.worldX;
		  ds.worldY = gp.player.worldY;
		  ds.direction = gp.player.direction;
		  ds.health = gp.player.health;
		  ds.strength = gp.player.strength;

      ds.gamePhase = gp.gamePhase;

      // Achievement
      ds.defeatedByFog = gp.player.defeatedByFog;
      ds.defeatedByGen = gp.player.defeatedByGen;
      ds.usedCard = gp.player.usedCard;
      ds.drown = gp.player.drown;
      ds.usedPortal = gp.player.usedPortal;
      ds.usedBlue = gp.player.usedBlue;
      ds.usedRed = gp.player.usedRed;
      ds.usedKey = gp.player.usedKey;
      for(int i = 0; i < gp.achiPool.size(); i++) {
        ds.unlocked.add(gp.achiPool.get(i).unlocked);
      }
		  
		  // CardPile
		  if(gp.player.getItemIndex("Card") != -1) {
		  	for(int i = 0; i < gp.player.cardPile.size(); i++) {
		  		ds.cardNum.add(gp.player.cardPile.get(i).rankNum);
		  	}
		  }
		  
		  // Inventory
      if (gp.player.inventory.size() > 0) {
        for(int i = 0; i < gp.player.inventory.size(); i++) {
  		  	ds.itemName.add(gp.player.inventory.get(i).name);
  		  	ds.itemAmounts.add(gp.player.inventory.get(i).amount);
  		  }
      }
		  
		  
		  // Map Objects
		  ds.mapOBJName = new String[gp.obj.length];
		  ds.mapOBJWorldX = new int[gp.obj.length];
		  ds.mapOBJWorldY = new int[gp.obj.length];
		  ds.mapOBJValue = new int[gp.obj.length];
		  ds.mapOBJOpened = new boolean[gp.obj.length];
		  
		  for(int i = 0; i < gp.obj.length; i++) {
		  	OBJ obj = gp.obj[i];
		  	if(obj == null) {
		  		ds.mapOBJName[i] = "NA";
		  	} else {
		  		ds.mapOBJName[i] = obj.name;
		  		ds.mapOBJWorldX[i] = obj.worldX;
		  		ds.mapOBJWorldY[i] = obj.worldY;
          ds.mapOBJValue[i] = obj.value;
		  		if(obj instanceof OBJ_Chest) {
		  			ds.mapOBJOpened[i] = ((OBJ_Chest)obj).opened;
		  		} else {
		  			ds.mapOBJOpened[i] = false;
		  		}
		  	}
		  }

      // NPCs
      ds.npcName = new String[gp.npc.length];
      ds.npcWorldX = new int[gp.npc.length];
      ds.npcWorldY = new int[gp.npc.length];
      ds.npcDialogueSet = new int[gp.npc.length];
      ds.npcFigNum = new int[gp.npc.length];

      for(int i = 0; i < gp.npc.length; i++) {
        NPC npc = gp.npc[i];
        if(npc == null){
          ds.npcName[i] = "NA";
        } else {
          ds.npcName[i] = npc.name;
          ds.npcWorldX[i] = npc.worldX;
          ds.npcWorldY[i] = npc.worldY;
          ds.npcDialogueSet[i] = npc.dialogueSet;
          ds.npcFigNum[i] = npc.figNum;
        }
      }
		  
		  oos.writeObject(ds);
		} catch (Exception e) {
      e.printStackTrace();
		}
		
	}

  /** Load the progress */
	public void load() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
		
			// Read DataStorage object
			Storage ds = (Storage)ois.readObject();
			
			// Player
			gp.player.life = ds.life;
			gp.player.maxLife = ds.maxLife;
			gp.player.coin = ds.coin;
		  gp.player.worldX = ds.worldX;
		  gp.player.worldY = ds.worldY;
		  gp.player.direction = ds.direction;
		  gp.player.health = ds.health;
		  gp.player.strength = ds.strength;

      gp.gamePhase = ds.gamePhase;
      
      // Achievement
      gp.player.defeatedByFog = ds.defeatedByFog;
      gp.player.defeatedByGen = ds.defeatedByGen;
      gp.player.usedCard = ds.usedCard;
      gp.player.drown = ds.drown;
      gp.player.usedPortal = ds.usedPortal;
      gp.player.usedBlue = ds.usedBlue;
      gp.player.usedRed = ds.usedRed;
      gp.player.usedKey = ds.usedKey;
      for(int i = 0; i < gp.achiPool.size(); i++) {
        gp.achiPool.get(i).unlocked = ds.unlocked.get(i);
      }
      
		  // CardPile
		  gp.player.cardPile.clear();
		  if(ds.cardNum.size() > 0) {
		  	for(int i : ds.cardNum) {
          OBJ_Card c = new OBJ_Card(gp, i);
		  		gp.player.cardPile.add(c);
		  	}
		  }
		  
		  // Inventory
		  gp.player.inventory.clear();
		  for(int i = 0; i < ds.itemName.size(); i++) {
		  	String name = ds.itemName.get(i);
		  	if (name.contains("Card")) {
		  		gp.player.inventory.add(gp.player.cardPile.peekLast());
		  	} else if (name.contains("Potion")) {
		  		if(name.equals("Red Potion")) {
		  			gp.player.inventory.add(gp.aSetter.getOBJ("Potion"));
		  		} else {
		  			gp.player.inventory.add(gp.aSetter.getOBJ("Potion", 2));
		  		}
		  	} else {
		  		gp.player.inventory.add(gp.aSetter.getOBJ(name));
		  	}
		  	gp.player.inventory.get(i).amount = ds.itemAmounts.get(i);
		  }
		  
		  // Objects on map
		  for(int i = 0; i < gp.obj.length; i++) {
		  	if(ds.mapOBJName[i].equals("NA")) {
		  		gp.obj[i] = null;
		  	} else {
		  		if (ds.mapOBJValue[i] != 0) {
            if (ds.mapOBJName[i].contains("Potion")) {
              gp.obj[i] = gp.aSetter.getOBJ("Potion", ds.mapOBJValue[i]);
            } else if (ds.mapOBJName[i].contains("Chest")) {
              gp.obj[i] = gp.aSetter.getOBJ("Chest", ds.mapOBJValue[i]);
            } else {
              gp.obj[i] = gp.aSetter.getOBJ(ds.mapOBJName[i], ds.mapOBJValue[i]);
            }
		  			
            if (gp.obj[i] instanceof OBJ_Chest) {
              OBJ_Chest chest = ((OBJ_Chest)gp.obj[i]);
		  			  chest.opened = ds.mapOBJOpened[i];
  		  			if(((OBJ_Chest)gp.obj[i]).opened) {
  		  				chest.image = chest.imageOp;
  		  			}
            }
		  		} else {
		  			gp.obj[i] = gp.aSetter.getOBJ(ds.mapOBJName[i]);
		  		}
          // System.out.println("DS: " + ds.mapOBJName[i] + " " + ds.mapOBJValue[i]);
          // System.out.println("OBJ: " + gp.obj[i].name + " " + gp.obj[i].value);
		  		gp.obj[i].worldX = ds.mapOBJWorldX[i];
		  		gp.obj[i].worldY = ds.mapOBJWorldY[i];
		  	}
		  	
		  }

      // NPC
      for (int i = 0; i < gp.npc.length; i++) {
        if (ds.npcName[i].equals("NA")){
          gp.npc[i] = null;
        } else {
          if (ds.npcFigNum[i] == 0) {
            gp.npc[i] = gp.aSetter.getNPC(ds.npcName[i]);
          } else {
            gp.npc[i] = gp.aSetter.getNPC(ds.npcName[i], ds.npcFigNum[i]);
          }
          gp.npc[i].worldX = ds.npcWorldX[i];
          gp.npc[i].worldY = ds.npcWorldY[i];
          gp.npc[i].dialogueSet = ds.npcDialogueSet[i];
        }
      }
      
      boolean hasPablo = false;
      for (int i = 0; i < gp.npc.length; i++) {
        if (gp.npc[i] != null && gp.npc[i].name.equals("Pablo")) {
          hasPablo = true;
          break;
        }
      }
      if (!hasPablo) {
        for(int i = 0; i < gp.npc.length; i++){
          if (gp.npc[i] != null && gp.npc[i].name.equals("Cat")){
            ((NPC_Cat)gp.npc[i]).getRealImage();
            ((NPC_Cat)gp.npc[i]).collision = true;
            ((NPC_Cat)gp.npc[i]).onPath = true;
          }
          break;
        }
      }
		  
		  ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
