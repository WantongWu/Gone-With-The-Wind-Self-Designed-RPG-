import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ArrayDeque;

/**
 *  Class to draw user interface
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class UI {

	/** GamePanel that controls the game */
	GamePanel gp;

  /** Graphics2D of the game */
	Graphics2D g2;
	
	/** Fonts */
	Font purisa;

  /** Player life image */
	BufferedImage heart100, heart0;

  /** include message to be displayed */
	ArrayList<String> message = new ArrayList<>();

  /** Message counter */
	ArrayList<Integer> messageCounter = new ArrayList<>();
	
	/** If the game is over */
	public boolean gameEnd = false;
	
	/** Displaying text */	
	public String curDialogue = "";

  /** Command Number */
	public int commandNum = 0;

  /** title substate */
	public int titleSubstate = 0; // 0 - Main Menu ; 1 - Control Intro

  /** option menu substate */
	public int optionSubstate = 0;

  /** battle substate */
	public int battleSubstate = 0; // 0 - My turn; 1 - Enemy turn; 2 - End Screen

  /** achievement number */
	public int achNum = 1;

  /** achievement page number */
	public int achPageNum = 0;

  /** current column in inventory */
	public int curSlotCol = 0;

  /** current row in inventory */
	public int curSlotRow = 0;

  /** pass data of an entity */
	public Entity speaker;

  /** Stores rolling battle information */
	public ArrayDeque<String> battleInfo = new ArrayDeque<String>();

  /** current character to be added */
	int charIndex = 0;

  /** store string to create rolling effect */
	String combinedText = "";

  /** string to be displayed in the dialogue Subwindow */
	public String dialSubwindow = "";

  /** Ending */
	public Achievement ending;
	
	
	/** Constructor 
	 *  @param gp GamePanel that controls the game
	 */
	public UI(GamePanel gp) {
		this.gp = gp;
		
		// Initialize font
		try {
			InputStream is = getClass().getResourceAsStream("/src/fonts/Purisa_Bold.ttf");
			purisa = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		// HUD object
		OBJ_Heart heart = new OBJ_Heart(gp);
		heart0 = heart.image;
		heart100 = heart.image1;
	}

  /** Add message 
	 *  @param text message to be added
	 */
	public void addMessage(String text) {
		message.add(text);
		messageCounter.add(0);
	}
	
	/** Draw the message and UI
	 *  @param g2 Graphics2D where UI is drawn
	 */
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		g2.setFont(purisa);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);
		
		// GAME STATE
		if(gp.state == GameState.TITLE) {
			drawTitle();
		} else if(gp.state == GameState.PLAY) {
			drawPlayerLife();
			drawMessage();
			// drawCurrentPosition();
		} else if (gp.state == GameState.PAUSE) {
			drawPause();
		} else if (gp.state == GameState.DIALOGUE) {
			drawPlayerLife();
			drawDialogue();
		} else if (gp.state == GameState.CHARACTER) {
			drawCharacterState();
			drawInventory();
		} else if (gp.state == GameState.OPTION) {
			drawOption();
		} else if (gp.state == GameState.GAMEOVER) {
			drawGameOver();
		} else if (gp.state == GameState.BATTLE) {
      if (battleSubstate == 3){
        ((Figure)speaker).afterBattle();
        drawDialogue();
      } else {
			  drawBattle();
  			drawEnemyLife();
      }
		} else if (gp.state == GameState.ACHIEVEMENT) {
			drawAchievement();
		} else if (gp.state == GameState.DIALOGUEOP) {
      drawDialogue();
			drawDialogueOp();
		}

	}

  /** Draw the player's life on the screen */
	public void drawPlayerLife() {
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		
		// Draw max life
		int i = 0;
		while(i < gp.player.maxLife) {
			g2.drawImage(heart0, x, y, null);
			i++;
			x += gp.tileSize;
		}
		
		// Reset
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		
		// Draw Current Life
		while(i < gp.player.life) {
			g2.drawImage(heart100, x, y, null);
			i++;
			x += gp.tileSize;
		}
	}

  /** Draw the enemy's life on the screen*/
	public void drawEnemyLife() {
		if(speaker instanceof NPC) {
			NPC enemy = (NPC)speaker;
			double oneScale = (double)gp.tileSize*6/enemy.maxHealth;
			double hpValue = oneScale*enemy.health;
			
			int x = gp.screenWidth/2 - gp.tileSize*3;
			int y = gp.tileSize*2;
			
			g2.setColor(new Color (200, 200, 200));
			g2.fillRect(x-1, y-1, gp.tileSize*6+2, 22);
			
			g2.setColor(new Color(255, 0, 30));
			g2.fillRect(x, y, (int)hpValue, 20);
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
			g2.setColor(Color.white);
			g2.drawString(enemy.name, x+4, y-10);
		}
	}

  /** Draw the position (in world row and column) of the player */
	public void drawCurrentPosition() {
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(25F));
		int x = gp.tileSize;
		int y = gp.tileSize*10;
		int col = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
		int row = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
		
		g2.drawString("Row: " + row, x, y);
		g2.drawString("Col: " + col, x, y+30);
	}

  /** Draw the rolling message on the screen */
	public void drawMessage() {
		int x = gp.tileSize;
		int y = gp.tileSize * 4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));
		
		for(int i = 0; i < message.size(); i++) {
			
			if(message.get(i) !=null) {
				g2.setColor(Color.black);
				g2.drawString(message.get(i), x+2, y+2);
				g2.setColor(Color.white);
				g2.drawString(message.get(i), x, y);
				
				messageCounter.set(i, messageCounter.get(i) + 1);
				y += 50;
				
				if(messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
			
		}
	}

  /** Draw the title of the game */
	public void drawTitle() {
		
		if(titleSubstate == 0) {
			// Background Color
			g2.setColor(Color.black);
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));
			String text = "Gone With the Wind";
			int x = getXforCenteredText(text);
			int y = gp.tileSize*3;
			
			// Shadow
			g2.setColor(new Color(125,125,125,200));
			g2.drawString(text, x+5, y+5);
			
			// Text Color
			g2.setColor(new Color(178,102,255));
			g2.drawString(text, x, y);
			
			// Player Image
			x = gp.screenWidth/2 - gp.tileSize;
			y += gp.tileSize*1.5;
			g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
			
			// Menu
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
			
			String[] menu = {"NEW GAME", "LOAD GAME", "ACHIEVEMENTS", "QUIT"};
			double[] dist = {3.5, 1, 1, 1};
			for(int i = 0; i < menu.length; i++) {
				text = menu[i];
				x = getXforCenteredText(text);
				y += gp.tileSize * dist[i];
				g2.drawString(text, x, y);
				if(commandNum == i) {
					g2.drawString(">", x-gp.tileSize, y);
				}
			}
		} else if (titleSubstate == 1) {
			// Background Color
			g2.setColor(Color.white);
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
					
			g2.setColor(Color.black);
			g2.setFont(g2.getFont().deriveFont(30F));
			
			String text = "";
			int x = 0;
			int y = 0;
			
			String[] menu = {"CONTROLS", "W - Move Up", "S - Move Down", "A - Move Left", 
					"D - Move Right", "E - Interact", "Q - Check Status", 
					"Enter - Proceed", "Esc - Options", "Start Adventure", "Back"};
			double[] dist = {1.5, 1.5, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 1.5, 1};
			for(int i = 0; i < menu.length; i++) {
				text = menu[i];
				x = getXforCenteredText(text);
				y += gp.tileSize * dist[i];
				g2.drawString(text, x, y);
				if(commandNum == i-9) {
					g2.drawString(">", x-gp.tileSize, y);
				}
			}
		} else if (titleSubstate == 2) {
			gp.state = GameState.ACHIEVEMENT;
		}
		
	}

  /** Draw pause screen */
	public void drawPause() {
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80));
		String text = "PAUSED";
		
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
		
	}

  /** Draw achievement screen */
	public void drawAchievement() {
		g2.setColor(Color.black);
		
		// Background
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		// Frames
		int frame1X = (int)(gp.tileSize*1.5);
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize*4;
		final int frameHeight = gp.tileSize*6;
		final int frameDist = 20;
		
		g2.setColor(new Color(100, 100, 100));
		g2.setStroke(new BasicStroke(3));
		for(int i = 0; i < 3; i++) {
			g2.drawRoundRect(frame1X, frameY, frameWidth, frameHeight, 25, 25);
			frame1X += frameWidth + frameDist;
		}
		
		frame1X = (int)(gp.tileSize*1.5);
		drawAchivementText(frame1X, frameY, frameWidth, frameDist);
		
		// Next Page
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(30f));
		String text = "Next Page";
		int textX = getXforCenteredText(text);
		int textY = (int)(gp.tileSize*9);
		g2.drawString(text, textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-30, textY);
		}
		
		// Back
		text = "Back";
		textX = getXforCenteredText(text);
		textY += (int)(gp.tileSize*1.5);
		g2.drawString(text, textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-30, textY);
		}
	}

  /** Draw text in achievement screen
   *  @param frame1X the first frame x coordinate
   *  @param frameY the first frame y coordinate
   *  @param frameWidth the frame width
   *  @param frameDist distance between frames
   */
	public void drawAchivementText(int frame1X, int frameY, int frameWidth, int frameDist) {
		int num = achPageNum * 3;
		int titleX = frame1X + 20;
		int titleY = frameY + gp.tileSize;
		for (int i = 0; i < 3; i++) {
			Achievement achi = gp.achiPool.get(num);
			
			String title = achi.name;
			g2.setFont(g2.getFont().deriveFont(20f));
			if(!achi.unlocked) {
				g2.setColor(new Color(150, 150, 150));
				if (title.contains("@")) {
					String[] tStr = title.split("@");
					g2.drawString(tStr[0], titleX, titleY);
					g2.drawString(tStr[1], titleX, titleY + 30);
				} else {
					g2.drawString(title, titleX, titleY);
				}	
			} else {
				int desX = titleX;
				int desY = titleY + gp.tileSize*2;
				String des = achi.description;
				g2.setColor(Color.white);
				if (title.contains("@")) {
					String[] tStr = title.split("@");
					g2.drawString(tStr[0], titleX, titleY);
					g2.drawString(tStr[1], titleX, titleY + 30);
				} else {
					g2.drawString(title, titleX, titleY);
				}	
				
				for(String line: des.split("@")) {
					g2.setFont(g2.getFont().deriveFont(15f));
					g2.drawString(line, desX, desY);
					desY += 30;
				}
			}
			num++;
			titleX = titleX + frameWidth + frameDist;
		}
		
	}

  /** Draw dialongue screen*/
	public void drawDialogue() {
		
		// Window
		int x = gp.tileSize *2;
		int y = gp.tileSize / 2;
		int width = gp.screenWidth - 2 * x;
		int height = gp.tileSize * 4;
		
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
		x += gp.tileSize;
		y += gp.tileSize;
    if (gp.state == GameState.BATTLE){
      ((NPC)gp.ui.speaker).afterBattle();
    }
		if(speaker.dialogues[speaker.dialogueSet][speaker.dialogueIndex] != null) {
			if(speaker.dialogues[speaker.dialogueSet][speaker.dialogueIndex].equals("FIGHT")) {
				((NPC)speaker).startBattle(speaker);
				speaker.dialogueIndex = 0;
			} else if (speaker.dialogues[speaker.dialogueSet][speaker.dialogueIndex].equals("LEAVE")) {
				((NPC)speaker).disappear();
				speaker.dialogueIndex = 0;
        gp.state = GameState.PLAY;
			} else if (speaker.dialogues[speaker.dialogueSet][speaker.dialogueIndex].equals("OPTION")) {
        gp.state = GameState.DIALOGUEOP;
      } else if (speaker.dialogues[speaker.dialogueSet][speaker.dialogueIndex].equals("END")) {
        gp.state = GameState.GAMEOVER;
        gp.ui.commandNum = -1;
        gp.ui.gameEnd = false;
      } else {
				char[] characters = speaker.dialogues[speaker.dialogueSet][speaker.dialogueIndex].toCharArray();
				
				if(charIndex < characters.length) {
					String s = String.valueOf(characters[charIndex]);
					combinedText = combinedText + s;
					curDialogue = combinedText;
					charIndex++;
				}
				
				if(gp.keyH.enterPressed && (gp.state == GameState.DIALOGUE || gp.state == GameState.BATTLE)) {
					charIndex = 0;
					combinedText = "";
					speaker.dialogueIndex++;
					gp.keyH.enterPressed = false;
				}
			}
		} else {
			speaker.dialogueIndex = 0;
			
			//TODO: ADD THING HERE
			if(gp.state == GameState.DIALOGUE || gp.state == GameState.BATTLE) {
				gp.state = GameState.PLAY;
			}
		}
		
		for(String line: curDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 30;
		}
	}

  /** Draw the dialogue option on the screen */
	public void drawDialogueOp() {
		
		int opX = gp.tileSize *2 + gp.tileSize * 7;
		int opY = gp.tileSize / 2 + gp.tileSize * 3;
		int opWidth = gp.tileSize * 6;
		int opHeight = gp.tileSize * 2;
		
		drawSubWindow(opX, opY, opWidth, opHeight);
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(20f));
		String[] line = dialSubwindow.split("@");
		g2.drawString(line[0], opX + 40, opY + 40);
		g2.drawString(line[1], opX + 40, opY + 70);
		if (commandNum == 0) {
			g2.drawString(">", opX + 20, opY + 40);
		} else if (commandNum == 1) {
			g2.drawString(">", opX + 20, opY + 70);
		}
		
		if(gp.keyH.enterPressed && gp.state == GameState.DIALOGUEOP) {
			gp.keyH.enterPressed = false;
		}
	}

  /** Draw the character status on the screen */
	public void drawCharacterState() {
		
		// Create a frame
		final int frameX = gp.tileSize;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize * 5;
		final int frameHeight = gp.tileSize * 10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		// Display text
		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		int lineHeight = 115;
		
		String[] attributes = {"Life", "Health", "Strength", "Coin"};
		for (int i = 0; i < attributes.length; i++) {
			g2.drawString(attributes[i], textX, textY);
			textY += lineHeight;
		}
		
		// Display value
		int tailX = frameX + frameWidth - 30;
		textY = frameY + gp.tileSize + 40;
		String value = "";
		int[] values = {gp.player.life, gp.player.health, gp.player.strength, gp.player.coin};
		for (int i = 0; i < values.length; i++) {
			value = String.valueOf(values[i]);
			if(i == 0) {
				value = value + "/" + gp.player.maxLife; 
			}
			textX = getXforAlignRightText(value, tailX);
			g2.drawString(value, textX, textY);
			textY += lineHeight;
		}
		
	}

  /** Draw the player's inventory on the screen */
	public void drawInventory() {
		
		// Frame
	 int frameX = gp.tileSize * 9;
	 int frameY = gp.tileSize;
	 int frameWidth = gp.tileSize * 6;
	 int frameHeight = gp.tileSize * 5;
	 drawSubWindow(frameX, frameY, frameWidth, frameHeight);
	 
	 // Slot
	 final int xStart = frameX + 20;
	 final int yStart = frameY + 20;
	 int slotX = xStart;
	 int slotY = yStart;
	 int slotSize = gp.tileSize + 3;
	 
	 // Draw items
	 for(int i = 0; i < gp.player.inventory.size(); i++) {
		 
		 // draw item image
		 OBJ item = gp.player.inventory.get(i);
		 g2.drawImage(item.image, slotX, slotY, null);
		 
		 // draw item amount (if any)
		 if(item.amount > 1 || (item.name.contains("Card") && gp.player.cardPile.size()>1)) {
			 g2.setFont(g2.getFont().deriveFont(25f));
			 int amountX;
			 int amountY;
			 String s = "";
			 if(item.name.contains("Card")) {
				 s = s + gp.player.cardPile.size();
			 } else {
				 s = s + item.amount;
			 }
			 amountX = getXforAlignRightText(s, slotX + gp.tileSize);
			 amountY = slotY + gp.tileSize;
			 
			 g2.setColor(new Color(60,60,60));
			 g2.drawString(s, amountX, amountY);
			 
			 g2.setColor(Color.white);
			 g2.drawString(s, amountX-2,amountY-2);
		 }
		 slotX += slotSize;
		 
		 if (i == 4 || i == 9 || i == 14) {
			 slotX = xStart;
			 slotY += slotSize;
		 }
	 }
	 
	 // Cursor
	 int cursorX = xStart + slotSize * curSlotCol;
	 int cursorY = yStart + slotSize * curSlotRow;
	 int cursorWidth = gp.tileSize;
	 int cursorHeight = gp.tileSize;
	 
	 g2.setColor(Color.white);
	 g2.setStroke(new BasicStroke(3));
	 g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
		 
	 
	 // Description
	 int dFrameY = frameY + frameHeight;
	 int dFrameHeight = gp.tileSize * 4;
	 
	 int textX = frameX + 15;
	 int textY = dFrameY + 30;
	 g2.setFont(g2.getFont().deriveFont(15F));
	 int itemIndex = getItemIndex();
	 if (itemIndex < gp.player.inventory.size()) {
		 drawSubWindow(frameX, dFrameY, frameWidth, dFrameHeight);
		 for (String line: gp.player.inventory.get(itemIndex).description.split("@")) {
			 g2.drawString(line, textX, textY);
			 textY += 19;
		 }
	 }
	}

  /**Draw option screen*/
	public void drawOption() {
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(30F));
		
		// Sub window
		int frameX = gp.tileSize * 4; 
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize*8;
		int frameHeight = gp.tileSize * 10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		switch(optionSubstate) {
		case 0: option_Main(frameX, frameY); break;
		case 1: option_Control(frameX, frameY); break;
		case 2: gp.state = GameState.ACHIEVEMENT; break;
		case 3: option_QuitConfirmation(frameX, frameY); break;
		case 4: option_QuitConfirmation(frameX, frameY); break;
		}
	}

  /** Draw the option menu (substate 0) */
	public void option_Main(int frameX, int frameY) {
		int textX;
		int textY = frameY;
		
		//TITLE
		String[] text = {"Options", "Controls", "Achievements", "Quit", "End Game", "Back"};
		int[] dist = {2, 2, 1, 2, 1, 1};
		
		for(int i = 0; i < text.length; i++) {
			textX = getXforCenteredText(text[i]);
			textY += gp.tileSize * dist[i];
			g2.drawString(text[i], textX, textY);
			
			if (commandNum == i-1) {
				g2.drawString(">", textX - 25, textY);
			}
		}
		
	}

  /** Draw the option menu - control (substate 1) */
	public void option_Control(int frameX, int frameY) {
		int x = 0;
		int y = frameY;
		
		g2.setFont(g2.getFont().deriveFont(28F));
		
		String text = "Control";
		x = getXforCenteredText(text);
		y += gp.tileSize*1.5;
		g2.drawString(text, x, y);
		
		g2.setFont(g2.getFont().deriveFont(23F));
		String[] menu = {"Move", "Confirm", "Proceed", "Interact", 
				"Check Status", "Pause", "Options"};
		double[] dist = {1.5, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8};
		x = frameX + gp.tileSize;
		for(int i = 0; i < menu.length; i++) {
			y += gp.tileSize * dist[i];
			g2.drawString(menu[i], x, y);
		}
		
		x = (int) (frameX + gp.tileSize*5.5);
		y = (int) (frameY + gp.tileSize*1.5);
		String[] keys = {"WASD", "ENTER", "ENTER", "E", "Q", "P", "ESC"};
		for(int i = 0; i < keys.length; i++) {
			y += gp.tileSize * dist[i];
			g2.drawString(keys[i], x, y);
		}
		
		g2.setFont(g2.getFont().deriveFont(28F));
		text = "Back";
		x = getXforCenteredText(text);
		y += gp.tileSize*1.5;
		g2.drawString("Back", x, y);
		g2.drawString(">", x-15, y);
	}

  /** Draw the option menu - quit confirmation */
	public void option_QuitConfirmation(int frameX, int frameY) {
		int x;
		int y = frameY + gp.tileSize*3;
		if(optionSubstate == 3) {
			curDialogue = "Quit the game \nand \nreturn to the \ntitle screen?";
		} else if (optionSubstate == 4) {
			curDialogue = "Quit the game \nand \nreturn to the \ndesktop?";
		}
		
		for(String line: curDialogue.split("\n")) {
			x = getXforCenteredText(line);
			g2.drawString(line, x, y);
			y += 40;
		}
		
		// YES
		String text = "Yes";
		x = getXforCenteredText(text);
		y += gp.tileSize*1.5;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-25, y);
		}
		
		// No
		text = "No";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-25, y);
		}
	}

  /**Draw game over screen*/
	public void drawGameOver() {
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
		
		// Title
		text = ending.name.replace("-@", "");
		
		// Shadow
		g2.setColor(Color.black);
		x = getXforCenteredText(text);
		y = gp.tileSize*4;
		g2.drawString(text, x, y);
		
		// Text
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		
		// Description
		text = ending.endingD;
		y += gp.tileSize*1.7;
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
		g2.setColor(Color.white);
		for (String line : text.split("@")) {
			x = getXforCenteredText(line);
			g2.drawString(line, x, y);
			y += 30;
		}
		
		// Back to title screen
		g2.setFont(g2.getFont().deriveFont(35f));
		text = "Quit";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		g2.drawString(">", x-40, y);
		
	}

  /** Draw the battle */
	public void drawBattle() {
      NPC enemy = (NPC)speaker;
		  // Background
  		g2.setColor(Color.black);
  		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
  		
  		// Sub window - Battle Info
  		int frameX = gp.tileSize * 4; 
  		int frameY = gp.tileSize;
  		int frameWidth = gp.tileSize*8;
  		int frameHeight = gp.tileSize * 10;
  		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
  		
  		// MY TURN
  		if(battleSubstate == 0) { 
  			battle_MyTurn(frameX, frameY, frameWidth);
  		// ENEMY TURN
  		} else if (battleSubstate == 1) { 
  			battle_Proceed(frameX, frameY, frameWidth);
  		}
  		
  		// DRAW BATTLE INFO
  		g2.setFont(g2.getFont().deriveFont(20f));
  		g2.setColor(Color.white);
  		int textX = frameX + gp.tileSize;
  		int textY = (int) (gp.tileSize*3.5);
  		if(battleInfo.size()>4) {battleInfo.remove();}
  		Iterator<String> it = battleInfo.iterator();
  		while(it.hasNext()) {
  			String text = it.next();
  			for(String i: text.split("\n")) {
  				g2.drawString(i, textX, textY);
  				textY += 25;
  			}
  			textY += 20;
  		}
  		
  		// END SCREEN
  		if(battleSubstate == 2) {
  			drawSubWindow(frameX, frameY + gp.tileSize*3, frameWidth, frameHeight - gp.tileSize*6);
  			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70F));
  			String t = "";
  			if(enemy.health <= 0) {t = "Victory"; enemy.ifWin = false;}
  			else if (gp.player.health <= 0) {t = "Defeat"; enemy.ifWin = true;}
  			g2.drawString(t, getXforCenteredText(t), (int)(frameY + gp.tileSize*5.5));
  			battle_Proceed(frameX, frameY, frameWidth);
  		}
  		
  		// DRAW TOP CARD INFO
  		int dFrameX = frameX - 4*gp.tileSize;
  		int dFrameY = frameY + frameHeight - gp.tileSize * 4;
  		int dFrameWidth = 5* gp.tileSize;
  		int dFrameHeight = gp.tileSize * 4;
  		drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
  		g2.setFont(g2.getFont().deriveFont(15F));
  		g2.setColor(Color.white);
  		g2.drawString("Your Top Card", dFrameX+gp.tileSize, dFrameY - 16);
  		textX = dFrameX + 15;
  		textY = dFrameY + gp.tileSize;
  		if(!gp.player.cardPile.isEmpty()) {
  			for (String line: gp.player.cardPile.peekLast().description.split("@")) {
  				g2.drawString(line, textX, textY);
  				textY += 19;
  			}
  		} else {
  			g2.drawString("You don't have any", textX, textY);
  			g2.drawString("card", textX, textY+19);
  		}
	}

  /** Draw the battle when it's player's turn 
   *  @param frameX the battle frame x coordinate
   *  @param frameY the battle frame y coordinate
   *  @param frameWidth the battle frame width
   */
	public void battle_MyTurn(int frameX, int frameY, int frameWidth) {
		int textX = frameX + frameWidth + gp.tileSize;
		int textY = frameY + gp.tileSize*5;
		g2.setFont(g2.getFont().deriveFont(20f));
		g2.setColor(Color.white);
		
		String[] text = {"Attack", "Use Card", "Sort Card"};
		double[] dist = {3.4, 0.7, 0.7};
		
		for(int i = 0; i < text.length; i++) {
			textY += (int)(gp.tileSize * dist[i]);
			g2.drawString(text[i], textX, textY);
			
			if (commandNum == i) {
				g2.drawString(">", textX - 25, textY);
			}
		}
	}

  /** Draw the battle (proceed and cursor) when it's player's turn or gameover
   *  @param frameX the battle frame x coordinate
   *  @param frameY the battle frame y coordinate
   *  @param frameWidth the battle frame width
   */
	public void battle_Proceed(int frameX, int frameY, int frameWidth) {
		int textX = frameX + frameWidth + gp.tileSize;
		int textY = frameY + gp.tileSize*9;
		g2.setFont(g2.getFont().deriveFont(20f));
		g2.setColor(Color.white);

		g2.drawString("Proceed", textX, textY);
		g2.drawString(">", textX - 25, textY);
	}

  /** Get the item index in inventory */
	public int getItemIndex() {
		return curSlotCol + curSlotRow * 5;
	}

  /** Draw a subwindow on the screen
   *  @param x the x coordinate of the frame
   *  @param y the y coordinate of the frame
   *  @param width width of subwindow
   *  @param height height of subwindow
   */
	public void drawSubWindow(int x, int y, int width, int height) {
		
		g2.setColor(new Color(0,0,0,200));
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
	}

  /** Get x for a centered text
   *  @param text the text to be searched
   *  @return int x coordinate of the text
   */
	public int getXforCenteredText(String text) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return gp.screenWidth/2 - length/2;
	}

  /** Get x for a align right text
   *  @param text the text to be searched
   *  @param tailX the x coordinate to be aligned  
   */
	public int getXforAlignRightText(String text, int tailX) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return tailX - length;
	}
	
}
