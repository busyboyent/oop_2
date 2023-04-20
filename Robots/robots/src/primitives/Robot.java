package primitives;

import static tools.Helper.asNormalizedRadians;

public class Robot {

    private double X;
    private double Y;
    private double direction;
    private double velocity;

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.001;

    public Robot(double x, double y){
        X = x;
        Y = y;
        direction = 0;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction){
        this.direction = direction;
    }

    public void setPosition(double x, double y){
        X = x;
        Y = y;
    }

    public void move(double duration, int targetX, int targetY, double distance)
    {
        velocity = maxVelocity;
        var angularVelocity = getAngularVelocity(targetX, targetY, distance);
        double newX = X + maxVelocity / angularVelocity *
                (Math.sin(direction  + angularVelocity * duration) -
                        Math.sin(direction));
        if (!Double.isFinite(newX))
        {
            newX = X + velocity * duration * Math.cos(direction);
        }
        double newY = Y - velocity / angularVelocity *
                (Math.cos(direction  + angularVelocity * duration) -
                        Math.cos(direction));
        if (!Double.isFinite(newY))
        {
            newY = Y + velocity * duration * Math.sin(direction);
        }
        X = newX;
        Y = newY;
        direction = asNormalizedRadians(direction + angularVelocity * duration);
    }

    private double getAngularVelocity(int targetX, int targetY, double distance){

        double angleToTarget = angleTo(X, Y, targetX, targetY);
        double angularVelocity = 0;
        var angleFromTargetToRobot = asNormalizedRadians(angleToTarget - direction);

        if (angleFromTargetToRobot > Math.PI)
        {
            angularVelocity = -maxAngularVelocity;
        }
        else if (angleFromTargetToRobot < Math.PI)
        {
            angularVelocity = maxAngularVelocity;
        }

        if (Math.abs(angleFromTargetToRobot) >= 0.1){
            velocity = angularVelocity * distance/2;
        }
        return applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
    }

    private static double applyLimits(double value, double min, double max)
    {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY)
    {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

}
