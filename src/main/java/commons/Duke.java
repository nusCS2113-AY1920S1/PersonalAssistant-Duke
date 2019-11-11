package commons;

import commands.Command;
import commands.RetrieveFreeTimesCommand;
import commands.RetrievePreviousCommand;
import dukeexceptions.DukeIOException;
import dukeexceptions.DukeInvalidDateTimeException;
import tasks.TaskList;
import parser.MainParser;
import userinterface.MainWindow;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A program that manages tasks input by user
 * with commands that includes adding, deleting, displaying list of tasks
 * and to mark completion of a task.
 */
public class Duke  {

    private final Storage storage;
    private final PreloadStorage preloadStorage;
    private final TaskList events;
    private final TaskList deadlines;
    private final UserInteraction ui;
    private final Reminder reminder;
    private final Logger logger = DukeLogger.getLogger(Duke.class);
    public static ArrayList<String> userInputs = new ArrayList<>();

    /**
     * Creates Duke object.
     */
    public Duke() {
        ui = new UserInteraction();
        storage = new Storage();
        preloadStorage = new PreloadStorage();
        events = new TaskList();
        deadlines = new TaskList();
        reminder = new Reminder();
        storage.setReminderObject(reminder);
        MainWindow.setStorage(storage);
        try {
            preloadStorage.readDeadlineList(deadlines);
            preloadStorage.readEventList(events);
            storage.readDeadlineList(deadlines);
            storage.readEventList(events);
            storage.updateDeadlineList(deadlines);
            storage.updateEventList(events);
            reminder.setDeadlines(deadlines);
            storage.setReminderOnStart();
        } catch (DukeIOException | DukeInvalidDateTimeException e) {
            logger.severe(ui.showLoadingError(e));
        }
    }

    /**
     * This method runs the Duke program.
     * @param input The user's input to the Duke program
     * @return This returns the string to respond to user's input
     */
    private String run(String input) {
        try {
            Command c = MainParser.parse(input);
            return c.execute(events, deadlines, ui, storage);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return ui.getError(e);
        }
    }

    public String getResponse(String input) {
        userInputs.add(input);
        return run(input);
    }

    public static ArrayList<String> getUserInputs() {
        return userInputs;
    }

    public static String getPreviousInput() {
        String previousInput = RetrievePreviousCommand.getChosenOutput();
        return previousInput;
    }

    /**
     * This method retrieves the free time option selected by the user.
     */
    public static String getSelectedOption() {
        String selectedOption = RetrieveFreeTimesCommand.getSelectedOption();
        return selectedOption;
    }
}