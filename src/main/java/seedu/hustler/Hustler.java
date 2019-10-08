package seedu.hustler;

import java.io.IOException;

import javafx.application.Platform;
import seedu.hustler.game.avatar.Avatar;
import seedu.hustler.command.Command;
import java.util.Scanner;

import seedu.hustler.data.AvatarStorage;
import seedu.hustler.data.Schedule;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.task.Reminders;
import seedu.hustler.ui.Ui;
import seedu.hustler.data.Storage;
import seedu.hustler.data.Folder;
import seedu.hustler.task.TaskList;
import seedu.hustler.parser.CommandParser;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * A personal assistant that takes in user input and gives and performs
 * an operation that can help the user
 * in his day to day needs. Has a task list with multiple features.
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
    private static Storage storage = new Storage("data/duke.txt");
    /**
     * UI instance that is used to take input from console
     * and display errors and responses. Handles user interaction.
     */
    private static Ui ui = new Ui(new Scanner(System.in));
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
     * Runs Duke which commences the user to machine
     * feedback loop until the user enters "bye".
     * Loads existing tasklist and avatar, and performs operations
     * like list, find, delete and add on the tasklist. Adds
     * the Tasks in the TreeMap.
     * Saves the list to disk for next duke session inside
     * data/duke.txt.
     * @see Storage
     * @see TaskList
     * @see CommandParser
     * @see Ui
     * @see Schedule
     */
    public static void initialize() throws IOException {
        ui.show_opening_string();
        Folder.checkDirectory();
        list = new TaskList(storage.load());

        // Display reminders at the start
        Reminders.runAll(list);
        Reminders.displayReminders();
        System.out.println();
        avatar = AvatarStorage.load();
        AvatarStorage.save(avatar);
    }

    public static void run(String rawInput) throws CommandLineException {
        if (rawInput.equals("bye")) {
            ui.show_bye_message();
            Platform.exit();
        }
        Command command = parser.parse(rawInput);
        command.execute();
        try {
            storage.save(list.return_list());
        } catch (IOException e) {
            ui.show_save_error();
        }
        System.out.println();
    }

    public void start(Stage stage) {};
}