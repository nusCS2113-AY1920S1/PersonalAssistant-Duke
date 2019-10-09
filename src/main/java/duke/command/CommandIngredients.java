package duke.command;

import duke.exception.DukeException;
import duke.ingredientlist.IngredientList;
import duke.storage.IngredientStorage;
import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

public abstract class CommandIngredients {
    protected String userInput;
    public CommandType commandType;

    public enum CommandType {
        BOOKING, RECIPE, INGREDIENT
    }

    public abstract ArrayList<String> feedback(IngredientList ingredientList, Ui ui, IngredientStorage ingredientStorage) throws DukeException, ParseException;

    public abstract boolean isExit();
}
