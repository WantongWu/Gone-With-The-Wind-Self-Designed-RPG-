/**
 *  Class to represent a location map with cost and path search inforation
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class Node {

  /** Parent node */
	Node parent;

  /** World column position */
	public int col;

  /** World row position */
	public int row;

  /** Costs information */
	int gCost, hCost, fCost;

  /** The status of node in a search */
	boolean solid, open, checked;

  /** Constructor */
	public Node(int col, int row) {
		this.col = col;
		this.row = row;
	}
}
