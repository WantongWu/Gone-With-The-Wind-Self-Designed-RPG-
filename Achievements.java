import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

/**
 *  Class that represent the achievement pool, implemented by linkedlist.
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class Achievements extends LinkedList<Achievement>{

  /** Game Panel */
	GamePanel gp;

  /** Constructor
   *  @param gp Game Panel
   */
	public Achievements(GamePanel gp) {
		super();
		this.gp = gp;
	}

  /** Constructor
   *  @param gp Game Panel
   *  @param achi the original Achievement
   */
	public Achievements(GamePanel gp, Achievement achi) {
		super();
		this.gp = gp;
		this.add(achi);
	}

  /** Constructor
   *  @param gp Game Panel
   *  @param achis a group of achievements to be set as original elements
   */
	public Achievements(GamePanel gp, Achievements achis) {
		super(achis);
		this.gp = gp;
	}

  /** Constructor
   *  @param gp Game Panel
   *  @param filename file name that contains a Achievements pool information
   */
	public Achievements(GamePanel gp, String filename) {
		this.gp = gp;
		
		// Create a scanner for the file
    Scanner sc = null;
    InputStream is = getClass().getResourceAsStream(filename);

		sc = new Scanner(new InputStreamReader(is));

    // Read the file and add achievement to ArrayList 
    while (sc.hasNextLine()) {
      String line = sc.nextLine();
      Achievement a = new Achievement(gp, line);
      this.add(a);
    }
    sc.close();
	}

  /** Get one achievement based on the rank number
   *  @param rankNum rank number of the achievement to be searched
   *  @return Achievement the achievement to be returned
   */
	public Achievement getAchievement(int rankNum) {
		Achievement result = null;
		ListIterator<Achievement> it = gp.achiPool.listIterator();
		while(it.hasNext()) {
			Achievement curAchi = it.next();
			if(curAchi.rankNum == rankNum) {
				result = curAchi;
				return result;
			}
		}
		return result;
	}
	
	/** Insertion Sort that sort card by rankNum 
   *  @return Achievements sorted linked list
   */
	public Achievements sort() {
		Achievements unsorted = this;
		Achievements sorted = new Achievements(gp);

    while(unsorted.size() != 0) {
    	Achievement first = unsorted.poll();
      if (sorted.size() == 0) {
        sorted.add(first);
      } else {
        ListIterator<Achievement> it = sorted.listIterator();
        while(it.hasNext()){
        	Achievement next = it.next();
          if (first.compareTo(next) < 0) {
            it.previous();
            it.add(first);
            it.next();
            break;
          }
        }
        if (!it.hasNext() && first.compareTo(it.previous()) > 0) {
          it.next();
          it.add(first);
        }
      }
    }

    // return the sorted result here
    return sorted;
	}
}