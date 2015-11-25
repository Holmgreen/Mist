package Entity;

import javax.imageio.ImageIO;

import Audio.JukeBox;

public class Shadow extends Spell{

    public Shadow() {
    //Must init() here.
    super();
    cd = 800;
	castingTime = 100;
	damage = 10;
	stringLength = 21;
	range = 80;
	isAoe = true;
	orbCost = 2;
	init();
	
	try {
	    icon = ImageIO.read(getClass().getResourceAsStream("/Objects/shadow.png"));
	}catch(Exception e) {e.printStackTrace();}
	
    }

    public void use(Ooru ooru) {
    	if(ooru.getOrbs() >= orbCost){
	setCooldown();
	ooru.doAoeDamage(damage);
	ooru.setAttacking(true);
	ooru.decrementOrbs(orbCost);
	JukeBox.play("ooruwhitedmg");
    	}
    }
    
    public int getRange() {
	return range;
    }

    public String getInfoToString() {
	return "Does aoe damage\nwithin specific range";
    }
    
    
}
