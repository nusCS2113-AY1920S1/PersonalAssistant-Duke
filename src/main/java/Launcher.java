import ListCommands.WatchlistHandler;
import MovieUI.Main;
import javafx.application.Application;
import task.TaskList;
import task.Tasks;

import java.util.ArrayList;
import MovieUI.Main;

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
