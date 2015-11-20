package Tiles;

import java.awt.image.BufferedImage;

public class Tile {
	
	private BufferedImage image;
	private int type;
	private int entranceState;
	
	// tile types
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	public static final int ENTRANCE = 2;
	
	public Tile(BufferedImage image, int type) {
		this.image = image;
		this.type = type;
	}
	
	//returns the state which the player is going to be teleported into.
	public int getEntranceState() {
	    if(type == ENTRANCE) {
		return entranceState;
	    }else {
		return -1;
	    }
	}
	
	public void setEntranceState(int entranceState) {
	    this.entranceState = entranceState;
	}
	
	public BufferedImage getImage() { return image; }
	public int getType() { return type; }
	
}
