import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *  Class that includes miscellaneous methods
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class UtilityTool {

  /** Scale and preload image 
	 *  @param original the original Buffered image
   *  @param width the target width of the image
	 *  @param height the target height of the image
	 *  @param BufferedImage the returned scaled image
	 */
	public BufferedImage scaleImage(BufferedImage original, int width, int height) {
		
		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(original, 0, 0, width, height, null);
		g2.dispose();
		
		return scaledImage;
	}
	
	
}
