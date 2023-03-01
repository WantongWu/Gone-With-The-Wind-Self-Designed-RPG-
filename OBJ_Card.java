import java.util.ListIterator;

/**
 *  Class for cards
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class OBJ_Card extends OBJ implements Comparable<OBJ_Card>{

  /** Rank number */
  public int rankNum;

  /** Value of instant damage */
	public int instDmg;

  /** Value of permanent damage */
	public int permDmg;

  /** Value of health */
	public int health;

  /**  Constructor */
	public OBJ_Card(GamePanel gp, int rankNum) {
		super(gp);
		
		this.rankNum = rankNum;
		stackable = true; 
		image = setupImage("objects/Card", gp.tileSize, gp.tileSize);
		type = EntityType.OBJ_CARD;
		
		ListIterator<OBJ_Card> it = gp.cardPool.listIterator();
		while(it.hasNext()) {
			OBJ_Card curCard = it.next();
			if(curCard.rankNum == rankNum) {
				this.description = curCard.description;
				this.instDmg = curCard.instDmg;
				this.permDmg = curCard.permDmg;
				this.health = curCard.health;
				this.name = curCard.name;
				break;
			}
		}
	}

   /**  Constructor */
	public OBJ_Card(GamePanel gp, int rankNum, String name, String description, int instDmg, int permDmg, int health) {
		super(gp);
		
		stackable = true;
		this.description = "["+name+"]@"+description;
		type = EntityType.OBJ_CARD;
		this.name = "Card: "+name;
		this.rankNum = rankNum;
		this.instDmg = instDmg;
		this.permDmg = permDmg;
		this.health = health;
		image = setupImage("objects/Card", gp.tileSize, gp.tileSize);
	}

  /** Constructor */
	public OBJ_Card(GamePanel gp, String line) {
		super(gp);
		
		stackable = true;
		type = EntityType.OBJ_CARD;
		String[] info = line.split(":");
		this.rankNum = Integer.parseInt(info[0]);
		this.name = "Card: "+info[1];
		this.description = "["+name+"]@"+info[2];
		this.instDmg = Integer.parseInt(info[3]);
		this.permDmg = Integer.parseInt(info[4]);
		this.health = Integer.parseInt(info[5]);
		image = setupImage("objects/Card", gp.tileSize, gp.tileSize);
	}

  /** Compare the value of cards */
	public int compareTo(OBJ_Card c2) {
		return ((Integer)this.rankNum).compareTo((Integer)c2.rankNum);
	}
	
}
