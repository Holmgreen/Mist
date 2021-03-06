package Entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Inventory.Inventory;
import Inventory.Item;
import Main.GamePanel;

public class UserInterface {
	private double health;
	private double maxHealth;
	private int orbs;
	private int maxOrbs;
	private int gold;

	private Inventory inv;
	
	private int healthSpace = 30;
	private int healthSize = 10;
	private int iconSpace = 3;

	private int numButtons = 8;
	private int buttonSize = 12;
	private int buttonSpace = 2;
	private int actionBarWidth = buttonSize * numButtons + buttonSpace
			* (numButtons - 1);
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
	public static final int HEAL = 2;
	public static final int CONSUME = 3;

	public UserInterface() {
		init();
	}

	public void init() {
		buttons = new SpellButton[numButtons];
		for (int i = 0; i < numButtons; i++) {
			buttons[i] = new SpellButton(GamePanel.WIDTH / 2 - actionBarWidth
					/ 2 + i * (buttonSize + buttonSpace), GamePanel.HEIGHT
					- barYOffset, buttonSize, buttonSize);
		}

		loadImages();
	}

	public void setAttributes(double health, double maxHealth, int orbs,
			int maxOrbs, int gold, Inventory inv) {
		this.health = health;
		this.maxHealth = maxHealth;
		this.orbs = orbs;
		this.maxOrbs = maxOrbs;
		this.gold = gold;
		this.inv = inv;
	}

	private void loadImages() {
		try {
			healthImage = ImageIO.read(getClass().getResourceAsStream(
					"/Objects/heart.png"));
			healthFrameImage = ImageIO.read(getClass().getResourceAsStream(
					"/Objects/heartframe.png"));
			orbImage = ImageIO.read(getClass().getResourceAsStream(
					"/Objects/orb.png"));
			frameOrbImage = ImageIO.read(getClass().getResourceAsStream(
					"/Objects/orbframe.png"));
			goldImage = ImageIO.read(getClass().getResourceAsStream(
					"/Objects/coin.png"));
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
	
	public SpellButton getSpellButton(int i) {
		return buttons[i];
	}

	public Spell[] getSpellArray() {
		Spell[] s = new Spell[buttons.length];
		for (int i = 0; i < buttons.length; i++) {
			s[i] = buttons[i].getSpell();
		}
		return s;
	}

	public void activateSpell(int i) {
		Spell s;
		if (i == BLINK) {
			s = new Blink();
			buttons[BLINK].assignSpell(s);
		}
		else if (i == SHADOW) {
			s = new Shadow();
			buttons[SHADOW].assignSpell(s);
		}else if(i == HEAL){
			s = new Heal();
			buttons[HEAL].assignSpell(s);
		}else if(i == CONSUME){
			s = new Consume();
			buttons[CONSUME].assignSpell(s);
		}
	}

	public void draw(Graphics2D g) {
		
		// Draw SpellButtons.
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].draw(g);
		}

		// Draw gold
		g.setColor(Color.DARK_GRAY);
		g.drawImage(goldImage, healthSpace / 2 + 1, GamePanel.HEIGHT
				- healthSpace - 5, healthSize - 2, healthSize - 2, null);
		g.drawString("" + gold, healthSpace / 2 + 10, GamePanel.HEIGHT
				- healthSpace + 3);

		// Draw hearts
		for (int i = 0; i < (int) maxHealth; i++) {
			if (i >= health) {
				g.drawImage(healthFrameImage, healthSpace / 2
						+ (healthSize + iconSpace) * i, GamePanel.HEIGHT
						- healthSpace + healthSize, healthSize, healthSize,
						null);
			} else {
				g.drawImage(healthImage, healthSpace / 2
						+ (healthSize + iconSpace) * i, GamePanel.HEIGHT
						- healthSpace + healthSize, healthSize, healthSize,
						null);
			}
		}
		
		//Draw black bar behind the orbs.
		g.setColor(Color.black);
		g.fillRect(GamePanel.WIDTH
				- (healthSpace / 2 + (healthSize + iconSpace) * (maxOrbs - 1)) - iconSpace,
				GamePanel.HEIGHT - healthSpace + healthSize,
				healthSize*maxOrbs + iconSpace*(maxOrbs) + iconSpace, healthSize);
		
		//Write out number of orbs in inventory.
		if(inv != null){
		g.drawString("x" + inv.quantity(Item.ORB), GamePanel.WIDTH
				- (healthSpace / 2 + (healthSize) * (maxOrbs - 1)) - iconSpace,
				GamePanel.HEIGHT - healthSpace);
		}
		
		// Draw orbs
		for (int i = 0; i < maxOrbs; i++) {
			if (i >= orbs) {
				g.drawImage(frameOrbImage, GamePanel.WIDTH
						- (healthSpace / 2 + (healthSize + iconSpace) * i),
						GamePanel.HEIGHT - healthSpace + healthSize,
						healthSize, healthSize, null);
			} else {
				g.drawImage(orbImage, GamePanel.WIDTH
						- (healthSpace / 2 + (healthSize + iconSpace) * i),
						GamePanel.HEIGHT - healthSpace + healthSize,
						healthSize, healthSize, null);
			}
		}
	}

}
