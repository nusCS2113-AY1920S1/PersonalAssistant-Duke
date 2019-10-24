package duke;

import duke.command.*;
import duke.exception.DukeException;
import duke.list.bookinglist.BookingList;
import duke.list.inventorylist.InventoryList;
import duke.list.recipelist.RecipeIngredientList;
import duke.list.recipelist.RecipeList;
import duke.list.recipelist.RecipeTitleList;
import duke.parser.Parser;
import duke.storage.*;
import duke.task.recipetasks.Feedback;
import duke.task.recipetasks.Rating2;
import duke.task.recipetasks.RecipeIngredient;
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
    private RecipeTitleStorage recipeTitleStorage;
    private InventoryList inventoryList;
    private RecipeIngredientList recipeIngredientList;
    private BookingList bookingList;
    private RecipeTitleList recipeTitleList;
    private RecipeIngredient recipeIngredient;
    private Rating2 rating2;
    private Feedback feedback;

    private RecipeStorage recipeStorage;
    private RecipeList recipeList;

    public Duke(Ui ui) {
        this.ui = ui;
        inventoryStorage = new InventoryStorage(filePathInventory);
        recipeIngredientStorage = new RecipeIngredientStorage(filePathRecipeIngredients);
        recipeTitleStorage = new RecipeTitleStorage(filePathRecipeTitle);
        bookingStorage = new BookingStorage(filePathBookings);
        recipeStorage = new RecipeStorage(filePathRecipes);

        try {
            inventoryList = new InventoryList(inventoryStorage.load());
            recipeIngredientList = new RecipeIngredientList(recipeIngredientStorage.load());
            recipeTitleList = new RecipeTitleList(recipeTitleStorage.load());
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

    //Should the runProgram method deals with string manipulations?
    // I hope its responsibility is to call relevant objects to initiate the run
    //I hope it is not the responsibility of the runProgram method to decide what does the command user type and react to it.
    // Please check it again
    public ArrayList<String> runProgram(String userInput) throws DukeException, ParseException {

        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.contains(COMMAND_ADD_RECIPE_TITLE)) {
            if (userInput.trim().substring(0, 14).equals(COMMAND_ADD_RECIPE_TITLE)) {
                CommandRecipeTitle command = Parser.parseRecipeTitle(userInput);
                return command.execute(recipeTitleList, ui, recipeTitleStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_ADD_RECIPE)) {
            System.out.println("stuck here5");
            if (userInput.trim().substring(0, 9).equals(COMMAND_ADD_RECIPE)) {
                System.out.println("stuck here6");
                CommandRecipe command = Parser.parseRecipe(userInput);
                return command.execute(recipeList, recipeStorage);
            } else {
                System.out.println("stuck here7");
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_DELETE_RECIPE)) {
            if (userInput.trim().substring(0, 12).equals(COMMAND_DELETE_RECIPE)) {
                System.out.println("stuck here6");
                CommandRecipe command = Parser.parseRecipe(userInput);
                return command.execute(recipeList, recipeStorage);
            } else {
                System.out.println("stuck here7");
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_LIST_RECIPES)) {
            if (userInput.trim().substring(0, 14).equals(COMMAND_LIST_RECIPES)) {
                System.out.println("stuck here6");
                CommandRecipe command = Parser.parseRecipe(userInput);
                return command.execute(recipeList, recipeStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_LIST_RECIPE_INGREDIENT)) {
            if (userInput.trim().substring(0, 14).equals(COMMAND_LIST_RECIPE_INGREDIENT)) {
                CommandRecipeIngredient command = Parser.parseRecipeIngredient(userInput);
                return command.execute(recipeIngredientList, ui, recipeIngredientStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_ADD_RECIPE_INGREDIENT)) {
            if (userInput.trim().substring(0, 19).equals(COMMAND_ADD_RECIPE_INGREDIENT)) {
                CommandRecipeIngredient command = Parser.parseRecipeIngredient(userInput);
                return command.execute(recipeIngredientList, ui, recipeIngredientStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_DELETE_RECIPE_INGREDIENT)) {
            if (userInput.trim().substring(0, 5).equals(COMMAND_DELETE_RECIPE_INGREDIENT)) {
                CommandRecipeIngredient command = Parser.parseRecipeIngredient(userInput);
                return command.execute(recipeIngredientList, ui, recipeIngredientStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_DELETE_RECIPE_TITLE)) {
            if (userInput.trim().substring(0, 5).equals(COMMAND_DELETE_RECIPE_TITLE)) {
                CommandRecipeTitle command = Parser.parseRecipeTitle(userInput);
                return command.execute(recipeTitleList, ui, recipeTitleStorage);
            } else {
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
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_DELETE_FROM_INVENTORY)) {
            if (userInput.trim().substring(0, 19).equals(COMMAND_DELETE_FROM_INVENTORY)) {
                CommandInventory command = Parser.parseIngredient(userInput);
                return command.execute(inventoryList, inventoryStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_LIST_INVENTORY)) {
            if (userInput.trim().substring(0, 13).equals(COMMAND_LIST_INVENTORY)) {
                CommandInventory command = Parser.parseIngredient(userInput);
                return command.execute(inventoryList, inventoryStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.trim().equals(COMMAND_LIST_BOOKINGS)) {
            CommandBooking command = Parser.parseBooking(userInput);
            return command.execute(bookingList, ui, bookingStorage);

        } else if (userInput.contains(COMMAND_ADD_BOOKING)) {
            if (userInput.trim().substring(0, 10).equals(COMMAND_ADD_BOOKING)) {
                CommandBooking command = Parser.parseBooking(userInput);
                return command.execute(bookingList, ui, bookingStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_DELETE_BOOKING)) {
            if (userInput.trim().substring(0, 13).equals(COMMAND_DELETE_BOOKING)) {
                CommandBooking command = Parser.parseBooking(userInput);
                return command.execute(bookingList, ui, bookingStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_VIEW_BOOKING_SCHEDULE)) {
            if (userInput.trim().substring(0, 19).equals(COMMAND_VIEW_BOOKING_SCHEDULE)) {
                CommandBooking command = Parser.parseBooking(userInput);
                return command.execute(bookingList, ui, bookingStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_FIND_BOOKING)) {
            if (userInput.trim().substring(0, 11).equals(COMMAND_FIND_BOOKING)) {
                CommandBooking command = Parser.parseBooking(userInput);
                return command.execute(bookingList, ui, bookingStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_VIEW_ORDERS)) {
            if (userInput.trim().substring(0, 10).equals(COMMAND_VIEW_ORDERS)) {
                CommandBooking command = Parser.parseBooking(userInput);
                return command.execute(bookingList, ui, bookingStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
            return arrayList;
        }
    }
}