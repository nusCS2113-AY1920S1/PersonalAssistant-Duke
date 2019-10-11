package Interface;
import Tasks.*;
import Commands.*;
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

    private final Storage storage;
    private final TaskList events;
    private final TaskList todos;
    private final TaskList deadlines;
    private final Ui ui;
    private static TaskList tentativeDates;

    /**
     * Creates Duke object.
     */
    public Duke() {

        ui = new Ui();
        storage = new Storage();
        todos = new TaskList();
        events = new TaskList();
        deadlines = new TaskList();
        tentativeDates = new TaskList();
        try {
            storage.readTentativeDates(tentativeDates);
            storage.readDeadlineList(deadlines);
            storage.readEventList(events);
        } catch (IOException | ParseException | StringIndexOutOfBoundsException e) {
            ui.showLoadingError(e);
            e.printStackTrace();
        }
    }
    /**
     * This method returns the loaded Tentative Dates.
     */
    public static TaskList getTentativeDates() {
        return tentativeDates;
    }

    @Override
    public void start(Stage primaryStage) {
        //...
    }

    /**
     * This method runs the Duke program.
     * @param input The user's input to the Duke program
     * @return This returns the string to respond to user's input
     */
    private String run(String input) {
        try {
            Command c = Parser.parse(input);
            return c.execute(todos, events, deadlines, ui, storage);
        } catch (DukeException e) {
            return ui.showError(e);
        } catch (Exception e) {
            return e.getMessage();
        }
   }

    public String getResponse(String input) {
        return run(input);
    }

}