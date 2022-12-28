package eg.edu.alexu.csd.oop.Circus.Shapes;

import eg.edu.alexu.csd.oop.game.GameObject;

public interface Shape  extends GameObject {
    public void setVisible(boolean visible);
    public void move();
    public ShapeState getState();
    public void setState(ShapeState state);
    public int getScreenWidth();
    public int getScreenHeight();
    public void setRandomImage();
}
