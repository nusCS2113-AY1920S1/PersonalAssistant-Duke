package duke;

import duke.logic.command.Command;
import duke.logic.command.CommandBooking;
import duke.logic.command.CommandInventory;
import duke.logic.command.CommandRecipe;
import duke.exception.DukeException;
import duke.model.list.bookinglist.BookingList;
import duke.model.list.inventorylist.InventoryList;
import duke.model.list.recipelist.RecipeIngredientList;
import duke.model.list.recipelist.RecipeList;
import duke.logic.parser.Parser;
import duke.storage.*;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.BookingMessages.*;
import static duke.common.InventoryMessages.*;
import static duke.common.Messages.*;
import static duke.common.RecipeMessages.*;

/**
 * Duke processes different commands.
 */
public class Duke {

    private Ui ui;

    private InventoryStorage inventoryStorage;
    private RecipeIngredientStorage recipeIngredientStorage;
    private BookingStorage bookingStorage;
    private InventoryList inventoryList;
    private RecipeIngredientList recipeIngredientList;
    private BookingList bookingList;

    private RecipeStorage recipeStorage;
    private RecipeList recipeList;

    public Duke(Ui ui) {
        this.ui = ui;
        inventoryStorage = new InventoryStorage(filePathInventory);
        recipeIngredientStorage = new RecipeIngredientStorage(filePathRecipeIngredients);
        bookingStorage = new BookingStorage(filePathBookings);
        recipeStorage = new RecipeStorage(filePathRecipes);

        try {
            inventoryList = new InventoryList(inventoryStorage.load());
            recipeIngredientList = new RecipeIngredientList(recipeIngredientStorage.load());
            bookingList = new BookingList(bookingStorage.load());
            recipeList = new RecipeList(recipeStorage.load());
        } catch (DukeException e) {
            ui.showIngredientLoadingError();
            ui.showLoadingError();
        }
    }

    public String showWelcome() {
        return ui.showWelcome();
    }

    public ArrayList<String> runProgram(String userInput) throws DukeException, ParseException {
        System.out.println("stuck here1");
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.contains(COMMAND_EDIT_RECIPE)) {
            System.out.println("stuck here2");
            if (userInput.trim().substring(0, 9).equals(COMMAND_EDIT_RECIPE)) {
                System.out.println("stuck here3");
                Command<RecipeList, Ui, RecipeStorage> command = Parser.parseRecipe(userInput);
                return command.execute(recipeList, ui, recipeStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                System.out.println("stuck here4");
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_ADD_RECIPE)) {
            System.out.println("stuck here5");
            if (userInput.trim().substring(0, 9).equals(COMMAND_ADD_RECIPE)) {
                System.out.println("stuck here6");
                Command<RecipeList, Ui, RecipeStorage> command = Parser.parseRecipe(userInput);
                return command.execute(recipeList, ui, recipeStorage);
            } else {
                System.out.println("stuck here7");
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_ADD_RECIPE_INGREDIENT)) {
            System.out.println("stuck here add recipe ingredient");
            if (userInput.trim().substring(0, 21).equals(COMMAND_ADD_RECIPE_INGREDIENT)) {
                System.out.println("stuck here add recipe ingredient 1");
                CommandRecipe<RecipeIngredientList, RecipeList, RecipeIngredientStorage, RecipeStorage> command = Parser.parseRecipeIngredient(userInput);
                return command.execute(recipeIngredientList, recipeList, recipeIngredientStorage, recipeStorage);
            } else {
                System.out.println("stuck here7");
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_DELETE_RECIPE)) {
            if (userInput.trim().substring(0, 12).equals(COMMAND_DELETE_RECIPE)) {
                System.out.println("stuck here6");
                Command<RecipeList, Ui, RecipeStorage> command = Parser.parseRecipe(userInput);
                return command.execute(recipeList, ui, recipeStorage);
            } else {
                System.out.println("stuck here7");
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_LIST_RECIPES)) {
            System.out.println("stuck here list all recipes 100");
            if (userInput.trim().substring(0, 14).equals(COMMAND_LIST_RECIPES)) {
                System.out.println("stuck here list all recipes 101");
                Command<RecipeList, Ui, RecipeStorage> command = Parser.parseRecipe(userInput);
                return command.execute(recipeList, ui, recipeStorage);
            } else {
                System.out.println("stuck here7");
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_ADD_TO_INVENTORY)) {
            System.out.println("stuck here17");
            if (userInput.trim().substring(0, 14).equals(COMMAND_ADD_TO_INVENTORY)) {
                System.out.println("stuck here18");
                CommandInventory command = Parser.parseIngredient(userInput);
                return command.execute(inventoryList, inventoryStorage);
            } else {
                System.out.println("stuck here19");
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_DELETE_FROM_INVENTORY)) {
            if (userInput.trim().substring(0, 19).equals(COMMAND_DELETE_FROM_INVENTORY)) {
                CommandInventory command = Parser.parseIngredient(userInput);
                return command.execute(inventoryList, inventoryStorage);
            } else {
                System.out.println("stuck here19");
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_LIST_INVENTORY)) {
            if (userInput.trim().substring(0, 13).equals(COMMAND_LIST_INVENTORY)) {
                CommandInventory command = Parser.parseIngredient(userInput);
                return command.execute(inventoryList, inventoryStorage);
            } else {
                System.out.println("stuck here19");
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.trim().equals(COMMAND_LIST_BOOKINGS)) {
            System.out.println("stuck here 20");
            CommandBooking command = Parser.parseBooking(userInput);
            return command.execute(bookingList, ui, bookingStorage);

        } else if (userInput.contains(COMMAND_ADD_BOOKING)) {
            System.out.println("stuck here 21");
            if (userInput.trim().substring(0, 10).equals(COMMAND_ADD_BOOKING)) {
                System.out.println("stuck here 21.1");
                CommandBooking command = Parser.parseBooking(userInput);
                return command.execute(bookingList, ui, bookingStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_DELETE_BOOKING)) {
            System.out.println("stuck here 22");
            if (userInput.trim().substring(0, 13).equals(COMMAND_DELETE_BOOKING)) {
                System.out.println("stuck here 22.1");
                CommandBooking command = Parser.parseBooking(userInput);
                return command.execute(bookingList, ui, bookingStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_VIEW_BOOKING_SCHEDULE)) {
            System.out.println("stuck here 23");
            if (userInput.trim().substring(0, 19).equals(COMMAND_VIEW_BOOKING_SCHEDULE)) {
                System.out.println("stuck here 23.1");
                CommandBooking command = Parser.parseBooking(userInput);
                return command.execute(bookingList, ui, bookingStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_FIND_BOOKING)) {
            System.out.println("stuck here 24");
            if (userInput.trim().substring(0, 11).equals(COMMAND_FIND_BOOKING)) {
                System.out.println("stuck here 24.1");
                CommandBooking command = Parser.parseBooking(userInput);
                return command.execute(bookingList, ui, bookingStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else {
            System.out.println("stuck here 99");
            arrayList.add(ERROR_MESSAGE_RANDOM);
            return arrayList;
        }
    }
}