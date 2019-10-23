package  duke;

import duke.command.Cmd;
import duke.dish.Dish;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.ingredient.IngredientsList;
import duke.order.Order;
import duke.order.OrderList;
import duke.parser.Parser;
import duke.dish.DishList;
import duke.storage.FridgeStorage;
import duke.storage.Storage;
import duke.storage.TaskStorage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * MAIN CLASS DUKE, start from main function.
 */
public class Duke {

    private Storage taskStorage;
    private Storage fridgeStorage;
    private TaskList tasks;
    private Ui ui;
    private DishList dish;
    private OrderList order;
    private Fridge fridge;

    /**
     * The constructor method for Duke.
     * @param filePath used to specify the location of the file in the hard disc.
     */
    public Duke(String filePath) {
        String fridgeFilePath= "data/fridge.txt";
        dish = new DishList();
        order = new OrderList();
        fridge = new Fridge();
        ui = new Ui();
        taskStorage = new TaskStorage(filePath);
        fridgeStorage = new FridgeStorage(fridgeFilePath);
        try {
            tasks = new TaskList(taskStorage.load().getAllEntries());
            fridge = new Fridge (new IngredientsList(fridgeStorage.load().getAllEntries()));
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
                    Cmd<Order> c = Parser.Parse(fullCommand); //execute the orderCommands, add order etc
                    c.execute(order, ui, taskStorage);
                    isExit = c.isExit();
                }
                else if (fullCommand.startsWith("dish")) {
                    Cmd<Dish> c = Parser.Parse(fullCommand); //execute the recipeCommands, add dishes etc
                    c.execute(dish, ui, taskStorage);
                    isExit = c.isExit();
                }
                else {
                    Cmd<Task> c = Parser.parse(fullCommand, tasks.size()); //execute the ingredientCommands, add dishes etc
                    c.execute(tasks, ui, taskStorage);
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
