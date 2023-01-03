package Game.Controller.Utils;

import eg.edu.alexu.csd.oop.game.GameObject;

import java.util.Iterator;
import java.util.List;

/**
 * Iterator Design Pattern
 * This class is responsible for iterating over the shapes in the game
 */
public class GameObjectIterator implements Iterator {
    private List<GameObject> gameObjects;
    private int index;

    public GameObjectIterator(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
        index = 0;
    }

    /**
     * Check if there is a next shape in the game
     *
     * @return true if there is a next shape, false otherwise
     */
    @Override
    public boolean hasNext() {
        return index < gameObjects.size();
    }

    /**
     * Get the next shape in the game
     *
     * @return GameObject next shape in the game
     */
    @Override
    public GameObject next() {
        if (hasNext()) {
            return gameObjects.get(index++);
        }
        return null;
    }
}

