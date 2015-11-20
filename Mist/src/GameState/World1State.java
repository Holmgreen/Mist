package GameState;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Audio.JukeBox;
import Enemies.Shelldon;
import Entity.Mob;

public class World1State extends WorldState {
    private ArrayList<Shelldon> enemies;

    public World1State(GameStateManager gsm) {
	super(gsm);
    }

    public void init() {
	super.init();

	JukeBox.load("/Music/world1music.mp3", "world1music");
	bgmusic = "world1music";
	JukeBox.loop(bgmusic);
	
	map.loadMap("/Worlds/world1.txt");
	map.loadTiles("/Tiles/tileset32.png");
	map.setTween(1);

	setEastExit();
	exitState[EAST] = GameStateManager.HOMEWORLD;
	
	ooru.setPosition(startx[EAST] - map.getTileSize(), starty[EAST]);
	//ooru.setPosition(1200, 550);

	enemies = new ArrayList<Shelldon>();

	populateEnemies();

    }

    private void populateEnemies() {
	enemies.add(new Shelldon(map, ooru, 220, 230));
	enemies.add(new Shelldon(map, ooru, 240, 230));
	enemies.add(new Shelldon(map, ooru, 230, 210));
	enemies.add(new Shelldon(map, ooru, 230, 130));
	enemies.add(new Shelldon(map, ooru, 260, 130));
	enemies.add(new Shelldon(map, ooru, 250, 130));
	enemies.add(new Shelldon(map, ooru, 130, 230));
	enemies.add(new Shelldon(map, ooru, 100, 230));
	enemies.add(new Shelldon(map, ooru, 230, 230));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, 290));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 100));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 200));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 100));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 200));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 100));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 200));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 100));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 200));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 100));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 200));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 100));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 200));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 100));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 200));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 100));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 200));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 100));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 200));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 100));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 200));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 100));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 200));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 100));
	enemies.add(new Shelldon(map, ooru, map.getWidth() - 150, map.getHeight() - 200));
    }

    public void update() {
	super.update();
	checkEastExit();
	//Sends the enemy array to ooru.
	ooru.updateEnemies(enemies);

	//Loops through the enemies.
	for (int i = 0; i < enemies.size(); i++) {
	    Mob e = enemies.get(i);
	    e.update();
	    if (e.shouldRemove()) {
		enemies.remove(i);
		i--;
	    } else {
		if (ooru.intersects(e.getHitBox()) && !e.isDead()) {
		    e.doWhiteDamage(ooru);
		}
	    }
	}

    }

    public void draw(Graphics2D g) {
	super.draw(g);
	for (int i = 0; i < enemies.size(); i++) {
	    enemies.get(i).draw(g);
	}
    }

}
