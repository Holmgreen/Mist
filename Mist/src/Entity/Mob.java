package Entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import Tiles.TileMap;

public abstract class Mob extends MapObject {
    protected double health;
    protected double maxHealth;
    protected double whiteDamage;
    protected double dmgDelay;
    protected double resistance;

    protected int whiteDmgRange;

    protected boolean attacking;
    protected boolean dead;

    protected Rectangle hitBox;
    protected long attackTimer;
    
    protected boolean remove;
    
    public Mob(TileMap map) {
	super(map);
    }

    public void doDamage(Mob o, double dmg) {
	if (System.currentTimeMillis() - attackTimer > dmgDelay) {
	    if (o != null) {
		attackTimer = System.currentTimeMillis();
		o.loseHealth(dmg);
	    }
	}

    }

    public void doWhiteDamage(Mob o) {
	if (System.currentTimeMillis() - attackTimer > dmgDelay) {
	    if (o != null) {
		attackTimer = System.currentTimeMillis();
		o.loseHealth(whiteDamage);
	    }
	}

    }
    
    public void stopAttack() {
	attacking = false;
    }
    
    public double getWhiteDamage() {
	return whiteDamage;
    }

    //Insert negative value to gain health.
    public void loseHealth(double damage) {
	health -= damage / resistance;
    }

    //Insert negative value to gain maxHealth.
    public void loseMaxHealth(double loss) {
	if (health < 0) {
	    health = 0;
	} else {
	    maxHealth -= loss;
	}
    }

    public void setMaxHealth(double h) {
	maxHealth = h;
    }
    
    public void setMoveSpeed(double speed) {
	moveSpeed = speed;
    }
    
    public void resetMoveSpeed() {
	moveSpeed = initMoveSpeed;
	maxSpeed = initMoveSpeed;
    }

    public void setHealth(double h) {
	health = h;
    }

    public double getHealth() {
	return health;
    }

    public double getMaxHealth() {
	return maxHealth;
    }

    public double getResistance() {
	return resistance;
    }

    public void setResistance(double res) {
	resistance = res;
    }

    public boolean isAttacking() {
	return attacking;
    }
    
    public boolean isDead() {
	return dead;
    }

    public void setAttacking(boolean b) {
	    attacking = b;
    }
    
    protected void getNextPosition() {
	double maxSpeed = this.maxSpeed;

	// movement
	if (left) {
	    dx -= moveSpeed;
	    if (dx < -maxSpeed) {
		dx = -maxSpeed;
	    }
	} else if (right) {
	    dx += moveSpeed;
	    if (dx > maxSpeed) {
		dx = maxSpeed;
	    }
	} else {
	    if (dx > 0) {
		dx -= stopSpeed;
		if (dx < 0) {
		    dx = 0;
		}
	    } else if (dx < 0) {
		dx += stopSpeed;
		if (dx > 0) {
		    dx = 0;
		}
	    }
	}

	if (up) {
	    dy -= moveSpeed;
	    if (dy < -maxSpeed) {
		dy = -maxSpeed;
	    }
	} else if (down) {
	    dy += moveSpeed;
	    if (dy > maxSpeed) {
		dy = maxSpeed;
	    }
	} else {
	    if (dy > 0) {
		dy -= stopSpeed;
		if (dy < 0) {
		    dy = 0;
		}
	    } else if (dy < 0) {
		dy += stopSpeed;
		if (dy > 0) {
		    dy = 0;
		}
	    }
	}

    }
    
    
    public boolean shouldRemove() {
	return remove;
    }

    protected void updateHitBox() {
	if (facingUp) {
	    hitBox.setBounds((int) (x + xmap - width/2), (int) (y + ymap - whiteDmgRange), width, whiteDmgRange);
	} else if (facingDown) {
	    hitBox.setBounds((int) (x + xmap - width/2), (int) (y + ymap), width, whiteDmgRange);
	} else if (facingRight) {
	    hitBox.setBounds((int) (x + xmap), (int) (y + ymap - height/2), whiteDmgRange, height);
	} else {
	    hitBox.setBounds((int) (x + xmap - whiteDmgRange), (int) (y + ymap - height/2), whiteDmgRange, height);
	}
    }

    //TEST
    protected void drawHitBox(Graphics2D g) {
	g.draw(hitBox);
    }
    
    public Rectangle getHitBox() {
	return hitBox;
    }
    
    public void draw(Graphics2D g) {
	super.draw(g);
	//drawHitBox(g);
    }

    public abstract void update();

}
