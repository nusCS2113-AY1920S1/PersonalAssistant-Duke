package seedu.hustler;

import java.io.IOException;

import javafx.application.Platform;
import seedu.hustler.game.avatar.Avatar;
import seedu.hustler.command.Command;

import seedu.hustler.data.AvatarStorage;
import seedu.hustler.data.CommandLog;
import seedu.hustler.data.MemoryManager;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.task.Reminders;
import seedu.hustler.ui.Ui;
import seedu.hustler.data.Storage;
import seedu.hustler.data.Folder;
import seedu.hustler.task.TaskList;
import seedu.hustler.parser.CommandParser;
import seedu.hustler.ui.timer.*;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * A personal assistant that takes in user input and gives and performs
 * an operation that can help the user in his day to day needs.
 * Has a task list with multiple features.
 */
public class Hustler extends Application {
    /**
     * TaskList instance that  stores all the tasks added by the
     * user and from storage.
     */
    public static TaskList list;

    /**
     * Storage instance that stores and loads tasks to and from
     * disk.
     */
    private static Storage storage = new Storage("data/hustler.txt");

    /**
     * UI instance that is used to take input from console
     * and display errors and responses. Handles user interaction.
     */
    private static Ui ui = new Ui();

    /**
     * Parser instance that makes sense of user input and
     * performs some operation on list.
     */
    private static CommandParser parser = new CommandParser();

    /**
     * Avatar instance that keeps track of the User's progress.
     */
    public static Avatar avatar;

    /**
     * TimerManager instance that starts the timer.
     */
    public static timerManager timermanager = new timerManager();

    /**
     * MemoryManager instance that starts the timer.
     */
    public static MemoryManager memorymanager = new MemoryManager();

    /**
     * CommandLog instance that records user tasks.
     */
    public static CommandLog commandlog = new CommandLog();

    /**
     * Initializes the essential components needed to run Hustler.
     * Loads existing task list and avatar.
     * Displays reminders at the start of Hustler.
     */
    public static void initialize() throws IOException {
        ui.show_opening_string();
        Folder.checkDirectory();
        loadStorage();
        memorymanager.createBackup();

        Reminders.runAll(list);
        Reminders.displayReminders();
        System.out.println();

        avatar = AvatarStorage.load();
        AvatarStorage.save(avatar);
    }

    /**
     * Runs Hustler until the users enters "bye".
     * Performs operations like list, find, delete and add on the task list.
     * Saves the list to disk for next Hustler session inside data/hustler.txt.
     *
     * @param rawInput full user's input inside text area of GUI.
     */
    public static void run(String rawInput) {
        if (rawInput.equals("bye")) {
            ui.show_bye_message();
            Platform.exit();
        }

        try {
            Command command = parser.parse(rawInput);
            command.execute();
            saveStorage();
            System.out.println();
        } catch (CommandLineException e) {
            e.getErrorMsg();
        }
    }

    public void start(Stage stage) {
    }

    public static void loadStorage() {
        list = new TaskList(storage.load());
        avatar = AvatarStorage.load();
    }

    public static void reloadBackup() {
        list = new TaskList(storage.reloadBackup());
        avatar = AvatarStorage.reloadBackup();
    }

    /**
     * Saves the task list to storage area.
     */
    public static void saveStorage() {
        try {
            storage.save(list.return_list());
            AvatarStorage.save(avatar);
        } catch (IOException e) {
            ui.show_save_error();
        }
    }
}