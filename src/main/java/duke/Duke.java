package duke;

import duke.logic.commands.Command;
import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.parser.Parser;
import duke.storage.FileStorage;
import duke.storage.Storage;
import duke.storage.StorageManager;
import duke.ui.Ui;

public class Duke {
    private Ui ui;
    private Storage storage;
    private LockerList lockers;
    private Parser parser;

    /**
     * This constructor instantiates the Duke class by loading data from a file.
     * @param filename stores the file name from which the data is being loaded.
     */
    public Duke(String filename) {
        try {
            ui = new Ui();
            parser = new Parser();
            storage = new StorageManager(filename);
            lockers = new LockerList(storage.retrieveData().getAllLockers());
        } catch (DukeException e) {
            ui.showLoadingError(e.getMessage());
            lockers = new LockerList();
        }
    }

    /**
     * This function is responsible for executing various tasks/commands related to Duke.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.printDash();
                Command c = parser.parse(fullCommand);
                c.execute(lockers, ui, storage);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * This function is responsible for instantiating Duke with the file name "storeData.txt".
     * storeData.txt is the file from which the data is loaded for the list of tasks.
     * @param args contains the supplied command-line arguments as an array of String objects.
     */
    public static void main(String[] args) {
        new Duke("storeData.json").run();
    }
}