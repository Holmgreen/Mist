package Tiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import GameState.GameStateManager;
import Main.GamePanel;




public class TileMap {
	
	// position
	private double x;
	private double y;
	
	// bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	private double tween;
	
	// map
	public int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	// tileset
	private BufferedImage tileset;
	private int numTilesAcross;
	private Tile[][] tiles;
	
	// drawing
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	// effects
	private boolean shaking;
	private int intensity;
	
	public TileMap(int tileSize) {
		this.tileSize = tileSize;
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;
		tween = 0.07;
	}
	
	public void loadTiles(String s) {
		
		try {

			tileset = ImageIO.read(
				getClass().getResourceAsStream(s)
			);
			numTilesAcross = tileset.getWidth() / tileSize;
			tiles = new Tile[4][numTilesAcross];
			
			BufferedImage subimage;
			for(int col = 0; col < numTilesAcross; col++) {
				subimage = tileset.getSubimage(
							col * tileSize,
							0,
							tileSize,
							tileSize
						);
				tiles[0][col] = new Tile(subimage, Tile.NORMAL);
				subimage = tileset.getSubimage(
							col * tileSize,
							tileSize,
							tileSize,
							tileSize
						);
				tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
				subimage = tileset.getSubimage(
					col * tileSize,
					2*tileSize,
					tileSize,
					tileSize
				);
				tiles[2][col] = new Tile(subimage, Tile.BLOCKED);
				subimage = tileset.getSubimage(
					col * tileSize,
					3*tileSize,
					tileSize,
					tileSize
				);
				tiles[3][col] = new Tile(subimage, Tile.ENTRANCE);
				tiles[3][col].setEntranceState(GameStateManager.HOMEHOUSESTATE);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("loading tiles failed");
			System.exit(0);
		}
		
	}
	
	public void loadMap(String s) {
		
		try {
			
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(
						new InputStreamReader(in)
					);
			
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;
			
			xmin = GamePanel.WIDTH - width;
			xmax = 0;
			ymin = GamePanel.HEIGHT - height;
			ymax = 0;
			
			String delims = "\\s+";
			for(int row = 0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]) - 1;
				}
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("loading map failed");
			System.exit(0);
		}
		
	}
	
	public int getTileSize() { return tileSize; }
	public double getx() { return x; }
	public double gety() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getNumRows() { return numRows; }
	public int getNumCols() { return numCols; }
	
	public int getType(int row, int col) {
	    	if(row > map.length - 1 || row < 0 || col > map[0].length - 1 || col < 0) {
	    	    return Tile.BLOCKED;
	    	}
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
	}
	
	public Tile getTile(int row, int col) {
	    try {
	    	int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c];
	    }catch(Exception e) {}
	    return null;
	}
	public boolean isShaking() { return shaking; }
	
	public void setTween(double d) { tween = d; }
	public void setShaking(boolean b, int i) { shaking = b; intensity = i; }
	public void setBounds(int i1, int i2, int i3, int i4) {
		xmin = GamePanel.WIDTH - i1;
		ymin = GamePanel.WIDTH - i2;
		xmax = i3;
		ymax = i4;
	}
	
	public void setPosition(double x, double y) {
		
		this.x += (x - this.x) * tween;
		this.y += (y - this.y) * tween;
		
		fixBounds();
		
		colOffset = (int)-this.x / tileSize;
		rowOffset = (int)-this.y / tileSize;
		
	}
	
	public void fixBounds() {
		if(x < xmin) x = xmin;
		if(y < ymin) y = ymin;
		if(x > xmax) x = xmax;
		if(y > ymax) y = ymax;
	}
	
	public void update() {
		if(shaking) {
			this.x += Math.random() * intensity - intensity / 2;
			this.y += Math.random() * intensity - intensity / 2;
		}
	}
	
	public void draw(Graphics2D g) {
		
		for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
		
			if(row >= numRows) break;
			
			for(int col = colOffset; col < colOffset + numColsToDraw; col++) {
				
				if(col >= numCols) break;
				
				
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				g.drawImage(
					tiles[r][c].getImage(),
					(int)x + col * tileSize,
					(int)y + row * tileSize,
					null
				);
				
			}
			
		}
		
	}
	
}


















