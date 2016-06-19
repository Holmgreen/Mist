package GameState;

import java.awt.Color;
import java.awt.Graphics2D;

import Audio.JukeBox;
import Handlers.Keys;
import Main.GamePanel;

public class TellingState extends GameState {
	private long timer;
	private long time;
	private int index;
	private int start;
	private int end;
	private long pause;

	// If i want to change the time back and forth on the talking speed.
	private int defTime;

	private boolean paused;

	private String[] story;
	private StringBuilder sb;
	private String drawingString;

	public TellingState(GameStateManager gsm) {
		super(gsm);
		init();
	}

	public void init() {
		story = new String[3]; // Change size if changing story.
		sb = new StringBuilder();

		story[0] = "Once upon a time, in the primitive lands of \nAlteria, the young one lived. \nWith a power yet to be discovered.";
		story[1] = "His name was Ooru and the whole of Alteria\nknew of the prophecy that told the\nlegend of an ascending savior.";
		story[2] = "At the time, evil grew greater and faster\n than ever.";

		for (int i = 0; i < story.length; i++) {
			sb.append(story[i]);
		}

		time = defTime = 3;
		pause = 50;

		start = 0;
		end = 0;

		paused = false;
	}

	public void update() {
		handleInput();
		if (timer >= time) {
			if (end == story[index].length()) {
				paused = true;
				if (timer >= pause) {
					paused = false;
					index++;
					sb.delete(start, end);
					if (index == story.length) {
						gsm.setState(GameStateManager.HOMEHOUSESTATE);
						sb = null; // Reseting and unloading
						return;
					}
					end = 0;
					timer = 0;
					start = 0;
				} else {
					timer++;
				}
			} else {
				end++;
			}

			if (!paused) {
				timer = 0;
				// JukeBox.play("typing");
			}
		} else {
			timer++;
		}

		drawingString = sb.substring(start, end);
	}

	public void draw(Graphics2D g) {
		// Got to be a better way to do this..
		g.setColor(Color.black);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.white);
		drawString(g, "" + drawingString, 10, GamePanel.HEIGHT - 100);
	}

	private void drawString(Graphics2D g, String text, int x, int y) {
		for (String line : text.split("\n"))
			g.drawString(line, x, y += (g.getFontMetrics().getHeight() + 1));
	}

	public void handleInput() {
		if (Keys.isPressed(Keys.ENTER))
			end = (story[index].length());
		if (Keys.isPressed(Keys.ESCAPE))
			gsm.setState(GameStateManager.HOMEHOUSESTATE);
	}

}
