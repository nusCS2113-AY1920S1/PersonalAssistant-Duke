import gazeeebo.logger.LogCenter;
import gazeeebo.storage.*;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import gazeeebo.notes.NoteList;
import gazeeebo.parser.ParserManager;
import gazeeebo.exception.DukeException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Gazeeebo {
    private static final Logger LOGGER = Logger.getLogger(Gazeeebo.class.getName());

    /**
     * Returns main function for duke.
     *
     * @param args a String array that takes in input from the command line
     */
    public static void main(String[] args) throws IOException {
        LogCenter.setUpLogger(LOGGER);
        ArrayList<Task> list;
        Stack<ArrayList<Task>> commandStack = new Stack<ArrayList<Task>>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();
        Storage store = new Storage();
        store.startUp();
        TasksPageStorage tasksPageStorage = new TasksPageStorage();
        TriviaStorage triviaStorage = new TriviaStorage();
        TriviaManager triviaManager = new TriviaManager(triviaStorage);
        boolean isExit = false;
        Ui ui = new Ui();
        try {
            ui.showWelcome();
            list = tasksPageStorage.readFromSaveFile();
            NoteStorage.readFromFile("NoteDaily.txt", NoteList.daily);
            NoteStorage.readFromFile("NoteWeekly.txt", NoteList.weekly);
            NoteStorage.readFromFile("NoteMonthly.txt", NoteList.monthly);
            NotePageStorage.readFromGoalFile();
            NotePageStorage.readFromModulesFile();
            ui.upcomingTask(list);
            ui.majorCategories();
            while (!isExit) {
                ui.readCommand();
                String command = ui.fullCommand;
                Command c = ParserManager.parse(command, ui);
                if (c != null) {
                    c.execute(list, ui, store, commandStack, deletedTask, triviaManager);
                    isExit = c.isExit();
                }
            }
        } catch (DukeException | ParseException | IOException | NullPointerException e) {
            if (e instanceof ParseException) {
                ui.showDateFormatError();
                LOGGER.log(Level.SEVERE,"Date time format error.", e);
            } else if (e instanceof IOException) {
                ui.showIOErrorMessage(e);
                LOGGER.log(Level.SEVERE,"Unable to read file", e);
            } else if (e instanceof NullPointerException) {
                ui.showSystemTerminateMessage();
                LOGGER.log(Level.INFO,"System terminating without an input", e);
            } else {
                ui.showErrorMessage(e);
                LOGGER.log(Level.SEVERE,"Other errors", e);
            }
        } finally {
            System.out.println("System exiting");
        }
    }
}