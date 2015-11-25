package Entity;

import javax.imageio.ImageIO;

import Audio.JukeBox;

public class Heal extends Spell {

	public Heal() {
		// Must init() here.
		super();
		cd = 1;
		castingTime = 1000;
		stringLength = 21;
		orbCost = 1;
		init();

		try {
			icon = ImageIO.read(getClass().getResourceAsStream(
					"/Objects/shadow.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void use(Ooru ooru) {
		if (ooru.getOrbs() >= orbCost) {
			setCooldown();
			ooru.incrementHealth();
			ooru.decrementOrbs();
			
			// Change this into healing sound.
			JukeBox.play("ooruwhitedmg");
		}
	}

	public int getRange() {
		return range;
	}

	public String getInfoToString() {
		return "Heals Ooru one life.";
	}

}
