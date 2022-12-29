package Game.View;

import Game.Controller.Logging;
import Game.Model.Shapes.Clown;
import Game.Model.Shapes.ClownWrapper;
import Game.Model.Shapes.ImageObject;
import Game.Model.Shapes.Shape;
import Game.Controller.Utils.ObjectPool;
import Game.Controller.Utils.Score;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MyWorld implements World {
    private static int MAX_TIME =  30 * 1000;    // 1 minute
    private Score score;
    private long startTime = System.currentTimeMillis();
    private final int width;
    private final int height;
    private List<GameObject> constant = new LinkedList<GameObject>();
    private final List<GameObject> moving = new LinkedList<GameObject>();
    private final List<GameObject> control = new LinkedList<GameObject>();
    private ObjectPool objectPool;
    private int activeCount;
    private long waveTime;
    private long lastWave;
    private Random rand = new Random();
    private int shelfLevel;
    private final double distBetwnRod = 0.08;
    private final double scaleHeightRod = 0.009;
    private final double scaleWidthRod = 5 / 18.0;
    private final double scaleBtnRods = 2;
    private int clowns;
    private final long deadTime = 10 * 1000;
    private Start start;
    Logging log = new Logging();

    public MyWorld(int screenWidth, int screenHeight, int activeCount, double averageVelocity, int diffShapes, int waveTime, int shelfLevel, int clowns, int maxScore, Start start) {
        width = screenWidth;
        height = screenHeight;
        this.activeCount = activeCount;
        this.waveTime = waveTime * 1000;
        this.shelfLevel = shelfLevel;
        this.clowns = clowns;
        this.start = start;
        constant.add(new ImageObject(0, 0, "Background.jpg", width, height));
        score = new Score(maxScore);
        objectPool = new ObjectPool(deadTime, width, height, averageVelocity, diffShapes, shelfLevel, distBetwnRod);
        initializeShelves();
        initializeClowns();
        int spawnFirst = rand.nextInt(activeCount);
        this.activeCount -= spawnFirst;
        for (int i = 0; i < spawnFirst; i++)
            moving.add(objectPool.getShape());
        lastWave = System.currentTimeMillis();
    }

    private void initializeClowns() {
        ClownWrapper cw = new ClownWrapper(width, this);
        for (int i = 0; i < clowns; i++) {
            Clown cl = new Clown((int) Math.round(1.0 * (i + 1) / (clowns + 1) * width) - (int) Math.round(width * 0.10) / 2, height - (int) Math.round(height * 0.25), "clown.png", (int) Math.round(width * 0.10), (int) Math.round(height * 0.23), this);
            cl.addObserver(score);
            cw.addClown(cl);
        }
        cw.addToWorld();
    }

    private void initializeShelves() {
        int w = (int) Math.round(scaleWidthRod * width);
        int h = (int) Math.round(distBetwnRod * height);
        for (int i = 0; i < shelfLevel; i++) {
            constant.add(new ImageObject(0, h, "rod.png", w, (int) Math.round(scaleHeightRod * height)));
            w /= scaleBtnRods;
            h += (int) Math.round(distBetwnRod * height);
        }
        w = (int) Math.round(scaleWidthRod * width);
        h = (int) Math.round(distBetwnRod * height);
        for (int i = 0; i < shelfLevel; i++) {
            constant.add((new ImageObject((int) (width - w), h, "rod.png", w, (int) Math.round(scaleHeightRod * height))));
            w /= scaleBtnRods;
            h += (int) Math.round(distBetwnRod * height);

        }
    }

    private void changeState(Shape s) {
        int shelfNumber = Math.round(s.getY() + s.getHeight()) / (int) Math.round(distBetwnRod * height);
        if (s.getState().getVelocityX() > 0 && s.getX() + s.getWidth() / 2 > constant.get(shelfNumber).getX() + constant.get(shelfNumber).getWidth()) {
            s.getState().setParameters(0.005, 0.001, 0.2);
        } else if (s.getState().getVelocityX() < 0 && s.getX() + s.getWidth() / 2 < constant.get(shelfNumber + shelfLevel).getX()) {
            s.getState().setParameters(0.005, 0.001, 0.2);
        }
    }

    @Override
    public boolean refresh() {
        boolean timeout = System.currentTimeMillis() - startTime > MAX_TIME; // time end and game over
        if (score.getStatues()) {
            start.setLevel(score.getScore() + 2);
        }
        if (timeout) {
            start.setLevel(10);
            return false;
        }
        long timeSinceLastWave = System.currentTimeMillis() - lastWave;
        List<GameObject> toRemove = new ArrayList<>();
        for (GameObject m : moving) {
            Shape s = (Shape) m;
            s.move();
            if (Math.abs(s.getState().getAcceleration()) < 1e-9) {
                changeState(s);
            }
            for (Clown t : ((ClownWrapper) control.get(0)).getClowns()) {
                if (t.checkIntersectAndAdd(m)) {
                    activeCount++;
                    toRemove.add(m);
                    log.help().info("clown got " + s.getClass().getName());
                }

            }
        }
        if (timeSinceLastWave > waveTime && activeCount > 0) {
            int spawnFirst = Math.min(rand.nextInt(activeCount) + 1, activeCount);
            activeCount -= spawnFirst;
            for (int i = 0; i < spawnFirst; i++)
                moving.add(objectPool.getShape());
            lastWave = System.currentTimeMillis();
        }
        for (GameObject m : moving) {
            if (m.getY() > height) {
                toRemove.add(m);
                objectPool.releaseShape((Shape) m);
                activeCount++;
                log.help().info(((Shape) m).getClass().getName() + " is broken");
            }
        }
        for (GameObject m : toRemove) {
            moving.remove(m);
        }
        return !timeout;
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
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public String getStatus() {
        return "Score=" + score.getScore() + "   |   Time=" + Math.max(0, (MAX_TIME - (System.currentTimeMillis() - startTime)) / 1000);    // update status
    }
}