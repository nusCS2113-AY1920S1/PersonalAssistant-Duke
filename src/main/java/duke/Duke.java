package duke;

import duke.bookinglist.BookingList;
import duke.command.Command;
import duke.exception.DukeException;

import duke.parser.Parser;
import duke.storage.BookingStorage;

import duke.ui.Ui;

import java.text.ParseException;

import static duke.common.GeneralMessages.filePathIngredients;
import static duke.common.GeneralMessages.filePathBookings;
import static duke.common.GeneralMessages.filePathRecipes;

/**
 * Duke processes different commands.
 */
public class Duke {

    //private IngredientStorage ingredientStorage;
     private BookingStorage bookingStorage;
    // private RecipeStorage recipeStorage;

    // private IngredientList ingredientList;
     private BookingList bookingList;
    // private RecipeList recipeList;
    private Ui ui;

    /**
     * Constructor for Duke class to instantiation Ui, Storage, ingredientList classes.
     * @param filePathBookings String containing the directory in which the tasks are to be stored
     */
    public Duke(String filePathBookings) { // add filePathBookings and filePathRecipes parameters
        ui = new Ui();
        bookingStorage = new BookingStorage(filePathBookings);
        try {
            bookingList = new BookingList(bookingStorage.load());
            // bookingList = new BookingList(bookingStorage.load());
            // recipeList = new RecipeList(recipeStorage.load());
        } catch (DukeException e) {
            ui.showBookingLoadingError();
            bookingList = new BookingList();
        }
    }

    /**
     * Method to start the program.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(bookingList, ui, bookingStorage);
                isExit = c.isExit();
            } catch (DukeException | ParseException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Starting the program.
     * @param args the command line parameter
     */
    public static void main(String[] args) {
        new Duke(filePathBookings).run();
    }

}