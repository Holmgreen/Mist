package NPCs;


import javax.imageio.ImageIO;

import Entity.Ooru;
import GameState.GameStateManager;
import Inventory.Item;
import Tiles.TileMap;

public class LabNPC extends NPC {
	private int stonesNeeded;
    
    public LabNPC(TileMap tm) {
	super(tm);
	try {
	    image = ImageIO.read(getClass().getResourceAsStream("/NPCs/labnpc.png"));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	sb = new StringBuilder();
	
	cwidth = 32;
	cheight = 64;
	
	//Waiting for his stones.
	waitingState = 1;
	stonesNeeded = 5;
    }
    
    //Can make this more efficient by not making a new string every time..
    protected void setStory() {
	if(GameStateManager.stateOfGame == 0) {
	    story = new String[6];
		story[0] = "Woaw! Who are you? \nI have not seen a person in here for years..";
		story[1] = "Wait a second.. There is a scar in your face...";
		story[2] = "YOU ARE THE CHOSEN ONE! OMGOMGOMG";
		story[3] = "I have been waiting for you my entire life.";
		story[4] = "As a reward for solving this labyrinth, \nI will give you this pair of\nmagic shoulders. Use them well.";
		story[5] = "Bring " + stonesNeeded + " stones to me, I need to\nbuild myself... something...";
		incrementState = true;
	}else if(GameStateManager.stateOfGame == 1){
	    story = new String[1];
	    story[0] = "With your magic powers I want you to bring\n" + stonesNeeded + " stones to me, I need to build... something...";
	    
	}else if(GameStateManager.stateOfGame == 2){
		story = new String[2];
	    story[0] = "OOooooooh... stones... I am so hungry\nNow I have to eat them...";
	    story[1] = "Yummy, now you have even more powers,\nuse them well.";
	}
	
	for (int i = 0; i < story.length; i++) {
	    sb.append(story[i]);
	}

    }

	public void requestQuestCompletion(Ooru p) {
		if(GameStateManager.stateOfGame == waitingState){
			if(p.getInventory().quantity(Item.STONE) >= stonesNeeded){
				GameStateManager.incrementTime();
				
				//Have to also take away stonesNeeded from the player inventory here as well.
				p.getInventory().searchAndDestroy(Item.STONE, stonesNeeded);
			}
		}
	}

}
