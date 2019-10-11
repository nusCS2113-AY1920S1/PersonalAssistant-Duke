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
    private static TaskList tasks;
    public static void main(String[] args) {
        tasks = new TaskList(new ArrayList<Tasks>());
        Application.launch(Main.class,args);
    }
}
