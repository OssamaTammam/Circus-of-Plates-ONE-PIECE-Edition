package Game.Model.Shapes;

import eg.edu.alexu.csd.oop.game.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageObject implements GameObject, Cloneable, Shape {
    protected BufferedImage[] images;
    protected int x;
    protected int y;
    protected boolean visible;
    protected int width;
    protected int height;
    private ShapeState state;

    public ImageObject(int x, int y, String path, int width, int height) {
        this.x = x;
        this.y = y;
        try {
            images = new BufferedImage[]{createResizedCopy(ImageIO.read(getClass().getClassLoader().getResourceAsStream(path)), width, height, false)};
        } catch (IOException e) {
            e.printStackTrace();
        }
        visible = true;
        this.height = height;
        this.width = width;
    }

    public ImageObject(int x, int y, BufferedImage[] images) {
        this.x = x;
        this.y = y;
        this.images = images;
        visible = true;
        width = images[0].getWidth();
        height = images[0].getHeight();
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

    public void resize(int newW, int newH) {
        Image tmp = images[0].getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        this.height = newH;
        this.width = newW;
        images[0] = dimg;
    }

    @Override
    public GameObject clone() {
        return new ImageObject(x, y, images);
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

    @Override
    public int getScreenWidth() {
        return 0;
    }

    @Override
    public int getScreenHeight() {
        return 0;
    }

    @Override
    public void setRandomImage() {
    }
}

