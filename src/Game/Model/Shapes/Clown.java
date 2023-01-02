package Game.Model.Shapes;

import Game.Controller.Factories.ShapeFactory;
import Game.View.MyWorld;
import Game.Model.Observer.DelegatedObserver;
import eg.edu.alexu.csd.oop.game.GameObject;


import java.util.Observer;
import java.util.Stack;

public class Clown extends ImageObject {
    private Stack<GameObject> left;
    private Stack<GameObject> right;
    private MyWorld myWorld;
    private ImageObject stickLeft;
    private ImageObject stickRight;
    private DelegatedObserver obs = new DelegatedObserver();

    public Clown(int x, int y, String path, int width, int height, MyWorld myWorld) {
        super(x, y, path, width, height);
        left = new Stack<>();
        right = new Stack<>();
        this.myWorld = myWorld;
        stickLeft = new ImageObject(x - (int) Math.round(0.43 * width), (int) (y - Math.round(0.20 * height)),
                "LeftStick.png", (int) Math.round(0.5 * width), (int) Math.round(0.5 * height));
        stickRight = new ImageObject(x + (int) Math.round(0.92 * width), (int) (y - Math.round(0.20 * height)),
                "RightStick.png", (int) Math.round(0.5 * width), (int) Math.round(0.5 * height));
        myWorld.getConstantObjects().add(stickLeft);
        myWorld.getConstantObjects().add(stickRight);
    }

    public boolean checkIntersectAndAdd(GameObject shape) {
        if (intersect(shape, 0)) {
            left.add(shape);
            myWorld.getConstantObjects().add(shape);
            if (left.size() == 1)
                shape.setY(stickLeft.getY() - shape.getHeight() + 7);
            else {
                GameObject top = left.peek();
                shape.setY(top.getY() + top.getHeight() - shape.getHeight());
            }
            if (checkTop(shape, left))
                updateScore(left);
            return true;
        } else if (intersect(shape, 1)) {
            right.add(shape);
            myWorld.getConstantObjects().add(shape);
            if (right.size() == 1)
                shape.setY(stickRight.getY() - shape.getHeight() + 7);
            else {
                GameObject top = right.peek();
                shape.setY(top.getY() + top.getHeight() - shape.getHeight());
            }
            if (checkTop(shape, right))
                updateScore(right);
            return true;
        } else
            return false;
    }

    private boolean checkTop(GameObject shape, Stack<GameObject> stk) {
        int size = stk.size();
        if (size < 3)
            return false;
        GameObject top = stk.get(size - 2);
        GameObject second = stk.get(size - 3);
        if (ShapeFactory.getInstance().isSame(shape, top) && ShapeFactory.getInstance().isSame(shape, second))
            return true;
        return false;
    }

    private void updateScore(Stack<GameObject> stk) {
        GameObject shape1 = stk.pop();
        GameObject shape2 = stk.pop();
        GameObject shape3 = stk.pop();
        myWorld.getObjectPool().releaseShape(shape1);
        myWorld.getObjectPool().releaseShape(shape2);
        myWorld.getObjectPool().releaseShape(shape3);
        myWorld.getConstantObjects().remove(shape1);
        myWorld.getConstantObjects().remove(shape2);
        myWorld.getConstantObjects().remove(shape3);
        obs.setChanged();
        obs.notifyObservers();
    }

    private boolean intersect(GameObject o1, int choice) {
        // 0 for left, 1 for right
        GameObject o2 = null;
        int midX = o1.getX() + o1.getWidth() / 2;
        int y = o1.getY() + o1.getHeight();
        int z = 1;
        if (choice == 0) {
            if (left.isEmpty()) {
                o2 = stickLeft;
                z = 2;
            } else {
                o2 = left.peek();
            }
        } else if (choice == 1) {
            if (right.isEmpty()) {
                o2 = stickRight;
                return ((o2.getX() + o2.getWidth() / 2) <= midX && midX <= (o2.getX() + o2.getWidth()) && Math.abs(o2.getY() - y) < 5);
            } else {
                o2 = right.peek();
            }
        }
        return (o2.getX() <= midX && midX <= (o2.getX() + o2.getWidth() / z) && Math.abs(o2.getY() - y) < 5);
//        return (Math.abs((o1.getX() + o1.getWidth() / 2) - (o2.getX() + o2.getWidth() / 2)) <= o1.getWidth())
//                && (Math.abs((o1.getY() + o1.getHeight() / 2) - (o2.getY() + o2.getHeight() / 2)) <= o1.getHeight());
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
}