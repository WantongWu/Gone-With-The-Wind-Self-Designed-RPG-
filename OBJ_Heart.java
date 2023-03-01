import java.awt.image.BufferedImage;

/**
 *  Class for hearts
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class OBJ_Heart extends OBJ {
	
	/** Store image */
	public BufferedImage image1;

	/** Constructor */
	public OBJ_Heart(GamePanel gp) {
		super(gp);
		name = "Heart";
		image = setupImage("objects/heart_0", gp.tileSize, gp.tileSize);
		image1 = setupImage("objects/heart_100", gp.tileSize, gp.tileSize);
		
	}
}
