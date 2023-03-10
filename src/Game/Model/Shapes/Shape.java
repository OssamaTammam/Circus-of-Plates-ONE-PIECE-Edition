package Game.Model.Shapes;

import eg.edu.alexu.csd.oop.game.GameObject;

public interface Shape extends GameObject {
    public void setVisible(boolean visible);

    public void move();

    public ShapeState getState();

    public void setState(ShapeState state);

}
