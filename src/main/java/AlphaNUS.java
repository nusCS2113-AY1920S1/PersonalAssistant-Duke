import command.Parser;
import command.Storage;
import common.TaskList;
import payment.Payee;
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
     * @param filepath Filepath to the storage.
     */
    public AlphaNUS(String filepath) {
        ui = new Ui();
        storage = new Storage(filepath);
        //ArrayList<Task> arraylist = storage.load(); <-- Giving file not found exception, to remove
        tasklist = new TaskList();
        fund = new Fund(); //TODO the fund need to be stored in the text file.
        commandList = storage.load();

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
    public static void main(String[] args) {
        new AlphaNUS("data/duke.txt").run();
    }
}
