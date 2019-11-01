import gazeeebo.storage.NotePageStorage;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;
import gazeeebo.commands.Command;
import gazeeebo.notes.NoteList;
import gazeeebo.parsers.Parser;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.NoteStorage;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class Gazeeebo {
    /**
     * Returns main function for duke.
     *
     * @param args a String array that takes in input from the command line
     */
    public static void main(String[] args) throws IOException {

        ArrayList<Task> list;
        Stack<ArrayList<Task>> CommandStack = new Stack<ArrayList<Task>>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();
        Storage store = new Storage();
        store.startUp();
        TriviaManager triviaManager = new TriviaManager(store);
        boolean isExit = false;
        Ui ui = new Ui();
        try {
            ui.showWelcome();
            list = store.readFromSaveFile();
            NoteStorage.readFromFile("NoteDaily.txt", NoteList.daily);
            NoteStorage.readFromFile("NoteWeekly.txt", NoteList.weekly);
            NoteStorage.readFromFile("NoteMonthly.txt", NoteList.monthly);
            NotePageStorage.readFromGoalFile();
            NotePageStorage.readFromModulesFile();
            ui.UpcomingTask(list);
            ui.MajorCategories();
            while (!isExit) {
                ui.readCommand();
                String command = ui.fullCommand;
                Command c = Parser.parse(command, ui);
                if (c != null) {
                    c.execute(list, ui, store, CommandStack, deletedTask, triviaManager);
                    isExit = c.isExit();
                }
            }
        } catch (DukeException | ParseException | IOException e) {
            if (e instanceof ParseException) {
                ui.showDateFormatError();
            } else if (e instanceof IOException) {
                ui.showIOErrorMessage(e);
            } else {
                ui.showErrorMessage(e);
            }
        } finally {
            System.out.println("System exiting");
        }
    }
}