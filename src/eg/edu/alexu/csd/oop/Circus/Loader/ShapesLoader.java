package eg.edu.alexu.csd.oop.Circus.Loader;

import eg.edu.alexu.csd.oop.Circus.Shapes.Cloneable;
import eg.edu.alexu.csd.oop.Circus.Shapes.Shape;
import eg.edu.alexu.csd.oop.Circus.Shapes.ShapeState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ShapesLoader {
    private char fS = File.separatorChar;
    private List<Class<? extends Shape>> loadedClass;
    private Set<Class<? extends Shape>> st;
    private Map<String, BufferedImage> mp;
    private Dimension screenSize;
    public ShapesLoader(List<Class<? extends Shape>> loadedClass, Map<String, BufferedImage> mp) {
        this.loadedClass = loadedClass;
        st = new HashSet<>();
        this.mp = mp;
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    }

    /**
     * Takes full class path and returns a class name with dots instead of /
     * @param fileName class path
     * @return String representing class path
     */
    private String getClassName(String fileName) {
        String newName = fileName.replace(fS, '.');
        newName = newName.replace('/', '.');
        return newName.substring(0, newName.length() - 6);
    }

    /**
     * Takes a Folder, Iterates through it and add any classes implementing the shape interface
     * @param directory Folder File
     *
     */
    public void loadDirectory(File directory) {
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File f, String name) {
                return name.endsWith("jar") || f.isDirectory();
            }
        };
        File[] files = directory.listFiles(filter);
        Set<Class<? extends Shape>> st = new HashSet<>();
        for (File f : files) {
            if(f.isDirectory()){
                loadDirectory(f);
            }else {
                loadJar(f);
            }
        }
    }

    /**
     * Loads all Images for the target class with specific names "Classname[0-9]*.png"
     * @param jar Target Jar file
     * @param cls Class object for which we need to load its images
     * @param className Name of the class
     *
     */
    public void loadClassImages(File jar, Class<? extends Shape> cls, String className) {
        URL[] forLoad = new URL[0];
        try {
            forLoad = new URL[]{jar.toURI().toURL()};
            URLClassLoader loader = URLClassLoader.newInstance(forLoad, getClass().getClassLoader());
            JarInputStream jis = new JarInputStream(new FileInputStream(jar));
            JarEntry entry = jis.getNextJarEntry();
            while (entry != null) {
                if (entry.getName().matches(className + "[0-9]*\\.png")) {
                    BufferedImage bufferedImage = ImageIO.read(loader.getResourceAsStream(entry.getName()));
                    mp.put(entry.getName(), createResizedCopy(bufferedImage, (int) Math.round((1200 / screenSize.getWidth()) * bufferedImage.getWidth()), (int) Math.round((650 / screenSize.getHeight()) * bufferedImage.getHeight()), false));
                }
                entry = jis.getNextJarEntry();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * Iterates through the jar searching for classes which implement the shape interface and loads all its resources.
     * @param jar Target Jar
     */
    public void loadJar(File jar) {
        URL[] forLoad = new URL[0];
        try {
            forLoad = new URL[]{jar.toURI().toURL()};
            URLClassLoader loader = URLClassLoader.newInstance(forLoad, getClass().getClassLoader());
            JarInputStream jis = new JarInputStream(new FileInputStream(jar));
            JarEntry entry = jis.getNextJarEntry();
            while (entry != null) {
                if (entry.getName().endsWith(".class")) {
                    String className = getClassName(entry.getName());
                    Class<?> cls = Class.forName(className, true, loader);
                    if (!cls.isInterface()
                            && !Modifier.isAbstract(cls.getModifiers())
                            && Shape.class.isAssignableFrom(cls)
                            && Cloneable.class.isAssignableFrom(cls)
                            && !st.contains(cls)
                            && constructorAvailableTest((Class<? extends Shape>) cls)) {
                        loadedClass.add((Class<? extends Shape>) cls);
                        st.add((Class<? extends Shape>) cls);
                        loadClassImages(jar, (Class<? extends Shape>) cls, className);

                    }
                }
                entry = jis.getNextJarEntry();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks the existence of a specific constructor
     * @param cls Target class
     * @return A boolean representing the existence of a specific  constructor
     */
    public boolean constructorAvailableTest(Class<? extends Shape> cls) {
        //int posX, int posY, BufferedImage[] images, int screenWidth, int screenHeight,  ShapeState state
        try {
            Constructor<?> contructor = cls.getConstructor(new Class[]{int.class, int.class, int.class, int.class, ShapeState.class});
        } catch (NoSuchMethodException nsme) {
            return false;
        }
        try {
            Constructor<?> contructor = cls.getConstructor(new Class[]{int.class, int.class, BufferedImage[].class, int.class, int.class, ShapeState.class});
        } catch (NoSuchMethodException nsme) {
            return false;
        }
        return true;
    }
    private BufferedImage createResizedCopy(Image originalImage,
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
}
