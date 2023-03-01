import java.awt.Rectangle;

/**
 *  Class to represent eventRect (implements Rectangle)
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class EventRect extends Rectangle {

  /** Default x and default Y position */
	int defaultX, defaultY;
	
	/** If the event happens yet */
	boolean done = false; // For one-time only event

  /** Constructor */
	public EventRect(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;		
	}
	
}
