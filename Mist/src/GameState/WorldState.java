package GameState;

import java.awt.Graphics2D;

import Audio.JukeBox;
import Entity.Ooru;
import Entity.UserInterface;
import Handlers.Keys;
import Main.GamePanel;
import NPCs.NPC;
import Tiles.Tile;
import Tiles.TileMap;

public abstract class WorldState extends GameState {
	protected Ooru ooru;
	protected UserInterface ui;
	protected TileMap map;
	protected int[] startx;
	protected int[] starty;

	protected NPC[] npcs;

	protected int[] exitState;

	public static final int EAST = 0;
	public static final int SOUTH = 1;
	public static final int WEST = 2;
	public static final int NORTH = 3;

	public WorldState(GameStateManager gsm) {
		super(gsm);
		init();
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
		JukeBox.load("/SoundEffects/Ooru_whiteattack.mp3", "ooruwhitedmg");
		JukeBox.load("/SoundEffects/enemydeath.mp3", "enemydeath");
		ui = ooru.getUI();
		ooru.setMap(map);
		startx = new int[4];
		starty = new int[4];
		exitState = new int[4];
	}

	public int getStartx(int i) {
		return startx[i];
	}

	public int getStarty(int i) {
		return starty[i];
	}

	protected void checkDeath() {
		if (ooru.getHealth() <= 0) {
			ooru.setHealth(3);
			gsm.setState(GameStateManager.HOMEHOUSESTATE);
		}
	}

	// Only east-west at the moment
	protected void checkExit() {
		checkEastExit();
		checkWestExit();
		// checkNorthExit();
		// checkSouthExit();
	}

	protected void checkEastExit() {
		if (ooru.getCurrentRow() == starty[EAST] / map.getTileSize()
				&& ooru.getCurrentCol() == startx[EAST] / map.getTileSize()) {
			gsm.setState(exitState[EAST]);
		}
	}

	protected void checkWestExit() {
		if (ooru.getCurrentRow() == starty[WEST] / map.getTileSize()
				&& ooru.getCurrentCol() == startx[WEST] / map.getTileSize()) {
			gsm.setState(exitState[WEST]);
		}
	}

	protected void setEastExit() {
		startx[EAST] = map.getTileSize() * (map.getNumCols() - 1);
		for (int row = 0; row < map.getNumRows(); row++) {
			if (map.getType(row, map.getNumCols() - 1) != Tile.BLOCKED) {
				starty[EAST] = row * map.getTileSize();
				break;
			}
		}
	}

	protected void setWestExit() {
		startx[WEST] = 0;
		for (int row = 0; row < map.getNumRows(); row++) {
			if (map.getType(row, 0) != Tile.BLOCKED) {
				starty[WEST] = row * map.getTileSize();
				break;
			}
		}
	}

	private void requestTalking() {
		if (npcs != null) {
			for (int i = 0; i < npcs.length; i++) {
				if (npcs[i].intersects(ooru)) {
					npcs[i].requestQuestCompletion(ooru);
					npcs[i].talk();
				}
			}
		}
	}

	public void update() {
		checkDeath();
		handleInput();
		ooru.update();
		if (npcs != null) {
			for (int i = 0; i < npcs.length; i++) {
				npcs[i].update();
			}
		}
		ui.update();
		map.update();
		map.setPosition(GamePanel.WIDTH / 2 - ooru.getx(), GamePanel.HEIGHT / 2
				- ooru.gety());
	}

	public void draw(Graphics2D g) {
		map.draw(g);
		if (npcs != null) {
			for (int i = 0; i < npcs.length; i++) {
				npcs[i].draw(g);
			}
		}
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
		if (Keys.keyState[Keys.R])
			ooru.requestCasting(ui.getSpellButton(UserInterface.BLINK));
		if (Keys.keyState[Keys.E])
			ooru.requestCasting(ui.getSpellButton(UserInterface.SHADOW));
		if (Keys.keyState[Keys.W])
			ooru.requestCasting(ui.getSpellButton(UserInterface.HEAL));
		if (Keys.keyState[Keys.Q])
			ooru.requestCasting(ui.getSpellButton(UserInterface.CONSUME));
		if (Keys.isPressed(Keys.C))
			requestTalking();
	}
}
