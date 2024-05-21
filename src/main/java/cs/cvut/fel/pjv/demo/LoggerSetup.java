package cs.cvut.fel.pjv.demo;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerSetup {
    /**
     * Configures the logger with console and file handlers.
     *
     * @param logger logger The logger instance to be configured
     */
    public static void setupLogger(Logger logger) {
        try {

            // Create a console handler to log messages to the console
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.ALL);
            // Use a simple formatter for log messages
            consoleHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(consoleHandler);

            // Create a file handler to log messages to a file named "application.log"
            FileHandler fileHandler = new FileHandler("application.log", true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            logger.setLevel(Level.ALL);
            logger.setUseParentHandlers(false);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred in setting up logger.", e);
        }
    }
}
