import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

/**
 *  Class that represent the card pool, implemented by linkedlist.
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class CardPile extends LinkedList<OBJ_Card>{

  /** Game Panel */
	GamePanel gp;

  /** Constructor
   *  @param gp Game Panel
   */
	public CardPile(GamePanel gp) {
		super();
		this.gp = gp;
	}

  /** Constructor
   *  @param gp Game Panel
   *  @param card the original card to be set
   */
	public CardPile(GamePanel gp, OBJ_Card card) {
		super();
		this.gp = gp;
		this.add(card);
	}

  /** Constructor
   *  @param gp Game Panel
   *  @param pile the original card pile to be set
   */
	public CardPile(GamePanel gp, CardPile pile) {
		super(pile);
		this.gp = gp;
	}

  /** Constructor
   *  @param gp Game Panel
   *  @param filename a file that contains card information
   */
	public CardPile(GamePanel gp, String filename) {
		this.gp = gp;
		
		// Create a scanner for the file
    Scanner sc = null;
    InputStream is = getClass().getResourceAsStream(filename);

		
		sc = new Scanner(new InputStreamReader(is));

    // Read the file and add cards to ArrayList 
    while (sc.hasNextLine()) {
      String line = sc.nextLine();
      OBJ_Card c = new OBJ_Card(gp, line);
      this.add(c);
    }
    sc.close();
	}
	
	/** Insertion Sort that sort card by rankNum */
	public CardPile sort() {
		CardPile unsorted = this;
    CardPile sorted = new CardPile(gp);

    while(unsorted.size() != 0) {
      OBJ_Card first = unsorted.poll();
      if (sorted.size() == 0) {
        sorted.add(first);
      } else {
        ListIterator<OBJ_Card> it = sorted.listIterator();
        while(it.hasNext()){
          OBJ_Card next = it.next();
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
