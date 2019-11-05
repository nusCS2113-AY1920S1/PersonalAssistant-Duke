package duke;

import duke.command.Command;
import duke.command.ViewTodoListCommand;
import duke.command.ingredientCommand.ExitCommand;
import duke.command.ingredientCommand.RemoveAllExpired;
import duke.dish.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.order.OrderList;
import duke.parser.Parser;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

/**
 * MAIN CLASS DUKE, start from main function.
 */
public class Duke {

    private FridgeStorage fridgeStorage;
    private OrderStorage orderStorage;
    private RecipeStorage recipeStorage;
    private Ui ui;
    private DishList dish;
    private OrderList order;
    private Fridge fridge;

    private final String fridgeFilePath = "data/fridge.txt";
    private final String orderFilePath = "data/order.txt";
    private final String recipeFilePath = "data/recipebook.txt";

    public enum Type {
        INGREDIENT, DISH, ORDER
    }

    /**
     * The constructor method for Duke.
     */
    public Duke() {
        dish = new DishList();
        order = new OrderList();
        fridge = new Fridge();
        ui = new Ui();
        try {
            fridgeStorage = new FridgeStorage(fridgeFilePath);
            orderStorage = new OrderStorage(orderFilePath);
            recipeStorage = new RecipeStorage(recipeFilePath); //TODO: use this
            fridge = new Fridge(fridgeStorage);
            order = new OrderList(orderStorage.getEntries().getAllEntries());
        } catch (DukeException e) {
            ui.showLoadingError();
            e.printStackTrace();
        }
    }

    /**
     * The execution core of the Duke class.
     */
    public void run() {
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
                        command.execute(null, dish, order, ui, fridgeStorage, orderStorage);
                        isExit = command.isExit();
                        break;
                    }
                    case "t": {
                        Command command = new ViewTodoListCommand();
                        command.execute(null, dish, order, ui, fridgeStorage, orderStorage);
                        isExit = command.isExit();
                        break;
                    }
                    case "a": {
                        Command command = new RemoveAllExpired(fridge);
                        command.execute(null, dish, order, ui, fridgeStorage, orderStorage);
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
                                    command.execute(null, dish, order, ui, fridgeStorage, orderStorage);
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
                                Command command = Parser.parse(fullCommand, Type.INGREDIENT);
                                command.execute(null, dish, order, ui, fridgeStorage, orderStorage);
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
                                    command.execute(null, dish, order, ui, fridgeStorage, orderStorage);
                                    isExit = command.isExit();
                                    break;
                                }
                                if (fullCommand.trim().equals("template")) {
                                    ui.showOrderTemplate();
                                    continue;
                                }
                                Command command = Parser.parse(fullCommand, Type.ORDER);
                                command.execute(null, dish, order, ui, fridgeStorage, orderStorage);
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
                                    command.execute(null, dish, order, ui, fridgeStorage, orderStorage);
                                    isExit = command.isExit();
                                    break;
                                }
                                if(fullCommand.trim().equals("back")) {
                                    break;
                                }
                                if(fullCommand.trim().equals("template")) {
                                    //ui.clearScreen();
                                    continue;
                                }
                                Command command = Parser.parse(fullCommand, Type.DISH);
                                command.execute(null, dish, order, ui, fridgeStorage, orderStorage);
                            } catch (DukeException e) {
                                System.out.println(e.getLocalizedMessage());
                            }
                        }
                        break;
                    }
                    default:
                        throw new DukeException("wrong input");
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