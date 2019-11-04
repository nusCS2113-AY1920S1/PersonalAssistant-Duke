import command.Parser;
import command.Storage;
import common.AlphaNUSException;
import common.TaskList;
import project.Fund;
import project.Project;
import ui.Ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * <h1>AlphaNUS</h1>
 * AlphaNUS is a program that tracks a list of tasks given by the user.
 *
 * @author Leow Yong Heng
 */

public class AlphaNUS {
    private static Ui ui;
    private static TaskList tasklist;
    private static Fund fund;
    private static Storage storage;
    private static ArrayList<String> commandList;

    /**
     * Creates a AlphaNUS instance and initialises the required attributes.
     */
    public AlphaNUS() throws AlphaNUSException {
        ui = new Ui();
        storage = new Storage();
        tasklist = new TaskList();
        fund = storage.readFromFundFile();
        //commandList = storage.load(); TODO

    }

    /**
     * Method to run the AlphaNUS instance and take in the inputs of the user.
     */
    public void run() {
        ui.startMessage();

        boolean isExit = false;
        while (!isExit) {

            String input = ui.readInput();
            isExit = Parser.parse(input, tasklist, ui, fund, storage, commandList);
        }

    }

    /**
     * The main method of the AlphaNUS program, which instantiates a AlphaNUS instance with the filepath to the storage.
     * @param args Unused.
     */
    public static void main(String[] args) throws AlphaNUSException {
        new AlphaNUS().run();
    }
}
