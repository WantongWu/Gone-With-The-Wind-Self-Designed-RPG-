import java.awt.image.BufferedImage;

/**
 *  Class for tiles
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class Tile {
	
	/** Image of a tile */
	public BufferedImage image;
	
	/** Define if players can walk on a tile */
	public boolean collision = false;

  /** If the tile is water */
	public boolean water = false;
}
