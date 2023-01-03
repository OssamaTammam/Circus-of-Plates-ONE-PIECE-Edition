package Game.View;

import Game.Model.Shapes.Clown;
import Game.Model.Shapes.ClownWrapper;
import Game.Model.Shapes.ImageObject;
import Game.Controller.Utils.ObjectPool;
import Game.Controller.Utils.Score;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class OnePiece implements World {
    private static int MAX_TIME = 95 * 1000;    // 1.5 minutes
    private Score score;
    private long startTime = System.currentTimeMillis();
    private final int screenWidth;
    private final int screenHeight;
    private List<GameObject> constant = new LinkedList<GameObject>();
    private final List<GameObject> moving = new LinkedList<GameObject>();
    private final List<GameObject> control = new LinkedList<GameObject>();
    private ObjectPool objectPool;
    private int activeCount;
    private long waveTime;
    private long lastWave; // Time of last wave
    private Random rand = new Random();
    private int nClowns; // Number of clowns
    private Start start;

    public OnePiece(int screenWidth, int screenHeight, int activeCount, double averageVelocity, int waveTime, int nClowns, int maxScore, Start start) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.activeCount = activeCount;
        this.waveTime = waveTime * 1000;
        this.nClowns = nClowns;
        this.start = start;
        constant.add(new ImageObject(0, 0, "Background.jpg", this.screenWidth, this.screenHeight));
        score = new Score(maxScore);
        objectPool = new ObjectPool(this.screenWidth, this.screenHeight, averageVelocity);
        initializeClowns();
        int spawnFirst = rand.nextInt(activeCount);
        this.activeCount -= spawnFirst;
        for (int i = 0; i < spawnFirst; i++)
            moving.add(objectPool.getPlate());
        lastWave = System.currentTimeMillis();
    }

    /**
     * Initialize clowns and add them to the world
     */
    private void initializeClowns() {
        ClownWrapper cw = new ClownWrapper(screenWidth, this);
        for (int i = 0; i < nClowns; i++) {
            Clown cl = new Clown((int) Math.round(1.0 * (i + 1) / (nClowns + 1) * screenWidth) - (int) Math.round(screenWidth * 0.10) / 2, screenHeight - (int) Math.round(screenHeight * 0.25), "clown.png", (int) Math.round(screenWidth * 0.10), (int) Math.round(screenHeight * 0.23), this);
            cl.addObserver(score);
            cw.addClown(cl);
        }
        cw.addToWorld();
    }

    @Override
    public boolean refresh() {
        boolean timeout = System.currentTimeMillis() - startTime > MAX_TIME; // Time end and game over

        // Check if the player won
        if (score.getStatues()) {
            start.setLevel(score.getScore() - 1);
        }

        // Check if the player lost
        if (timeout) {
            start.setLevel(10);
            return false;
        }

        // Check if it's time to spawn a new wave
        long timeSinceLastWave = System.currentTimeMillis() - lastWave;

        List<GameObject> toRemove = new ArrayList<>(); // List of objects to remove from the world

        //Checks if the clown caught a falling plate
        for (GameObject fallingObject : moving) {
            ImageObject s = (ImageObject) fallingObject;
            s.move();
            for (Clown clown : ((ClownWrapper) control.get(0)).getClowns()) {
                if (clown.checkIntersectAndAdd(fallingObject)) {
                    activeCount++;
                    toRemove.add(fallingObject);
                }
            }
        }

        //Spawns new wave
        if (timeSinceLastWave > waveTime && activeCount > 0) {
            int spawnFirst = Math.min(rand.nextInt(activeCount) + 1, activeCount);
            activeCount -= spawnFirst;
            for (int i = 0; i < spawnFirst; i++)
                moving.add(objectPool.getPlate());
            lastWave = System.currentTimeMillis();
        }

        // Remove objects from the world if it's out of bounds
        for (GameObject m : moving) {
            if (m.getY() > screenHeight) {
                toRemove.add(m);
                objectPool.releaseShape(m);
                activeCount++;
            }
        }

        // Remove objects from the world
        for (GameObject m : toRemove) {
            moving.remove(m);
        }
        return true;
    }

    public ObjectPool getObjectPool() {
        return objectPool;
    }

    @Override
    public int getSpeed() {
        return 20;
    }

    @Override
    public int getControlSpeed() {
        return 20;
    }

    @Override
    public List<GameObject> getConstantObjects() {
        return constant;
    }

    @Override
    public List<GameObject> getMovableObjects() {
        return moving;
    }

    @Override
    public List<GameObject> getControlableObjects() {
        return control;
    }

    @Override
    public int getWidth() {
        return screenWidth;
    }

    @Override
    public int getHeight() {
        return screenHeight;
    }

    @Override
    public String getStatus() {
        return "Score=" + score.getScore() + "   |   Time=" + Math.max(0, (MAX_TIME - (System.currentTimeMillis() - startTime)) / 1000);    // update status
    }
}