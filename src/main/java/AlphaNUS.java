import command.Parser;
import command.Storage;
import common.TaskList;
import payment.Payee;
import ui.Ui;

import java.util.HashMap;

/**
 * <h1>AlphaNUS</h1>
 * AlphaNUS is a program that tracks a list of tasks given by the user.
 *
 * @author Leow Yong Heng
 */

public class AlphaNUS {
    private static Ui ui;
    private static TaskList tasklist;
    private static Storage storage;
    private static HashMap<String, Payee> managermap;

    /**
     * Creates a AlphaNUS instance and initialises the required attributes.
     * @param filepath Filepath to the storage.
     */
    private AlphaNUS(String filepath) {
        ui = new Ui();
        storage = new Storage(filepath);
        //ArrayList<Task> arraylist = storage.load(); <-- Giving file not found exception, to remove
        tasklist = new TaskList();
        managermap = new HashMap<String, Payee>();
    }

    /**
     * Method to run the AlphaNUS instance and take in the inputs of the user.
     */
    private void run() {
        ui.startMessage();

        boolean isExit = false;
        while (!isExit) {
            String input = ui.readInput();
            isExit = Parser.parse(input, tasklist, ui, storage, managermap);
        }
    }

    /**
     * The main method of the AlphaNUS program, which instantiates a AlphaNUS instance with the filepath to the storage.
     * @param args Unused.
     */
    public static void main(String[] args) {
        new AlphaNUS("data/AlphaNUS.txt").run();
    }
}
