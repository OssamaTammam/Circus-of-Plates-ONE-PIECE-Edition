package Game.Model.Shapes;

import Game.View.OnePiece;
import eg.edu.alexu.csd.oop.game.GameObject;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class ClownWrapper implements GameObject {
    List<Clown> clowns = new ArrayList<>();
    int width;
    int x;
    OnePiece onePiece;

    public ClownWrapper(int width, OnePiece world) {
        this.onePiece = world;
        this.width = width;
        this.x = (int) Math.round(width / 2.0);
    }

    public void addClown(Clown clown) {
        clowns.add(clown);
    }

    public List<Clown> getClowns() {
        return clowns;
    }

    @Override
    public int getX() {
        return x;
    }


    /**
     * Changes the X coordinate of the clown wrapper
     *
     * @param x new X coordinate
     */
    @Override
    public void setX(int x) {
        int vec = x - this.x; //Vector of the change in X

        Clown leftClown = clowns.get(0);
        Clown rightClown = clowns.get(clowns.size() - 1);

        //بتخلي سانجي تفضل ع نفس المسافة من بعضها
        //Keeps clown wrapper in frame
        if (leftClown.getMaxLeft() + vec <= 0) {
            vec = (-leftClown.getMaxLeft());
        } else if (rightClown.getMaxRight() + vec >= width) {
            vec = width - (rightClown.getMaxRight());
        }

        //Moves all clowns in the wrapper
        for (Clown c : clowns) {
            c.setX(c.getX() + vec);
        }

        //Moves the wrapper
        this.x += vec;
    }

    @Override
    public BufferedImage[] getSpriteImages() {

        return new BufferedImage[]{new BufferedImage(1, 1, 1)};
    }

    /**
     * Adds all clowns in the wrapper & the wrapper to the world
     */
    public void addToWorld() {
        onePiece.getControlableObjects().add(this);
        for (Clown c : getClowns()) {
            c.addToWorld();
        }
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public void setY(int y) {
        return;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public boolean isVisible() {
        return false;
    }
}
