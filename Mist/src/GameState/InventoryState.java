package GameState;

import java.awt.Graphics2D;

import Handlers.Keys;
import Inventory.Inventory;

public class InventoryState extends GameState {

    private Inventory inventory;
    private int choosingStage;

    public InventoryState(GameStateManager gsm) {
	super(gsm);
    }

    public void init() {

    }

    public void update() {
	inventory.update();
	handleInput();
    }

    public void draw(Graphics2D g) {
	inventory.draw(g);
    }

    public void setInventory(Inventory inv) {
	inventory = inv;
    }

    public void handleInput() {
	if (Keys.isPressed(Keys.I)) gsm.setInventoryState(false);
//	if (choosingStage == 0) {
	    if (Keys.isPressed(Keys.RIGHT)) inventory.moveChoice(1);
	    if (Keys.isPressed(Keys.LEFT)) inventory.moveChoice(-1);
	    if (Keys.isPressed(Keys.DOWN)) inventory.moveChoice(inventory.getRows());
	    if (Keys.isPressed(Keys.UP)) inventory.moveChoice(-inventory.getRows());
	    if (Keys.isPressed(Keys.ENTER)) {
		inventory.select(gsm.getOoru());
//		choosingStage = false;
	    }
	    if(Keys.isPressed(Keys.DELETE)) inventory.deleteCurrentStack();
//	}else if(choosingState == 1){
//	    if (Keys.isPressed(Keys.DOWN)) inventory.moveMenuChoice(-1)
//	    if (Keys.isPressed(Keys.UP)) inventory.moveMenuChoice(1);
//	    if (Keys.isPressed(Keys.ENTER)) {
//		choosingState = inventory.menuSelect();
//	    }
//	}else if(choosingState == 2) {
//	    
//	}
    }

}
