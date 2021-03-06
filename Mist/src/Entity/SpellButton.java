package Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Handlers.Mouse;

public class SpellButton extends Rectangle {
	private static final long serialVersionUID = 1L;

	private Spell spell;
	private BufferedImage buttonImage;
	private BufferedImage buttonCdImage;

	private boolean mouseover;

	private Font cdFont;
	private Font infoFont;
	private Color infoColor;

	public SpellButton(int x, int y, int width, int height) {
		setBounds(x, y, width, height);

		cdFont = new Font("Verdana", Font.BOLD, 10);
		infoFont = new Font("Arial", Font.PLAIN, 9);
		infoColor = new Color(150, 150, 150);

		try {
			buttonImage = ImageIO.read(getClass().getResourceAsStream(
					"/Objects/button.png"));
			buttonCdImage = ImageIO.read(getClass().getResourceAsStream(
					"/Objects/buttoncd.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update() {
		checkMouseOver();
		if (spell != null)
			spell.update();
	}

	public boolean onCooldown() {
		return spell.onCooldown();
	}

	private void checkMouseOver() {
		if (contains(Mouse.mousePoint)) {
			mouseover = true;

		} else {
			mouseover = false;
		}
	}

	private void drawString(Graphics2D g, String text, int x, int y) {
		for (String line : text.split("\n"))
			g.drawString(line, x, y += (g.getFontMetrics().getHeight() + 1));
	}

	public void draw(Graphics2D g) {
		g.drawImage(buttonImage, x, y, width, height, null);
		if (spell != null) {
			g.drawImage(spell.getIcon(), x, y, width, height, null);
			if (onCooldown()) {
				g.drawImage(buttonCdImage, x, y, width, height, null);
				g.setColor(Color.WHITE);
				g.setFont(cdFont);
				g.drawString("" + spell.getTimeRemaining() / 1000, x + 2,
						y + 10);
			} else {
				g.drawImage(buttonImage, x, y, width, height, null);
				g.setColor(Color.GRAY);
				g.setFont(cdFont);
				g.drawString("" + spell.getKey(), x + 2, y + 10);
			}

			// Info route
			if (mouseover) {
				g.setColor(Color.black);
				g.fillRect(
						x,
						y - spell.getStringRows()
								* (g.getFontMetrics().getHeight()) - height,
						spell.getLength() * 4 + 3,
						spell.getStringRows()
								* (g.getFontMetrics().getHeight()) + 9);
				g.setColor(infoColor);
				g.setFont(infoFont);
				drawString(
						g,
						spell.getString(),
						x + 1,
						y - spell.getStringRows()
								* (g.getFontMetrics().getHeight()) - height);
			}
		}
	}

	public Spell getSpell() {
		return spell;
	}

	public void assignSpell(Spell sp) {
		spell = sp;
	}

}
