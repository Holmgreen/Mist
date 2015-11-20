package Enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import Audio.JukeBox;
import Entity.Animation;
import Entity.Mob;
import Entity.Ooru;
import Handlers.EnemyHandler;
import Inventory.Item;
import Inventory.ItemContent;
import Inventory.Stone;
import Tiles.TileMap;

public class Shelldon extends Mob {
    private Ooru ooru;

    private Random rand;

    private final int[] NUMFRAMES = { 2, 2, 2, 1, 1, 1, 1, 1, 1 };
    private final int[] FRAMEWIDTHS = { 26, 26, 26, 26, 26, 26, 26, 26, 26 };
    private final int[] FRAMEHEIGHTS = { 26, 26, 26, 26, 26, 26, 26, 26, 26 };
    private final int[] DELAYS = { 30, 30, 30, -1, -1, -1, -1, -1, -1 };
    private ArrayList<BufferedImage[]> sprites;

    private boolean aggro;

    //looting
    private boolean looted;
    private boolean looting;
    private long lootTimer;
    private long lootTime;
    private static Item[] itemRange = { ItemContent.stone, ItemContent.orb };
    private int[] itemdrop = { -1, -1 };

    private double aggroSpeed;
    private int deaggroRange;

    private long moveTimer;

    public Shelldon(TileMap map, Ooru ooru, int x, int y) {
	super(map);
	this.x = x;
	this.y = y;
	this.ooru = ooru;
	init();
    }

