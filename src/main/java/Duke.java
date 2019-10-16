import command.Parser;
import command.Storage;
import common.TaskList;
import payment.Payee;
import payment.PaymentList;
import task.Task;
import ui.Ui;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * <h1>Duke</h1>
 * Duke is a program that tracks a list of tasks given by the user.
 *
 * @author Leow Yong Heng
 */

public class Duke {
    private static Ui ui;
    private static TaskList tasklist;
    private static PaymentList paymentlist;
    private static Storage storage;
    private static HashMap<String, Payee> managermap;

    /**
     * Creates a Duke instance and initialises the required attributes.
     * @param filepath Filepath to the storage.
     */
    private Duke(String filepath) {
        ui = new Ui();
        storage = new Storage(filepath);
        //ArrayList<Task> arraylist = storage.load(); <-- Giving file not found exception, to remove
        tasklist = new TaskList();
        paymentlist = new PaymentList();
        managermap = new HashMap<String, Payee>();
    }

    /**
     * Method to run the Duke instance and take in the inputs of the user.
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
     * The main method of the Duke program, which instantiates a duke instance with the filepath to the storage.
     * @param args Unused.
     */
    public static void main(String[] args) {
        new Duke("data/duke.txt").run();
    }
}
