package Entity;

import javax.imageio.ImageIO;

public class Blink extends Spell {
	public static final int RANGE = 4;

	public Blink() {
		super();

		cd = 10000;
		castingTime = 0;
		damage = 0;
		orbCost = 1;
		range = 4; // Amount of tiles that Ooru will teleport through.
		key = "F";

		stringLength = 20;
		init();
		try {
			icon = ImageIO.read(getClass().getResourceAsStream(
					"/Objects/blink.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void use(Ooru ooru) {
		if (ooru.teleport()) {
			setCooldown();
		}
	}

	public String getInfoToString() {
		return "Teleports player to\na point located\nin facing direction";
	}

}
