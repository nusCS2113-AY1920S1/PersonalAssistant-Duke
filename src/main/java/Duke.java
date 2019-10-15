import gazeeebo.Tasks.Task;
import gazeeebo.UI.Ui;
import gazeeebo.Storage.Storage;
import gazeeebo.commands.Command;
import gazeeebo.notes.NoteList;
import gazeeebo.parsers.*;
import gazeeebo.Exception.DukeException;
import Storage.NoteStorage;
import java.io.*;
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
    public static void main(String[] args) {
        ArrayList<Task> list;
        Stack<String> CommandStack = new Stack<String>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();
        Storage store = new Storage();
        boolean isExit = false;
        Ui ui = new Ui();
        try {
            ui.showWelcome();
            list = store.ReadFile();
            NoteStorage.readFromFile("NoteDaily.txt", NoteList.daily);
            NoteStorage.readFromFile("NoteWeekly.txt", NoteList.weekly);
            NoteStorage.readFromFile("NoteMonthly.txt", NoteList.monthly);
            ui.UpcomingTask(list);
            while (!isExit) {
                ui.ReadCommand();
                String command = ui.FullCommand.trim();
                Command c = Parser.parse(command);
                c.execute(list, ui, store, CommandStack, deletedTask);
                if (!command.equals("undo") && !command.equals("list") && !command.contains("confirm")) {
                    CommandStack.push(command);
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