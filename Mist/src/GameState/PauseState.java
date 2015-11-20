package GameState;

import java.awt.Color;
import java.awt.Graphics2D;

import Handlers.Keys;
import Main.GamePanel;

public class PauseState extends GameState{

    public PauseState(GameStateManager gsm) {
	super(gsm);
    }

    public void init() {
	
    }

    public void update() {
	handleInput();
    }

    public void draw(Graphics2D g) {
	g.setColor(new Color(255, 255, 255, 100));
	g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
    }

    public void handleInput() {
	if(Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(false);
    }

}
