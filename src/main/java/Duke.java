import Tasks.Task;
import UI.Ui;
import Storage.Storage;
import commands.Command;
import notes.Note;
import notes.NoteList;
import parsers.*;
import Exception.DukeException;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;

public class Duke {
    /**
     * Returns main function for duke.
     *
     * @param args a String array that takes in input from the command line
     * @throws DukeException | ParseException | IOException | NullPointerException
     */
    public static void main(String[] args) {
        ArrayList<Task> list;
        Storage store = new Storage();
        boolean isExit = false;
        Ui ui = new Ui();
        try {
            ui.showWelcome();
            list = store.ReadFile();
            ui.UpcomingTask(list);
            while (!isExit) {
                ui.ReadCommand();
                String command = ui.FullCommand.trim();
                Command c = Parser.parse(command);
                c.execute(list, ui, store);
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