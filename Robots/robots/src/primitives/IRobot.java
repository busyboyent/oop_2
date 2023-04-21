package primitives;

public interface IRobot {
    double getX();
    double getY();
    double getDirection();
    void move(double duration, int targetX, int targetY, double distance);
}
