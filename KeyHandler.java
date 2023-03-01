import java.awt.event.*;

/**
 *  Class to handle the key input.
 *
 *  @author Wantong Wu, Yina Bao, Ray Qin
 *  @version Fall 2022
 */
public class KeyHandler implements KeyListener {

  /** Game Panel */
	GamePanel gp;

  /** If a key is pressed */
  public boolean upPressed, downPressed, leftPressed, rightPressed, interactPressed, enterPressed;

  /** Constructor */
  public KeyHandler(GamePanel gp) {
  	this.gp = gp;
  }
  
  /** Override */
  public void keyTyped(KeyEvent e) {}

  /** Override */
  public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();
    
    // TITLE STATE
    if(gp.state == GameState.TITLE) {
    	titleState(code);    	
    }
    
    // PLAY STATE
    if (gp.state == GameState.PLAY) {
    	playState(code);
    	
    // PAUSE STATE  
    } else if (gp.state == GameState.PAUSE) {
    	pauseState(code);
    
    // DIALOGUE STATE  
    } else if (gp.state == GameState.DIALOGUE) {
    	dialogueState(code);
    	
    // Character Menu
    } else if (gp.state == GameState.CHARACTER) {
    	characterState(code);
    	
    // Option Menu
    } else if (gp.state == GameState.OPTION) {
    	optionState(code);
    	
    // GAMEOVER 
    } else if (gp.state == GameState.GAMEOVER) {
    	gameOverState(code);
    
    // Battle
    } else if (gp.state == GameState.BATTLE) {
    	battleState(code);
    
    // Battle
  	} else if (gp.state == GameState.ACHIEVEMENT) {
    	achievementState(code);
    } else if (gp.state == GameState.DIALOGUEOP) {
    	dialogueOpState(code);
    }

  }

  /** Title state key handler
   *  @param code the integer representing the key input
   */
  public void titleState(int code) {
  	if(gp.ui.titleSubstate == 0) {
      traverseList(code, 3);
      
      if(code == KeyEvent.VK_ENTER) {
      	if(gp.ui.commandNum == 0) {
      		gp.ui.titleSubstate = 1;
      	} else if(gp.ui.commandNum == 1) {
      		gp.saveLoad.load();
      		gp.state = GameState.PLAY;
      	} else if(gp.ui.commandNum == 2) {
      		gp.ui.titleSubstate = 2;
      		gp.ui.achNum = 1;
      		gp.ui.commandNum = 0;
      	} else if(gp.ui.commandNum == 3) {
      		System.exit(0);
      	}
      }
  	} else if(gp.ui.titleSubstate == 1) {
  		traverseList(code, 1);
      if(code == KeyEvent.VK_ENTER) {
      	if(gp.ui.commandNum == 0) {
      		gp.state = GameState.PLAY;
      		gp.ui.titleSubstate = 0;
      		gp.resetGame(true);
      	} else if(gp.ui.commandNum == 1) {
      		gp.ui.titleSubstate = 0;
      		gp.ui.commandNum = 0;
      	}
      }
  	}
  }

  /** Play state key handler
   *  @param code the integer representing the key input
   */
  public void playState(int code) {
  	// Moving
    if(code == KeyEvent.VK_W) {
      upPressed = true;
    }
    if(code == KeyEvent.VK_S) {
      downPressed = true;
    }
    if(code == KeyEvent.VK_A) {
      leftPressed = true;
    }
    if(code == KeyEvent.VK_D) {
      rightPressed = true;
    }
    
    // Pause
    if(code == KeyEvent.VK_P) {
    	gp.state = GameState.PAUSE;
    }
    
    // Check Player Status
    if(code == KeyEvent.VK_Q) {
    	gp.state = GameState.CHARACTER;
    }
    
    if(code == KeyEvent.VK_E) {
    	interactPressed = true;
    }
    
    if(code == KeyEvent.VK_ESCAPE) {
    	gp.state = GameState.OPTION;
    }
  }

  /** Pause state key handler
   *  @param code the integer representing the key input
   */
  public void pauseState(int code) {
  	if(code == KeyEvent.VK_P) {
  		gp.state = GameState.PLAY;
    }
  }

  /** Achievement state key handler
   *  @param code the integer representing the key input
   */
  public void achievementState(int code) {
  	traverseList(code, 1);
  	if(code == KeyEvent.VK_ENTER) {
  		if(gp.ui.commandNum == 0) {
  			gp.ui.achPageNum++;
  			if(gp.ui.achPageNum > 6) {
  				gp.ui.achPageNum = 0;
  			}
  		} else if(gp.ui.commandNum == 1) {
  			if(gp.ui.achNum == 1) {
	  			gp.state = GameState.TITLE;
	  			gp.ui.titleSubstate = 0;
	  			gp.ui.commandNum = 2;
	  		} else if (gp.ui.achNum == 2) {
	  			gp.state = GameState.OPTION;
	  			gp.ui.optionSubstate = 0;
	  			gp.ui.commandNum = 1;
	  		}
  		}
  	}
  	
  }

  /** Dialogue state key handler
   *  @param code the integer representing the key input
   */
  public void dialogueState(int code) {
		if(code == KeyEvent.VK_ENTER) {
  		enterPressed = true;
  	}
  }

  /** Dialogue Option state key handler
   *  @param code the integer representing the key input
   */
  public void dialogueOpState(int code) {
		traverseList(code, 1);
		if(code == KeyEvent.VK_ENTER) {
      gp.ui.speaker.dialogueIndex = 0;
      boolean ifDial;
      if(gp.ui.commandNum == 0) {
        ifDial = ((NPC)gp.ui.speaker).dialogueOption1();
      } else {
        ifDial = ((NPC)gp.ui.speaker).dialogueOption2();
      }
      enterPressed = true;
      gp.ui.commandNum = 0;
      if (ifDial){
        gp.state = GameState.DIALOGUE;
        ((NPC)gp.ui.speaker).startDialogue(gp.ui.speaker, ((NPC)gp.ui.speaker).afterOpDialSet);
      } else {
        gp.state = GameState.PLAY;
      }
		}
  }

  /** Character state key handler
   *  @param code the integer representing the key input
   */
  public void characterState(int code) {
  	if(code == KeyEvent.VK_Q) {
    	gp.state = GameState.PLAY;
    }
  	if(code == KeyEvent.VK_W && gp.ui.curSlotRow != 0) {
    	gp.ui.curSlotRow--;
    }
  	if(code == KeyEvent.VK_A && gp.ui.curSlotCol != 0) {
  		gp.ui.curSlotCol--;
    }
  	if(code == KeyEvent.VK_S && gp.ui.curSlotRow != 3) {
  		gp.ui.curSlotRow++;
    }
  	if(code == KeyEvent.VK_D && gp.ui.curSlotCol != 4) {
    	gp.ui.curSlotCol++;
    }
  	if(code == KeyEvent.VK_ENTER) {
    	gp.player.selectItem();
    }
  }

  /** Option (substate of dialogue) state key handler
   *  @param code the integer representing the key input
   */
  public void optionState(int code) {
  	if(code == KeyEvent.VK_ESCAPE) {
  		gp.state = GameState.PLAY;
  	}
  	if(code == KeyEvent.VK_ENTER) {
  		if (gp.ui.optionSubstate == 0) {
  			switch(gp.ui.commandNum) {
  			case 0: 
  				gp.ui.optionSubstate = 1; // Controls
  				break;
  			case 1:
  				gp.ui.optionSubstate = 2; // Achievements
  				gp.ui.commandNum = 0;
  				gp.ui.achNum = 2;
  				break;
  			case 2:
  				gp.ui.optionSubstate = 3; // Quit
	    		gp.ui.commandNum = 0;
	    		break;
  			case 3:
  				gp.ui.optionSubstate = 4; // Quit Game
	    		gp.ui.commandNum = 0;
  				break;
  			case 4:
  				gp.state = GameState.PLAY; // BACK
	    		gp.ui.commandNum = 0;
	    		break;
  			}
  		} else if (gp.ui.optionSubstate == 1) {
  			gp.ui.optionSubstate = 0;
  		} else if (gp.ui.optionSubstate == 3) {
  			switch(gp.ui.commandNum) {
  			case 0: 
  				gp.ui.optionSubstate = 0; 
  				gp.state = GameState.TITLE;
  				gp.resetGame(false);
  				break;
  			case 1: gp.ui.optionSubstate = 0; gp.ui.commandNum = 2; break;
  			}
  		} else if (gp.ui.optionSubstate == 4) {
  			switch(gp.ui.commandNum) {
  			case 0: gp.resetGame(true); System.exit(0); break;
  			case 1: gp.ui.optionSubstate = 0; gp.ui.commandNum = 3; break;
  			}
  		}
  		
  	}
  	
  	int maxCommandNum = 0;
  	switch(gp.ui.optionSubstate) {
  	case 0: maxCommandNum = 4; break;
  	case 3: maxCommandNum = 1; break;
  	case 4: maxCommandNum = 1; break;
  	}
  	traverseList(code, maxCommandNum);
  }

  /** Gameover state key handler
   *  @param code the integer representing the key input
   */
  public void gameOverState(int code) {
  	traverseList(code, 1);
  	
  	if(code == KeyEvent.VK_ENTER) {
			gp.state = GameState.TITLE;
			gp.resetGame(true);
			gp.ui.ending = null;
  	}
  }

  /** Battle state key handler
   *  @param code the integer representing the key input
   */
  public void battleState(int code) {
  	if(gp.ui.battleSubstate == 0) {
  		traverseList(code, 2);
  		if(code == KeyEvent.VK_ENTER) {
  			gp.player.battle();
  			gp.ui.commandNum = 0;
  			if(((NPC)gp.ui.speaker).health <= 0 || gp.player.health <= 0) {
  				gp.ui.battleSubstate = 2;
  			} else {
  				gp.ui.battleSubstate = 1;
  			}
  		}
  	} else if (gp.ui.battleSubstate == 1) {
  		if(code == KeyEvent.VK_ENTER) {
  			((NPC)gp.ui.speaker).battle();
  			if(gp.player.health <= 0) {
  				gp.ui.battleSubstate = 2;
  			} else {
  				gp.ui.battleSubstate = 0;
  			}
  		}
  	} else if (gp.ui.battleSubstate == 2) {
  		if(code == KeyEvent.VK_ENTER) {
        if (gp.player.health <= 0) {gp.player.life--;}
  			gp.ui.battleSubstate = 3;
  			gp.ui.battleInfo.clear();
        ((NPC)gp.ui.speaker).afterBattle();
  			((NPC)gp.ui.speaker).health = ((NPC)gp.ui.speaker).maxHealth;
  			gp.player.health = gp.player.maxHealth;
        gp.ui.battleSubstate = 0;
  		}
  	} else if (gp.ui.battleSubstate == 3){
      dialogueState(code);
    }
  }

  /** Travese a vertical list
   *  @param code the integer representing the key input
   *  @param maxNum the number of elements in the vertical list minus 1
   */
  public void traverseList(int code, int maxNum) {
  	if(code == KeyEvent.VK_W) {
  		gp.ui.commandNum--;
  		if (gp.ui.commandNum < 0) {
  			gp.ui.commandNum = maxNum;
  		}
  	}
  	if(code == KeyEvent.VK_S) {
  		gp.ui.commandNum++;
  		if (gp.ui.commandNum > maxNum) {
  			gp.ui.commandNum = 0;
  		}
  	}
  }
  
  @Override
  public void keyReleased(KeyEvent e) {
    int code = e.getKeyCode();

    if(code == KeyEvent.VK_W) {
      upPressed = false;
    }
    if(code == KeyEvent.VK_S) {
      downPressed = false;
    }
    if(code == KeyEvent.VK_A) {
      leftPressed = false;
    }
    if(code == KeyEvent.VK_D) {
      rightPressed = false;
    }
  }
}