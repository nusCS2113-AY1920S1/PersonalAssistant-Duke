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
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static duke.common.BookingMessages.COMMAND_LIST_BOOKINGS;
import static duke.common.BookingMessages.COMMAND_ADD_BOOKING;
import static duke.common.BookingMessages.COMMAND_DELETE_BOOKING;
import static duke.common.BookingMessages.COMMAND_VIEW_BOOKING_SCHEDULE;
import static duke.common.BookingMessages.COMMAND_FIND_BOOKING;
import static duke.common.BookingMessages.COMMAND_VIEW_ORDERS;

import static duke.common.InventoryMessages.COMMAND_ADD_TO_INVENTORY;
import static duke.common.InventoryMessages.COMMAND_LIST_INVENTORY;
import static duke.common.InventoryMessages.COMMAND_DELETE_FROM_INVENTORY;
import static duke.common.InventoryMessages.COMMAND_CLEAR_INVENTORY;
import static duke.common.InventoryMessages.COMMAND_USE_RECIPE;

import static duke.common.Messages.ERROR_MESSAGE_RANDOM;

import static duke.common.RecipeMessages.COMMAND_ADD_RECIPE;
import static duke.common.RecipeMessages.COMMAND_DELETE_RECIPE;
import static duke.common.RecipeMessages.COMMAND_EDIT_REQ_INGREDIENT;
import static duke.common.RecipeMessages.COMMAND_EDIT_RATING;
import static duke.common.RecipeMessages.COMMAND_EDIT_FEEDBACK;
import static duke.common.RecipeMessages.COMMAND_EDIT_PREPSTEP;
import static duke.common.RecipeMessages.COMMAND_EDIT_PREPTIME;
import static duke.common.RecipeMessages.COMMAND_LIST_RECIPES;
import static duke.common.RecipeMessages.COMMAND_VIEW_RECIPE;
import static duke.common.RecipeMessages.COMMAND_VIEW_REQ_INGREDIENT;

/**
 * Duke processes different commands.
 */
public class Duke {

    private static final Logger logger = Logger.getLogger(Duke.class.getName());

    private Ui ui;

    private InventoryStorage inventoryStorage;
    private BookingStorage bookingStorage;
    private InventoryList inventoryList;
    private BookingList bookingList;

    private RecipeStorage recipeStorage;
    private RecipeList recipeList;

    private static void setupLogger() {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.INFO);

        try {
            FileHandler fh = new FileHandler("logFile.log",true);
            fh.setLevel(Level.WARNING);
            logger.addHandler(fh);
        } catch (java.io.IOException e) {
            logger.log(Level.SEVERE, "File logger is not working.", e);
        }
    }

    /**
     * Constructor for class Duke.
     *
     * @param ui deals with interactions with the user
     */
    public Duke(Ui ui) {
        this.ui = ui;
        String currentDir = System.getProperty("user.dir");
        String filePathInventory = currentDir + "\\data\\inventories.txt";
        String filePathBookings = currentDir + "\\data\\bookings.txt";
        String filePathRecipes = currentDir + "\\data\\recipes.txt";

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

    /**
     * Processes all the input from user.
     *
     * @param userInput string containing the input from the user
     */
    public ArrayList<String> runProgram(String userInput) throws ParseException {
        Duke.setupLogger();
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
        } else if (userInput.contains(COMMAND_ADD_TO_INVENTORY)) { // INVENTORY.
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
        } else if (userInput.trim().equals(COMMAND_LIST_BOOKINGS)) { // BOOKING.
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
            logger.warning("Wrong command entered!");
            arrayList.add(ERROR_MESSAGE_RANDOM);
            return arrayList;
        }
    }
}