package Game.Model.Shapes;

/**
 * Strategy Design Pattern
 * This class is responsible for the state of the shapes
 */
public class ShapeState {
    private double fallingSpeed; //Falling speed of the shape

    public ShapeState(double fallingSpeed) {
        this.fallingSpeed = fallingSpeed;
    }

    /**
     * Move the shape vertically by constantly adding the speed to the Y coordinate
     *
     * @param shape falling shape
     */
    public void move(ImageObject shape) {
        int y = shape.getY();
        int newY = (int) Math.round(y + fallingSpeed);
        shape.setY(newY);
    }

    public double getFallingSpeed() {
        return fallingSpeed;
    }

    public void setFallingSpeed(double fallingSpeed) {
        this.fallingSpeed = fallingSpeed;
    }
}