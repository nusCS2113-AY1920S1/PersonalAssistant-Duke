package duke;

import duke.command.CommandBooking;
import duke.command.CommandIngredients;
import duke.command.CommandRecipeIngredient;
import duke.command.CommandRecipeTitle;
import duke.exception.DukeException;
import duke.list.bookinglist.BookingList;
import duke.list.ingredientlist.IngredientList;
import duke.list.recipelist.RecipeIngredientList;
import duke.parser.Parser;
import duke.list.recipelist.RecipeTitleList;
import duke.storage.BookingStorage;
import duke.storage.IngredientStorage;
import duke.storage.RecipeIngredientStorage;
import duke.storage.RecipeTitleStorage;
import duke.task.recipetasks.Feedback;
import duke.task.recipetasks.Rating;
import duke.task.recipetasks.RecipeIngredient;
import duke.ui.Ui;

import java.util.ArrayList;
import java.text.ParseException;

import static duke.common.BookingMessages.*;
import static duke.common.IngredientMessages.*;
import static duke.common.Messages.*;
import static duke.common.RecipeMessages.*;

/**
 * Duke processes different commands.
 */
public class Duke {

    private Ui ui;

    private IngredientStorage ingredientStorage;
    private RecipeIngredientStorage recipeIngredientStorage;
    private BookingStorage bookingStorage;
    private RecipeTitleStorage recipeTitleStorage;
    private IngredientList ingredientList;
    private RecipeIngredientList recipeIngredientList;
    private BookingList bookingList;
    private RecipeTitleList recipeTitleList;
    private RecipeIngredient recipeIngredient;
    private Rating rating;
    private Feedback feedback;

    public Duke(Ui ui) {
        this.ui = ui;
        ingredientStorage = new IngredientStorage(filePathIngredients);
        recipeIngredientStorage = new RecipeIngredientStorage(filePathRecipeIngredients);
        recipeTitleStorage = new RecipeTitleStorage(filePathRecipeTitle);
        bookingStorage = new BookingStorage(filePathBookings);

        try {
            ingredientList = new IngredientList(ingredientStorage.load());
            recipeIngredientList = new RecipeIngredientList(recipeIngredientStorage.load());
            recipeTitleList = new RecipeTitleList(recipeTitleStorage.load());
            bookingList = new BookingList(bookingStorage.load());
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
        } else if (userInput.contains(COMMAND_LIST_RECIPES)) {
            if (userInput.trim().substring(0, 14).equals(COMMAND_LIST_RECIPES)) {
                CommandRecipeTitle command = Parser.parseRecipeTitle(userInput);
                return command.execute(recipeTitleList, ui, recipeTitleStorage);
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
        } else if (userInput.contains(COMMAND_ADD_INGREDIENT)) {
            if (userInput.trim().substring(0, 14).equals(COMMAND_ADD_INGREDIENT)) {
                CommandIngredients command = Parser.parseIngredient(userInput);
                return command.execute(ingredientList, ui, ingredientStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_DELETE_INGREDIENT)) {
            if (userInput.trim().substring(0, 19).equals(COMMAND_DELETE_INGREDIENT)) {
                CommandIngredients command = Parser.parseIngredient(userInput);
                return command.execute(ingredientList, ui, ingredientStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_LIST_INGREDIENTS)) {
            if (userInput.trim().substring(0, 13).equals(COMMAND_LIST_INGREDIENTS)) {
                CommandIngredients command = Parser.parseIngredient(userInput);
                return command.execute(ingredientList, ui, ingredientStorage);
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