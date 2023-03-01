import java.util.ListIterator;

/**
 *  Class that represents Achievement.
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class Achievement implements Comparable<Achievement>{

  /** Game Panel */
	GamePanel gp;

  /** Rank number of the class, used for sorting and creating card as a shortcut */
	public int rankNum;

  /** Name of the card */
	public String name;

  /** Description of the card */
	public String description;

  /** Ending description of the card (if any) */
	public String endingD = "";

  /** If the achievement is an ending */
	public boolean isEnding = false;

  /** If the achievement is unlocked */
	public boolean unlocked = false;

  /** Constructor
   *  @param gp Game Panel
   *  @param rankNum rank number of the card
   */
	public Achievement(GamePanel gp, int rankNum) {
		this.gp = gp;
		
		this.rankNum = rankNum;
		
		ListIterator<Achievement> it = gp.achiPool.listIterator();
		while(it.hasNext()) {
			Achievement curAchi = it.next();
			if(curAchi.rankNum == rankNum) {
				this.description = curAchi.description;
				this.endingD = curAchi.endingD;
				this.isEnding = curAchi.isEnding;
				this.name = curAchi.name;
				break;
			}
		}
	}

  /** Constructor
   *  @param gp Game Panel
   *  @param rankNum rank number of the achievement
   *  @param name name of the achievement
   *  @param description description of the achievement
   *  @param endingD ending description of the achievement
   *  @param isEnding if the achievement is an ending
   */
	public Achievement(GamePanel gp, int rankNum, String name, String description, String endingD, boolean isEnding) {
		this.gp = gp;
		
		this.rankNum = rankNum;
		this.name = name;
		this.description = description;
		this.endingD = endingD;
		this.isEnding = isEnding;
	}

  /** Constructor
   *  @param gp Game Panel
   *  @param line a line of string that contains achievement information
   */
	public Achievement(GamePanel gp, String line) {
		this.gp = gp;

		String[] info = line.split(":");
		this.rankNum = Integer.parseInt(info[0]);
		this.name = info[1];
		this.description = info[2];
		if (info.length > 3) {
			this.endingD = info[3];
			this.isEnding = true;
		}
	}

	@Override
	public int compareTo(Achievement a2) {
		if(this.unlocked && !a2.unlocked) {
			return ((Integer)(this.rankNum - 100)).compareTo((Integer)a2.rankNum);
		} else if (!this.unlocked && a2.unlocked){
			return ((Integer)this.rankNum).compareTo((Integer)(a2.rankNum-100));
		} else {
			return ((Integer)this.rankNum).compareTo((Integer)a2.rankNum);
		}
		
	}
	
}
	