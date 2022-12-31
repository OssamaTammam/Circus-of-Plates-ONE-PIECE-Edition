package Game.Model.Shapes;

public class ShapeState {
    private double speed;

    public ShapeState(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void move(ImageObject s) {
        int y = s.getY();
        int newY = (int) Math.round(y + speed);
        s.setY(newY);
    }


    public ShapeState clone() {
        return new ShapeState(speed);
    }
}