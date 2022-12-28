package eg.edu.alexu.csd.oop.Circus.Shapes;

import eg.edu.alexu.csd.oop.game.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageObject implements GameObject, Cloneable {
    protected BufferedImage[] images;
    protected int x;
    protected int y;
    protected boolean visible;
    protected int width;
    protected int height;
    public ImageObject(int x, int y, String path, int width, int height){
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
    public ImageObject( int x , int y, BufferedImage[] images){
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
    public void setSpriteImages(BufferedImage[] newImages){
        images = newImages;
    }
    protected BufferedImage createResizedCopy(Image originalImage,
                                    int scaledWidth, int scaledHeight,
                                    boolean preserveAlpha)
    {
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
    public GameObject clone(){
        return new ImageObject(x, y, images);
    }
}
