import java.util.ArrayList;

/**
 *  Class to calculate the path of npc using A* search algorithm
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class PathFinder {

  /** Game Panel */
	GamePanel gp;

  /** 2d array class that stores all node representing the map */
	Node[][] node;

  /** Open list nodes */
	ArrayList<Node> openList = new ArrayList<>();

  /** Path nodes */
	public ArrayList<Node> pathList = new ArrayList<>();

  /** Node that store the information for calculation */
	Node startNode, goalNode, currentNode;

  /** If the goal node is reached */
	boolean goalReached = false;

  /** Step of passing nodes */
	int step = 0;

  /** Constructor */
	public PathFinder(GamePanel gp) {
		this.gp = gp;
		instantiateNodes();
	}

  /** Instantiate nodes in map */
	public void instantiateNodes() {
		node = new Node[gp.maxWorldCol][gp.maxWorldRow];
		
		for(int col = 0; col < gp.maxWorldCol; col++) {
			for(int row = 0; row < gp.maxWorldRow; row++) {
				node[col][row] = new Node(col, row);
			}
		}
	}

  /** Reset all ths nodes and lists that store the nodes */
	public void resetNodes() {
		for(int col = 0; col < gp.maxWorldCol; col++) {
			for(int row = 0; row < gp.maxWorldRow; row++) {
				node[col][row].open = false;
				node[col][row].checked = false;
				node[col][row].solid = false;
			}
		}
		
		// Reset lists
		openList.clear();
		pathList.clear();
		goalReached = false;
		step = 0;
	}

  /** Set nodes for the search
   *  @param startCol start Column position
   *  @param startRow start row position
   *  @param goalCol goal Column position
   *  @param goalRow goal row position
   *  @param entity entity who is walking the path (not in use)
   */
	public void setNodes(int startCol, int startRow, int goalCol, int goalRow, Entity entity) {
		resetNodes();
		
		// Set Start and Goal Node
		startNode = node[startCol][startRow];
		currentNode = startNode;
		goalNode = node[goalCol][goalRow];
		openList.add(currentNode);
		
		for(int col = 0; col < gp.maxWorldCol; col++) {
			for(int row = 0; row < gp.maxWorldRow; row++) {
				// Set Solid Node
				// Check tiles
				int tileNum = gp.tileM.mapTileNum[col][row];
				if(gp.tileM.tile[tileNum].collision) {
					node[col][row].solid = true;
				}
				
				// Set Cost
				getCost(node[col][row]);
			}
		}
	}

  /** Calculate the path cost from a node to start node and goal node
   *  @param node the node cost to be calculated
   */
	public void getCost(Node node) {
		// G cost
		int xDist = Math.abs(node.col - startNode.col);
		int yDist = Math.abs(node.row - startNode.row);
		node.gCost = xDist + yDist;
		
		//H cost
		xDist = Math.abs(node.col - goalNode.col);
		yDist = Math.abs(node.row - goalNode.row);
		node.hCost = xDist + yDist;
		
		//F cost
		node.fCost = node.gCost + node.hCost;
	}

  /** Search the path
   *  @return boolean if there is a path
   */
	public boolean search() {
		while(!goalReached && step < 500) {
			int col = currentNode.col;
			int row = currentNode.row;
			
			currentNode.checked = true;
			openList.remove(currentNode);
			
			if(row - 1 >= 0) {
				openNode(node[col][row-1]);
			}
			if(col - 1 >= 0) {
				openNode(node[col-1][row]);
			}
			if(row + 1 < gp.maxWorldRow) {
				openNode(node[col][row+1]);
			}
			if(col + 1 < gp.maxWorldCol) {
				openNode(node[col+1][row]);
			}
			
			int bestNodeIndex = 0;
			int bestNodefCost = 999;
			
			for (int i = 0; i < openList.size(); i++) {
				
				// Check if F cost is better
				if(openList.get(i).fCost < bestNodefCost) {
					bestNodeIndex = i;
					bestNodefCost = openList.get(i).fCost;
					
				// If F cost is equal, check G cost
				} else if (openList.get(i).fCost == bestNodefCost) {
					if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
			}
			
			// If no node in openList, end the loop
			if(openList.size() == 0) {
				break;
			}
			
			currentNode = openList.get(bestNodeIndex);
			if(currentNode == goalNode) {
				goalReached = true;
				trackThePath();
			}
			step++;
		}
		return goalReached;
	}

  /** Open a node after last node is checked 
   *  @param the node to be opened
   */
	public void openNode(Node node) {
		if((!node.open) && (!node.checked) && (!node.solid)) {
			node.open = true;
			node.parent = currentNode;
			openList.add(node);
		}
	}

  /** Trace the path */
	public void trackThePath() {
		Node current = goalNode;
		while(current != startNode) {
			
			pathList.add(0, current);
			current = current.parent;
			
		}
	}
}
