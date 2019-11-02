package duke;

import duke.command.Command;
import duke.command.ViewTodoListCommand;
import duke.command.ingredientCommand.ExitCommand;
import duke.command.ingredientCommand.RemoveAllExpired;
import duke.dish.Dish;
import duke.dish.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.ingredient.Ingredient;
import duke.order.Order;
import duke.order.OrderList;
import duke.parser.Parser;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

import java.io.IOException;

/**
 * MAIN CLASS DUKE, start from main function.
 */
public class Duke {

    private FridgeStorage fridgeStorage;
    private Storage<Order> orderStorage;
    private TaskList tasks;
    private Ui ui;
    private DishList dish;
    private OrderList order;
    private Fridge fridge;

    public enum Type {
        INGREDIENT, DISH, ORDER
    }
    /**
     * The constructor method for Duke.
     *
     * @param fridgeFilePath  used to specify the location of the fridge storage file in the hard disc.
     * @param  orderFilePath used to specify the location of the order storage file in the hard disc.
     */
    public Duke(String fridgeFilePath,  String orderFilePath) throws DukeException {
        dish = new DishList();
        order = new OrderList();
        fridge = new Fridge();
        ui = new Ui();
        fridgeStorage = new FridgeStorage(fridgeFilePath);
        orderStorage = new OrderStorage(orderFilePath);
        try {
            fridge = new Fridge(fridgeStorage);
            order = new OrderList(orderStorage.getEntries().getAllEntries());
        } catch (DukeException e) {
            ui.showLoadingError();
            e.printStackTrace();
            tasks = new TaskList();
        }
    }

    /**
     * The execution core of the Duke class.
     */
    public void run() throws IOException, InterruptedException {
        String fullCommand;
        //ui.clearScreen();
        ui.showWelcome();
        if (fridge.hasExpiredIngredients()) {
            ui.showHasExpiring();
            fullCommand = ui.readCommand();
            ui.showLine();
            if (fullCommand.equalsIgnoreCase("yes")) {
                ui.show(fridge.getExpiredIngredients().toString());
            }
        }
        ui.showLine();

        boolean isExit = false;
        boolean back = false;
        while (!isExit) {
            try {
                ui.chefDrawing();
                ui.showOptions();
                ui.showLine();
                fullCommand = ui.readCommand();
                //ui.clearScreen();
                ui.showLine();
                switch (fullCommand) {
                    case "options": {
                        ui.showOptions();
                        break;
                    }
                    case "q": {
                        Command command = new ExitCommand();
                        command.execute(null, ui, null);
                        isExit = command.isExit();
                        break;
                    }
                    case "t": {
                        Command command = new ViewTodoListCommand();
                        command.execute(order, ui, orderStorage);
                        isExit = command.isExit();
                        break;
                    }
                    case "a": {
                        Command<Ingredient> command = new RemoveAllExpired(fridge);
                        command.execute(fridge.getAllIngredients(), ui, fridgeStorage);
                        isExit = command.isExit();
                        break;
                    }
                    case "b": {
                        // ui.showIngredientsInFridge(fridge.getAllIngredients());
                        ui.showIngredientTask();
                        while (true) {
                            try {
                                fullCommand = ui.readCommand();
                                //ui.clearScreen();
                                if (fullCommand.trim().equals("back")) {
                                    break;
                                }
                                if (fullCommand.trim().equals("q")) {
                                    Command command = new ExitCommand();
                                    command.execute(null, ui, null);
                                    isExit = command.isExit();
                                    break;
                                }
                                if (fullCommand.trim().equals("show")) {
                                    ui.showIngredientsInFridge(fridge.getAllIngredients());
                                    continue;
                                }
                                if (fullCommand.trim().equals("template")) {
                                    ui.showIngredientTemplate();
                                    continue;
                                }
                                Command<Ingredient> command = Parser.parse(fullCommand, Type.INGREDIENT);
                                command.execute(fridge.getAllIngredients(), ui, fridgeStorage);
                            } catch (DukeException e) {
                                System.out.println(e.getLocalizedMessage());
                                // e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case "c": {
                        System.out.println("\t Managing order now\n\t You can type 'template' to retrieve command format");
                        ui.showLine();
                        while (true) {
                            try {
                                fullCommand = ui.readCommand();
                                //ui.clearScreen();
                                if (fullCommand.trim().equals("back")) { break; }
                                if (fullCommand.trim().equals("q")) {
                                    Command command = new ExitCommand();
                                    command.execute(null, ui, null);
                                    isExit = command.isExit();
                                    break;
                                }
                                if (fullCommand.trim().equals("template")) {
                                    ui.showOrderTemplate();
                                    continue;
                                }
                                Command<Order> command = Parser.parse(fullCommand, Type.ORDER);
                                command.execute(order, ui, orderStorage);
                            } catch (DukeException e) {
                                System.out.println(e.getLocalizedMessage());
                            }
                        }
                        break;
                    }
                    case "d": {
                        while(true) {
                            try {
                                ui.showDishTemplate();
                                fullCommand = ui.readCommand();
                                //ui.clearScreen();
                                if(fullCommand.trim().equals("q")) {
                                    Command command = new ExitCommand();
                                    command.execute(null, ui, null);
                                    isExit = command.isExit();
                                    break;
                                }
                                if(fullCommand.trim().equals("back")) {
                                    break;
                                }
                                if(fullCommand.trim().equals("template")) {
                                    ui.clearScreen();
                                    continue;
                                }
                                Command<Dish> command = Parser.parse(fullCommand, Type.DISH);
                                command.execute(dish, ui, orderStorage);
                            } catch (DukeException e) {
                                System.out.println(e.getLocalizedMessage());
                            }
                        }
                        break;
                    }
                    default:
                        throw new DukeException("wrong input");
                }
            } catch (DukeException | IOException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * =============== MAIN FUNCTION ===============.
     */
    public static void main(String[] args) throws IOException, InterruptedException, DukeException {
        String fridgeFilePath = "data/fridge.txt";
        String orderFilePath = "data/order.txt";
        new Duke(fridgeFilePath, orderFilePath).run();
    }
}
