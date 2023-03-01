import java.awt.Graphics2D;
import java.io.*;
import javax.imageio.ImageIO;

/**
 *  Class for tile settings
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class TileManager {
	
	/** GamePanel where manager should be created */
	GamePanel gp;
	
	/** Different types of tile */
	public Tile[] tile;
	
	/** 2D array that records the map */
	public int mapTileNum[][];
	
	/** Constructor
	 *  @param gp GamePanel where the manager is created
	 */
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tile = new Tile[100]; // Types of tile
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		initializeImage();
		loadMap("/src/settings/map.csv");
	}
	
	/** Initialize tile images from src folder */
	public void initializeImage() {

		// Placeholder
		setup(0, "10earth", false, false);
		setup(1, "10earth", false, false);
		setup(2, "10earth", false, false);
		setup(3, "10earth", false, false);
		setup(4, "10earth", false, false);
		setup(5, "10earth", false, false);
		setup(6, "10earth", false, false);
		setup(7, "10earth", false, false);
		setup(8, "10earth", false, false);
		setup(9, "10earth", false, false);
		
		// Tiles
		setup(10, "10earth", false, false);
		setup(11, "11grass", false, false);
		setup(12, "12grass", false, false);
		setup(13, "13grass", false, false);
		setup(14, "14road", false, false);
		setup(15, "15road", false, false);
		setup(16, "16sand", false, false);
		setup(17, "17sandRd", false, false);
		setup(18, "18sandRd", false, false);
		setup(19, "19sandRd", false, false);
		setup(20, "20sandRd", false, false);
		setup(21, "21sandRd", false, false);
		setup(22, "22sandRd", false, false);
		setup(23, "23sandRd", false, false);
		setup(24, "24sandRd", false, false);
		setup(25, "25sandRd", false, false);
		setup(26, "26sandRd", false, false);
		setup(27, "27sandRd", false, false);
		setup(28, "28sandRd", false, false);
		setup(29, "29sandRd", false, false);
		setup(30, "30tree", true, false);
		setup(31, "31wall_br", true, false);
		setup(32, "32wall_brBG", false, false);
		setup(33, "33wall_wood", true, false);
		setup(34, "34wall_woodBG", false, false);
		setup(35, "35water", false, true);
		setup(36, "36water", false, true);
		setup(37, "37water", true, false);
		setup(38, "38water", false, true);
		setup(39, "39water", false, true);
		setup(40, "40water", false, true);
		setup(41, "41water", false, true);
		setup(42, "42water", false, true);
		setup(43, "43water", false, true);
		setup(44, "44water", true, false);
		setup(45, "45water", false, true);
		setup(46, "46water", false, true);
		setup(47, "47water", false, true);
		setup(48, "48water", false, true);
			
	}

  /** Set up tile
	 *  @param index the index of tile in tile array
   *  @param imageName name of the tile in src folder
	 *  @param collision if the collision is on
	 *  @param water if the tile is water
	 */
	public void setup(int index, String imageName, boolean collision, boolean water) {
		UtilityTool uTool = new UtilityTool(); 
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/src/tiles/" + imageName + ".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
			tile[index].water = water;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** Load the Map
	 *  @param mapPath the file path where the map is recorded (in txt format)
	 */
	public void loadMap(String mapPath) {
		
		try {
			InputStream is = getClass().getResourceAsStream(mapPath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			for(int row = 0; row < gp.maxWorldRow; row++) {
				String line = br.readLine();
				String[] number = line.split(",");
				for(int col = 0; col < gp.maxWorldCol; col++) {
					int num = Integer.parseInt(number[col]);
					mapTileNum[col][row] = num;
				}
			}
			
			br.close();
			
		} catch(Exception e) {}
	}
	
	/** Draw the background within the camera range
	 *  @param g2 a Graphics2D object where tiles should be added
	 */
	public void draw(Graphics2D g2) {
		
		for(int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++) {
			for(int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++) {
				int tileNum = mapTileNum[worldCol][worldRow];
				
				int worldX = worldCol * gp.tileSize;
				int worldY = worldRow * gp.tileSize;
				
				if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
						worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
						worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
						worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
					
					int screenX = worldX - gp.player.worldX + gp.player.screenX;
					int screenY = worldY - gp.player.worldY + gp.player.screenY;
					
					g2.drawImage(tile[tileNum].image, screenX, screenY, null);
				}
			}
		}
	}
}
