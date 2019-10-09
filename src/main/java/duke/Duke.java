package duke;

import duke.command.Command;
import duke.exception.DukeException;
import duke.ingredientlist.IngredientList;
import duke.parser.Parser;
import duke.storage.IngredientStorage;
import duke.ui.Ui;

import static duke.common.Messages.filePathIngredients;

import java.text.ParseException;

/**
 * Duke processes different commands.
 */
public class Duke {

    private IngredientStorage ingredientStorage;
    // private BookingStorage bookingStorage;
    // private RecipeStorage recipeStorage;
    private IngredientList ingredientList;
    // private BookingList bookingList;
    // private RecipeList recipeList;
    private Ui ui;

    /**
     * Constructor for Duke class to instantiation Ui, Storage, ingredientList classes.
     * @param filePathIngredients String containing the directory in which the tasks are to be stored
     */
    public Duke(String filePathIngredients) { // add filePathBookings and filePathRecipes parameters
        ui = new Ui();
        ingredientStorage = new IngredientStorage(filePathIngredients);
        try {
            ingredientList = new IngredientList(ingredientStorage.load());
        } catch (DukeException e) {
            ui.showIngredientLoadingError();
            ingredientList = new IngredientList();
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
                c.execute(ingredientList, ui, ingredientStorage);
                /*
                if (c.commandType.equals(Command.CommandType.INGREDIENT)) {
                    c.execute(ingredientList, ui, ingredientStorage);
                }
                */
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
        new Duke(filePathIngredients).run();
    }

    public String getResponse(String input) {
        return "Duke heard: " + input;
    }
}