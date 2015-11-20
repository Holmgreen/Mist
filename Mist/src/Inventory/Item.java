package Inventory;


import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Entity.Ooru;

public abstract class Item extends Rectangle{
    private static final long serialVersionUID = 1L;
    protected BufferedImage image;
    protected int stackSize;
    protected int type;
    
    protected boolean useable;
    
    protected int quantity;
    
    public static final int STONE = 0;
    public static final int ORB = 1;
    
    public Item () {
	quantity = 0;
	width = height = 12;
    }
    
    public BufferedImage getImage() {
	return image;
    }
    
    public boolean useable() {
	return useable;
    }
    
    public abstract String toString();
    
    public int getStackSize() {
	return stackSize;
    }
    
    public int getType() {
	return type;
    }
    
    //Returns true if item should be removed after use.
    public abstract boolean use(Ooru ooru);
    
    public void setQuantity(int i) {
	quantity = i;
    }
    
    public void incrementQuantity(int i) {
	quantity += i;
	if(quantity <= 0) {
	    quantity = 0;
	}
    }
    
    public int getQuantity() {
	return quantity;
    }
}
