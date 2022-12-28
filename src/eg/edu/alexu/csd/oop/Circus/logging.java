package eg.edu.alexu.csd.oop.Circus;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class logging {
    private static Logger logger ;
    private static void Log_Method() {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.ALL);
        ConsoleHandler ch=new ConsoleHandler();
        ch.setLevel(Level.WARNING);
        logger.addHandler(ch);

        try {
            FileHandler file=new FileHandler("MyLogger.log");
            file.setLevel(Level.ALL);
            logger.addHandler(file);
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public  Logger help(){
        if(logger==null) {
            logger =Logger.getLogger(logging.class.getName());
            Log_Method();
        }
        return logger;
    }

}
