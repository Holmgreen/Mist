package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Audio.JukeBox;
import Handlers.Keys;
import Main.GamePanel;

public class MenuState extends GameState {
    private int currentChoice = 0;
    private String[] choices = { "New Game", "Load Game", "Quit" };

    private int titlex = 100;
    private int titley = 100;
    private Color defCol = new Color(200, 200, 200);
    private Color currentChoiceCol = new Color(100, 100, 100);

    private Font defFont = new Font("Courier", Font.BOLD, 14);
    private Font titleFont = new Font("Arial", Font.BOLD, 26);

    public MenuState(GameStateManager gsm) {
	super(gsm);
	init();
    }

    public void init() {
	JukeBox.load("/Music/menumusic.mp3", "menumusic");
	JukeBox.load("/SoundEffects/menuopt.mp3", "menuopt");
	JukeBox.loop("menumusic");
    }

    public void update() {
	handleInput();
    }

    public void draw(Graphics2D g) {

	//Background for now:
	g.setColor(Color.WHITE);
	g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

	g.setColor(Color.cyan);
	g.setFont(titleFont);
	g.drawString("MIST", titlex, titley);
	g.setFont(defFont);

	g.setColor(defCol);
	for (int i = 0; i < choices.length; i++) {
	    if (i == currentChoice) {
		g.setColor(currentChoiceCol);
		g.drawString(choices[i], titlex, titley + 30 + 30 * i);
	    } else {
		g.setColor(defCol);
		g.drawString(choices[i], titlex, titley + 30 + 30 * i);
	    }
	}

    }

    public void handleInput() {
	if (Keys.isPressed(Keys.UP)) {
	    currentChoice--;
	    JukeBox.play("menuopt", 0);
	}
	if (Keys.isPressed(Keys.DOWN)) {
	    currentChoice++;
	    JukeBox.play("menuopt", 0);
	}
	if (currentChoice >= choices.length) {
	    currentChoice = 0;
	}
	if (currentChoice < 0) {
	    currentChoice = choices.length - 1;
	}
	if (Keys.isPressed(Keys.ENTER)) {
	    if (currentChoice == 0) {
		gsm.setState(GameStateManager.TELLINGSTATE);
		JukeBox.stop("menumusic");
	    }
	    if (currentChoice == 1) {
		System.out.println("Loading games is not implemented yet.");
	    }
	    if (currentChoice == 2) {
		System.exit(0);
	    }
	}
    }

}
