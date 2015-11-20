package Inventory;

import Entity.Animation;
import Entity.Ooru;

public abstract class WearableItem extends Item {
    private static final long serialVersionUID = 1L;
    protected Animation animation;
    
    public abstract String toString();

    public boolean use(Ooru ooru) {
	return false;
    }
    
}
