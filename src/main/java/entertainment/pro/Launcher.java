package entertainment.pro;

import entertainment.pro.storage.user.WatchlistHandler;
import javafx.application.Application;

/**
 * Start the javafx program.
 */
public class Launcher {
    private static WatchlistHandler tasks;
    public static void main(String[] args) {
        tasks = new WatchlistHandler();
        Application.launch(Main.class,args);
    }
}
