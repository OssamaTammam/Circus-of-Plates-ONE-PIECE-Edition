package Game.Controller.Factories;

import Game.Model.Shapes.ImageObject;
import Game.Controller.Logging;
import eg.edu.alexu.csd.oop.game.GameObject;

import java.awt.image.BufferedImage;

public class ShapeFactory {
    public static ShapeFactory instance;
    Logging log = new Logging();

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

    public GameObject getRandomImage(int posX, int posY, int screenWidth, int screenHeight) {

        int imgNum = (int) (Math.random() * 3 + 1);
        String imgName = "plate" + imgNum + ".png";
        ImageObject plate = new ImageObject(posX, posY, imgName, screenWidth, screenHeight);
        plate.resize(50, 40);
        return plate;
    }

    public boolean isSame(GameObject a, GameObject b) {
        // Compares Images by Resolution and By RGB of every Pixel
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