    public void init() {

	moveTimer = 0;

	rand = EnemyHandler.getRand();

	try {

	    BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Mobs/Shelldon.png"));

	    int count = 0;
	    sprites = new ArrayList<BufferedImage[]>();
	    for (int i = 0; i < NUMFRAMES.length; i++) {
		BufferedImage[] bi = new BufferedImage[NUMFRAMES[i]];
		for (int j = 0; j < NUMFRAMES[i]; j++) {
		    bi[j] = spritesheet.getSubimage(j * FRAMEWIDTHS[i], count, FRAMEWIDTHS[i], FRAMEHEIGHTS[i]);
		}
		sprites.add(bi);
		count += FRAMEHEIGHTS[i];
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

	//Attributes
	health = 3;
	maxHealth = 3;
	resistance = 2;

	//Acceleration
	initMoveSpeed = moveSpeed = 0.6;
	aggroSpeed = 1;
	maxSpeed = 0.6;
	stopSpeed = 0.6;

	//Damage
	whiteDamage = 2;
	whiteDmgRange = 26;
	dmgDelay = 1500;
	deaggroRange = 120;

	//Sizes
	width = 26;
	height = 26;
	cwidth = 26;
	cheight = 20;
	hitBox = new Rectangle(0, 0, 0, 0);

	//Direction initialization
	right = true;

	//Looting
	lootTime = 2000;

	//Animation
	animation = new Animation();
	setAnimation(EnemyHandler.IDLE_DOWN);
    }

    public void loot(Ooru ooru) {
	if (!looting) {
	    looting = true;
	    lootTimer = System.currentTimeMillis();

	    //TEST
	    int randInt = rand.nextInt(10);
	    if (randInt % 2 == 0) {
		ooru.getInventory().add(itemRange[1]);
		itemdrop[1] = 1;
	    }
	    if (randInt != 5) {
		ooru.getInventory().add(itemRange[0]);
		itemdrop[0] = 1;
	    }
	}

    }

    private void checkLooted() {
	if (System.currentTimeMillis() - lootTimer > lootTime && dead) {
	    looted = true;
	    looting = false;
	}
    }

    public void update() {
	checkLooted();
	checkRemove();
	if (!dead) {
	    nextMove();
	    getNextPosition();
	    checkTileMapCollision();
	    setPosition(xtemp, ytemp);
	    updateHitBox();
	    animation.update();
	}

    }

    //Path finding to the player if aggro, else random direction after 4 seconds
    private void nextMove() {
	if (!aggro) {
	    if (System.currentTimeMillis() - moveTimer > 4000 || collided) {
		changeDirection();
		checkFacing();
		checkAnimation();
		moveTimer = System.currentTimeMillis();
	    }
	} else {
	    aggroMove();
	    checkFacing();
	    checkAnimation();
	}
	checkAggro();

    }

    public void setAggro(boolean b) {
	aggro = b;
    }

    public void loseHealth(double damage) {
	health -= damage / resistance;
	aggro = true;
	moveSpeed = aggroSpeed;
	maxSpeed = aggroSpeed;
	if (health <= 0) {
	    dead = true;
	    JukeBox.play("enemydeath");
	    loot(ooru);
	}
    }

    protected void aggroMove() {
	int px = ooru.getx();
	int py = ooru.gety();
	if (hitBox.intersects(ooru.getRectangle())) {
	    left = down = up = right = false;
	} else if (collided) {
	    if (right || left) {
		if (py > y) {
		    left = right = up = false;
		    down = true;
		} else {
		    left = right = down = false;
		    up = true;
		}
	    } else if (up || down) {
		if (px > x) {
		    up = down = left = false;
		    right = true;
		} else {
		    up = down = right = false;
		    left = true;
		}
	    }
	} else {
	    if (Math.abs(px - x) > Math.abs(py - y)) {
		if (px > x) {
		    left = down = up = false;
		    right = true;
		} else {
		    right = down = up = false;
		    left = true;
		}

	    } else {
		if (py > y) {
		    down = true;
		    up = left = right = false;
		} else {
		    up = true;
		    down = left = right = false;
		}
	    }
	}
    }

    protected void checkAggro() {
	int px = ooru.getx();
	int py = ooru.gety();
	if (Math.sqrt((px - x) * (px - x) + (py - y) * (py - y)) > deaggroRange && aggro) {
	    aggro = false;
	    resetMoveSpeed();
	}else if(Math.sqrt((px - x) * (px - x) + (py - y) * (py - y)) < deaggroRange && !aggro){
	    aggro = true;
	    moveSpeed = aggroSpeed;
	    maxSpeed = aggroSpeed;
	}
    }

    public boolean isAggro() {
	return aggro;
    }

    private void checkRemove() {
	if (health <= 0 && looted) {
	    remove = true;
	} else {
	    remove = false;
	}
    }

    private void checkFacing() {
	if (right) {
	    facingRight = true;
	    facingUp = false;
	    facingDown = false;
	} else if (left) {
	    facingRight = false;
	    facingDown = false;
	    facingUp = false;
	} else if (up) {
	    facingUp = true;
	    facingDown = false;
	    facingRight = false;
	} else if (down) {
	    facingDown = true;
	    facingUp = false;
	    facingRight = false;
	}
    }

    private void changeDirection() {
	int temp = rand.nextInt(4);
	if (up) {
	    up = false;
	    if (temp == 0) {
		right = true;
		left = false;
		down = false;
	    }
	    if (temp == 1) {
		right = false;
		left = true;
		down = false;
	    }
	    if (temp == 2) {
		right = false;
		left = false;
		down = true;
	    }
	    if (temp == 3) {
		right = left = down = false;
	    }
	} else if (down) {
	    down = false;
	    if (temp == 0) {
		right = true;
		left = false;
		up = false;
	    }
	    if (temp == 1) {
		right = false;
		left = true;
		up = false;
	    }
	    if (temp == 2) {
		right = false;
		left = false;
		up = true;
	    }
	    if (temp == 3) {
		right = left = up = false;
	    }
	} else if (right) {
	    right = false;
	    if (temp == 0) {
		down = true;
		left = false;
		up = false;
	    }
	    if (temp == 1) {
		down = false;
		left = true;
		up = false;
	    }
	    if (temp == 2) {
		down = false;
		left = false;
		up = true;
	    }
	    if (temp == 3) {
		down = left = up = false;
	    }
	} else if (left) {
	    left = false;
	    if (temp == 0) {
		right = true;
		left = false;
		up = false;
	    }
	    if (temp == 1) {
		right = false;
		left = true;
		up = false;
	    }
	    if (temp == 2) {
		right = false;
		left = false;
		up = true;
	    }
	    if (temp == 3) {
		down = right = up = false;
	    }
	} else {
	    if (temp == 0) {
		right = true;
		left = false;
		up = false;
		down = false;
	    }
	    if (temp == 1) {
		right = false;
		left = true;
		up = false;
		down = false;
	    }
	    if (temp == 2) {
		right = false;
		left = false;
		up = true;
		down = false;
	    }
	    if (temp == 3) {
		right = left = up = false;
		down = true;
	    }
	}
    }

    private void setAnimation(int i) {
	currentAction = i;
	animation.setFrames(sprites.get(currentAction));
	animation.setDelay(DELAYS[currentAction]);
    }

    //Only movement for now.
    private void checkAnimation() {
	if (right || left) {
	    if (currentAction != EnemyHandler.WALKING_SIDE) setAnimation(EnemyHandler.WALKING_SIDE);
	} else if (up) {
	    if (currentAction != EnemyHandler.WALKING_UP) setAnimation(EnemyHandler.WALKING_UP);
	} else if (down) {
	    if (currentAction != EnemyHandler.WALKING_DOWN) setAnimation(EnemyHandler.WALKING_DOWN);
	} else {
	    if (facingUp) {
		if (currentAction != EnemyHandler.IDLE_UP) {
		    setAnimation(EnemyHandler.IDLE_UP);
		}
	    } else if (facingDown) {
		if (currentAction != EnemyHandler.IDLE_DOWN) {
		    setAnimation(EnemyHandler.IDLE_DOWN);
		}
	    } else {
		if (currentAction != EnemyHandler.IDLE_SIDE) {
		    setAnimation(EnemyHandler.IDLE_SIDE);
		}
	    }
	}
    }

    public void draw(Graphics2D g) {
	super.draw(g);
	if (looting) {
	    //TEST
	    //Loot window
	    g.setColor(Color.black);
	    g.fillRect((int) (x + xmap - width / 2), (int) (y + ymap - height / 2), width, height);

	    //Loot items
	    for (int i = 0; i < itemdrop.length; i++) {
		if(itemdrop[i] != -1) {
		g.drawImage(itemRange[i].getImage(), (int) (x + xmap - width / 2), (int) (y + ymap - height / 2) + (int) (i*itemRange[i].getHeight()), (int) itemRange[i].getWidth(), (int) itemRange[i].getHeight(), null);
		} 
	    }
	}

	//Health bar
	if (!dead) {
	    g.setColor(Color.RED);
	    g.fillRect((int) (x + xmap - width / 2), (int) (y + ymap - height / 2), width, 3);
	    g.setColor(Color.GREEN);
	    g.fillRect((int) (x + xmap - width / 2), (int) (y + ymap - height / 2), (int) ((width) * (health * 1000 / maxHealth)) / 1000, 3);
	}
    }
}
