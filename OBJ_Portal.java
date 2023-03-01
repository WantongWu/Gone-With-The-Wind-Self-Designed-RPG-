/**
 *  Class for portals
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class OBJ_Portal extends OBJ {

	/** Constructor */
	public OBJ_Portal(GamePanel gp) {
		super(gp);
		name = "Portal";
		image = setupImage("objects/portal", gp.tileSize, gp.tileSize);
	}
}
