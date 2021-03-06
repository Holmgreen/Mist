package Entity;

import java.awt.image.BufferedImage;

public abstract class Spell {
    protected BufferedImage icon;
    protected Animation animation;
    
    //Damage
    protected double damage;
    
    //Cooldown related
    private boolean onCooldown;
    
    private long elapsed;
    private long cdTimer;
    protected long cd;
    protected int range;
    
    protected String key;
    
    protected boolean isAoe;
    
    protected int orbCost;
    
    protected String infoString;
    protected int stringLength;
    
    //Casting
    protected long castingTime;
    
    public Spell() {
	
    }
    
    public void init(){
    	infoString = "Cooldown: " + cd + " ms\nDamage: " + damage +  "\nCasting time: " + castingTime + " ms\n" + getInfoToString();
    }
    public BufferedImage getIcon() {
	return icon;
    }

    public void update() {
	checkCooldown();
    }

    public void checkCooldown() {
	if (onCooldown) {
	    elapsed = System.currentTimeMillis() - cdTimer;
	    if (elapsed >= cd) {
		onCooldown = false;
	    }
	}
    }
    
    public abstract void use(Ooru ooru);

    public long getTimeRemaining() {
	return cd - elapsed;
    }

    public void setCooldown() {
	if (!onCooldown) {
	    onCooldown = true;
	    cdTimer = System.currentTimeMillis();
	}
    }

    public boolean onCooldown() {
	return onCooldown;
    }
    
    public long getCooldown() {
	return cd;
    }
    
    public long getCastingTime() {
	return castingTime;
    }
    
    public double getDmg() {
	return damage;
    }

    public String getString() {
	return infoString;
    }
    
    public int getRange() {
	return range;
    }
    
    public int getLength() {
	return stringLength;
    }
   
    public abstract String getInfoToString();
	
    public int getStringRows() {
	int count = 0;
	for (String infoString : infoString.split("\n")) {
	    count++;
	}
	return count;
    }
    
    
    public boolean isAoe() {
	return isAoe;
    }

	public String getKey() {
		return key;
	}



}
