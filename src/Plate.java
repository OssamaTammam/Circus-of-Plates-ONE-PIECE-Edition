import eg.edu.alexu.csd.oop.Circus.Factories.ShapeFactory;
import eg.edu.alexu.csd.oop.Circus.Shapes.Cloneable;
import eg.edu.alexu.csd.oop.Circus.Shapes.Shape;
import eg.edu.alexu.csd.oop.Circus.Shapes.ShapeState;
import eg.edu.alexu.csd.oop.game.GameObject;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Plate implements Shape, Cloneable {
    private BufferedImage[] images;
    private int x;
    private int y;
    private boolean visible = true;
    private int width;
    private int height;
    private ShapeState state;
    private int screenHeight;
    private int screenWidth;
    public Plate(int posX, int posY, int screenWidth, int screenHeight,  ShapeState state){
        x = posX;
        y = posY;
        this.state = state;
        Random rand = new Random();
        int idx = rand.nextInt(5);
        images = new BufferedImage[]{ShapeFactory.getInstance().getImage("Plate" + ((idx != 0) ? idx : "") + ".png" )};
        width = images[0].getWidth();
        height = images[0].getHeight();
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
    }
    public Plate(int posX, int posY, BufferedImage[] images, int screenWidth, int screenHeight,  ShapeState state){
        x = posX;
        y = posY;
        this.state = state;
        this.images = images;
        width = images[0].getWidth();
        height = images[0].getHeight();
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
    }
    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
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
    public boolean isVisible() {
        return visible;
    }

    @Override
    public BufferedImage[] getSpriteImages() {
        return images;
    }


    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void move() {
        state.move(this);
    }

    @Override
    public ShapeState getState() {
        return state;
    }

    @Override
    public void setState(ShapeState state) {
        this.state = state;
    }

    @Override
    public int getScreenWidth() {
        return screenWidth;
    }

    @Override
    public int getScreenHeight() {
        return screenHeight;
    }
    @Override
    public void setRandomImage(){
        Random rand = new Random();
        int idx = rand.nextInt(5);
        images = new BufferedImage[]{ShapeFactory.getInstance().getImage("Plate" + ((idx != 0) ? idx : "") + ".png" )};
    }
    @Override
    public GameObject clone() {
        return new Plate(x, y, images, screenWidth, screenHeight, this.getState().clone());
    }

}
