package Entity;

import javax.imageio.ImageIO;

public class Blink extends Spell {

    public Blink() {
	super();
	cd = 3000;
	castingTime = 0;
	damage = 0;
	range = 4; //Amount of tiles that Ooru will teleport to

	stringLength = 20;

	try {
	    icon = ImageIO.read(getClass().getResourceAsStream("/Objects/blink.png"));
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    public void use(Ooru ooru) {
	if(ooru.teleport()) {
	setCooldown();
	}
    }

    public String getInfoToString() {
	return "Teleports player to\na point located\nin facing direction";
    }

}
