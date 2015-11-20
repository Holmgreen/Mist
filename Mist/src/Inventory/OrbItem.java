package Inventory;

import javax.imageio.ImageIO;

import Entity.Ooru;

public class OrbItem extends Item {

    private static final long serialVersionUID = 1L;

    public OrbItem() {
	try {
	    image = ImageIO.read(getClass().getResourceAsStream("/Objects/orb.png"));

	} catch (Exception e) {
	    e.printStackTrace();
	}

	type = ORB;
	stackSize = 5;

	useable = true;
    }
    
    public String toString() {
	return "Orb. Use: Increase player orb count";
    }


    
    public boolean use(Ooru ooru) {
	ooru.incrementOrbs();
	return true;
    }
}
