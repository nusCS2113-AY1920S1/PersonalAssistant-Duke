package duke;

import duke.command.Cmd;
import duke.dish.Dish;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.order.Order;
import duke.order.OrderList;
import duke.parser.Parser;
import duke.dish.DishList;
import duke.storage.FridgeStorage;
import duke.storage.Storage;
import duke.storage.TaskStorage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * MAIN CLASS DUKE, start from main function.
 */
public class Duke {

    //CONSTANTS
    private final String taskFilePath = "data/tasks.txt";
    private final String fridgeFilePath= "data/fridge.txt";

    private Storage taskStorage;
    private Storage fridgeStorage;
    private TaskList tasks;
    private Ui ui;
    private DishList dish;
    private OrderList order;
    private Fridge fridge;

    /**
     * The constructor method for Duke.
     */
    public Duke() {
        dish = new DishList();
        order = new OrderList();
        fridge = new Fridge();
        ui = new Ui();
        taskStorage = new TaskStorage(taskFilePath);
        fridgeStorage = new FridgeStorage(fridgeFilePath);
        try {
            tasks = new TaskList(taskStorage.load());
            fridge = new Fridge (fridgeStorage.load());
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
                if (fullCommand.startsWith("order")) {
                    Cmd<Order> c = Parser.parse(fullCommand);
                    c.execute(order, ui, taskStorage);
                    isExit = c.isExit();
                }
                else if (fullCommand.startsWith("dish")) {
                    Cmd<Dish> c = Parser.parse(fullCommand);
                    c.execute(dish, ui, taskStorage);
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
        new Duke().run();
    }
}
