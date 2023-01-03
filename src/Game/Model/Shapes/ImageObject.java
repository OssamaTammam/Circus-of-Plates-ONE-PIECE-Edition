package Game.Model.Shapes;

import eg.edu.alexu.csd.oop.game.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This class is responsible for creating the objects using images
 */
public class ImageObject implements GameObject, Shape {
    protected BufferedImage[] images; // The images of the object
    protected int x;
    protected int y;
    protected boolean visible;
    protected int width;
    protected int height;
    private ShapeState state;

    public ImageObject(int x, int y, String imageName, int width, int height) {
        this.x = x;
        this.y = y;
        try {
            images = new BufferedImage[]{createResizedCopy(ImageIO.read(getClass().getClassLoader().getResourceAsStream(imageName)), width, height, false)};
        } catch (IOException e) {
            e.printStackTrace();
        }
        visible = true;
        this.height = height;
        this.width = width;
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

    public void setSpriteImages(BufferedImage[] newImages) {
        images = newImages;
    }

    protected BufferedImage createResizedCopy(Image originalImage,
                                              int scaledWidth, int scaledHeight,
                                              boolean preserveAlpha) {
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void move() {
        state.move(this);
    }


    public ShapeState getState() {
        return state;
    }

    public void setState(ShapeState state) {
        this.state = state;
    }

}

