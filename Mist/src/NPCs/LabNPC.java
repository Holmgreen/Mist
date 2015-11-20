package NPCs;


import javax.imageio.ImageIO;

import Tiles.TileMap;

public class LabNPC extends NPC {
    
    public LabNPC(TileMap tm) {
	super(tm);
	try {
	    image = ImageIO.read(getClass().getResourceAsStream("/NPCs/labnpc.png"));
	    if(image == null) {
		System.out.println("imagenull");
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	sb = new StringBuilder();
	
	cwidth = 32;
	cheight = 64;
	
    }

    protected void setStory(int state) {
	if(state == 0) {
	    story = new String[5];
		story[0] = "Woaw! Who are you? \nI have not seen a person in here for years..";
		story[1] = "Wait a second.. There is a scar in your face...";
		story[2] = "YOU ARE THE CHOSEN ONE! OMGOMGOMG";
		story[3] = "I have been waiting for you my entire life.";
		story[4] = "As a reward for solving this labyrinth, \nI will give you these pair of\n magic shoulders. Use them well.";
		
		incrementState = true;
	}else {
	    story = new String[1];
	    story[0] = "I believe in you, young one.";
	}
	
	for (int i = 0; i < story.length; i++) {
	    sb.append(story[i]);
	}

    }

}
