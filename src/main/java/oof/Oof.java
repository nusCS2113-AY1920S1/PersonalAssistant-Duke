package oof;

import oof.command.Command;
import oof.exception.OofException;

import java.io.IOException;

/**
 * Represents a Personal Assistant bot. An Oof object corresponds to three other classes,
 * namely called Storage, Ui and TaskList.
 */
public class Oof {

    private Storage storage;
    private TaskList arr;
    private Ui ui;
    private Reminder reminder;

    /**
     * Constructor for Oof for instantiation of other classes Ui, Storage and TaskList.
     */
    public Oof() {
        ui = new Ui();
        storage = new Storage();
        reminder = new Reminder();
        try {
            arr = new TaskList(storage.readFromFile());
        } catch (IOException e) {
            arr = new TaskList();
        }
    }

    /**
     * Allows developers to run tests using this public method.
     * @param line Command to be tested.
     * @throws OofException Exceptions to be handled during tests.
     */
    public void runTest(String line) throws OofException {
        Command command = CommandParser.parse(line);
        command.execute(arr, ui, storage);
    }

    public TaskList getArr() {
        return arr;
    }

    /**
     * Runs the Personal Assistant.
     */
    private void run() throws OofException {
        ui.hello();
        reminder.checkDeadline(arr, ui);
        boolean isExit = false;
        while (!isExit) {
            try {
                String line = ui.scanLine();
                Command command = CommandParser.parse(line);
                command.execute(arr, ui, storage);
                isExit = command.isExit();
            } catch (OofException exception) {
                ui.printOofException(exception);
            }
        }
    }

    /**
     * This is the main method which makes use of run method.
     *
     * @param args Unused.
     */
    public static void main(String[] args) throws OofException {
        new Oof().run();
    }
}