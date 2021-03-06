package NPCs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Entity.MapObject;
import Entity.Ooru;
import GameState.GameStateManager;
import Handlers.Keys;
import Main.GamePanel;
import Tiles.TileMap;

public abstract class NPC extends MapObject {
	// Could be an animation in future
	protected BufferedImage image;
	protected boolean talking;
	protected boolean waiting;
	
	protected StringBuilder sb;
	protected String[] story;
	
	//Specifies the state(s?) where this NPC is waiting for a quest to be finished. Make this a vector?
	protected int waitingState;

	private int timer;

	// having default value on this one.
	private int time;
	
	private Font talkingFont = new Font("Verdana", Font.BOLD, 13);;

	private int start;
	private int end;
	private int index;
	
	//If true, the npc should continue to say his next story element.
	private boolean cont;

	// Determines whether the stateOfGame should increment after talking.
	protected boolean incrementState = false;

	private String drawingString;

	public NPC(TileMap tm) {
		super(tm);

		// could be changed, but most npcs will be 32x32
		width = height = 32;

		talking = false;
		time = 3;
		timer = 0;

		start = 0;
		end = 0;
	}

	public void talk() {
		if (!talking) {
			talking = true;
			setStory();
		}
	}
	
	public abstract void requestQuestCompletion(Ooru p);
	protected abstract void setStory();

	public void update() {
		if (talking) {
			checkEnter();
			if (timer >= time) {
				if (end == story[index].length()) {
					if (cont) {
						index++;
						sb.delete(start, end);
						if (index == story.length) {
							//Here the story is over. If the state of the game is supposed to be incremented, it will be so here.
							talking = false;
							if (incrementState) {
								GameStateManager.incrementTime();
								incrementState = false;
							}
							index = 0;
							start = 0;
							end = 0;
							cont = false;
							return;
						}
						end = 0;
						timer = 0;
						start = 0;
						cont = false;
					}
				} else {
					end++;
				}

				timer = 0;
			} else {
				timer++;
			}
			drawingString = sb.substring(start, end);
		}
	}

	// overrides mapobject
	public void draw(Graphics2D g) {
		setMapPosition();
		g.drawImage(image, (int) (x + xmap - width / 2),
				(int) (y + ymap - height / 2), width, height, null);
		if (talking) {
			g.setColor(Color.BLACK);
			drawString(g, drawingString, 10, GamePanel.HEIGHT - 100);
		}
	}

	private void drawString(Graphics2D g, String text, int x, int y) {
		//Put the font setting somewhere else?
		g.setFont(talkingFont);
		for (String line : text.split("\n")){
			g.drawString(line, x, y += (g.getFontMetrics().getHeight() + 1));
		}
	}

	private void checkEnter() {
		if (Keys.isPressed(Keys.ENTER)) {
			if (end == story[index].length()) {
				cont = true;
			} else {
				end = story[index].length();
			}
		}
	}

}
