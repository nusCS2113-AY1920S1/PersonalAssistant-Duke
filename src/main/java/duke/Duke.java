package  duke;

import duke.command.Cmd;
import duke.exception.DukeException;
import duke.order.OrderList;
import duke.parser.Parser;
import duke.Dishes.DishList;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * MAIN CLASS DUKE, start from main function.
 */
public class Duke {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private DishList dish;
    private OrderList order;

    /**
     * The constructor method for Duke.
     * @param filePath used to specify the location of the file in the hard disc.
     */
    public Duke(String filePath) {
        dish = new DishList();
        order = new OrderList();
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * The execution core of the Duke class.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
//                Command c = Parser.parse(fullCommand, tasks.size());
                if (fullCommand.startsWith("order")) {
                    Cmd<OrderList> c = Parser.Parse(fullCommand); //execute the orderCommands, add order etc
                    c.execute(order, ui, storage);
                    isExit = c.isExit();
                }
                else if (fullCommand.startsWith("dish")) {
                    Cmd<DishList> c = Parser.Parse(fullCommand); //execute the recipeCommands, add dishes etc
                    c.execute(dish, ui, storage);
                    isExit = c.isExit();
                }
                else {
                    Cmd<TaskList> c = Parser.parse(fullCommand, tasks.size()); //execute the recipeCommands, add dishes etc
                    c.execute(tasks, ui, storage);
                    isExit = c.isExit();
                }
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * =============== MAIN FUNCTION ===============.
     */
    public static void main(String[] args) {
        new Duke("data/tasks.txt").run();
    }
}
