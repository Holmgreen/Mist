package GameState;

import java.awt.Graphics2D;

import Audio.JukeBox;
import Entity.Ooru;
import Entity.UserInterface;
import Handlers.Keys;
import Main.GamePanel;
import Tiles.Tile;
import Tiles.TileMap;

public abstract class HouseState extends GameState {
	// This is a game state with only one entrance, special is that you cant
	// attack inside the house.

	protected Ooru ooru;
	protected UserInterface ui;
	protected TileMap map;
	protected int startx;
	protected int starty;
	
	//health timer
	private int timer = 0;
	private int time = 100;

	protected int exitState;

	public HouseState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		map = new TileMap(32);
		if (gsm.firstWorld) {
			ooru = new Ooru(map, new UserInterface());
			gsm.setOoru(ooru);
			gsm.firstWorld = false;
		} else {
			ooru = gsm.getOoru();
		}
		ooru.setMap(map);
		ui = ooru.getUI();
		JukeBox.load("/Music/housemusic.mp3", "housemusic");
		bgmusic = "housemusic";
		JukeBox.loop(bgmusic);
	}

	public int getStartx(int i) {
		return startx;
	}

	public int getStarty(int i) {
		return starty;
	}

	protected void checkDeath() {
		if (ooru.getHealth() == 0) {
			gsm.setState(GameStateManager.MENUSTATE);
			ooru.setHealth(ooru.getMaxHealth() - 2);
		}
	}

	// Only east at the moment
	protected void checkExit() {
		if (ooru.getCurrentRow() == starty / map.getTileSize()
				&& ooru.getCurrentCol() == startx / map.getTileSize()) {
			gsm.setState(exitState);
		}
	}

	protected void setExit() {
		for (int col = 0; col < map.getNumCols(); col++) {
			for (int row = 0; row < map.getNumRows(); row++) {
				if (map.getType(row, col) == Tile.ENTRANCE) {
					starty = row * map.getTileSize();
					startx = col * map.getTileSize();
					break;
				}
			}
		}
	}

	public void update() {
		checkDeath();
		handleInput();
		checkExit();
		ooru.update();
		ui.update();
		map.update();
		map.setPosition(GamePanel.WIDTH / 2 - ooru.getx(), GamePanel.HEIGHT / 2
				- ooru.gety());
		if(timer == time){
				ooru.incrementHealth();
			timer = 0;
		}else{
			timer++;
		}
	}

	public void draw(Graphics2D g) {
		map.draw(g);
		ooru.draw(g);
		ui.draw(g);
	}

	public void handleInput() {
		if (Keys.isPressed(Keys.ESCAPE))
			gsm.setPaused(true);
		if (Keys.isPressed(Keys.I)) {
			gsm.getInventoryState().setInventory(ooru.getInventory());
			gsm.setInventoryState(true);
		}
		ooru.setUp(Keys.keyState[Keys.UP]);
		ooru.setLeft(Keys.keyState[Keys.LEFT]);
		ooru.setDown(Keys.keyState[Keys.DOWN]);
		ooru.setRight(Keys.keyState[Keys.RIGHT]);
	}

}
