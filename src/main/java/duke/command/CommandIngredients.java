package duke.command;

import duke.exception.DukeException;
import duke.list.ingredientlist.IngredientList;
import duke.storage.IngredientStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

public abstract class CommandIngredients {
    protected String userInput;
    protected CommandType commandType;

    public enum CommandType {
        BOOKING, RECIPE, INGREDIENT
    }

    public abstract ArrayList<String> execute(IngredientList ingredientList, Ui ui, IngredientStorage ingredientStorage) throws DukeException, ParseException;

    public abstract boolean isExit();
}
