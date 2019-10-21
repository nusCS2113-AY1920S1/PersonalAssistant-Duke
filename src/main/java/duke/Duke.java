package duke;

import duke.command.Command;
import duke.command.CommandBooking;
import duke.command.CommandIngredients;
import duke.exception.DukeException;
import duke.list.bookinglist.BookingList;
import duke.list.ingredientlist.IngredientList;
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
    private Rating2 rating2;
    private Feedback feedback;

    private RecipeStorage recipeStorage;
    private RecipeList recipeList;


//    /**
//     * Constructor for Duke class to instantiation Ui, Storage, TaskList classes.
//     * @param filePath String containing the directory in which the tasks are to be stored
//     */

    public Duke(Ui ui) {
        this.ui = ui;
        ingredientStorage = new IngredientStorage(filePathIngredients);
        recipeIngredientStorage = new RecipeIngredientStorage(filePathRecipeIngredients);
        recipeTitleStorage = new RecipeTitleStorage(filePathRecipeTitle);
        bookingStorage = new BookingStorage(filePathBookings);
        recipeStorage = new RecipeStorage(filePathRecipes);

        try {
            ingredientList = new IngredientList(ingredientStorage.load());
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

    public ArrayList<String> runProgram(String userInput) throws DukeException, ParseException {
        System.out.println("stuck here1");
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.contains(COMMAND_ADD_RECIPE_TITLE)) {
            System.out.println("stuck here2");
            if (userInput.trim().substring(0, 14).equals(COMMAND_ADD_RECIPE_TITLE)) {
                System.out.println("stuck here3");
                Command<RecipeTitleList, Ui, RecipeTitleStorage> command = Parser.parseRecipeTitle(userInput);
                return command.execute(recipeTitleList, ui, recipeTitleStorage);
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
//        } else if (userInput.contains(COMMAND_LIST_RECIPE_INGREDIENT)) {
//            System.out.println("stuck here8");
//            if (userInput.trim().substring(0, 14).equals(COMMAND_LIST_RECIPE_INGREDIENT)) {
//                System.out.println("stuck here9");
//                Command<RecipeIngredientList, Ui, RecipeIngredientStorage> command = Parser.parseRecipeIngredient(userInput);
//                return command.execute(recipeIngredientList, ui, recipeIngredientStorage);
//            } else {
//                arrayList.add(ERROR_MESSAGE_RANDOM);
//                System.out.println("stuck here10");
//                return arrayList;
//            }
        } else if (userInput.contains(COMMAND_ADD_RECIPE_INGREDIENT)) {
            System.out.println("stuck here11");
            if (userInput.trim().substring(0, 19).equals(COMMAND_ADD_RECIPE_INGREDIENT)) {
                System.out.println("stuck here12");
                Command<RecipeList, Ui, RecipeStorage> command = Parser.parseRecipe(userInput);
                return command.execute(recipeList, ui, recipeStorage);
            } else {
                System.out.println("stuck here13");
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
//        } else if (userInput.contains(COMMAND_DELETE_RECIPE_INGREDIENT)) {
//            System.out.println("stuck here14");
//            if (userInput.trim().substring(0, 5).equals(COMMAND_DELETE_RECIPE_INGREDIENT)) {
//                System.out.println("stuck here15");
//                Command<RecipeIngredientList, Ui, RecipeIngredientStorage> command = Parser.parseRecipeIngredient(userInput);
//                return command.execute(recipeIngredientList, ui, recipeIngredientStorage);
//            } else {
//                System.out.println("stuck here16");
//                arrayList.add(ERROR_MESSAGE_RANDOM);
//                return arrayList;
//            }
        } else if (userInput.contains(COMMAND_DELETE_RECIPE_TITLE)) {
            System.out.println("stuck here33");
            if (userInput.trim().substring(0, 5).equals(COMMAND_DELETE_RECIPE_TITLE)) {
                System.out.println("stuck here34");
                Command<RecipeTitleList, Ui, RecipeTitleStorage> command = Parser.parseRecipeTitle(userInput);
                return command.execute(recipeTitleList, ui, recipeTitleStorage);
            } else {
                System.out.println("stuck here35");
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_ADD_INGREDIENT)) {
            System.out.println("stuck here17");
            if (userInput.trim().substring(0, 14).equals(COMMAND_ADD_INGREDIENT)) {
                System.out.println("stuck here18");
                CommandIngredients command = Parser.parseIngredient(userInput);
                return command.execute(ingredientList, ui, ingredientStorage);
            } else {
                System.out.println("stuck here19");
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_DELETE_INGREDIENT)) {
            if (userInput.trim().substring(0, 19).equals(COMMAND_DELETE_INGREDIENT)) {
                CommandIngredients command = Parser.parseIngredient(userInput);
                return command.execute(ingredientList, ui, ingredientStorage);
            } else {
                System.out.println("stuck here19");
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_LIST_INGREDIENTS)) {
            if (userInput.trim().substring(0, 13).equals(COMMAND_LIST_INGREDIENTS)) {
                CommandIngredients command = Parser.parseIngredient(userInput);
                return command.execute(ingredientList, ui, ingredientStorage);
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