import ListCommands.WatchlistHandler;
import MovieUI.Main;
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
