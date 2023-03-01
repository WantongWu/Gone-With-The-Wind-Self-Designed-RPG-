import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 *  Set objects and npcs.
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class AssetSetter {

  /** GamePanel */
  GamePanel gp;

  /** Constructor */
  public AssetSetter(GamePanel gp) {
    this.gp = gp;
  }

  /** Set objects in array */
  public void setObject() {

    InputStream is = getClass().getResourceAsStream("/src/settings/OBJ_setting.txt");

    Scanner sc;
    int lineNum = 0;

    sc = new Scanner(new InputStreamReader(is));
    while (sc.hasNextLine()) {
      String line = sc.nextLine();
      String[] obj = line.split(" ");
      if (obj.length == 3) {
        gp.obj[lineNum] = getOBJ(obj[0]);
      } else {
        gp.obj[lineNum] = getOBJ(obj[0], Integer.parseInt(obj[3]));
      }
      gp.obj[lineNum].worldX = gp.tileSize * Integer.parseInt(obj[1]);
      gp.obj[lineNum].worldY = gp.tileSize * Integer.parseInt(obj[2]);
      lineNum++;
    }

    sc.close();
  }

  /** Get object based on its name
   *  @param name name of the object
   *  @return OBJ the new object to be set
   */
  public OBJ getOBJ(String name) {
    OBJ result = null;
    switch (name) {
      case "Chest":
        result = new OBJ_Chest(gp);
        break;
      case "Coin":
        result = new OBJ_Coin(gp);
        break;
      case "Door":
        result = new OBJ_Door(gp);
        break;
      case "Heart":
        result = new OBJ_Heart(gp);
        break;
      case "Key":
        result = new OBJ_Key(gp);
        break;
      case "Potion":
        result = new OBJ_Potion(gp);
        break;
      case "Bed":
        result = new OBJ_Bed(gp);
        break;
      case "Portal":
        result = new OBJ_Portal(gp);
        break;
    }
    return result;
  }

  /** Get object based on its name and value (if any)
   *  @param name name of the object
   *  @param value value of the object
   *  @return OBJ the new object to be set
   */
  public OBJ getOBJ(String name, int value) {
    OBJ result = null;
    switch (name) {
      case "Coin":
        result = new OBJ_Coin(gp, value);
        break;
      case "Chest":
        result = new OBJ_Chest(gp, value);
        break;
      case "Potion":
        result = new OBJ_Potion(gp, value);
        break;
      case "Door":
        result = new OBJ_Door(gp, value);
        break;
    }
    return result;
  }

  /** Set npcs in array */
  public void setNPC() {
    InputStream is = getClass().getResourceAsStream("/src/settings/NPC_setting.txt");

    Scanner sc;
    int lineNum = 0;

    sc = new Scanner(new InputStreamReader(is));
    while (sc.hasNextLine()) {
      String line = sc.nextLine();
      String[] npc = line.split(" ");
      if (npc.length == 3) {
        gp.npc[lineNum] = getNPC(npc[0]);
      } else {
        gp.npc[lineNum] = getNPC(npc[0], Integer.parseInt(npc[3]));
      }
      gp.npc[lineNum].worldX = gp.tileSize * Integer.parseInt(npc[1]);
      gp.npc[lineNum].worldY = gp.tileSize * Integer.parseInt(npc[2]);
      lineNum++;
    }
    sc.close();
  }

  /** Get NPC based on its name
   *  @param name name of the NPC
   *  @return NPC the new npc to be set
   */
  public NPC getNPC(String name) {
    NPC result = null;

    switch (name) {
      case "Cat":
        result = new NPC_Cat(gp);
        break;
      case "Fog":
        result = new NPC_Fog(gp);
        break;
      case "Nun":
        result = new NPC_Nun(gp);
        break;
      case "Fairy":
        result = new NPC_Fairy(gp);
        break;
      case "Pablo":
        result = new NPC_Pablo(gp);
        break;
      case "General":
        result = new NPC_General(gp);
        break;
      default:
        result = new NPC_Animal(gp, name);
        break;
    }
    return result;
  }

  /** Get NPC based on its name
   *  @param name name of the NPC
   *  @param value value of the NPC
   *  @return NPC the new npc to be set
   */
  public NPC getNPC(String name, int value) {
    NPC result = null;
    switch (name) {
      case "Lumberman":
        result = new NPC_Lumberman(gp, value);
        break;
      case "Soldier":
        result = new NPC_Soldier(gp, value);
        break;
      case "Goblin":
        result = new NPC_Goblin(gp, value);
        break;
    }
    return result;
  }

}
