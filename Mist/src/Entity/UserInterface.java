package Entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class UserInterface {
    private double health;
    private double maxHealth;
    private int orbs;
    private int maxOrbs;
    private int gold;
    
    private int healthSpace = 30;
    private int healthSize = 10;
    private int iconSpace = 3;
    
    private int numButtons = 8;
    private int buttonSize = 12;
    private int buttonSpace = 2;
    private int actionBarWidth = buttonSize*numButtons + buttonSpace*(numButtons - 1);
    private int barYOffset = 20;

    private SpellButton[] buttons;

    private BufferedImage orbImage;
    private BufferedImage healthImage;
    private BufferedImage healthFrameImage;
    private BufferedImage frameOrbImage;
    private BufferedImage goldImage;
    
    public static final int HEIGHT = 26;
    public static final int WIDTH = GamePanel.WIDTH;
    
    public static final int BLINK = 0;
    public static final int SHADOW = 1;
    public static final int SPELL3 = 2;
    public static final int SPELL4 = 3;

    public UserInterface() {
	init();
	System.out.println("creating UI");
    }

    public void init() {
	buttons = new SpellButton[numButtons];
	for(int i = 0; i < numButtons; i++) {
	buttons[i] = new SpellButton(GamePanel.WIDTH / 2 - actionBarWidth/2 + i*(buttonSize + buttonSpace), GamePanel.HEIGHT - barYOffset, buttonSize, buttonSize);
	}
	buttons[BLINK].assignSpell(new Blink());
	buttons[SHADOW].assignSpell(new Shadow());
	
	
	loadImages();
    }
    
    public void setAttributes(double health, double maxHealth, int orbs, int maxOrbs, int gold) {
	this.health = health;
	this.maxHealth = maxHealth;
	this.orbs = orbs;
	this.maxOrbs = maxOrbs;
	this.gold = gold;
    }
    
    private void loadImages() {
	try {
	    healthImage = ImageIO.read(getClass().getResourceAsStream("/Objects/heart.png"));
	    healthFrameImage = ImageIO.read(getClass().getResourceAsStream("/Objects/heartframe.png"));
	    orbImage = ImageIO.read(getClass().getResourceAsStream("/Objects/orb.png"));
	    frameOrbImage = ImageIO.read(getClass().getResourceAsStream("/Objects/orbframe.png"));
	    goldImage = ImageIO.read(getClass().getResourceAsStream("/Objects/coin.png"));
	} catch (Exception e) {
	}
    }

    public void update() {

	for (int i = 0; i < buttons.length; i++) {
	    buttons[i].update();
	}

    }
    
    public Spell getSpell(int i) {
	return buttons[i].getSpell();
    }
    
    public Spell[] getSpellArray() {
	Spell[] s = new Spell[buttons.length];
	for(int i = 0; i < buttons.length; i++) {
	   s[i] =  buttons[i].getSpell();
	}
	return s;
    }
    
    public void draw(Graphics2D g) {

	//Draw SpellButtons.
	for (int i = 0; i < buttons.length; i++) {
	    buttons[i].draw(g);
	}
	
	//Draw gold
	g.setColor(Color.DARK_GRAY);
	g.drawImage(goldImage, healthSpace / 2 + 1, GamePanel.HEIGHT - healthSpace - 5, healthSize - 2, healthSize - 2, null);
	g.drawString("" + gold, healthSpace / 2 + 10, GamePanel.HEIGHT - healthSpace + 3);
	
	//Draw hearts
	for (int i = 0; i < (int) maxHealth; i++) {
	    if (i >= health) {
		g.drawImage(healthFrameImage, healthSpace / 2 + (healthSize + iconSpace) * i, GamePanel.HEIGHT - healthSpace + healthSize, healthSize, healthSize, null);
	    } else {
		g.drawImage(healthImage, healthSpace / 2 + (healthSize + iconSpace) * i, GamePanel.HEIGHT - healthSpace + healthSize, healthSize, healthSize, null);
	    }
	}

	//Draw orbs
	for (int i = 0; i < maxOrbs; i++) {
	    if (i >= orbs) {
		g.drawImage(frameOrbImage, GamePanel.WIDTH - (healthSpace / 2 + (healthSize + iconSpace) * i), GamePanel.HEIGHT - healthSpace + healthSize, healthSize, healthSize, null);
	    } else {
		g.drawImage(orbImage, GamePanel.WIDTH - (healthSpace / 2 + (healthSize + iconSpace) * i), GamePanel.HEIGHT - healthSpace + healthSize, healthSize, healthSize, null);
	    }
	}
    }

}