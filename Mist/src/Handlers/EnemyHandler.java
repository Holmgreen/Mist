package Handlers;

import java.util.Random;

public class EnemyHandler {
    private static Random rand = new Random();
    
    public static final int WALKING_DOWN = 0;
    public static final int WALKING_SIDE = 1;
    public static final int WALKING_UP = 2;
    public static final int ATTACKING_DOWN = 3;
    public static final int ATTACKING_SIDE = 4;
    public static final int ATTACKING_UP = 5;
    public static final int IDLE_DOWN = 6;
    public static final int IDLE_SIDE = 7;
    public static final int IDLE_UP = 8;
    
    public static Random getRand() {
	return rand;
    }
}
