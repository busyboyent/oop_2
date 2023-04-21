package primitives;

public class DefaultRobot implements IRobot {
    private double X;
    private double Y;
    private double direction;

    public DefaultRobot(double x, double y){
        X = x;
        Y = y;
        direction = 0;
    }

    @Override
    public double getX() {
        return X;
    }

    @Override
    public double getY() {
        return Y;
    }

    @Override
    public double getDirection() {
        return direction;
    }

    @Override
    public void move(double duration, int targetX, int targetY, double distance) {

    }
}
