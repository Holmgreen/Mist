package GameState;

import java.awt.Graphics2D;

import Audio.JukeBox;
import Entity.Ooru;

public abstract class GameState {
	
	protected GameStateManager gsm;
	protected Ooru ooru;
	protected String bgmusic;
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	public abstract void init();
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract void handleInput();
	
	
	public void stopBackgroundMusic() {
	    	if(bgmusic != null)
		JukeBox.stop(bgmusic);
	    }
	
	public Ooru getOoru() {
	    return ooru;
	}
	
}
