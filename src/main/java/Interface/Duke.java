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
    private final TaskList deadlines;
    private final Ui ui;
    private final Reminder reminder;

    /**
     * Creates Duke object.
     */
    public Duke() {

        ui = new Ui();
        storage = new Storage();
        events = new TaskList();
        deadlines = new TaskList();
        reminder = new Reminder();
        storage.setReminderObject(reminder);
        try {
            storage.readDeadlineList(deadlines);
            storage.readEventList(events);
            reminder.setDeadlines(deadlines);
            storage.setReminderOnStart();
        } catch (IOException | ParseException | DukeException e) {
            ui.showLoadingError(e);
        }
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
            return c.execute(events, deadlines, ui, storage);
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