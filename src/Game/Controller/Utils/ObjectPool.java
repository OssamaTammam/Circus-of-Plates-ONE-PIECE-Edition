package Game.Controller.Utils;

import Game.Controller.Factories.ShapeFactory;
import Game.Model.Shapes.ImageObject;
import Game.Model.Shapes.ShapeState;
import eg.edu.alexu.csd.oop.game.GameObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Object Pool Design Pattern
 * This class is responsible for creating the shapes that will be used in the game
 */
public class ObjectPool {
    final private int screenWidth;
    final private int screenHeight;
    final private double averageVelocity; //Average falling velocity of the shapes
    final private List<GameObject> inUse; //Shapes that are currently in use
    final private List<GameObject> available; //Shapes that are currently on standby

    public ObjectPool(int screenWidth, int screenHeight, double averageVelocity) {

        inUse = new LinkedList<>();
        available = new LinkedList<>();
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.averageVelocity = averageVelocity;
    }

    /**
     * Get a shape from the pool whether it's new or reused from the available list
     *
     * @return GameObject plate that will be used in the game
     */
    public synchronized GameObject getPlate() {

        //Reuse shapes from the available list
        if (!available.isEmpty()) {

            for (GameObject availablePlate : available) {

                availablePlate.setX((int) (Math.random() * screenWidth));
                availablePlate.setY((int) (Math.random() * screenHeight / 2) - this.screenHeight);
                available.remove(availablePlate);
                inUse.add(availablePlate);
                return availablePlate;
            }
        }

        GameObject newPlate = ShapeFactory.getInstance().getRandomImage((int) (Math.random() * screenWidth), (int) (Math.random() * screenHeight / 3) - this.screenHeight, 50, 40);
        ((ImageObject) newPlate).setState(new ShapeState(averageVelocity));
        inUse.add(newPlate);
        return newPlate;
    }

    /**
     * Return a shape to the pool
     *
     * @param plate GameObject to be returned to the pool
     */
    public void releaseShape(GameObject plate) {
        available.add(plate);
        inUse.remove(plate);
    }
}
