import java.io.Serializable;
import java.util.ArrayList;

/**
 *  Class to test the heap and heap sort
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class Storage implements Serializable{
	
	// Player data
  /** life of the player */
	int life;
  
  /** max life */
	int maxLife;

  /** Coin player has */
	int coin;

  /** position of the player */
  int worldX, worldY;

  /** direction of the player */
  String direction;

  /** Battle information of the player */
  int health, strength;

  /** Game phase */
  int gamePhase;

  // Achievement
  /** number of times defeated by the fog */
  int defeatedByFog;

  /** number of times defeated by the general */
  int defeatedByGen;

  /** number of times the player used card(s) */
  int usedCard;

  /** number of times the player was drown */
  int drown;

  /** number of times the player used portal(s) */
  int usedPortal;

  /** number of times the player used blue potion(s) */
  int usedBlue;

  /** number of times the player used red potion(s) */
  int usedRed;
  
  /** number of times the player used key(s) */
  int usedKey;

  /** If the achievement is unlocked */
  ArrayList<Boolean> unlocked = new ArrayList<>();
  
  /** Card the player has (in number form) */
  ArrayList<Integer> cardNum = new ArrayList<>();
  
  // Inventory
  /** Item names in inventory */
  ArrayList<String> itemName = new ArrayList<>();

  /** Item amounts in inventory */
  ArrayList<Integer> itemAmounts = new ArrayList<>();
  
  // Object on the map
  /** Objects' names on map */
  String mapOBJName[];

  /** Objects' world x coordinate on map */
  int mapOBJWorldX[];

  /** Objects' world y coordinate on map */
  int mapOBJWorldY[];

  /** Objects' values */
  int mapOBJValue[];

  /** If the chests are open */
  boolean mapOBJOpened[];

  // NPC on the map
  /** NPCs' names on map */
  String npcName[];

  /** NPCs' world x coordinate on map */
  int npcWorldX[];
  
  /** NPCs' world y coordinate on map */
  int npcWorldY[];

  /** NPCs' figure number */
  int npcFigNum[];

  /** NPCs' current dialogue set */
  int npcDialogueSet[];
}
