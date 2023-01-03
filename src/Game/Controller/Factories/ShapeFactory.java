package Game.Controller.Factories;

import Game.Model.Shapes.ImageObject;
import eg.edu.alexu.csd.oop.game.GameObject;

import java.awt.image.BufferedImage;

/**
 * Factory Design Pattern & Singleton Design Pattern
 * This class is responsible for creating the shapes that will be used in the game
 * It is a singleton class because we only need one instance of it
 */
public class ShapeFactory {
    public static ShapeFactory instance;

    /**
     * Singleton Design Pattern
     *
     * @return Single instance of this class
     */
    public synchronized static ShapeFactory getInstance() {
        if (instance == null) {
            instance = new ShapeFactory();
        }
        return instance;
    }

    /**
     * Generate a random plate that will be used in the game
     *
     * @param posX   X coordinate f the shape
     * @param posY   Y coordinate of the image
     * @param width  width of the image
     * @param height height of the image
     * @return GameObject plate that will be used in the game
     */
    public GameObject getRandomImage(int posX, int posY, int width, int height) {

        int imgNum = (int) (Math.random() * 3 + 1);
        String imgName = "plate" + imgNum + ".png";
        return new ImageObject(posX, posY, imgName, width, height);
    }

    /**
     * Compares Images by Resolution and By RGB of every Pixel
     *
     * @param a GameObject
     * @param b GameObject
     * @return boolean true if every pixel in both images is the same
     */
    public boolean isSame(GameObject a, GameObject b) {

        BufferedImage imageA = a.getSpriteImages()[0];
        BufferedImage imageB = b.getSpriteImages()[0];
        if (imageA.getWidth() == imageB.getWidth() && imageA.getHeight() == imageB.getHeight()) {
            for (int x = 0; x < imageA.getWidth(); x++) {
                for (int y = 0; y < imageA.getHeight(); y++) {
                    if (imageA.getRGB(x, y) != imageB.getRGB(x, y))
                        return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
