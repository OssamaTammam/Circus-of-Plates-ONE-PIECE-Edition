package Game.Controller.Utils;

import Game.Controller.Factories.ShapeFactory;
import Game.Model.Shapes.ImageObject;
import Game.Model.Shapes.Shape;
import Game.Model.Shapes.ShapeState;
import eg.edu.alexu.csd.oop.game.GameObject;

import java.awt.font.GlyphMetrics;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ObjectPool {
    private int width;
    private int height;
    private double averageVelocity;
    private HashMap<GameObject, Long> inUse;
    private HashMap<GameObject, Long> available;

    public ObjectPool(int screenWidth, int screenHeight, double averageVelocity) {
        inUse = new HashMap<>();
        available = new HashMap<>();
        this.width = screenWidth;
        this.height = screenHeight;
        this.averageVelocity = averageVelocity;
    }

    public synchronized GameObject getPlate() {
        long now = System.currentTimeMillis();
        //Reuse shapes
        if (!available.isEmpty()) {
            for (Map.Entry<GameObject, Long> entry : available.entrySet()) {
                GameObject plate = entry.getKey();
                ((ImageObject) plate).setState(new ShapeState(averageVelocity));
                plate.setX((int) (Math.random() * width));
                plate.setY((int) (Math.random() * height / 2) - this.height);
                available.remove(plate);
                inUse.put(plate, now);
                return plate;
            }
        }

        GameObject plate = ShapeFactory.getInstance().getRandomImage((int) Math.random() * width, (int) (Math.random() * height / 3), width, height);
        ((ImageObject) plate).setState(new ShapeState(averageVelocity));
        plate.setX((int) (Math.random() * width));
        plate.setY((int) (Math.random() * height / 2) - this.height);
        inUse.put(plate, now);
        return plate;
    }

    public void releaseShape(GameObject sh) {
        available.put(sh, System.currentTimeMillis());
        inUse.remove(sh);
    }
}
