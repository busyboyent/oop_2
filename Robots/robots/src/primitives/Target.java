package primitives;

import java.awt.*;

public class Target {
    private int X;
    private int Y;

    public Target(int x, int y){
        X = x;
        Y = y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void setTargetPosition(Point p)
    {
        X = p.x;
        Y = p.y;
    }
}
