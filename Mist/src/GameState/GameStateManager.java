package GameState;

import java.awt.Graphics2D;

import Audio.JukeBox;
import Entity.Ooru;
import Entity.UserInterface;
import Main.GamePanel;

public class GameStateManager {

    private GameState[] gameStates;
    private int currentState;
    private int previousState;
    
    public boolean firstWorld;
    
    private Ooru ooru;

    private PauseState pauseState;
    private boolean paused;

    private InventoryState inventoryState;
    private boolean invState;
    
    public static int stateOfGame;
    public static final int NUMSTATESOFGAME = 2;

    public static final int NUMGAMESTATES = 5;
    public static final int MENUSTATE = 0;
    public static final int WORLD1STATE = 1;
    public static final int TELLINGSTATE = 2;
    public static final int HOMEWORLD = 3;
    public static final int HOMEHOUSESTATE = 4;

    //	public static final int LEVEL1BSTATE = 3;
    //	public static final int LEVEL1CSTATE = 4;
    //	public static final int ACIDSTATE = 15;

    public GameStateManager() {
	
	//TEST, this number will be given from the loaded file (or new game).
	stateOfGame = 0;
	
	JukeBox.init();
	
	firstWorld = true;
	
	gameStates = new GameState[NUMGAMESTATES];

	pauseState = new PauseState(this);
	paused = false;

	inventoryState = new InventoryState(this);
	
	currentState = MENUSTATE;
	previousState = -1;
	loadState(currentState);

    }

    
    private void loadState(int state) {
	if (state == MENUSTATE)
	    gameStates[state] = new MenuState(this);
	else if (state == WORLD1STATE)
	    gameStates[state] = new World1State(this);
	else if (state == TELLINGSTATE) {
	    gameStates[state] = new TellingState(this);
	}else if(state == HOMEWORLD) {
	    gameStates[state] = new HomeWorld(this);
	}else if(state == HOMEHOUSESTATE) {
	    gameStates[state] = new HomeHouseState(this);
	}
    }
    
    public Ooru getOoru() {
	return ooru;
    }
    
    public void setOoru(Ooru ooru) {
	this.ooru = ooru;
    }

    private void unloadState(int state) {
	gameStates[state] = null;
    }

    public void setState(int state) {
	if(previousState != -1) {
	unloadState(previousState);
	}
	gameStates[currentState].stopBackgroundMusic();
	previousState = currentState;
	currentState = state;
	loadState(currentState);
    }
    
    public int getPreviousState() {
	return previousState;
    }
    
    public GameState getPreviousGameState() {
	return gameStates[previousState];
    }
    
    public GameState getCurrentState() {
	return gameStates[currentState];
    }
    
    public void setPaused(boolean b) {
	paused = b;
    }

    public void update() {
	if (paused) {
	    pauseState.update();
	    return;
	} else if (invState) {
	    inventoryState.update();
	    return;
	}
	if (gameStates[currentState] != null) gameStates[currentState].update();
    }

    public InventoryState getInventoryState() {
	return inventoryState;
    }

    public void draw(Graphics2D g) {
	if (gameStates[currentState] != null) {
	    gameStates[currentState].draw(g);
	    if (paused) {
		pauseState.draw(g);
		return;
	    } else if (invState) {
		inventoryState.draw(g);
		return;
	    }
	} else {
	    g.setColor(java.awt.Color.BLACK);
	    g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
	}
    }

    public void setInventoryState(boolean b) {
	invState = b;

    }
    
    public static void incrementTime(){
    	stateOfGame++;
    	JukeBox.play("enemydeath");
    }
    
}