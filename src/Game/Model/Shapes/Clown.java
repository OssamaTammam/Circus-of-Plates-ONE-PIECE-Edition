package Game.Model.Shapes;

import Game.Controller.Factories.ShapeFactory;
import Game.View.OnePiece;
import Game.Model.Observer.DelegatedObserver;
import eg.edu.alexu.csd.oop.game.GameObject;

import java.util.Observer;
import java.util.Stack;

/**
 * This class is responsible for creating the clown that will be used in the game
 */
public class Clown extends ImageObject {
    private Stack<GameObject> leftPlateStack;
    private Stack<GameObject> rightPlateStack;
    private OnePiece onePiece;
    private ImageObject stickLeft;
    private ImageObject stickRight;
    private DelegatedObserver obs = new DelegatedObserver();

    public Clown(int x, int y, String path, int width, int height, OnePiece onePiece) {

        super(x, y, path, width, height);
        leftPlateStack = new Stack<>();
        rightPlateStack = new Stack<>();
        this.onePiece = onePiece;
        stickLeft = new ImageObject(x - (int) Math.round(0.43 * width), (int) (y - Math.round(0.20 * height)),
                "LeftStick.png", (int) Math.round(0.5 * width), (int) Math.round(0.5 * height));
        stickRight = new ImageObject(x + (int) Math.round(0.92 * width), (int) (y - Math.round(0.20 * height)),
                "RightStick.png", (int) Math.round(0.5 * width), (int) Math.round(0.5 * height));
        onePiece.getConstantObjects().add(stickLeft);
        onePiece.getConstantObjects().add(stickRight);
    }

    /**
     * This method is responsible for checking intersection then adding a plate to the stack
     *
     * @param plate plate that will be added to the left stack
     * @return returns true if the plate is added to the stack
     */
    public boolean checkIntersectAndAdd(GameObject plate) {
        //0 for left, 1 for right
        if (intersect(plate, 0)) {

            leftPlateStack.add(plate);
            onePiece.getConstantObjects().add(plate);

            //Checks if it intersects with the top element of the stack or the stick
            if (leftPlateStack.size() == 1)
                plate.setY(stickLeft.getY() - plate.getHeight() + 7);
            else {
                GameObject top = leftPlateStack.peek();
                plate.setY(top.getY() + top.getHeight() - plate.getHeight());
            }

            //Checks the top 3 elements of the stack to see if they are the same then updates the score
            if (checkTop(plate, leftPlateStack))
                updateScore(leftPlateStack);

            return true;
        } else if (intersect(plate, 1)) {

            rightPlateStack.add(plate);
            onePiece.getConstantObjects().add(plate);

            //Checks if it intersects with the top element of the stack or the stick
            if (rightPlateStack.size() == 1)
                plate.setY(stickRight.getY() - plate.getHeight() + 7);
            else {
                GameObject top = rightPlateStack.peek();
                plate.setY(top.getY() + top.getHeight() - plate.getHeight());
            }

            //Checks the top 3 elements of the stack to see if they are the same then updates the score
            if (checkTop(plate, rightPlateStack))
                updateScore(rightPlateStack);

            return true;
        } else
            return false;
    }

    /**
     * This method is responsible for checking if the top 3 elements of the stack are the same
     *
     * @param plate plate that will be added to the stack
     * @param stack stack that will be checked
     * @return returns true if the top 3 elements of the stack are the same
     */
    private boolean checkTop(GameObject plate, Stack<GameObject> stack) {

        int size = stack.size();

        //Checks if the stack has at least 3 elements
        if (size < 3)
            return false;

        GameObject top = stack.get(size - 2);
        GameObject secondTop = stack.get(size - 3);

        if (ShapeFactory.getInstance().isSame(plate, top) && ShapeFactory.getInstance().isSame(plate, secondTop))
            return true;
        return false;
    }

    /**
     * This method is responsible for updating the score
     *
     * @param stack stack that will be checked
     */
    private void updateScore(Stack<GameObject> stack) {

        //Removes the top 3 elements of the stack
        GameObject shape1 = stack.pop();
        GameObject shape2 = stack.pop();
        GameObject shape3 = stack.pop();

        //Removes the 3 elements from the world
        onePiece.getObjectPool().releaseShape(shape1);
        onePiece.getObjectPool().releaseShape(shape2);
        onePiece.getObjectPool().releaseShape(shape3);
        onePiece.getConstantObjects().remove(shape1);
        onePiece.getConstantObjects().remove(shape2);
        onePiece.getConstantObjects().remove(shape3);

        //Updates the score by notifying observers
        obs.setChanged();
        obs.notifyObservers();
    }

    /**
     * This method is responsible for checking intersection
     *
     * @param plate plate that will be checked
     * @param side  0 for left, 1 for right
     * @return returns true if the plate intersects with the stick
     */
    private boolean intersect(GameObject plate, int side) {
        // 0 for left, 1 for right

        GameObject stackTop = null;
        int midX = plate.getX() + plate.getWidth() / 2; //Midpoint of the plate
        int y = plate.getY() + plate.getHeight(); //Bottom of the plate

        if (side == 0) {
            if (leftPlateStack.isEmpty()) {
                stackTop = stickLeft;
                return (stackTop.getX() <= midX && midX <= (stackTop.getX() + stackTop.getWidth() / 2) && Math.abs(stackTop.getY() - y) < 8);
            } else {
                stackTop = leftPlateStack.peek();
            }
        } else if (side == 1) {
            if (rightPlateStack.isEmpty()) {
                stackTop = stickRight;
                return ((stackTop.getX() + stackTop.getWidth() / 2) <= midX && midX <= (stackTop.getX() + stackTop.getWidth()) && Math.abs(stackTop.getY() - y) < 8);
            } else {
                stackTop = rightPlateStack.peek();
            }
        }
        return (stackTop.getX() <= midX && midX <= (stackTop.getX() + stackTop.getWidth()) && Math.abs(stackTop.getY() - y) < 8);
    }

    /**
     * This method is responsible for the setting a new X coordinate for the clown and both the stacks
     *
     * @param x new X coordinate
     */
    @Override
    public void setX(int x) {

        int vec = x - this.x; //Vector of the change in X

        //Keeps clown in frame
        if (stickLeft.getX() + vec <= 0) {
            vec = (-stickLeft.getX());
        } else if (stickRight.getX() + stickRight.getWidth() + vec >= onePiece.getWidth()) {
            vec = onePiece.getWidth() - (stickRight.getX() + stickRight.getWidth());
        }

        //Updates the X coordinate of the sticks
        stickLeft.setX(stickLeft.getX() + vec);
        stickRight.setX(stickRight.getX() + vec);

        //Updates the X coordinate of the stacks
        for (GameObject plate : rightPlateStack) {
            plate.setX(plate.getX() + vec);
        }
        for (GameObject plate : leftPlateStack) {
            plate.setX(plate.getX() + vec);
        }

        //Updates the X coordinate of the clown
        this.x += vec;
    }

    /**
     * This method is responsible for adding the clown, sticks and the stacks to the world
     */
    public void addToWorld() {

        //Adds clown and sticks to constant objects
        onePiece.getConstantObjects().add(stickLeft);
        onePiece.getConstantObjects().add(stickRight);
        onePiece.getConstantObjects().add(this);

        //Adds stack elements to constant objects
        for (GameObject plate : leftPlateStack) {
            onePiece.getConstantObjects().add(plate);
        }
        for (GameObject plate : rightPlateStack) {
            onePiece.getConstantObjects().add(plate);
        }
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
}