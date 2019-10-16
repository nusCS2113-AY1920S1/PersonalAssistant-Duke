import gazeeebo.Tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.Storage.Storage;
import gazeeebo.commands.Command;
import gazeeebo.notes.NoteList;
import gazeeebo.parsers.*;
import gazeeebo.Exception.DukeException;
import Storage.NoteStorage;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class Duke {
    /**
     * Returns main function for duke.
     *
     * @param args a String array that takes in input from the command line
     * @throws DukeException | ParseException | IOException | NullPointerException
     */
    public static void main(final String[] args) {
        ArrayList<Task> list;
        Stack<String> commandStack = new Stack<String>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();
        Storage store = new Storage();
        TriviaManager triviaManager = new TriviaManager();
        boolean isExit = false;
        Ui ui = new Ui();
        try {
            ui.showWelcome();
            list = store.readFile();
            store.Read_Trivia(triviaManager);
            NoteStorage.readFromFile("NoteDaily.txt", NoteList.daily);
            NoteStorage.readFromFile("NoteWeekly.txt", NoteList.weekly);
            NoteStorage.readFromFile("NoteMonthly.txt", NoteList.monthly);
            ui.UpcomingTask(list);
            while (!isExit) {
                ui.readCommand();
                String command = ui.fullCommand.trim();
                Command c = Parser.parse(command);
                c.execute(list, ui, store, commandStack, deletedTask, triviaManager);
                if (!command.equals("undo") && !command.equals("list") && !command.contains("confirm")) {
                    commandStack.push(command);
                }

                isExit = c.isExit();
            }
        } catch (DukeException | ParseException | IOException | NullPointerException e) {
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