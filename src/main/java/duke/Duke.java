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
//import duke.storage.Storage;
//import duke.list.tasklist.TaskList;
import duke.task.recipetasks.Feedback;
import duke.task.recipetasks.Rating;
import duke.task.recipetasks.RecipeIngredient;
import duke.ui.Ui;

import java.util.ArrayList;
import java.text.ParseException;

import static duke.common.IngredientMessages.COMMAND_ADD_INGREDIENT;
import static duke.common.Messages.*;
import static duke.common.RecipeMessages.*;

/**
 * Duke processes different commands.
 */
public class Duke {

//    private Storage storage;
//    private TaskList taskList;
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

//    /**
//     * Constructor for Duke class to instantiation Ui, Storage, TaskList classes.
//     * @param filePath String containing the directory in which the tasks are to be stored
//     */

    public Duke(Ui ui) {
        this.ui = ui;
 //       storage = new Storage(filePath);
        ingredientStorage = new IngredientStorage(filePathIngredients);
        recipeIngredientStorage = new RecipeIngredientStorage(filePathRecipeIngredients);
        recipeTitleStorage = new RecipeTitleStorage(filePathRecipeTitle);
        bookingStorage = new BookingStorage(filePathBookings);

        try {
//            taskList = new TaskList(storage.load());
            ingredientList = new IngredientList(ingredientStorage.load());
            recipeIngredientList = new RecipeIngredientList(recipeIngredientStorage.load());
            recipeTitleList = new RecipeTitleList(recipeTitleStorage.load());
            bookingList = new BookingList(bookingStorage.load());
//            System.out.println(taskList.getSize());
        } catch (DukeException e) {
            ui.showIngredientLoadingError();
            ui.showLoadingError();
        }
    }

    public String showWelcome() {
        return ui.showWelcome();
    }

    public ArrayList<String> runProgram(String userInput) throws DukeException, ParseException {
        System.out.println("stuck here4");
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.contains(COMMAND_ADD_RECIPE_TITLE)) {
            if (userInput.trim().substring(0, 14).equals(COMMAND_ADD_RECIPE_TITLE)) {
                CommandRecipeTitle command = Parser.parseRecipeTitle(userInput);
                return command.execute(recipeTitleList, ui, recipeTitleStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                System.out.println("stuck here5");
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
        } else if (userInput.contains(COMMAND_ADD_RECIPE_INGREDIENT)) {
            if (userInput.trim().substring(0, 19).equals(COMMAND_ADD_RECIPE_INGREDIENT)) {
                CommandRecipeIngredient command = Parser.parseRecipeIngredient(userInput);
                return command.execute(recipeIngredientList, ui, recipeIngredientStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                System.out.println("stuck here10");
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_DELETE_RECIPE_INGREDIENT)) {
            if (userInput.trim().substring(0, 3).equals(COMMAND_DELETE_RECIPE_INGREDIENT)) {
                CommandRecipeIngredient command = Parser.parseRecipeIngredient(userInput);
                return command.execute(recipeIngredientList, ui, recipeIngredientStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                System.out.println("stuck here10");
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_ADD_INGREDIENT)) {
            if (userInput.trim().substring(0, 13).equals(COMMAND_ADD_INGREDIENT)) {
                CommandIngredients command = Parser.parseIngredient(userInput);
                return command.execute(ingredientList, ui, ingredientStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.trim().equals("allbookings")) {
            CommandBooking command = Parser.parseBooking(userInput);
            return command.execute(bookingList, ui, bookingStorage);
        }
        else if (userInput.contains("addbooking")) {
            if (userInput.trim().substring(0, 10).equals("addbooking")) {
                CommandBooking command = Parser.parseBooking(userInput);
                return command.execute(bookingList, ui, bookingStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        }
        else if (userInput.contains("deletebooking")) {
            if (userInput.trim().substring(0, 13).equals("deletebooking")) {
                CommandBooking command = Parser.parseBooking(userInput);
                return command.execute(bookingList, ui, bookingStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        }
        else if (userInput.contains("viewbookingschedule")) {
            if (userInput.trim().substring(0, 19).equals("viewbookingschedule")) {
                CommandBooking command = Parser.parseBooking(userInput);
                return command.execute(bookingList, ui, bookingStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        }
        else if (userInput.contains("findbooking")) {
            if (userInput.trim().substring(0, 11).equals("findbooking")) {
                CommandBooking command = Parser.parseBooking(userInput);
                return command.execute(bookingList, ui, bookingStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        }

        else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
            System.out.println("stuck here3");
            return arrayList;
        }
//        CommandRecipeTitle command = Parser.parseRecipeTitle(fullCommand);
//        return command.execute(recipeTitleList, ui, recipeTitleStorage);

//        CommandRecipeIngredient command = Parser.parseRecipeIngredients(fullCommand);
//        return command.execute(recipeIngredientList, ui, recipeIngredientStorage);
//
//        CommandIngredients command = Parser.parseIngredients(fullCommand);
//        return command.execute(ingredientList, ui, ingredientStorage);
//
//        CommandTest command = Parser.parseTest(fullCommand);
//        return command.execute(taskList, ui, storage);
    }
}