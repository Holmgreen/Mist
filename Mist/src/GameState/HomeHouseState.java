package GameState;

public class HomeHouseState extends HouseState{

    public HomeHouseState(GameStateManager gsm) {
	super(gsm);
	init();
    }
    
    public void init() {
	super.init();
	map.loadMap("/Worlds/homehouse.txt");
	map.loadTiles("/Tiles/hometileset32.png");
	map.setTween(1);
	setExit();
	exitState = GameStateManager.HOMEWORLD;
	ooru.setPosition(startx + 16, starty - 16);
    }

}
