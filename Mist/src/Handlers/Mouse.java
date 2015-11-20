package Handlers;

import java.awt.Point;

public class Mouse {
    public static Point mousePoint = new Point(0,0);
    
    public static void setMse(Point mse){
	mousePoint = mse;
    }
}
