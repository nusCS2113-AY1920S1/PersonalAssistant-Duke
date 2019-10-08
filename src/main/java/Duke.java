import command.UpcomingCommand;
import command.Command;
import exception.DukeException;
import storage.Constants;
import storage.Storage;
import task.TaskList;
import ui.Ui;
import user.Login;

import java.text.ParseException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

// JavaDoc should be written like user stories
/**
 * Main Duke class.
 * Duke is a chatbot that manage tasks for the user.
 */
public class Duke {
    private Storage storage;
    private UpcomingCommand upcomingCommand;
    private TaskList tasks;
    private Ui ui;
    private boolean isExit;

    /**
     * Constructor for Duke.
     * @param filePath path of text file containing task list
     */
    public Duke(String filePath) {
        ui = new Ui();
        ui.showWelcome();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (FileNotFoundException | DukeException e) {
            ui.showLoadingError();
            tasks = new TaskList();
            try {
                new File("data").mkdir();
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }
    }

    /**
     *  Main Duke logic run here.
     */
    public void run() {

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (DukeException | IOException | ParseException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * Main function of Duke.
     * @param args input from command line
     */
    public static void main(String[] args) {
        new Duke(Constants.FILENAME).run();
    }
}