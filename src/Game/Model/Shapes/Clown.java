package Game.Model.Shapes;

import Game.Controller.Factories.ShapeFactory;
import Game.View.MyWorld;
import Game.Model.Observer.DelegatedObserver;
import eg.edu.alexu.csd.oop.game.GameObject;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Observer;

public class Clown extends ImageObject implements Cloneable {
    private LinkedList<GameObject> left;
    private LinkedList<GameObject> right;
    private MyWorld myWorld;
    private ImageObject stickLeft;
    private ImageObject stickRight;
    private DelegatedObserver obs = new DelegatedObserver();

    public Clown(int x, int y, String path, int width, int height, MyWorld myWorld) {
        super(x, y, path, width, height);
        left = new LinkedList<>();
        right = new LinkedList<>();
        this.myWorld = myWorld;
        stickLeft = new ImageObject(x - (int) Math.round(0.43 * width), (int) (y - Math.round(0.20 * height)),
                "LeftStick.png", (int) Math.round(0.5 * width), (int) Math.round(0.5 * height));
        stickRight = new ImageObject(x + (int) Math.round(0.92 * width), (int) (y - Math.round(0.20 * height)),
                "RightStick.png", (int) Math.round(0.5 * width), (int) Math.round(0.5 * height));
        myWorld.getConstantObjects().add(stickLeft);
        myWorld.getConstantObjects().add(stickRight);
    }

    public Clown(int x, int y, BufferedImage[] sprite, ImageObject stickLeft, ImageObject stickRight,
            LinkedList<GameObject> left, LinkedList<GameObject> right, DelegatedObserver obs,
            MyWorld myWorld) {
        super(x, y, sprite);
        this.obs = obs;
        this.stickLeft = stickLeft;
        this.stickRight = stickRight;
        this.left = left;
        this.right = right;
        this.myWorld = myWorld;
    }

    public boolean checkIntersectAndAdd(GameObject shape) {
        int midX = shape.getX() + shape.getWidth() / 2;
        int y = shape.getY() + shape.getHeight();
        // check if the shape is falling on the left stick then add it to stack if it does
        if (left.isEmpty()) {
            if (stickLeft.getX() <= midX && y >= stickLeft.getY()) {
                shape.setY(stickLeft.getY() - shape.getHeight());
                return addShape(shape, left);
            }
        } else {
            RemoveFromStk(left);
            AddToStk(left); // to make sure that the shape is at the top of the stack
            GameObject top = left.peekLast();
            if (top != null) {
                if (top.getX() <= midX &&  y >= top.getY()) {
                    shape.setY(top.getY() - shape.getHeight());
                    return addShape(shape, left);
                }
            }
        }
        if (right.isEmpty()) {
            if (stickRight.getX() <= midX  && y >= stickRight.getY()) {
                shape.setY(stickRight.getY() - shape.getHeight());
                return addShape(shape, right);
            }
        } else {
            RemoveFromStk(right);
            AddToStk(right);
            GameObject top = right.peekLast();
            if (top != null) {
                if (top.getX() <= midX && y >= top.getY()) {
                    shape.setY(top.getY() - shape.getHeight());
                    return addShape(shape, right);
                }
            }
        }
        return false;
    }

    public boolean addShape(GameObject shape, LinkedList<GameObject> stk) {
        if (stk.size() >= 2 && checkTop(0, shape, stk)) {
            Shape sh1 = (Shape) stk.removeLast();
            Shape sh2 = (Shape) stk.removeLast();
            myWorld.getObjectPool().releaseShape(sh1);
            myWorld.getObjectPool().releaseShape(sh2);
            myWorld.getConstantObjects().remove(sh1);
            myWorld.getConstantObjects().remove(sh2);
            obs.setChanged();
            obs.notifyObservers();
        } else {
            stk.add(shape);
            myWorld.getConstantObjects().add(shape);
        }
        return true;
    }

    private void RemoveFromStk(LinkedList<GameObject> stk) {
        while (stk.size() > 0) {
            if (!myWorld.getConstantObjects().contains(stk.getLast())) {
                stk.remove(stk.size() - 1);
            } else
                break;
            ;
        }
    }

    private void AddToStk(LinkedList<GameObject> stk) {
        int i = myWorld.getConstantObjects().size() - 1;
        while (i > 0) {
            if (!stk.contains(myWorld.getConstantObjects().get(i)))
                i--;
            break;
        }

        for (int j = i + 1; j < myWorld.getConstantObjects().size(); j++) {
            GameObject gameObject = myWorld.getConstantObjects().get(j);
            int midX = gameObject.getX() + gameObject.getWidth() / 2;
            GameObject top = stk.peekLast();
            if (top != null) {
                if (top.getX() <= midX && midX <= (top.getX() + top.getWidth()) && Math.abs(top.getY() - y) < 15) {
                    gameObject.setY(top.getY() - gameObject.getHeight());
                    stk.add(gameObject);
                }

            }
        }
    }

    private boolean checkTop(int n, GameObject shape, LinkedList<GameObject> stk) {
        if (n == 2)
            return true;
        GameObject p = stk.removeLast();
        boolean flag = false;
        if (ShapeFactory.getInstance().equalColor(p, shape)) {
            flag = checkTop(n + 1, shape, stk);
        }
        stk.add(p);
        return flag;
    }

    @Override
    public void setX(int x) {
        int vec = x - this.x;
        if (stickLeft.getX() + (x - this.x) <= 0) {
            vec = (-stickLeft.getX());
        } else if (stickRight.getX() + stickRight.getWidth() + (x - this.x) >= myWorld.getWidth()) {
            vec = myWorld.getWidth() - (stickRight.getX() + stickRight.getWidth());
        }
        stickLeft.setX(stickLeft.getX() + vec);
        stickRight.setX(stickRight.getX() + vec);
        for (GameObject o : right) {
            o.setX(o.getX() + vec);
        }
        for (GameObject o : left) {
            o.setX(o.getX() + vec);
        }
        this.x += vec;
    }

    @Override
    public void setY(int Y) {
        return;
    }

    public int getMaxLeft() {
        return stickLeft.getX();
    }

    public int getMaxRight() {
        return (stickRight.getX() + stickRight.getWidth());
    }

    public void addObserver(Observer ob) {
        obs.addObserver(ob);
    }

    public void removeObserver(Observer ob) {
        obs.deleteObserver(ob);
    }

    public void addToWorld() {
        myWorld.getConstantObjects().add(stickLeft);
        myWorld.getConstantObjects().add(stickRight);
        myWorld.getConstantObjects().add(this);
        for (GameObject o : left) {
            myWorld.getConstantObjects().add(o);
        }
        for (GameObject o : right) {
            myWorld.getConstantObjects().add(o);
        }
    }

    @Override
    public GameObject clone() {
        LinkedList<GameObject> cpyleft = new LinkedList<>();
        LinkedList<GameObject> cpyRight = new LinkedList<>();
        for (GameObject o : this.left) {
            cpyleft.add(((Cloneable) o).clone());
        }
        for (GameObject o : this.right) {
            cpyRight.add(((Cloneable) o).clone());
        }
        return new Clown(x, y, images, (ImageObject) stickLeft.clone(), (ImageObject) stickRight.clone(), cpyleft,
                cpyRight, obs, myWorld);
    }
}