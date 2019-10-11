package duke.command;

import duke.exception.DukeException;
import duke.list.ingredientlist.IngredientList;
import duke.storage.IngredientStorage;
import duke.ui.Ui;

import java.text.ParseException;

/**
 * Abstract class to represent command.
 */
public abstract class Command {
    protected String userInput;
    public CommandType commandType;

//    public abstract void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException, ParseException;
//    public abstract TaskList exe(Ui ui, Storage storage) throws DukeException, ParseException;
    public enum CommandType {
        BOOKING, RECIPE, INGREDIENT
    }

    public abstract void execute(IngredientList ingredientList, Ui ui, IngredientStorage ingredientStorage) throws DukeException, ParseException;

    public abstract boolean isExit();
}
