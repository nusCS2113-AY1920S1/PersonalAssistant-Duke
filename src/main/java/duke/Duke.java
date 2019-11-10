package duke;

import duke.command.Command;
import duke.command.ViewTodoListCommand;
import duke.command.ExitCommand;
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

import java.io.IOException;

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

    private final String fridgeFilePath = "fridge.txt";
    private final String orderFilePath = "order.txt";
    private final String recipeFilePath = "recipebook.txt";

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
            order = new OrderList(orderStorage.load().getAllEntries());
            dish = new DishList(recipeStorage.load().getAllEntries());
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
        ui.showWelcome();

        if (fridge.hasExpiredIngredients()) {
            ui.showHasExpiring();
            fullCommand = ui.readCommand();
            ui.showLine();
            if (fullCommand.equalsIgnoreCase("yes")) {
                ui.showExpiredIngredients(fridge.getExpiredIngredients());
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
                ui.showLine();
                switch (fullCommand) {
                    case "options": {
                        ui.showOptions();
                        break;
                    }
                    case "q": {
                        Command command = new ExitCommand();
                        command.execute(fridge, dish, order, ui, fridgeStorage, orderStorage, recipeStorage) ;
                        isExit = command.isExit();
                        break;
                    }
                    case "t": {
                        Command command = new ViewTodoListCommand();
                        command.execute(fridge, dish, order, ui, fridgeStorage, orderStorage, recipeStorage);
                        isExit = command.isExit();
                        break;
                    }
                    case "a": {
                        Command command = new RemoveAllExpired(fridge);
                        command.execute(fridge, dish, order, ui, fridgeStorage, orderStorage, recipeStorage);
                        isExit = command.isExit();
                        break;
                    }
                    case "b": {
                        ui.showIngredientTask();
                        while (true) {
                            try {
                                fullCommand = ui.readCommand();
                                if (fullCommand.trim().equals("back")) {
                                    break;
                                }
                                if (fullCommand.trim().equals("q")) {
                                    Command command = new ExitCommand();
                                    command.execute(fridge, dish, order, ui, fridgeStorage, orderStorage, recipeStorage);
                                    isExit = command.isExit();
                                    break;
                                }
                                if (fullCommand.trim().equals("show")) {
                                    ui.showIngredientsInFridge(fridge.getAllIngredients());
                                    continue;
                                }
                                if (fullCommand.trim().equals("template")) {
                                    ui.ingredientDrawing();
                                    ui.showIngredientTemplate();
                                    continue;
                                }
                                Command command = Parser.parse(fullCommand, Type.INGREDIENT);
                                command.execute(fridge, dish, order, ui, fridgeStorage, orderStorage, recipeStorage);
                            } catch (DukeException e) {
                                System.out.println(e.getLocalizedMessage());
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
                                if (fullCommand.trim().equals("back")) {
                                    break;
                                }
                                if (fullCommand.trim().equals("q")) {
                                    Command command = new ExitCommand();
                                    command.execute(null, dish, order, ui, fridgeStorage, orderStorage, recipeStorage);
                                    isExit = command.isExit();
                                    break;
                                }
                                if (fullCommand.trim().equals("template")) {
                                    ui.showOrderTemplate();
                                    continue;
                                }
                                Command command = Parser.parse(fullCommand, Type.ORDER);
                                command.execute(null, dish, order, ui, fridgeStorage, orderStorage, recipeStorage);
                            } catch (DukeException e) {
                                System.out.println(e.getLocalizedMessage());
                            }
                        }
                        break;
                    }
                    case "d": {
                        ui.showDishTemplate();
                        while (true) {
                            try {
                                fullCommand = ui.readCommand();
                                if (fullCommand.trim().equals("q")) {
                                    Command command = new ExitCommand();
                                    command.execute(null, dish, order, ui, fridgeStorage, orderStorage, recipeStorage);
                                    isExit = command.isExit();
                                    break;
                                }
                                if (fullCommand.trim().equals("back")) {
                                    break;
                                }
                                if (fullCommand.trim().equals("template")) {
                                    ui.showDishTemplate();
                                    continue;
                                }
                                Command command = Parser.parse(fullCommand, Type.DISH);
                                command.execute(null, dish, order, ui, fridgeStorage, orderStorage, recipeStorage);
                            } catch (DukeException e) {
                                System.out.println(e.getLocalizedMessage());
                            }
                        }
                        break;
                    }
                    default:
                        System.out.println("\t OOPS!!! Wrong input!");
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
    public static void main(String[] args) {
        new Duke().run();
    }
}