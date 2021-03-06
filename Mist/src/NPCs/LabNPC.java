package NPCs;

import javax.imageio.ImageIO;

import Entity.Ooru;
import GameState.GameStateManager;
import Inventory.Item;
import Tiles.TileMap;

public class LabNPC extends NPC {
	private int stonesNeeded;

	public LabNPC(TileMap tm) {
		super(tm);
		try {
			image = ImageIO.read(getClass().getResourceAsStream(
					"/NPCs/labnpc.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		sb = new StringBuilder();

		cwidth = 32;
		cheight = 64;

		// Waiting for his stones.
		waitingState = 1; //Will be compared with current game state.
		stonesNeeded = 20;
	}

	// Can make this more efficient by not making a new string every time..
	protected void setStory() {
		if (GameStateManager.stateOfGame == 0) {
			story = new String[8];
			story[0] = "Woaw! Who are you? \nI have not seen a person in here for years..";
			story[1] = "Wait a second.. The prophecy predicted this...";
			story[2] = "I have been waiting for you my entire \nlife, you are Ooru!";
			story[3] = "I will give you this pair of\nmagic shoulders. Use them to defeat evil.";
			story[4] = "I have now taught you Shadow, a spell\nthat requires orbs. Press E to use Shadow.";
			story[5] = "You can heal yourself with W and regenerate\norbs that you have gathered with Q.";
			story[6] = "Bring "
					+ stonesNeeded
					+ " stones to me and\nI will teach you teleportation, which will\nallow you to reach new places.";
			story[7] = "You can reach your inventory by pressing I.";
			incrementState = true;
		} else if (GameStateManager.stateOfGame == 1) {
			story = new String[1];
			story[0] = "With your magic powers I want you to bring\n"
					+ stonesNeeded + " stones to me.";

		} else if (GameStateManager.stateOfGame == 2) {
			story = new String[3];
			story[0] = "You made it!";
			story[1] = "You're not even wounded... The legend is true...";
			story[2] = "My knowledge of teleportation is now channeled\nthrough you.Press F to instantaneously\ntravel from one point to another.";
		} else {
			story = new String[1];
			story[0] = "I have faith in you, Ooru.";
		}

		for (int i = 0; i < story.length; i++) {
			sb.append(story[i]);
		}

	}

	public void requestQuestCompletion(Ooru p) {
		if (GameStateManager.stateOfGame == waitingState) {
			if (p.getInventory().quantity(Item.STONE) >= stonesNeeded) {
				GameStateManager.incrementTime();

				// Have to also take away stonesNeeded from the player inventory
				// here as well.
				p.getInventory().searchAndDestroy(Item.STONE, stonesNeeded);
			}
		}
	}

}
