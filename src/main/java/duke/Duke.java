package duke;

import duke.logic.command.Command;
import duke.logic.parser.Parser;
import duke.model.list.bookinglist.BookingList;
import duke.model.list.inventorylist.InventoryList;
import duke.model.list.recipelist.RecipeList;
import duke.storage.BookingStorage;
import duke.storage.InventoryStorage;
import duke.storage.RecipeStorage;
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
    private BookingStorage bookingStorage;
    private InventoryList inventoryList;
    private BookingList bookingList;

    private RecipeStorage recipeStorage;
    private RecipeList recipeList;

    public Duke(Ui ui) {
        this.ui = ui;
        inventoryStorage = new InventoryStorage(filePathInventory);
        bookingStorage = new BookingStorage(filePathBookings);
        recipeStorage = new RecipeStorage(filePathRecipes);

        inventoryList = new InventoryList(inventoryStorage.load());
        bookingList = new BookingList(bookingStorage.load());
        recipeList = new RecipeList(recipeStorage.load());
    }

    public String showWelcome() {
        return ui.showWelcome();
    }

    //Should the runProgram method deals with string manipulations?
    // I hope its responsibility is to call relevant objects to initiate the run
    //I hope it is not the responsibility of the runProgram method to decide what does the command user type and react to it.
    // Please check it again
    public ArrayList<String> runProgram(String userInput) throws ParseException {

        ArrayList<String> arrayList = new ArrayList<>();

        // RECIPE.
        if (userInput.contains(COMMAND_ADD_RECIPE)) {
            if (userInput.trim().substring(0, 9).equals(COMMAND_ADD_RECIPE)) {
                Command<RecipeList, Ui, RecipeStorage> command = Parser.parse(userInput);
                return command.execute(recipeList, ui, recipeStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_DELETE_RECIPE)) {
            if (userInput.trim().substring(0, 12).equals(COMMAND_DELETE_RECIPE)) {
                Command<RecipeList, Ui, RecipeStorage> command = Parser.parse(userInput);
                return command.execute(recipeList, ui, recipeStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_LIST_RECIPES)) {
            if (userInput.trim().substring(0, 14).equals(COMMAND_LIST_RECIPES)) {
                Command<RecipeList, Ui, RecipeStorage> command = Parser.parse(userInput);
                return command.execute(recipeList, ui, recipeStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_VIEW_RECIPE)) {
            if (userInput.trim().substring(0, 10).equals(COMMAND_VIEW_RECIPE)) {
                Command<RecipeList, Ui, RecipeStorage> command = Parser.parse(userInput);
                return command.execute(recipeList, ui, recipeStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_VIEW_REQ_INGREDIENT)) {
            if (userInput.trim().substring(0, 17).equals(COMMAND_VIEW_REQ_INGREDIENT)) {
                Command<RecipeList, Ui, RecipeStorage> command = Parser.parse(userInput);
                return command.execute(recipeList, ui, recipeStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_EDIT_REQ_INGREDIENT)) {
            if (userInput.trim().substring(0, 17).equals(COMMAND_EDIT_REQ_INGREDIENT)) {
                Command<RecipeList, Ui, RecipeStorage> command = Parser.parse(userInput);
                return command.execute(recipeList, ui, recipeStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_EDIT_RATING)) {
            if (userInput.trim().substring(0, 10).equals(COMMAND_EDIT_RATING)) {
                Command<RecipeList, Ui, RecipeStorage> command = Parser.parse(userInput);
                return command.execute(recipeList, ui, recipeStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_EDIT_FEEDBACK)) {
            if (userInput.trim().substring(0, 12).equals(COMMAND_EDIT_FEEDBACK)) {
                Command<RecipeList, Ui, RecipeStorage> command = Parser.parse(userInput);
                return command.execute(recipeList, ui, recipeStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_EDIT_PREPSTEP)) {
            if (userInput.trim().substring(0, 12).equals(COMMAND_EDIT_PREPSTEP)) {
                Command<RecipeList, Ui, RecipeStorage> command = Parser.parse(userInput);
                return command.execute(recipeList, ui, recipeStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_EDIT_PREPTIME)) {
            if (userInput.trim().substring(0, 12).equals(COMMAND_EDIT_PREPTIME)) {
                Command<RecipeList, Ui, RecipeStorage> command = Parser.parse(userInput);
                return command.execute(recipeList, ui, recipeStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        }

        // INVENTORY.
        else if (userInput.contains(COMMAND_ADD_TO_INVENTORY)) {
            if (userInput.trim().substring(0, 14).equals(COMMAND_ADD_TO_INVENTORY)) {
                Command<InventoryList, Ui, InventoryStorage> command = Parser.parse(userInput);
                return command.execute(inventoryList, ui, inventoryStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_DELETE_FROM_INVENTORY)) {
            if (userInput.trim().substring(0, 19).equals(COMMAND_DELETE_FROM_INVENTORY)) {
                Command<InventoryList, Ui, InventoryStorage> command = Parser.parse(userInput);
                return command.execute(inventoryList, ui, inventoryStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_LIST_INVENTORY)) {
            if (userInput.trim().substring(0, 13).equals(COMMAND_LIST_INVENTORY)) {
                Command<InventoryList, Ui, InventoryStorage> command = Parser.parse(userInput);
                return command.execute(inventoryList, ui, inventoryStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_CLEAR_INVENTORY)) {
            if (userInput.trim().substring(0, 14).equals(COMMAND_CLEAR_INVENTORY)) {
                Command<InventoryList, Ui, InventoryStorage> command = Parser.parse(userInput);
                return command.execute(inventoryList, ui, inventoryStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_USE_RECIPE)) {
            if (userInput.trim().substring(0, 9).equals(COMMAND_USE_RECIPE)) {
                Command<InventoryList, RecipeList, InventoryStorage> command = Parser.parse(userInput);
                return command.execute(inventoryList, recipeList, inventoryStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        }


        // BOOKING.
        else if (userInput.trim().equals(COMMAND_LIST_BOOKINGS)) {
            Command<BookingList, Ui, BookingStorage> command = Parser.parse(userInput);
            return command.execute(bookingList, ui, bookingStorage);

        } else if (userInput.contains(COMMAND_ADD_BOOKING)) {
            if (userInput.trim().substring(0, 10).equals(COMMAND_ADD_BOOKING)) {
                Command<BookingList, Ui, BookingStorage> command = Parser.parse(userInput);
                return command.execute(bookingList, ui, bookingStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_DELETE_BOOKING)) {
            if (userInput.trim().substring(0, 13).equals(COMMAND_DELETE_BOOKING)) {
                Command<BookingList, Ui, BookingStorage> command = Parser.parse(userInput);
                return command.execute(bookingList, ui, bookingStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_VIEW_BOOKING_SCHEDULE)) {
            if (userInput.trim().substring(0, 19).equals(COMMAND_VIEW_BOOKING_SCHEDULE)) {
                Command<BookingList, Ui, BookingStorage> command = Parser.parse(userInput);
                return command.execute(bookingList, ui, bookingStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_FIND_BOOKING)) {
            if (userInput.trim().substring(0, 11).equals(COMMAND_FIND_BOOKING)) {
                Command<BookingList, Ui, BookingStorage> command = Parser.parse(userInput);
                return command.execute(bookingList, ui, bookingStorage);
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
                return arrayList;
            }
        } else if (userInput.contains(COMMAND_VIEW_ORDERS)) {
            if (userInput.trim().substring(0, 10).equals(COMMAND_VIEW_ORDERS)) {
                Command<BookingList, Ui, BookingStorage> command = Parser.parse(userInput);
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