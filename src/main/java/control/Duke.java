package control;

import command.Command;
import exception.DukeException;
import storage.Constants;
import storage.Storage;
import task.TaskList;
import ui.Ui;

import java.io.*;
import java.text.ParseException;

/**
 * Main control.Duke class
 * control.Duke is a chatbot that manage tasks for the user
 */
public class Duke {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private boolean isExit;

    /**
     * Constructor for control.Duke
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
        }
    }

    /**
     *  Main control.Duke logic run here
     */
    public void run() {
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showUserInput(fullCommand);
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (DukeException | IOException | ParseException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * Main function of control.Duke
     * @param args input from command line
     */
    public static void main(String[] args) {
        new Duke(Constants.FILENAME).run();
    }

    public String getResponse(String input) {
        try {
            ui.setOutput("");
            Command c = Parser.parse(input);
            c.execute(tasks, ui, storage);
            return ui.getOutput();
        } catch (DukeException | IOException | ParseException e) {
            return e.getMessage();
        }
    }
}