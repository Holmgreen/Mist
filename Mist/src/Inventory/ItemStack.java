package Inventory;

public class ItemStack {
    private int maxSize;
    private int size;
    private Item item;
    
    public ItemStack(Item item) {
	size = 0;
	this.item = item;
	maxSize = item.getStackSize();
    }
    
    public boolean add(int i) {
	if(size == maxSize) {
	    return false;
	}else {
	size+=i;
	if(size >= maxSize) size = maxSize;
	return true;
	}
    }
    
    public void remove(int i) {
	size-=i;
	if(size < 0) size = 0;
    }
    
    public Item getItem() {
	return item;
    }
    
    public int getSize() {
	return size;
    }
    
    public boolean isFull() {
	return size == maxSize;
    }
    
    public int rest(int i) {
	if(i + size > maxSize) {
	return (i + size)%maxSize;
	}
	return 0;
    }
    
    //adds an itemstack on another. returns the rest. returns -1 if full.
    public int addStack(ItemStack other) {
	if(isFull()) return -1;
	int addAmount = other.getSize();
	int leftOver = rest(addAmount);
	add(addAmount);
	other.remove(addAmount - leftOver);
	return leftOver;
    }
    
    public boolean containsItem(Item item) {
	return this.item.getType() == item.getType();
    }
}
