import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import java.text.ParseException;

/**
 * A program that manages tasks input by user
 * with commands that includes adding, deleting, displaying list of tasks
 * and to mark completion of a task.
 */
public class Duke extends Application {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Creates Duke object.
     */
    public Duke() {
        String filePath = System.getProperty("user.dir") + "\\data\\duke.txt";
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList();
        try {
            storage.readFile(tasks);
        } catch (IOException | ParseException e) {
            ui.showLoadingError(e);
            tasks = new TaskList();
        }
    }

    /**
     * This method runs the Duke program.
     * @param input The user's input to the Duke program
     * @return This returns the string to respond to user's input
     */
    private String run(String input) {
        try {
            Command c = Parser.parse(input);
            return c.execute(tasks, ui, storage);
        } catch (DukeException e) {
            return ui.showError(e);
        } catch (Exception e) {
            return e.getMessage();
        }
   }

    @Override
    public void start(Stage stage) {
        //...
    }

    /**
     * This method is to get the response to the user's input.
     * When called, it calls on the run() method to run the Duke program.
     * @param input The user's input to the Duke program
     * @return It returns the result of the run() method
     */
    public String getResponse(String input) {
        return run(input);
    }
}