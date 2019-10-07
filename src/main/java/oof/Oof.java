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
     * Runs the Personal Assistant.
     */
    private void run() {
        ui.hello();
        reminder.checkDeadline(arr, ui);
        boolean isExit = false;
        while (!isExit) {
            try {
                String line = ui.scanLine();
                Command command = CommandParser.parse(line);
                command.execute(arr, ui, storage);
                isExit = command.isExit();
            } catch (OofException | IOException exception) {
                ui.printOofException((OofException) exception);
            }
        }
    }

    /**
     * This is the main method which makes use of run method.
     *
     * @param args Unused.
     */
    public static void main(String[] args) {
        new Oof().run();
    }
}