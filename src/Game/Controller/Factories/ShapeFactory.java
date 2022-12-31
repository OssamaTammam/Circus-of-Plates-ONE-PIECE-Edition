package Game.Controller.Factories;

//import Game.Controller.Loader.ShapesLoader;

import Game.Model.Shapes.ImageObject;
import Game.Model.Shapes.Shape;
import Game.Model.Shapes.ShapeState;
import Game.Controller.Logging;
import eg.edu.alexu.csd.oop.game.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;

public class ShapeFactory {
    private List<Class<? extends Game.Model.Shapes.Shape>> loadedClass;
    private Map<String, BufferedImage> mp; // Flyweight Design Pattern
    //private ShapesLoader sL;
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

    /**
     * Initializing Factory
     */
    private ShapeFactory() {
        loadedClass = new ArrayList<>();
        mp = new HashMap<>();
        //sL = new ShapesLoader(loadedClass, mp);
//        FilenameFilter filter = new FilenameFilter() {
//            public boolean accept(File f, String name) {
//                return name.endsWith("jar");
//            }
//        };

//        File f = new File("Plugins");
//        if (!f.exists()) {
//            f.mkdir();
//        }
//        sL.loadDirectory(f);
//        ;
//        if (loadedClass.isEmpty()) {
//            log.help().finer("plugins are empty");
//            throw new RuntimeException("Plugins can't be Empty, There needs to be at least one class for this game to run");
//        }
    }

    /**
     * Returns a Random Shape Object with indexes between 0 and count-1
     * Note: Currently it uses default constructor (Empty)
     *
     * @param count Number of Different Shapes
     * @return A random Shape Object
     */
//    public Game.Model.Shapes.Shape getRandomShape(int count, int posX, int posY, int screenWidth, int screenHeight, ShapeState state) {
//        if (count > loadedClass.size()) {
//            log.help().finer("No enough classes");
//            throw new RuntimeException("There is no enough classes for this Command");
//        }
//        Random rand = new Random();
//        Game.Model.Shapes.Shape sh = null;
//        int idx = rand.nextInt(count);
//
//        try {
//            sh = (Shape) loadedClass.get(idx).getDeclaredConstructor(new Class[]{int.class, int.class, int.class, int.class, ShapeState.class}).newInstance(
//                    new Object[]{posX, posY, screenWidth, screenHeight, state});
//            log.help().info(sh.getClass().getName() + " is Loaded");
//        } catch (IllegalAccessException e) {
//            log.help().finer("Can't access the class");
//            System.out.println("Can't Access this class " + loadedClass.get(idx));
//            e.printStackTrace();
//        } catch (InstantiationException | NoSuchMethodException e) {
//            log.help().finer("Can't create object of this class");
//            System.out.println("Can't Create an object of this class " + loadedClass.get(idx));
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        return sh;
//    }
    public GameObject getRandomImage(int posX, int posY, int screenWidth, int screenHeight) {

        int imgNum = (int) (Math.random() * 3 + 1);
        String imgName = "plate" + imgNum + ".png";
        ImageObject plate = new ImageObject(posX, posY, imgName, screenWidth, screenHeight);
        plate.resize(70, 70);
        return plate;
    }


    /**
     * Returns a BufferedImage object with the given name if its loaded
     *
     * @param name name of the image with extension (.png) only
     * @return Returns a BufferedImage Object representing the image
     * @throws throws expection if the image doesn't exist
     */
    public BufferedImage getImage(String name) {
        if (!mp.containsKey(name)) {
            log.help().finer("Image doesn't exist");
            throw new RuntimeException("Image doesn't exist");
        }
        return mp.get(name);
    }

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
//    public boolean equalColor(GameObject a, GameObject b) {
//        BufferedImage imageA = a.getSpriteImages()[0];
//        BufferedImage imageB = b.getSpriteImages()[0];
//        Color avgColorA = averageColor(imageA);
//        Color avgColorB = averageColor(imageB);
//        return similarTo(avgColorA, avgColorB);
//    }
//
//    private boolean similarTo(Color a, Color b) {
//        double distance = (a.getRed() - b.getRed()) * (a.getRed() - b.getRed()) + (a.getGreen() - b.getGreen()) * (a.getGreen() - b.getGreen()) + (a.getBlue() - b.getBlue()) * (a.getBlue() - b.getBlue());
//        if (distance < 700) {
//            log.help().info("shapes have the same color");
//            return true;
//        } else {
//            log.help().info("shapes have different colors");
//            return false;
//        }
//    }
//
//    private static Color averageColor(BufferedImage bi) {
//        long sumr = 0, sumg = 0, sumb = 0;
//        for (int x = 0; x < bi.getWidth(); x++) {
//            for (int y = 0; y < bi.getHeight(); y++) {
//                Color pixel = new Color(bi.getRGB(x, y));
//                if (isTransparent(bi, x, y))
//                    continue;
//                sumr += pixel.getRed();
//                sumg += pixel.getGreen();
//                sumb += pixel.getBlue();
//            }
//        }
//        int num = bi.getWidth() * bi.getHeight();
//        int avgr = (int) (sumr / num);
//        int avgg = (int) (sumg / num);
//        int avgb = (int) (sumb / num);
//        return new Color(avgr, avgg, avgb);
//    }
//
//    private static boolean isTransparent(BufferedImage bi, int x, int y) {
//        int pixel = bi.getRGB(x, y);
//        if ((pixel >> 24) == 0x00) {
//            return true;
//        }
//        return false;
//    }
//
//    public int getSupportedShapesCount() {
//        return loadedClass.size();
//    }
}
