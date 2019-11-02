package duke.command;

import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.list.GenericList;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Represents a specific {@link Command} used to find a String occurring in the {@link TaskList}.
 */
public class FindIngredientCommand extends Command<Ingredient> {

    private String toFind;

    public FindIngredientCommand(String toFind) {
        this.toFind = toFind;
    }



    @Override
    public void execute(GenericList<Ingredient> tasklist, Ui ui, Storage storage) throws DukeException {

    }
}