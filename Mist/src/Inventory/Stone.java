package Inventory;

import javax.imageio.ImageIO;

import Entity.Ooru;

public class Stone extends Item{
    private static final long serialVersionUID = 1L;

    public Stone() {
	
	try{
	    image = ImageIO.read(getClass().getResourceAsStream("/Objects/stone.png"));
	    
	}catch(Exception e) {
	    e.printStackTrace();
	}
	
	type = STONE;
	stackSize = 20;
	
	useable = false;
    }

    public String toString() {
	return "Stone";
    }



    public boolean use(Ooru ooru) {
	return false;
    }

}
