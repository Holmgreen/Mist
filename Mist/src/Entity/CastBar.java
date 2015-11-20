package Entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class CastBar extends Rectangle {

    private static final long serialVersionUID = 1L;
    private long castTime;
    private long castTimer;
    private long elapsed;
    private boolean successful;
    private boolean interrupted;

    public CastBar(int x, int y, int width, int height) {
	setBounds(x, y, width, height);
    }

    public void setCastTime(long castTime) {
	this.castTime = castTime;
    }

    public void startCasting() {
	if (castTime == 0) {
	    successful = true;
	} else {
	    successful = false;
	    interrupted = false;
	    castTimer = System.currentTimeMillis();
	}
    }

    public void stopCasting() {
	successful = false;
	interrupted = true;
    }

    public boolean successful() {
	return successful;
    }

    private void checkCasting() {
	if (!interrupted) {
	    elapsed = System.currentTimeMillis() - castTimer;
	    if (elapsed > castTime) {
		successful = true;
	    }
	} else {
	    elapsed = 0;
	    successful = false;
	}
    }

    public void update() {
	checkCasting();
    }

    public void draw(Graphics2D g) {
	g.setColor(Color.BLACK);
	g.fill(this);
	g.setColor(Color.GREEN);
	g.fillRect(x + 1, y + 1, (int) ((width - 2) * (elapsed * 1000 / castTime)) / 1000, height - 2);
    }

}
